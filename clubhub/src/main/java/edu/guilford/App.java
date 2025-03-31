package edu.guilford;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONObject;

import edu.guilford.supabase.SupabaseAuth;
import edu.guilford.supabase.SupabaseQuery;
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

        // Test data
        String testEmail = "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        String testPassword = "securePassword123";
        String testFullName = "Test User";
        int testClassYear = 2025;
        String testPhone = "555-123-4567";

        // 1. Test Signup
        System.out.println("\n--- Testing Signup ---");
        JSONObject newUser = SupabaseAuth.signUp(
                testEmail,
                testPassword,
                testFullName,
                testClassYear,
                testPhone
        );

        if (newUser != null) {
            System.out.println("✅ Signup Successful");
            System.out.println("User ID: " + SupabaseAuth.getUserId());
            System.out.println("Auth Token: " + SupabaseAuth.getAuthToken().substring(0, 20) + "...");

            // 2. Verify Profile Creation
            System.out.println("\n--- Verifying Profile ---");
            JSONObject profile = SupabaseQuery.getById("users", SupabaseAuth.getUserId());

            if (profile != null) {
                System.out.println("✅ Profile Created Successfully");
                System.out.println("Profile Data:");
                System.out.println("Email: " + profile.getString("email"));
                System.out.println("Full Name: " + profile.getString("full_name"));
                System.out.println("Class Year: " + profile.getInt("class_year"));
                System.out.println("Phone: " + profile.getString("phone_number"));
            } else {
                System.out.println("❌ Profile Creation Failed");
            }

            // 3. Test Login
            System.out.println("\n--- Testing Login ---");
            SupabaseAuth.logout(); // Clear previous session

            boolean loginSuccess = SupabaseAuth.login(testEmail, testPassword);
            if (loginSuccess) {
                System.out.println("✅ Login Successful");
                System.out.println("Current User ID: " + SupabaseAuth.getUserId());
            } else {
                System.out.println("❌ Login Failed");
            }

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
