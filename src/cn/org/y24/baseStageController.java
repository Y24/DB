/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

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