package edu.guilford.gui;

import java.io.IOException;

import edu.guilford.gui.scenes.LoginScene;
import edu.guilford.gui.scenes.MainScene;
import edu.guilford.gui.scenes.SignupScene;

public class GUIManager {

    LoginScene loginScene;
    SignupScene signupScene;
    MainScene mainScene;

    public GUIManager(double WINDOW_WIDTH, double WINDOW_HEIGHT) {

        try {
            loginScene = new LoginScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            signupScene = new SignupScene(WINDOW_WIDTH, WINDOW_HEIGHT);
            mainScene = new MainScene(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
