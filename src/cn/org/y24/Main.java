/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static String primarySceneManagerName = "primaryStage";
    private static stageManager StageManager = new stageManager();

    @Override
    public void start(Stage primaryStage) {
        sceneManager primarySceneManager = new sceneManager(primaryStage);
        FXMLLoader rootFXMLLoader = primarySceneManager.getFXMLoaderFromRes("sample.fxml");
        try {
            Parent rootParent = rootFXMLLoader.load();
            Scene rootScene = new Scene(rootParent, 300, 400);
            primarySceneManager.add(rootScene, rootScene.hashCode() + "");
            primarySceneManager.switchTo(rootScene.hashCode() + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Warning: only after the call of FXMLLoader.load() can we get the controller instance!
        baseStageController controller = rootFXMLLoader.getController();
        controller.setStageManager(StageManager);

        StageManager.add(primarySceneManager, primarySceneManagerName);
        StageManager.showOnly(primarySceneManagerName);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
