/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rigths reserved.
 */

package cn.org.y24;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Every controller of a Stage must implies the interface.
 */

interface IStageController {
    void setStageManager(stageManager StageManager);
}

public abstract class baseStageController implements IStageController {
    protected stageManager stageManager;

    @Override
    public void setStageManager(stageManager stageManager) {
        this.stageManager = stageManager;
    }

}