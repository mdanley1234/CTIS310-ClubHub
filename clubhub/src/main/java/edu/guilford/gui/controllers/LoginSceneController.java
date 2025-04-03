package edu.guilford.gui.controllers;

import java.io.IOException;

import edu.guilford.gui.GUIManager;
import javafx.fxml.FXML;

// Login scene controller
public class LoginSceneController {
    
    // Button handling
    @FXML
    private void switchToSignup() throws IOException {
        GUIManager.loadSignupScene();
    }

}
