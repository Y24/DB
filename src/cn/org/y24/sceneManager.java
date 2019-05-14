/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


class sceneManager extends baseManager<Scene> {

    private Stage ownerStage;
    private Scene currentScene = null;

    public sceneManager(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Parent init(String resource, stageManager StageManager) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            Parent parent = fxmlLoader.load();
            baseStageController controller = fxmlLoader.getController();
            controller.setStageManager(StageManager);
            return parent;
        } catch (IOException e) {
            System.err.println("Cannot load Parent!");
            return null;
        }
    }

    boolean select(String sceneName) {
        if (get(sceneName) == null) {
            return false;
        } else {
            getOwnerStage().setScene(get(sceneName));
            currentScene = get(sceneName);
            return true;
        }
    }
}
