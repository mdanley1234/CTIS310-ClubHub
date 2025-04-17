package edu.guilford.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ContentPaneController {

    @FXML
    private VBox contentBox;

    @FXML
    private Label contentHeader;

    public void setHeader(String header) {
        contentHeader.setText(header);
    }

    public void addContent(Node e) {
        contentBox.getChildren().add(e);
    }

}
