/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController extends baseStageController implements Initializable {
    @FXML
    public TableView tableViewID;
    @FXML
    BorderPane rootPaneID;
    @FXML
    MenuBar menuBarID;
    private stageManager StageManager;

    @Override
    public void setStageManager(stageManager StageManager) {
        this.StageManager = StageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = (Connection) StageManager.receiveBroadcastMessage().toArray()[0];
        try {
            StageManager.get(Main.primarySceneManagerName).getOwnerStage().setTitle(connection.getSchema());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
