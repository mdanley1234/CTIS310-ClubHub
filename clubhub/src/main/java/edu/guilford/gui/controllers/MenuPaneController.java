package edu.guilford.gui.controllers;

import edu.guilford.data.DataBundle;
import edu.guilford.gui.scenes.MainScene;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controller class for each menu pane item in the main scene.
 * <p>
 * Handles UI updates for the club name and styling, and triggers loading of
 * the associated {@link DataBundle} when selected.
 */
public class MenuPaneController {

    /** The {@link DataBundle} associated with this menu item. */
    private DataBundle bundle;

    /** Text label displaying the club name. */
    @FXML
    private Text clubLabel;

    /** Background panel for the menu item (used for styling). */
    @FXML
    private StackPane backPanel;

    /**
     * Sets the visible name of the club and applies special styling for
     * reserved bundle names (e.g., Dashboard, Directory).
     *
     * @param clubName the name of the club
     */
    public void setClubName(String clubName) {
        clubLabel.setText(clubName);

        // Apply color-coding for special bundles
        if ("Dashboard".equals(clubName)) {
            backPanel.setStyle("-fx-background-color: orange; " +
                               "-fx-background-radius: 10; " +
                               "-fx-border-radius: 10; " +
                               "-fx-border-color: black; " +
                               "-fx-border-width: 2;");
        } else if ("Directory".equals(clubName)) {
            backPanel.setStyle("-fx-background-color: blue; " +
                               "-fx-background-radius: 10; " +
                               "-fx-border-radius: 10; " +
                               "-fx-border-color: black; " +
                               "-fx-border-width: 2;");
        }
    }

    /**
     * Assigns the {@link DataBundle} for this menu item.
     *
     * @param bundle the data bundle to associate
     */
    public void setBundle(DataBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Loads the associated {@link DataBundle} into the main scene.
     * Triggered by user interaction (e.g., clicking the menu item).
     */
    @FXML
    public void loadBundle() {
        MainScene.loadBundle(bundle);
    }
}
