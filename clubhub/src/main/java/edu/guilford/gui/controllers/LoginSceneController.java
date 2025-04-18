package edu.guilford.gui.controllers;

import java.io.IOException;

import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.gui.GUIManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller class for handling user authentication on the login screen.
 * <p>
 * Provides functionality to switch to the signup screen and process login attempts
 * using Supabase authentication.
 */
public class LoginSceneController {
    
    /** Input field for the user's email address. */
    @FXML
    private TextField emailField;

    /** Input field for the user's password. */
    @FXML
    private TextField passwordField;

    /** Text element to display login failure messages. */
    @FXML
    private Text failMessage;

    /**
     * Navigates the user to the signup scene.
     *
     * @throws IOException if loading the signup scene fails
     */
    @FXML
    private void switchToSignup() throws IOException {
        GUIManager.loadSignupScene();
    }

    /**
     * Handles the login action when the user submits their credentials.
     * If successful, navigates to the main application scene. Otherwise, 
     * displays an error message.
     *
     * @throws IOException if loading the main scene fails
     */
    @FXML
    private void handleLogin() throws IOException {
        // Create a login profile packet using the entered email
        ProfilePacket loginPacket = new ProfilePacket(emailField.getText());

        // Attempt login using the entered password
        if (loginPacket.login(passwordField.getText())) {
            // SUCCESSFUL LOGIN
            GUIManager.loadMainScene();
        } else {
            // LOGIN FAILED – show error message
            failMessage.setOpacity(1);
        }
    }
}
