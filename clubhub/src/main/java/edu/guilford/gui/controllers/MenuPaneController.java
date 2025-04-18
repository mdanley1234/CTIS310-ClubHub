package edu.guilford.gui.controllers;

import edu.guilford.data.DataBundle;
import edu.guilford.gui.scenes.MainScene;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controller class for the menu pane in the main scene.
 * <p>
 * Responsible for displaying the selected club name and
 * triggering the loading of its associated {@link DataBundle}.
 */
public class MenuPaneController {

    /** The {@link DataBundle} associated with the selected club. */
    private DataBundle bundle;

    /** The label displaying the club name in the UI. */
    @FXML
    private Text clubLabel;

    @FXML
    private StackPane backPanel; 

    /**
     * Sets the club name in the menu pane UI.
     *
     * @param clubName The name of the club to display
     */
    public void setClubName(String clubName) {
        clubLabel.setText(clubName);

        // Special methods for special bundles (Dashboard | Special Bundle - 0) (Directory | Special Bundle - 1)
        if ("Dashboard".equals(clubName)) {
            backPanel.setStyle("-fx-background-color: orange; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: black; -fx-border-width: 2;");
        } else if ("Directory".equals(clubName)) {
            backPanel.setStyle("-fx-background-color: blue; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: black; -fx-border-width: 2;");
        } 
    }

    /**
     * Assigns the {@link DataBundle} to be used when loading content.
     *
     * @param bundle The DataBundle representing the club's content
     */
    public void setBundle(DataBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Loads the assigned {@link DataBundle} into the main scene.
     * Triggered by a user interaction (e.g. button click).
     */
    @FXML
    public void loadBundle() {
        MainScene.loadBundle(bundle);
    }
}