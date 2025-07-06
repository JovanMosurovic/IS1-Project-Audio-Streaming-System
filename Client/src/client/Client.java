package client;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            URL resource = getClass().getResource("mainwindow/MainView.fxml");
            if (resource == null) {
                System.out.println("FXML Resource not found!");
                System.exit(-5);
            } else {
                System.out.println("FXML Resource found: " + resource.toString());
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(getClass().getResource("/client/mainwindow/styles.css").toExternalForm());

            primaryStage.setTitle("Audio Streaming System - API Client");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);

            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
