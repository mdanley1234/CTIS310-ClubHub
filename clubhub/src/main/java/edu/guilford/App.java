package edu.guilford;

import java.io.IOException;

import edu.guilford.gui.controllers.MainSceneController;
import edu.guilford.gui.elements.ContentPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    // Set application dimensions
    public static final double WINDOW_HEIGHT = 720;
    public static final double WINDOW_WIDTH = 1280;
    
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // scene = new Scene(loadFXML("primary"), WINDOW_WIDTH, WINDOW_HEIGHT);

        // BorderPane bp = new BorderPane();
        // bp = FXMLLoader.load(getClass().getResource("primary.fxml"));


        // scene = new Scene(bp, WINDOW_WIDTH, WINDOW_HEIGHT);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mainStage.fxml"));
        Parent root = loader.load();
        MainSceneController mainStageController = loader.getController();

        // ScrollPane contentPane = new ScrollPane();
        // contentPane = FXMLLoader.load(getClass().getResource("contentPane.fxml"));
        // mainStageController.addContentPane(contentPane);

        ContentPane pane = new ContentPane();
        mainStageController.addContentPane(pane);

        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("ClubHub");

        // Add ClubHub icon
        Image icon = new Image(getClass().getResourceAsStream("/edu/guilford/ClubHubLogo.jpg"));
        stage.getIcons().add(icon);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load(); 

    }
    public static void main(String[] args) {
        launch();
    }

}