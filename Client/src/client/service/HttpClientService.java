package client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientService {
    
    public static class HttpResponse {
        private final int statusCode;
        private final String responseBody;
        private final Map<String, String> headers;
        
        public HttpResponse(int statusCode, String responseBody, Map<String, String> headers) {
            this.statusCode = statusCode;
            this.responseBody = responseBody;
            this.headers = headers;
        }
        
        public int getStatusCode() { return statusCode; }
        public String getResponseBody() { return responseBody; }
        public Map<String, String> getHeaders() { return headers; }
    }
    
    public HttpResponse sendRequest(String method, String urlString, String requestBody, Map<String, String> headers) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method
            connection.setRequestMethod(method);
            
            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            
            // Set request body for POST, PUT, PATCH
            if (requestBody != null && !requestBody.trim().isEmpty() && 
                (method.equals("POST") || method.equals("PUT") || method.equals("PATCH"))) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            // Read response body
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    responseCode >= 200 && responseCode < 300 ? 
                    connection.getInputStream() : connection.getErrorStream(),
                    StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }
            }
            
            // Get response headers
            Map<String, String> responseHeaders = connection.getHeaderFields().entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .collect(java.util.stream.Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> String.join(", ", entry.getValue())
                ));
            
            return new HttpResponse(responseCode, response.toString(), responseHeaders);
            
        } catch (IOException e) {
            e.printStackTrace();
            return new HttpResponse(-1, "Error: " + e.getMessage(), null);
        }
    }
}