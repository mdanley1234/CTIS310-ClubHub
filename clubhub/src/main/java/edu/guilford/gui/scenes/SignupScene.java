package edu.guilford.gui.scenes;

import java.io.IOException;

import edu.guilford.gui.controllers.SignupSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The {@code SignupScene} class represents the scene where users can sign up for an account.
 * It loads the FXML layout and initializes the associated controller for the scene.
 */
public class SignupScene extends Scene {
    
    /** The controller for the signup scene. */
    private static SignupSceneController controller;

    /**
     * Constructs a new {@code SignupScene} with the specified width and height.
     * This constructor calls the superclass {@link Scene} constructor and loads the FXML layout for the scene.
     *
     * @param WINDOW_WIDTH the width of the window
     * @param WINDOW_HEIGHT the height of the window
     * @throws IOException if an error occurs while loading the FXML layout
     */
    public SignupScene(double WINDOW_WIDTH, double WINDOW_HEIGHT) throws IOException {
        super(loadRoot(), WINDOW_WIDTH, WINDOW_HEIGHT); // Call super first
    }

    /**
     * Loads the root element for the signup scene from the FXML file.
     * It also sets the controller for the scene.
     *
     * @return the root element for the signup scene
     * @throws IOException if an error occurs while loading the FXML file
     */
    private static Parent loadRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(SignupScene.class.getResource("signup_scene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        return root;
    }
}
