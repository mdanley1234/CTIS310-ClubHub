package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.gui.controllers.ContentPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ContentPane extends Pane {

    // ContentPane sizes (Vertical height)
    public static final double SMALL = 200;
    public static final double MEDIUM = 280;
    public static final double LARGE = 350;
    public static final double XLARGE = 450;
    private static final double WIDTH = 500;

    // ContentPane attributes
    private ContentPaneController controller;

    public ContentPane(double paneSize) {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("content_pane.fxml"));
        this.setHeight(paneSize);
        this.setWidth(WIDTH);

        try {
            ScrollPane pane = loader.load();
            this.getChildren().add(pane);
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
