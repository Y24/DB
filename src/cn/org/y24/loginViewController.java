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
        //userName = userTextFieldID.getText();
        passwordID.requestFocus();
    }

    @FXML
    void ActionForPassword() {
        // password = passwordID.getText();
        databaseNameID.requestFocus();
    }

    @FXML
    void ActionForDatabase() {
        // databaseName = databaseNameID.getText();
        loginButtonID.requestFocus();
    }

    @FXML
    void loginAction() {
        Connection connection = authorize(userTextFieldID.getText(), passwordID.getText(), databaseNameID.getText());
        if (connection != null) {
            messageLabel.setVisible(false);
            sceneManager SceneManager = StageManager.get(Main.primarySceneManagerName);
            StageManager.sendBroadcastMessage(SceneManager.getCurrentScene().hashCode(), connection);
            Parent mainParent = SceneManager.init("mainView.fxml", StageManager);
            Scene mainScene = new Scene(mainParent, 1000, 800);
            Stage mainStage = new Stage();
            mainStage.setScene(mainScene);
            sceneManager mainSceneManager = new sceneManager(mainStage);
            StageManager.add(mainSceneManager, Main.mainSceneManagerName);
            mainSceneManager.add(mainScene, "main");
            mainSceneManager.select("main");
            mainSceneManager.getOwnerStage().setTitle("Database Manager");
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