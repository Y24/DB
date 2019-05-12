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
import java.util.HashMap;
import java.util.LinkedList;


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

    public FXMLLoader getFXMLoaderFromRes(String resource) {
        return new FXMLLoader(getClass().getResource(resource));
    }

    boolean switchTo(String sceneName) {
        if (get(sceneName) == null) {
            return false;
        } else {
            getOwnerStage().setScene(get(sceneName));
            currentScene = get(sceneName);
            return true;
        }
    }

}
