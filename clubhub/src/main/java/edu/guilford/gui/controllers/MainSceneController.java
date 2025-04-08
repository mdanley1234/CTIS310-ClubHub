package edu.guilford.gui.controllers;

import edu.guilford.gui.elements.ContentPane;
import edu.guilford.gui.elements.MenuPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

// Main view controller
public class MainSceneController {

    // mainGrid contains ContentPane objects
    @FXML
    private GridPane mainGrid;

    // menuGrid contains MenuPane objects
    @FXML
    private VBox menuGrid;

    // Profile Information

    @FXML
    private Label schoolLabel;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label studentIDLabel;

    // Set Header Labels
    public void setHeaderLabels(String school, String studentName, String studentID) {
        schoolLabel.setText("School:  " + school);
        studentNameLabel.setText("Student Name:  " + studentName);
        studentIDLabel.setText("Student ID:  " + studentID);
    }

    // Method to add containers to the mainGrid (GridPane) automatically at the next location
    private int nextRow = 0;
    private int nextCol = 0;

    public void addContentPane(ContentPane pane) {
        mainGrid.add(pane, nextCol, nextRow);

        // Update the next location (move to the next column, or next row if at the end of a row)
        nextCol++;
        if (nextCol >= mainGrid.getColumnCount()) {
            nextCol = 0;
            nextRow++;
        }
    }

    public void addMenuPane(MenuPane pane) {
        menuGrid.getChildren().add(pane);
    }

    // // Method to add containers to the menuGrid (VBox)
    // public void addMenuPane(String menuItemName) {
    //     // Creating a button as a menu item
    //     Button menuButton = new Button(menuItemName);
        
    //     // Add the button to the VBox
    //     menuGrid.getChildren().add(menuButton);
    // }

}