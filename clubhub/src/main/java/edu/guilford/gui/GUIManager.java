package edu.guilford.gui;

import java.io.IOException;

import edu.guilford.gui.scenes.LoginScene;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.gui.scenes.SignupScene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUIManager {

    // Attribute objects
    private static Stage stage;                // The stage
    private static LoginScene loginScene;      // Login scene
    private static SignupScene signupScene;    // Signup scene
    private static MainScene mainScene;        // Main scene

    public GUIManager(Stage stage, double WINDOW_WIDTH, double WINDOW_HEIGHT) {
        this.stage = stage;

        try {
            loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the stage TODO: CHANGE TO LOGIN SCENE
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
        stage.setScene(mainScene);
    }
}
