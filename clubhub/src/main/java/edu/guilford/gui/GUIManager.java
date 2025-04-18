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
 * The {@code GUIManager} class is responsible for managing and switching between different scenes in the application.
 * It handles the initialization and display of the login, signup, and main scenes.
 */
public class GUIManager {

    // Attribute objects for the scenes
    private static Stage stage;                // The main application stage
    private static LoginScene loginScene;      // Login scene
    private static SignupScene signupScene;    // Signup scene
    private static MainScene mainScene;        // Main scene

    /**
     * Constructs a new {@code GUIManager} and initializes the scenes.
     *
     * @param stage the main stage of the application
     * @param WINDOW_WIDTH the width of the application window
     * @param WINDOW_HEIGHT the height of the application window
     */
    public GUIManager(Stage stage, double WINDOW_WIDTH, double WINDOW_HEIGHT) {
        GUIManager.stage = stage;

        // Initialize the application scenes
        try {
            loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            System.err.println("Error: Unable to create one of the application screens.");
            e.printStackTrace();
            System.exit(1);
        }

        // Load the login scene initially
        loadLoginScene();

        // Set up the GUI window title and icon
        stage.setTitle("ClubHub");
        Image icon = new Image(getClass().getResourceAsStream("ClubHubLogo.jpg"));
        stage.getIcons().add(icon);
        stage.show();
    }

    // Static methods for loading different scenes

    /**
     * Switches the scene to the login screen.
     */
    public static void loadLoginScene() {
        stage.setScene(loginScene);
    }

    /**
     * Switches the scene to the signup screen.
     */
    public static void loadSignupScene() {
        stage.setScene(signupScene);
    }

    /**
     * Builds and switches to the main scene. It ensures that the user is logged in and data is fetched before loading.
     */
    public static void loadMainScene() {

        // Check if the user is logged in
        if (SupabaseAuth.getUserId() == null) {
            System.err.println("Error: User is not logged in. Cannot load main scene.");
            return;
        }

        // Fetch necessary data from Supabase (e.g., user-specific bundles)
        DataManager.initDataManager(SupabaseAuth.getUserId()); // Initialize the data manager with the user ID

        // Build and switch to the main scene
        mainScene.buildMain(); // Build the main scene with user data
        stage.setScene(mainScene);
    }
}
