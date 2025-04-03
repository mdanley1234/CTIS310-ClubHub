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
 * The SignupSceneController handles user registration and authentication.
 * It collects user input and communicates with the authentication system.
 */
public class SignupSceneController {

    /** Text field for user email input. */
    @FXML
    private TextField emailField;

    /** Text field for student ID input. */
    @FXML
    private TextField studentIdField;

    /** Text field for user's first name. */
    @FXML
    private TextField firstNameField;

    /** Text field for user's last name. */
    @FXML
    private TextField lastNameField;

    /** Text field for user's date of birth. */
    @FXML
    private TextField dateOfBirthField;

    /** Text field for user's graduation year. */
    @FXML
    private TextField graduationYearField;

    /** Text field for user's phone number. */
    @FXML
    private TextField phoneNumberField;

    /** Text field for user's address. */
    @FXML
    private TextField addressField;

    /** Text field for user password input. */
    @FXML
    private TextField passwordField;

    /** Displays signup failure messages. */
    @FXML
    private Text failMessage;

    /**
     * Switches the scene to the login screen.
     * 
     * @throws IOException if the scene transition fails.
     */
    @FXML
    private void switchToLogin() throws IOException {
        GUIManager.loadLoginScene();
    }

    /**
     * Handles the signup process by creating a new user profile.
     * If signup is successful, the user is automatically logged in and redirected to the main scene.
     * Otherwise, an error message is displayed.
     * 
     * @throws IOException if the scene transition fails.
     */
    @FXML
    private void handleSignup() throws IOException {

        // Check that all fields are not null or empty
        if (emailField.getText().isEmpty() || 
            studentIdField.getText().isEmpty() || 
            firstNameField.getText().isEmpty() || 
            lastNameField.getText().isEmpty() || 
            dateOfBirthField.getText().isEmpty() || 
            graduationYearField.getText().isEmpty() || 
            phoneNumberField.getText().isEmpty() || 
            addressField.getText().isEmpty() || 
            passwordField.getText().isEmpty()) {
            failMessage.setText("All fields must be filled out.");
            failMessage.setOpacity(1);
            return;
        }

        try {
            ProfilePacket signupPacket = new ProfilePacket(
                emailField.getText(),
                Integer.parseInt(studentIdField.getText()), // Convert to int
                firstNameField.getText(),
                lastNameField.getText(),
                dateOfBirthField.getText(),
                Integer.parseInt(graduationYearField.getText()), // Convert to int
                phoneNumberField.getText(),
                addressField.getText()
            );

            if (SupabaseAuth.signUp(signupPacket, passwordField.getText())) {
                if (SupabaseAuth.login(signupPacket, passwordField.getText())) {
                    // Successful signup and login
                    DataManager.setProfilePacket(signupPacket);
                    GUIManager.loadMainScene();
                } else {
                    failMessage.setOpacity(1);
                    failMessage.setText("Signup attempt was unsuccessful.");
                }
            } else {
                failMessage.setOpacity(1);
                failMessage.setText("Signup attempt was unsuccessful.");
            }
        } catch (NumberFormatException e) {
            failMessage.setText("Invalid input for Student ID or Graduation Year.");
            failMessage.setOpacity(1);
        }
    }
}