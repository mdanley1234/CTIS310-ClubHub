package edu.guilford.gui.scenes;

import java.io.IOException;

import edu.guilford.gui.controllers.SignupSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SignupScene extends Scene {
    private static SignupSceneController controller;

    public SignupScene(double WINDOW_WIDTH, double WINDOW_HEIGHT) throws IOException {
        super(loadRoot(), WINDOW_WIDTH, WINDOW_HEIGHT); // Call super first
    }

    private static Parent loadRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(SignupScene.class.getResource("signup_scene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        return root;
    }
}
