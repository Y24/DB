/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String primarySceneManagerName = "primaryStage";
    public static final String primarySceneName = "primaryScene";
    public static final String mainSceneManagerName = "mainStage";
    private static stageManager StageManager = new stageManager();


    @Override
    public void start(Stage primaryStage) {
        sceneManager primarySceneManager = new sceneManager(primaryStage);
        primaryStage.setResizable(false);
        Parent rootParent = primarySceneManager.init("loginView.fxml", StageManager);
        Scene rootScene = new Scene(rootParent, 600, 600);
        primaryStage.setResizable(false);
       /* Parent rootParent = primarySceneManager.init("mainView.fxml", StageManager);
        Scene rootScene = new Scene(rootParent);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);*/
        primarySceneManager.add(rootScene, primarySceneName);
        primarySceneManager.select(primarySceneName);
        StageManager.add(primarySceneManager, primarySceneManagerName);
        StageManager.showOnly(primarySceneManagerName);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
