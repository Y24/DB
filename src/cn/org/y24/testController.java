/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Collection;


public class testController extends baseStageController {
    private stageManager StageManager;
    @FXML
    Button bt;

    @FXML
    void btAction() {
        sceneManager primary = StageManager.get(Main.primarySceneManagerName);
        Stage stage = primary.getOwnerStage();
        Collection<deliverer> messages = StageManager.receiveBroadcastMessage();
        stage.setTitle((String) (((deliverer) messages.toArray()[0]).getMessage()));
        //  primary.select(Main.primarySceneName);
    }

    @Override
    public void setStageManager(stageManager StageManager) {
        this.StageManager = StageManager;
    }
}
