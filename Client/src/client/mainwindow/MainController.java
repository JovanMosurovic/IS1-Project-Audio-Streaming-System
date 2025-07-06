package client.mainwindow;

import client.service.HttpClientService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    private ComboBox<String> methodComboBox;
    @FXML
    private TextField urlTextField;
    @FXML
    private Button sendButton;
    @FXML
    private ComboBox<PresetRequest> presetComboBox;
    @FXML
    private TextArea requestBodyTextArea;
    @FXML
    private TextArea requestHeadersTextArea;
    @FXML
    private TextArea responseBodyTextArea;
    @FXML
    private TextArea responseHeadersTextArea;
    @FXML
    private Label statusLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label requestInfoLabel;
    @FXML
    private HBox filterContainer;
    @FXML
    private VBox responseContainer;
    @FXML
    private HBox statusBox;
    @FXML
    private Label createdByLabel;
    @FXML
    private TabPane responseTabPane;
    @FXML
    private Button clearButton;
    @FXML
    private Button formatButton;

    private HttpClientService httpService;
    private ObservableList<PresetRequest> allPresets;
    private String currentFilter = "ALL";

    private Button filterAll;
    private Button filterGet;
    private Button filterPost;
    private Button filterPut;
    private Button filterDelete;

    public static class PresetRequest {

        private final String name;
        private final String method;
        private final String url;
        private final String body;

        public PresetRequest(String name, String method, String url, String body) {
            this.name = name;
            this.method = method;
            this.url = url;
            this.body = body;
        }

        public String getName() {
            return name;
        }

        public String getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        public String getBody() {
            return body;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        httpService = new HttpClientService();

        setupMethodComboBox();
        setupDefaultValues();
        initializePresetRequests();
        initializeFilterButtons();
        setupEventHandlers();
        setupStyling();

        statusLabel.setText("Ready");
        timeLabel.setText("Ready to send request");
        updateMethodStyling();
    }

    private void setupMethodComboBox() {
        methodComboBox.setItems(FXCollections.observableArrayList("GET", "POST", "PUT", "DELETE"));
        methodComboBox.setValue("GET");
        methodComboBox.setOnAction(e -> updateMethodStyling());

        methodComboBox.getStyleClass().add("method-combo");
    }

    private void setupDefaultValues() {
        urlTextField.setText("http://localhost:8080/Server/api/");
        urlTextField.setPromptText("Enter API endpoint URL...");
    }

    private void setupEventHandlers() {
        sendButton.setOnAction(e -> sendRequest());
        clearButton.setOnAction(e -> clearAll());
        formatButton.setOnAction(e -> formatJson());
        presetComboBox.setOnAction(e -> loadPreset());
    }

    private void setupStyling() {
        if (responseContainer != null) {
            responseContainer.setAlignment(Pos.TOP_LEFT);
        }
        if (statusBox != null) {
            statusBox.setAlignment(Pos.CENTER);
        }
        if (createdByLabel != null) {
            createdByLabel.setText("IS1 Project Audio Streaming System Client v1.0.0 • © 2025 Jovan Mosurović\nSchool of Electrical Engineering, University of Belgrade");
        }
    }

    private void initializeFilterButtons() {
        if (filterContainer != null) {
            filterContainer.getChildren().clear();

            filterAll = createFilterButton("All", "ALL", "filter-all");
            filterGet = createFilterButton("GET", "GET", "filter-get");
            filterPost = createFilterButton("POST", "POST", "filter-post");
            filterPut = createFilterButton("PUT", "PUT", "filter-put");
            filterDelete = createFilterButton("DELETE", "DELETE", "filter-delete");

            filterAll.getStyleClass().add("active");
            filterContainer.getChildren().addAll(filterAll, filterGet, filterPost, filterPut, filterDelete);

            filterPresets(currentFilter);
        }
    }

    private Button createFilterButton(String text, String method, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().addAll("filter-button", styleClass);
        button.setOnAction(e -> filterPresets(method));
        return button;
    }

    private void filterPresets(String method) {
        currentFilter = method;

        filterAll.getStyleClass().remove("active");
        filterGet.getStyleClass().remove("active");
        filterPost.getStyleClass().remove("active");
        filterPut.getStyleClass().remove("active");
        filterDelete.getStyleClass().remove("active");

        switch (method) {
            case "ALL":
                filterAll.getStyleClass().add("active");
                break;
            case "GET":
                filterGet.getStyleClass().add("active");
                break;
            case "POST":
                filterPost.getStyleClass().add("active");
                break;
            case "PUT":
                filterPut.getStyleClass().add("active");
                break;
            case "DELETE":
                filterDelete.getStyleClass().add("active");
                break;
        }

        ObservableList<PresetRequest> filteredPresets;
        if (method.equals("ALL")) {
            filteredPresets = allPresets;
            presetComboBox.setPromptText("Select a preset request...");
        } else {
            filteredPresets = FXCollections.observableArrayList(
                    allPresets.stream()
                            .filter(preset -> preset.getMethod().equals(method))
                            .collect(Collectors.toList())
            );
            presetComboBox.setPromptText("Select " + method.toLowerCase() + " request...");
        }
        presetComboBox.setItems(filteredPresets);
        presetComboBox.setValue(null);
    }

    private void updateMethodStyling() {
        // Remove all method styles
        methodComboBox.getStyleClass().removeAll("method-get", "method-post", "method-put", "method-delete");
        sendButton.getStyleClass().removeAll("method-get", "method-post", "method-put", "method-delete");

        String method = methodComboBox.getValue();
        if (method != null) {
            String styleClass = "method-" + method.toLowerCase();
            methodComboBox.getStyleClass().add(styleClass);
            sendButton.getStyleClass().add(styleClass);
        }
    }

    private void initializePresetRequests() {
        allPresets = FXCollections.observableArrayList(
                new PresetRequest("Get All Mesta", "GET", "http://localhost:8080/Server/api/mesto", ""),
                new PresetRequest("Get Mesto by ID", "GET", "http://localhost:8080/Server/api/mesto/1", ""),
                new PresetRequest("Create Mesto", "POST", "http://localhost:8080/Server/api/mesto",
                        "{\n  \"naziv\": \"Novi Sad\"\n}"),
                new PresetRequest("Get All Korisnici", "GET", "http://localhost:8080/Server/api/korisnik", ""),
                new PresetRequest("Get Korisnik by ID", "GET", "http://localhost:8080/Server/api/korisnik/1", ""),
                new PresetRequest("Create Korisnik", "POST", "http://localhost:8080/Server/api/korisnik",
                        "{\n  \"ime\": \"Petar Petrovic\",\n  \"email\": \"petar@email.com\",\n  \"godiste\": 1992,\n  \"pol\": \"MUSKI\",\n  \"mestoId\": 1\n}"),
                new PresetRequest("Update Korisnik Email", "PUT", "http://localhost:8080/Server/api/korisnik/1/email",
                        "{\n  \"email\": \"marko.novi@email.com\"\n}"),
                new PresetRequest("Update Korisnik Mesto", "PUT", "http://localhost:8080/Server/api/korisnik/1/mesto",
                        "{\n  \"mestoId\": 2\n}"),
                new PresetRequest("Get All Kategorije", "GET", "http://localhost:8080/Server/api/kategorija", ""),
                new PresetRequest("Create Kategorija", "POST", "http://localhost:8080/Server/api/kategorija",
                        "{\n  \"naziv\": \"Elektronska muzika\"\n}"),
                new PresetRequest("Get All Audio", "GET", "http://localhost:8080/Server/api/audio", ""),
                new PresetRequest("Create Audio", "POST", "http://localhost:8080/Server/api/audio",
                        "{\n  \"naziv\": \"Moja nova pesma\",\n  \"trajanje\": 240,\n  \"vlasnikId\": 1\n}"),
                new PresetRequest("Update Audio Naziv", "PUT", "http://localhost:8080/Server/api/audio/1/naziv",
                        "{\n  \"naziv\": \"Moja pesma - nova verzija\",\n  \"vlasnikId\": 1\n}"),
                new PresetRequest("Add Kategorija to Audio", "POST", "http://localhost:8080/Server/api/audio/1/kategorija",
                        "{\n  \"kategorijaId\": 2\n}"),
                new PresetRequest("Get Kategorije for Audio", "GET", "http://localhost:8080/Server/api/audio/1/kategorije", ""),
                new PresetRequest("Delete Audio", "DELETE", "http://localhost:8080/Server/api/audio/1?vlasnikId=1", ""),
                new PresetRequest("Get All Paketi", "GET", "http://localhost:8080/Server/api/paket", ""),
                new PresetRequest("Create Paket", "POST", "http://localhost:8080/Server/api/paket",
                        "{\n  \"trenutnaCena\": 1299.99\n}"),
                new PresetRequest("Update Paket Cena", "PUT", "http://localhost:8080/Server/api/paket/1/cena",
                        "{\n  \"trenutnaCena\": 1099.99\n}"),
                new PresetRequest("Create Pretplata", "POST", "http://localhost:8080/Server/api/pretplata",
                        "{\n  \"korisnikId\": 4,\n  \"paketId\": 1,\n  \"placenaCena\": 999.99\n}"),
                new PresetRequest("Get Pretplate for Korisnik", "GET", "http://localhost:8080/Server/api/pretplata/korisnik/1", ""),
                new PresetRequest("Create Slusanje", "POST", "http://localhost:8080/Server/api/slusanje",
                        "{\n  \"korisnikId\": 1,\n  \"audioId\": 2,\n  \"pocetniSekund\": 0,\n  \"brojOdslusanihSekundi\": 1200\n}"),
                new PresetRequest("Get Slusanja for Audio", "GET", "http://localhost:8080/Server/api/slusanje/audio/2", ""),
                new PresetRequest("Add Omiljeni Audio", "POST", "http://localhost:8080/Server/api/omiljeni",
                        "{\n  \"korisnikId\": 1,\n  \"audioId\": 3\n}"),
                new PresetRequest("Get Omiljeni for Korisnik", "GET", "http://localhost:8080/Server/api/omiljeni/korisnik/1", ""),
                new PresetRequest("Create Ocena", "POST", "http://localhost:8080/Server/api/ocena",
                        "{\n  \"korisnikId\": 1,\n  \"audioId\": 3,\n  \"vrednost\": 5\n}"),
                new PresetRequest("Update Ocena", "PUT", "http://localhost:8080/Server/api/ocena/1",
                        "{\n  \"korisnikId\": 1,\n  \"vrednost\": 4\n}"),
                new PresetRequest("Delete Ocena", "DELETE", "http://localhost:8080/Server/api/ocena/1?korisnikId=1", ""),
                new PresetRequest("Get Ocene for Audio", "GET", "http://localhost:8080/Server/api/ocena/audio/2", "")
        );
        presetComboBox.setItems(allPresets);
    }

    @FXML
    private void loadPreset() {
        PresetRequest preset = presetComboBox.getValue();
        if (preset != null) {
            methodComboBox.setValue(preset.getMethod());
            urlTextField.setText(preset.getUrl());
            requestBodyTextArea.setText(preset.getBody());
            updateMethodStyling();
            updateRequestInfo(preset.getMethod(), preset.getUrl());
        }
    }

    @FXML
    private void clearAll() {
        methodComboBox.setValue("GET");
        urlTextField.setText("http://localhost:8080/Server/api/");
        requestBodyTextArea.clear();
        requestHeadersTextArea.clear();
        responseBodyTextArea.clear();
        responseHeadersTextArea.clear();
        statusLabel.setText("Cleared");
        statusLabel.getStyleClass().removeAll("status-success", "status-error", "status-warning");
        timeLabel.setText("Ready to send request");
        updateRequestInfo("GET", "http://localhost:8080/Server/api/");
        updateMethodStyling();
        presetComboBox.setValue(null);
    }

    @FXML
    private void formatJson() {
        String json = requestBodyTextArea.getText();
        if (json != null && !json.trim().isEmpty()) {
            String formatted = formatJsonString(json);
            requestBodyTextArea.setText(formatted);
        }
    }

    @FXML
    private void sendRequest() {
        String method = methodComboBox.getValue();
        String url = urlTextField.getText();
        String requestBody = requestBodyTextArea.getText();
        String requestHeaders = requestHeadersTextArea.getText();

        if (url == null || url.trim().isEmpty()) {
            showStyledAlert("Error", "Please enter a URL");
            return;
        }

        sendButton.setDisable(true);
        statusLabel.setText("Sending request...");
        statusLabel.getStyleClass().removeAll("status-success", "status-error", "status-warning");
        timeLabel.setText("Request in progress...");

        updateRequestInfo(method, url);

        Map<String, String> headers = parseHeaders(requestHeaders);

        Task<HttpClientService.HttpResponse> task = new Task<HttpClientService.HttpResponse>() {
            @Override
            protected HttpClientService.HttpResponse call() throws Exception {
                long startTime = System.currentTimeMillis();
                HttpClientService.HttpResponse response = httpService.sendRequest(method, url, requestBody, headers);
                long endTime = System.currentTimeMillis();

                Platform.runLater(() -> timeLabel.setText("Completed in " + (endTime - startTime) + " ms"));

                return response;
            }
        };

        task.setOnSucceeded(e -> {
            HttpClientService.HttpResponse response = task.getValue();
            Platform.runLater(() -> {
                statusLabel.setText("Status: " + response.getStatusCode() + " " + getStatusText(response.getStatusCode()));
                updateStatusStyling(response.getStatusCode());
                String responseBody = response.getResponseBody();
                
                // If not JSON, present plain text
                if (responseBody != null && !responseBody.trim().isEmpty()) {
                    responseBodyTextArea.setText(formatJsonString(responseBody));
                } else {
                    responseBodyTextArea.setText("No response body");
                }

                StringBuilder headersText = new StringBuilder();
                if (response.getHeaders() != null) {
                    for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                        headersText.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
                    }
                }
                responseHeadersTextArea.setText(headersText.toString());

                sendButton.setDisable(false);
                sendButton.setText("Send");

                // Switch to response body tab
                responseTabPane.getSelectionModel().select(0);
            });
        });

        task.setOnFailed(e -> {
            Platform.runLater(() -> {
                statusLabel.setText("Request failed");
                statusLabel.getStyleClass().removeAll("status-success", "status-warning");
                statusLabel.getStyleClass().add("status-error");
                responseBodyTextArea.setText("Error: " + task.getException().getMessage());
                sendButton.setDisable(false);
                sendButton.setText("Send");
            });
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void updateStatusStyling(int statusCode) {
        statusLabel.getStyleClass().removeAll("status-success", "status-error", "status-warning");
        if (statusCode >= 200 && statusCode < 300) {
            statusLabel.getStyleClass().add("status-success");
        } else if (statusCode >= 400 && statusCode < 500) {
            statusLabel.getStyleClass().add("status-warning");
        } else if (statusCode >= 500) {
            statusLabel.getStyleClass().add("status-error");
        }
    }

    private void updateRequestInfo(String method, String url) {
        if (requestInfoLabel != null) {
            StringBuilder info = new StringBuilder();
            info.append("Method: ").append(method).append("\n");
            info.append("URL: ").append(url).append("\n");
            info.append("Time: ").append(java.time.LocalDateTime.now().toString()).append("\n");
            info.append("Client: IS1 Project Audio Streaming System Client v1.0.0");
            requestInfoLabel.setText(info.toString());
        }
    }

    private Map<String, String> parseHeaders(String headersText) {
        Map<String, String> headers = new HashMap<>();
        if (headersText != null && !headersText.trim().isEmpty()) {
            String[] lines = headersText.split("\n");
            for (String line : lines) {
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        headers.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }
        return headers;
    }

    private String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Not Found";
            case 409:
                return "Conflict";
            case 500:
                return "Internal Server Error";
            default:
                return "Unknown";
        }
    }

    private String formatJsonString(String json) {
        if (json == null || json.trim().isEmpty()) {
            return json;
        }

        String trimmed = json.trim();

        if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
            return json;
        }

        try {
            StringBuilder formatted = new StringBuilder();
            int indentLevel = 0;
            boolean inString = false;
            char prev = 0;

            for (char c : json.toCharArray()) {
                if (c == '"' && prev != '\\') {
                    inString = !inString;
                }

                if (!inString) {
                    if (c == '{' || c == '[') {
                        formatted.append(c).append('\n');
                        indentLevel++;
                        appendIndent(formatted, indentLevel);
                    } else if (c == '}' || c == ']') {
                        formatted.append('\n');
                        indentLevel--;
                        appendIndent(formatted, indentLevel);
                        formatted.append(c);
                    } else if (c == ',') {
                        formatted.append(c).append('\n');
                        appendIndent(formatted, indentLevel);
                    } else if (c == ':') {
                        formatted.append(c).append(' ');
                    } else if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                        formatted.append(c);
                    }
                } else {
                    formatted.append(c);
                }
                prev = c;
            }
            return formatted.toString();
        } catch (Exception e) {
            return json;
        }
    }

    private void appendIndent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
    }

    private void showStyledAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            dialogPane.getStyleClass().add("root");
        } catch (Exception e) {
            // Ignore styling errors
        }
        alert.showAndWait();
    }
}
