package edu.guilford.gui;

import java.io.IOException;

import edu.guilford.gui.scenes.LoginScene;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.gui.scenes.SignupScene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUIManager {

    // Attribute objects
    private Stage stage;                // The stage
    private LoginScene loginScene;      // Login scene
    private SignupScene signupScene;    // Signup scene
    private MainScene mainScene;        // Main scene

    public GUIManager(Stage stage, double WINDOW_WIDTH, double WINDOW_HEIGHT) {
        this.stage = stage;

        try {
            // loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            // signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the stage
        stage.setScene(mainScene);

        // Finish setting up GUI stage object
        stage.setTitle("ClubHub");
        Image icon = new Image(getClass().getResourceAsStream("ClubHubLogo.jpg"));
        stage.getIcons().add(icon);
        stage.show();
    }
    
}
