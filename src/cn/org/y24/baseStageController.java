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

abstract public class baseStageController implements IStageController{

}