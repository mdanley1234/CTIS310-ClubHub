package edu.guilford.gui.elements;

import java.io.IOException;
import java.util.UUID;

import edu.guilford.data.DataBundle;
import edu.guilford.data.DataManager;
import edu.guilford.data.packets.DataPacket;
import edu.guilford.gui.controllers.ContentPaneController;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.supabase.SupabaseAuth;
import edu.guilford.supabase.SupabaseDelete;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * ContentPane is a GUI element that dynamically displays information based on a
 * given DataPacket. It supports different types of packets (e.g., club,
 * profile) and builds the UI accordingly.
 */
public class ContentPane extends Pane {

    // Predefined pane height options
    public static final double SMALL = 200;
    public static final double MEDIUM = 300;
    public static final double LARGE = 350;
    public static final double XLARGE = 450;

    // UI controller reference
    private ContentPaneController controller;

    /**
     * Constructs a ContentPane for the given DataPacket, displaying its content
     * visually.
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

            String labelStyle = "-fx-font-size: 16px; -fx-font-family: 'Segoe UI', sans-serif;";
            String labelStyle2 = "-fx-font-size: 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-font-weight: bold;";

            // Display content based on packet type
            switch (packet.getTable()) {
                case "clubs":
                    HBox buttonBox = new HBox(10); // spacing of 10 between buttons
                    this.setHeight(LARGE);
                    controller.setHeader(packet.get("club_name").toString());

                    controller.addContent(new Label("Founded: " + packet.get("founding_year")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Description: " + packet.get("club_description")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Website: " + packet.get("club_website")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Email: " + packet.get("club_email")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Meeting Schedule: " + packet.get("meeting_schedule")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Meeting Location: " + packet.get("meeting_location")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    controller.addContent(new Label("Active: " + ((boolean) packet.get("active") ? "Yes" : "No")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    if (packet.hasMetadata("button")) {
                        Button joinButton = new Button("Join Club");
                        joinButton.setStyle(
                                "-fx-font-size: 14px; "
                                + "-fx-font-family: 'Segoe UI', sans-serif; "
                                + "-fx-background-color: #4CAF50; "
                                + "-fx-text-fill: white; "
                                + "-fx-background-radius: 5px; "
                                + "-fx-padding: 8 16;"
                        );

                        // Add button functionality
                        joinButton.setOnAction(event -> {
                            System.out.println("Join Club button clicked!");

                            if (getBundle(packet) == null) {
                                DataPacket bundlePacket = new DataPacket("bundles");
                                bundlePacket.put("profile_id", SupabaseAuth.getUserId());
                                bundlePacket.put("club_id", UUID.fromString(packet.getString("club_id")));
                                bundlePacket.put("role", "member");
                                bundlePacket.insertPacket("bundles");

                                DataManager.initDataManager(SupabaseAuth.getUserId()); // Refresh data manager to include new bundle
                                MainScene.refreshMenuPanes(); // Refresh menu panes to include new bundle
                                MainScene.loadBundle(DataManager.getDataBundles().getLast()); // Load the new bundle (Club | Member)
                            }

                        });

                        buttonBox.getChildren().addAll(joinButton);
                    }

                    // Default remover button included
                    Button leaveButton = new Button("Leave Club");
                    leaveButton.setStyle(
                            "-fx-font-size: 14px; "
                            + "-fx-font-family: 'Segoe UI', sans-serif; "
                            + "-fx-background-color: #f44336; "
                            + "-fx-text-fill: white; "
                            + "-fx-background-radius: 5px; "
                            + "-fx-padding: 8 16;"
                    );

                    leaveButton.setOnAction(event -> {
                        System.out.println("Leave Club button clicked!");

                        DataBundle bundle = getBundle(packet);
                        if (bundle != null) {
                            SupabaseDelete.deleteById("bundles", "bundle_id", bundle.getBundleId());

                            DataManager.initDataManager(SupabaseAuth.getUserId()); // Refresh data manager to include new bundle
                            MainScene.refreshMenuPanes(); // Refresh menu panes to include new bundle
                            MainScene.loadBundle(DataManager.getDataBundles().get(1)); // Load the first bundle (Directory | Special Bundle - 1)
                        }

                    });

                    buttonBox.getChildren().addAll(leaveButton);
                    controller.addContent(buttonBox);
                    break;
                case "profiles":
                    this.setHeight(MEDIUM);
                    controller.setHeader("User Profile Information");

                    controller.addContent(new Label("Name: " + packet.get("first_name") + " " + packet.get("last_name")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Email: " + packet.get("email")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Student ID: " + packet.get("student_id")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Date of Birth: " + packet.get("date_of_birth")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Phone Number: " + packet.get("phone_number")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Address: " + packet.get("address")) {
                        {
                            setStyle(labelStyle);
                        }
                    });

                    controller.addContent(new Label("Graduation Year: " + packet.get("graduation_year")) {
                        {
                            setStyle(labelStyle);
                        }
                    });
                    break;
                case "list":
                    controller.setHeader("List of Your Clubs");
                    for (String data : packet.getMetadata()) {
                        controller.addContent(new Label(data) {
                            {
                                setStyle(labelStyle2);
                            }
                        });
                    }
                    break;

                case "events":
                    this.setHeight(MEDIUM);
                    controller.setHeader("Club Event Information");

                    controller.addContent(new Label("Event Name: " + packet.get("title")) {
                        {
                            setStyle(labelStyle2);
                        }
                    });
                    controller.addContent(new Label("Event Date/Time: " + packet.get("date_time")) {
                        {
                            setStyle(labelStyle2);
                        }
                    });
                    controller.addContent(new Label("Event Location: " + packet.get("location")) {
                        {
                            setStyle(labelStyle2);
                        }
                    });
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to load content pane FXML.");
            e.printStackTrace();
        }
    }

    /**
     * Helper method to check if a bundle already exists for a club.
     * 
     * @param packet The data packet containing club information.
     * @return The associated DataBundle if it exists, otherwise null.
     */
    private DataBundle getBundle(DataPacket packet) {
        for (DataBundle dataBundle : DataManager.getDataBundles()) {
            if (dataBundle.getClubName().equals(packet.get("club_name"))) {
                return dataBundle; // User is a member of the club
            }
        }
        return null; // User is not a member of the club
    }
}
