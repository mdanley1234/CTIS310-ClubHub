package edu.guilford.gui.controllers;

import java.io.IOException;

import edu.guilford.data.DataManager;
import edu.guilford.data.ProfilePacket;
import edu.guilford.gui.GUIManager;
import edu.guilford.supabase.SupabaseAuth;
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
        ProfilePacket loginPacket = new ProfilePacket(emailField.getText());
        
        if (SupabaseAuth.login(loginPacket, passwordField.getText())) {
            // Successful login

            // Populate global data
            DataManager.setProfilePacket(loginPacket);
            DataManager.buildClubList();

            GUIManager.loadMainScene();
        } else {
            failMessage.setOpacity(1);
        }
    }
}