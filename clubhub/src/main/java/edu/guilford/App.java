package edu.guilford;

import java.io.IOException;

import edu.guilford.data.ProfilePacket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    // Set application dimensions
    public static final double WINDOW_HEIGHT = 720;
    public static final double WINDOW_WIDTH = 1280;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // GUIManager guiManager = new GUIManager(stage, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Supabase testing
        System.out.println("=== Starting Supabase Auth Test ===");

        ProfilePacket testPacket = new ProfilePacket(
            "test@example.com",
            123456,
            "John",
            "Doe",
            "01192007",
            2024,
            "336-345-9256",
            "123 Test Street, Test City, TS 12345"
        );

        System.out.println("ProfilePacket created: " + testPacket);

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch();
    }
}
