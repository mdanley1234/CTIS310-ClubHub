package edu.guilford.gui.elements;

import java.io.IOException;

import edu.guilford.data.packets.DataPacket;
import edu.guilford.gui.controllers.ContentPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * ContentPane is a GUI element that dynamically displays information based on a given DataPacket.
 * It supports different types of packets (e.g., club, profile) and builds the UI accordingly.
 */
public class ContentPane extends Pane {

    // Predefined pane height options
    public static final double SMALL = 200;
    public static final double MEDIUM = 280;
    public static final double LARGE = 350;
    public static final double XLARGE = 450;

    // Fixed width
    private static final double WIDTH = 500;

    // UI controller reference
    private ContentPaneController controller;

    /**
     * Constructs a ContentPane for the given DataPacket, displaying its content visually.
     *
     * @param packet The data packet to be visualized.
     */
    public ContentPane(DataPacket packet) {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("content_pane.fxml"));
            ScrollPane pane = loader.load();
            controller = loader.getController();
            this.getChildren().add(pane);
            this.setWidth(WIDTH);

            // Display content based on packet type
            switch (packet.getTable()) {
                case "clubs":
                    this.setHeight(XLARGE);
                    controller.setHeader(packet.get("club_name").toString());
                    controller.addContent(new Label("Founded: " + packet.get("founding_year")));
                    controller.addContent(new Label("Description: " + packet.get("club_description")));
                    controller.addContent(new Label("Website: " + packet.get("club_website")));
                    controller.addContent(new Label("Email: " + packet.get("club_email")));
                    controller.addContent(new Label("Meeting Schedule: " + packet.get("meeting_schedule")));
                    controller.addContent(new Label("Meeting Location: " + packet.get("meeting_location")));
                    controller.addContent(new Label("Active: " + ((boolean) packet.get("active") ? "Yes" : "No")));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to load content pane FXML.");
            e.printStackTrace();
        }
    }
}
