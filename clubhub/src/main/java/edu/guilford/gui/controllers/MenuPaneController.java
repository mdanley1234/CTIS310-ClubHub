package edu.guilford.gui.controllers;

import edu.guilford.data.DataBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuPaneController {

    private DataBundle bundle;

    @FXML
    private Text clubLabel;

    public void setClubName(String clubName) {
        clubLabel.setText(clubName);
    }

    public void setBundle(DataBundle bundle) {
        this.bundle = bundle;
    }   

    @FXML
    public void buildBundle() {
        System.out.println("Building bundle: " + bundle.getClubName());
    }

}
