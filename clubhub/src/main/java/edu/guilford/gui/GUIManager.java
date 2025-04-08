package edu.guilford.gui;

import java.io.IOException;

import edu.guilford.gui.scenes.LoginScene;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.gui.scenes.SignupScene;
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

        // Build 3 app scenes
        try {
            loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
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
    public static void loadLoginScene() {
        stage.setScene(loginScene);
    }

    public static void loadSignupScene() {
        stage.setScene(signupScene);
    }

    public static void loadMainScene() {
        mainScene.buildScene(); // Build the main scene
        stage.setScene(mainScene);
    }
}
