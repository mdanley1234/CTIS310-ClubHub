package edu.guilford;

import java.io.IOException;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseAuth;
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

        // Signup a new user
        JSONObject newUser = SupabaseAuth.signUp(
                "student@university.edu",
                "securePassword123",
                "John Doe",
                12345,
                "2025",
                "2000-01-01",
                "555-123-4567",
                "123 College Ave",
                "Jane Doe",
                "555-987-6543"
        );

        if (newUser != null) {
            // Get the full profile
            // JSONObject profile = SupabaseAuth.getCurrentUserProfile();
            // System.out.println("Welcome " + profile.getString("full_name"));

            // Later login
            boolean success = SupabaseAuth.login("student@university.edu", "securePassword123");
            System.out.println(success);
        }
        else {
            System.out.println("Failed to make user");
        }

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
