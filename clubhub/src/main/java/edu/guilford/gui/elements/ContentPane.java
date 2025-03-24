package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.gui.controllers.ContentPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ContentPane extends Pane {
    private ContentPaneController controller;

    public ContentPane() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("content_pane.fxml"));

        try {
            ScrollPane pane = loader.load();
            this.getChildren().add(pane);
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
