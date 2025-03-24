package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.gui.controllers.MenuPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MenuPane extends Pane {
    private MenuPaneController controller;

    public MenuPane() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_pane.fxml"));

        try {
            Pane pane = loader.load();
            this.getChildren().add(pane);
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
