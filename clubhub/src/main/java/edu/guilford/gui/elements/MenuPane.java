package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.data.DataBundle;
import edu.guilford.gui.controllers.MenuPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * The {@code MenuPane} is a reusable UI component representing a clickable club menu item.
 * <p>
 * It loads the corresponding FXML layout and binds a {@link DataBundle} to the associated
 * {@link MenuPaneController}.
 */
public class MenuPane extends Pane {

    /** The controller associated with this menu pane. */
    private MenuPaneController controller;

    /**
     * Constructs a new {@code MenuPane} using the provided {@link DataBundle}.
     * Loads the FXML layout and initializes the UI elements and controller state.
     *
     * @param bundle the data bundle representing the club to associate with this menu item
     */
    public MenuPane(DataBundle bundle) {
        super();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_pane.fxml"));

        try {
            // Load the FXML layout and add it to the pane
            Pane pane = loader.load();
            this.getChildren().add(pane);

            // Initialize the controller and set the bundle
            controller = loader.getController();
            controller.setBundle(bundle);
            controller.setClubName(bundle.getClubName());

        } catch (IOException e) {
            // Error handling if the FXML layout fails to load
            System.err.println("Error: Unable to load menu pane.");
            System.exit(1);
        }
    }
}
