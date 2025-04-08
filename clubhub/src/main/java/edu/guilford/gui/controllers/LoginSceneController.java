package edu.guilford.gui.controllers;

import java.io.IOException;

import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.gui.GUIManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * The LoginSceneController handles user authentication via Supabase.
 * It manages user login attempts and scene transitions.
 */
public class LoginSceneController {
    
    /** Text field for user email input. */
    @FXML
    private TextField emailField;

    /** Text field for user password input. */
    @FXML
    private TextField passwordField;

    /** Displays login failure messages. */
    @FXML
    private Text failMessage;

    /**
     * Switches the scene to the signup screen.
     * 
     * @throws IOException if the scene transition fails.
     */
    @FXML
    private void switchToSignup() throws IOException {
        GUIManager.loadSignupScene();
    }

    /**
     * Handles the login attempt using credentials entered in the text fields.
     * If authentication is successful, transitions to the main scene.
     * Otherwise, displays a failure message.
     * 
     * @throws IOException if the scene transition fails.
     */
    @FXML
    private void handleLogin() throws IOException {
        // Build login packet
        ProfilePacket loginPacket = new ProfilePacket(emailField.getText());
        
        // Attempt login
        if (loginPacket.login(passwordField.getText())) {
            // SUCCESSFUL LOGIN
            GUIManager.loadMainScene();
        } else {
            // UNSUCCESSFUL LOGIN
            failMessage.setOpacity(1);
        }
    }
}