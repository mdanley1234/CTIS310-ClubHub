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

public class MainScene extends Scene {
    
    private static MainSceneController controller;

    public MainScene(double WINDOW_WIDTH, double WINDOW_HEIGHT) throws IOException {
        super(loadRoot(), WINDOW_WIDTH, WINDOW_HEIGHT); // Call super first
    }

    private static Parent loadRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("main_scene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        return root;
    }

    // Construct the Main Scene after login
    public void buildMain() {
        // Add profile information header
        ProfilePacket profile = DataManager.getProfilePacket();
        controller.setProfileLabels("Early College at Guilford", (String) profile.get("first_name") + " " + (String) profile.get("last_name"), String.valueOf(profile.get("student_id")));

        // Add menu panes
        refreshMenuPanes();

        // Load the first bundle (Dashboard | Special Bundle - 0)
        loadBundle(DataManager.getDataBundles().get(0));
    }

    // Code to reload menu panes
    public static void refreshMenuPanes() {
        controller.clearMenuPanes(); // Clear existing panes
        for (DataBundle bundle : DataManager.getDataBundles()) {
            controller.addMenuPane(new MenuPane(bundle));
        }
    }

    // Code to build application scene from bundle
    public static void loadBundle(DataBundle bundle) {
        controller.clearContentPanes(); // Clear existing panes
        ArrayList<DataPacket> packets = bundle.fetchPackets(); // Get packets from bundle
        
        for (DataPacket packet : packets) {
            System.out.println(packet.getTable() + " | " + packet.toString());
            controller.addContentPane(new ContentPane(packet));
        }
    }
}
