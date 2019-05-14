/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends baseStageController implements Initializable {
    private stageManager StageManager;
    String TA1String = null;
    @FXML
    MenuBar menubar;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button bt1;
    @FXML
    TextField TA1;

    @FXML
    void bt1Click()  {
        sceneManager primary = StageManager.get(Main.primarySceneManagerName);
        if (primary.get("test") == null) {
            Parent parent = primary.init("test.fxml", StageManager);
            Scene scene = new Scene(parent, 400, 500);
            StageManager.sendBroadcastMessage(scene.hashCode(),TA1String);
            primary.add(scene, "test");
            primary.select("test");
        } else primary.select("test");
    }

    @FXML
    void TAAction() {
        TA1String = TA1.getText();
        bt1.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menubar.prefWidthProperty().bind(anchorPane.widthProperty());
    }

    @Override
    public void setStageManager(stageManager StageManager) {
        this.StageManager = StageManager;
    }
}

