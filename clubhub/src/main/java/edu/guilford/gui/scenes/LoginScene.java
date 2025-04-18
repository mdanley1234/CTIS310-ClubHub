package edu.guilford.gui.scenes;

import java.io.IOException;

import edu.guilford.gui.controllers.LoginSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The {@code LoginScene} class represents the login scene in the GUI application.
 * It is responsible for loading and displaying the login screen where users can input their credentials.
 */
public class LoginScene extends Scene {

    /** The controller for the login scene. */
    private static LoginSceneController controller;

    /**
     * Constructs a new {@code LoginScene} with the specified width and height.
     * This constructor calls the superclass {@link Scene} constructor and loads the FXML layout for the scene.
     *
     * @param WINDOW_WIDTH the width of the window
     * @param WINDOW_HEIGHT the height of the window
     * @throws IOException if an error occurs while loading the FXML layout
     */
    public LoginScene(double WINDOW_WIDTH, double WINDOW_HEIGHT) throws IOException {
        super(loadRoot(), WINDOW_WIDTH, WINDOW_HEIGHT); // Call super first
    }

    /**
     * Loads the root element for the login scene from the FXML file.
     * It also sets the controller for the scene.
     *
     * @return the root element for the login scene
     * @throws IOException if an error occurs while loading the FXML file
     */
    private static Parent loadRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginScene.class.getResource("login_scene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        return root;
    }
}
