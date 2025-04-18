package edu.guilford.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller class for the content pane component.
 * <p>
 * Provides methods to update the content header and dynamically add UI components
 * to the content area.
 */
public class ContentPaneController {

    /** Container for dynamically injected content */
    @FXML
    private VBox contentBox;

    /** Label used for displaying the section header */
    @FXML
    private Label contentHeader;

    /**
     * Sets the header text displayed at the top of the content pane.
     *
     * @param header the string to display as the header
     */
    public void setHeader(String header) {
        contentHeader.setText(header);
    }

    /**
     * Adds a JavaFX Node to the content area of the pane.
     *
     * @param e the node (e.g., Label, Button, Pane) to add
     */
    public void addContent(Node e) {
        contentBox.getChildren().add(e);
    }
}
