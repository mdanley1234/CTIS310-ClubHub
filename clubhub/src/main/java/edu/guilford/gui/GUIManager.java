package edu.guilford.gui;

import java.io.IOException;

import edu.guilford.data.DataManager;
import edu.guilford.gui.scenes.LoginScene;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.gui.scenes.SignupScene;
import edu.guilford.supabase.SupabaseAuth;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The GUI Manager builds all GUI functionality.
 */
public class GUIManager {

    // Attribute objects
    private static Stage stage;                // The stage
    private static LoginScene loginScene;      // Login scene
    private static SignupScene signupScene;    // Signup scene
    private static MainScene mainScene;        // Main scene

    public GUIManager(Stage stage, double WINDOW_WIDTH, double WINDOW_HEIGHT) {
        GUIManager.stage = stage;

        // Build 3 application scenes
        try {
            loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            System.err.println("Error: Unable to create one of the application screens.");
            e.printStackTrace();
            System.exit(1);
        }

        // Load login scene
        loadLoginScene();

        // Finish setting up GUI stage object
        stage.setTitle("ClubHub");
        Image icon = new Image(getClass().getResourceAsStream("ClubHubLogo.jpg"));
        stage.getIcons().add(icon);
        stage.show();
    }

    // Static methods to load scenes

    // Switch to login scene
    public static void loadLoginScene() {
        stage.setScene(loginScene);
    }

    // Switch to signup scene
    public static void loadSignupScene() {
        stage.setScene(signupScene);
    }

    // Build and switch to main scene (ENTRY POINT)
    public static void loadMainScene() {

        // Check that user is logged in
        if (SupabaseAuth.getUserId() == null) {
            System.err.println("Error: User is not logged in. Cannot load main scene.");
            return;
        }

        // Pull necessary data from Supabase (Fetch Bundles)
        DataManager.initDataManager(SupabaseAuth.getUserId()); // Initialize the data manager with the user ID

        // Construct and switch to mainScene
        mainScene.buildMain(); // Build the main scene
        stage.setScene(mainScene);
    }
}
