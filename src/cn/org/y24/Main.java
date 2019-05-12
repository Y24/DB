/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rigths reserved.
 */

package cn.org.y24;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String primarySceneManagerName = "primaryStage";
    private static stageManager stagemanager = new stageManager();

    @Override
    public void start(Stage primaryStage) {

    }


    public static void main(String[] args) {
        launch(args);
    }
}
