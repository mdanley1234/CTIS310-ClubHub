package edu.guilford.gui.controllers;

import edu.guilford.gui.elements.ContentPane;
import edu.guilford.gui.elements.MenuPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main application scene.
 * <p>
 * Manages the layout of content and menu panes, as well as displaying profile information.
 */
public class MainSceneController {

    /** GridPane containing the primary content containers (ContentPane). */
    @FXML
    private GridPane mainGrid;

    /** VBox containing the vertical menu items (MenuPane). */
    @FXML
    private VBox menuGrid;

    /** Label displaying the user's school. */
    @FXML
    private Label schoolLabel;

    /** Label displaying the user's full name. */
    @FXML
    private Label studentNameLabel;

    /** Label displaying the user's student ID. */
    @FXML
    private Label studentIDLabel;

    /** Internal counter to track the next column for content pane insertion. */
    private int nextCol = 0;

    /** Internal counter to track the next row for content pane insertion. */
    private int nextRow = 0;

    /**
     * Updates the profile label section with user-specific details.
     *
     * @param school      the school name
     * @param studentName the student's full name
     * @param studentID   the student's ID number
     */
    public void setProfileLabels(String school, String studentName, String studentID) {
        schoolLabel.setText("School:  " + school);
        studentNameLabel.setText("Student Name:  " + studentName);
        studentIDLabel.setText("Student ID:  " + studentID);
    }

    /**
     * Adds a new content pane to the next available location in the main grid.
     * Automatically wraps to a new row when the column limit is reached.
     *
     * @param pane the content pane to add
     */
    public void addContentPane(ContentPane pane) {
        mainGrid.add(pane, nextCol, nextRow);

        nextCol++;
        if (nextCol >= mainGrid.getColumnCount()) {
            nextCol = 0;
            nextRow++;
        }
    }

    /**
     * Adds a new menu pane to the vertical menu grid.
     *
     * @param pane the menu pane to add
     */
    public void addMenuPane(MenuPane pane) {
        menuGrid.getChildren().add(pane);
    }

    /**
     * Clears all content panes from the main grid and resets position counters.
     */
    public void clearContentPanes() {
        mainGrid.getChildren().clear();
        nextRow = 0;
        nextCol = 0;
    }

    /**
     * Clears all menu panes from the vertical menu grid.
     */
    public void clearMenuPanes() {
        menuGrid.getChildren().clear();
    }
}
