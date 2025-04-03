package edu.guilford.gui.controllers;

import java.io.IOException;

import edu.guilford.gui.GUIManager;
import javafx.fxml.FXML;

public class SignupSceneController {

    // Button handling
    @FXML
    private void switchToLogin() throws IOException {
        GUIManager.loadLoginScene();
    }

}
