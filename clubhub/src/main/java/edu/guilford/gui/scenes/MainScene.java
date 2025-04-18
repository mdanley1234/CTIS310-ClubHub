package edu.guilford.gui.scenes;

import java.io.IOException;
import java.util.ArrayList;

import edu.guilford.data.DataBundle;
import edu.guilford.data.DataManager;
import edu.guilford.data.packets.DataPacket;
import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.gui.controllers.MainSceneController;
import edu.guilford.gui.elements.ContentPane;
import edu.guilford.gui.elements.MenuPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The {@code MainScene} class represents the main scene of the application after the user logs in.
 * It is responsible for displaying the user's profile and dynamically loading various menu and content panes.
 */
public class MainScene extends Scene {
    
    /** The controller for the main scene. */
    private static MainSceneController controller;

    /**
     * Constructs a new {@code MainScene} with the specified width and height.
     * This constructor calls the superclass {@link Scene} constructor and loads the FXML layout for the scene.
     *
     * @param WINDOW_WIDTH the width of the window
     * @param WINDOW_HEIGHT the height of the window
     * @throws IOException if an error occurs while loading the FXML layout
     */
    public MainScene(double WINDOW_WIDTH, double WINDOW_HEIGHT) throws IOException {
        super(loadRoot(), WINDOW_WIDTH, WINDOW_HEIGHT); // Call super first
    }

    /**
     * Loads the root element for the main scene from the FXML file.
     * It also sets the controller for the scene.
     *
     * @return the root element for the main scene
     * @throws IOException if an error occurs while loading the FXML file
     */
    private static Parent loadRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("main_scene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        return root;
    }

    /**
     * Constructs the main scene after the user successfully logs in.
     * It sets up the profile header, menu panes, and the first data bundle (e.g., Dashboard).
     */
    public void buildMain() {
        // Add profile information header
        ProfilePacket profile = DataManager.getProfilePacket();
        controller.setProfileLabels(
            "Early College at Guilford", 
            (String) profile.get("first_name") + " " + (String) profile.get("last_name"), 
            String.valueOf(profile.get("student_id"))
        );

        // Add menu panes
        refreshMenuPanes();

        // Load the first bundle (Dashboard | Special Bundle - 0)
        loadBundle(DataManager.getDataBundles().get(0));
    }

    /**
     * Refreshes the menu panes by clearing existing ones and adding new ones based on the data bundles.
     */
    public static void refreshMenuPanes() {
        controller.clearMenuPanes(); // Clear existing panes
        for (DataBundle bundle : DataManager.getDataBundles()) {
            controller.addMenuPane(new MenuPane(bundle));
        }
    }

    /**
     * Loads the content associated with a given data bundle and adds it to the scene.
     * Clears any existing content panes before loading the new ones.
     *
     * @param bundle the data bundle to load content from
     */
    public static void loadBundle(DataBundle bundle) {
        controller.clearContentPanes(); // Clear existing panes
        ArrayList<DataPacket> packets = bundle.fetchPackets(); // Get packets from the bundle
        
        for (DataPacket packet : packets) {
            System.out.println(packet.getTable() + " | " + packet.toString());
            controller.addContentPane(new ContentPane(packet));
        }
    }
}
