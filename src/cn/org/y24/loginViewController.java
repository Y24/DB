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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginViewController extends baseStageController implements Initializable {
    private stageManager StageManager;
    private static final String dbURLPrefix = "jdbc:mysql://localhost:3306/";
    private String userName = null;
    private String password = null;
    private String databaseName = null;
    @FXML
    TextField userTextFieldID;
    @FXML
    PasswordField passwordID;
    @FXML
    TextField databaseNameID;
    @FXML
    Button loginButtonID;
    @FXML
    Label messageLabel;

    @Override
    public void setStageManager(stageManager StageManager) {
        this.StageManager = StageManager;
    }

    @FXML
    void ActionForUser() {
        userName = userTextFieldID.getText();
        passwordID.requestFocus();
    }

    @FXML
    void ActionForPassword() {
        password = passwordID.getText();
        databaseNameID.requestFocus();
    }

    @FXML
    void ActionForDatabase() {
        databaseName = databaseNameID.getText();
        loginButtonID.requestFocus();
    }

    @FXML
    void loginAction() {
        Connection connection = authorize(userName, password, databaseName);
        if (connection != null) {
            messageLabel.setVisible(false);
            sceneManager SceneManager = StageManager.get(Main.primarySceneManagerName);
            Parent mainParent = SceneManager.init("mainView.fxml", StageManager);
            Scene mainScene = new Scene(mainParent);
            Stage mainStage = new Stage();
            mainStage.setScene(mainScene);
            mainStage.setMaximized(true);
            //mainStage.setTitle("Y24");
            sceneManager mainSceneManager = new sceneManager(mainStage);
            StageManager.add(mainSceneManager, Main.mainSceneManagerName);
            mainSceneManager.add(mainScene, "main");
            mainSceneManager.select("main");
            StageManager.sendBroadcastMessage(SceneManager.getCurrentScene().hashCode(), connection);

            StageManager.convertTo(Main.mainSceneManagerName);
        } else {//connection fails
            messageLabel.setVisible(true);
        }
    }

    private Connection authorize(String userName, String password, String databaseName) {
        String dbURL = dbURLPrefix + databaseName;
        try {
            return DriverManager.getConnection(dbURL, userName, password);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}