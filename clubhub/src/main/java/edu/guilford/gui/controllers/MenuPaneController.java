package edu.guilford.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuPaneController {


    @FXML
    private Text clubLabel;

    public void setClubName(String clubName) {
        clubLabel.setText(clubName);
    }

}
