package edu.guilford.gui.scenes;

import java.io.IOException;

import edu.guilford.data.DataBundle;
import edu.guilford.data.DataManager;
import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.gui.controllers.MainSceneController;
import edu.guilford.gui.elements.MenuPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainScene extends Scene {
    
    private static MainSceneController controller;
    private DataBundle currentBundle;

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
    public void buildScene() {
        // Add profile information header
        ProfilePacket profile = DataManager.getProfilePacket();
        controller.setHeaderLabels("Early College at Guilford", (String) profile.get("first_name") + " " + (String) profile.get("last_name"), String.valueOf(profile.get("student_id")));

        // Add menu panels
        for (DataBundle bundle : DataManager.getDataBundles()) {
            controller.addMenuPane(new MenuPane(bundle));
        }

        // Select bundle
        // currentBundle = DataManager.getDataBundles().get(1); // CLUB BUNDLE
    }

    public void loadBundle(DataBundle bundle) {
        currentBundle = bundle;

        // Construct ContentPanes

    }
}
