package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.data.DataBundle;
import edu.guilford.gui.controllers.MenuPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MenuPane extends Pane {

    // MenuPane sizes
    private static final double HEIGHT = 65;
    private static final double WIDTH = 150;

    // MenuPane attributes
    private MenuPaneController controller;
    private DataBundle bundle;

    public MenuPane(DataBundle bundle) {
        super();

        this.bundle = bundle;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_pane.fxml"));
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        try {
            Pane pane = loader.load();
            this.getChildren().add(pane);
            controller = loader.getController();
            controller.setBundle(bundle);
            controller.setClubName(bundle.getClubName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
