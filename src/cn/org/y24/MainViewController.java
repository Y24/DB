/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class MainViewController extends baseStageController implements Initializable {
    @FXML
    public MenuItem tablesMenuItemID;
    @FXML
    public Tab tablesDetailsTabID;
    @FXML
    public Tab SQLDetailsTabID;
    @FXML
    public VBox SQLEditorVBoxID;
    @FXML
    public Accordion tablesDetailsAccordionID;
    @FXML
    public TabPane SQLEditorTabPaneID;
    @FXML
    public TextField queryTextFieldID;
    @FXML
    public TextArea logTextAreaID;
    private Connection connection = null;
    @FXML
    BorderPane rootPaneID;
    @FXML
    MenuBar menuBarID;
    private stageManager StageManager;
    private SQLEditor sqlEditor;
    private SQLLogger sqlLogger = new SQLLogger();

    @Override

    public void setStageManager(stageManager StageManager) {
        this.StageManager = StageManager;
    }

    private void receiveConnection() {
        if (connection == null)
            connection = (Connection) ((deliverer) StageManager.receiveBroadcastMessage().toArray()[0]).getMessage();
        //  System.out.println(connection.toString());
    }

    public void showTables() {
        List<String[]> table = executeQuerySQL("show tables");
        table.remove(0);
        Collection<TitledPane> collection = new ArrayList<>();
        for (String[] name : table) {
            collection.add(formTitledPaneForTable(name[0], executeQuerySQL("select * from " + name[0])));
        }
        tablesDetailsAccordionID.getPanes().setAll(collection);
        tablesDetailsAccordionID.setStyle("-fx-padding: 14px");
    }

    private TitledPane formTitledPaneForTable(String title, List<String[]> result) {
        TableView<ObservableList<StringProperty>> tableView = new TableView<>();
        String[] label = result.remove(0);
        int rowLength = label.length;
        int columnLength = result.size();
        List<TableColumn<ObservableList<StringProperty>, String>> tableColumns = new ArrayList<>(rowLength);
        if (columnLength > 0) {
            for (int i = 0; i < rowLength; i++) {
                tableColumns.add(i, createTableColumns(label[i], i));
            }
            for (int i = 0; i < columnLength; i++) {
                ObservableList<StringProperty> data = FXCollections.observableArrayList();
                for (int j = 0; j < rowLength; j++) {
                    data.add(new SimpleStringProperty(result.get(i)[j]));
                }
                tableView.getItems().add(i, data);
            }
            tableView.getColumns().setAll(tableColumns);

        }
        tableView.setStyle("-fx-padding: 14px");
        return new TitledPane(title, tableView);
    }

    private TableColumn<ObservableList<StringProperty>, String> createTableColumns(String title, int index) {
        TableColumn<ObservableList<StringProperty>, String> newColumn = new TableColumn<>(title);
        newColumn.setCellValueFactory(p -> p.getValue().get(index));
        newColumn.setStyle("-fx-background-color: rgba(94,117,255,0.31)");
        return newColumn;
    }

    private ArrayList<String[]> executeQuerySQL(String SQLStatement) {
        receiveConnection();
        Statement statement;
        ArrayList<String[]> result = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet tables = statement.executeQuery(SQLStatement);
            sqlLogger.add(SQLStatement, Calendar.getInstance());
            ResultSetMetaData tablesMetaData = tables.getMetaData();
            int count = tablesMetaData.getColumnCount();
            String[] labelName = new String[count];
            for (int i = 1; i <= count; i++) {
                labelName[i - 1] = tablesMetaData.getColumnLabel(i);
            }
            result.add(labelName);
            while (tables.next()) {
                String[] value = new String[count];
                for (int i = 1; i <= count; i++) {
                    value[i - 1] = tables.getString(i);
                }
                result.add(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logTextAreaID.textProperty().bindBidirectional(sqlLogger.getLogMessage());
    }

    public void showConnectionDetails() {
        receiveConnection();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            Alert connectionMessageAlert = createMessageAlertFrom(databaseMetaData);
            connectionMessageAlert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Alert createMessageAlertFrom(DatabaseMetaData databaseMetaData) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connection Details");
        //catalogInfo
      /*  data.put("CatalogSeparator", databaseMetaData.getCatalogSeparator());
        data.put("CatalogTerm", databaseMetaData.getCatalogTerm());*/
        //functionInfo
       /* data.put("getNumericFunctions", databaseMetaData.getNumericFunctions());
        data.put("getStringFunctions", databaseMetaData.getStringFunctions());
        data.put("getSystemFunctions", databaseMetaData.getSystemFunctions());
        data.put("getTimeDateFunctions", databaseMetaData.getTimeDateFunctions());*/
        //etc.
      /*  data.put("getSQLKeywords", databaseMetaData.getSQLKeywords());
        data.put("getExtraNameCharacters", databaseMetaData.getExtraNameCharacters());
        data.put("getIdentifierQuoteString", databaseMetaData.getIdentifierQuoteString());*/
        //  alert.setContentText(formStringFromHashMap(data));
        Accordion accordion = new Accordion();
        ArrayList<TitledPane> titledPanes = new ArrayList<>();
        List<String[]> basicInfo = new ArrayList<>();
        basicInfo.add(new String[2]);
        basicInfo.add(new String[]{"UserName", databaseMetaData.getUserName()});
        basicInfo.add(new String[]{"URL", databaseMetaData.getURL()});
        titledPanes.add(formTitledPaneForTable("basic Info", basicInfo));
        List<String[]> DBInfo = new ArrayList<>();
        DBInfo.add(new String[2]);
        DBInfo.add(new String[]{"ProductName", databaseMetaData.getDatabaseProductName()});
        DBInfo.add(new String[]{"ProductVersion", databaseMetaData.getDatabaseProductVersion()});
        DBInfo.add(new String[]{"MajorVersion", databaseMetaData.getDatabaseMajorVersion() + ""});
        DBInfo.add(new String[]{"MinorVersion", databaseMetaData.getDatabaseMinorVersion() + ""});
        titledPanes.add(formTitledPaneForTable("Database Info", DBInfo));
        List<String[]> DriverInfo = new ArrayList<>();
        DriverInfo.add(new String[2]);
        DriverInfo.add(new String[]{"DriverName", databaseMetaData.getDriverName()});
        DriverInfo.add(new String[]{"DriverVersion", databaseMetaData.getDriverVersion()});
        DriverInfo.add(new String[]{"MajorVersion", databaseMetaData.getDriverMajorVersion() + ""});
        DriverInfo.add(new String[]{"MinorVersion", databaseMetaData.getDriverMinorVersion() + ""});
        titledPanes.add(formTitledPaneForTable("Driver Info", DriverInfo));
        accordion.getPanes().setAll(titledPanes);
        accordion.setPrefWidth(550);
        accordion.setPrefHeight(250);
        alert.setGraphic(accordion);
        alert.setHeaderText("info");
        return alert;
    }

    @FXML
    public void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About this Application");
        alert.setContentText(new aboutMessage("Y24", "1.0.0", "https://github.com/y24", "Linux/Intellj IDEA/OpenJFX 12", "light-weight Database Manager").toString());
        alert.showAndWait();
    }

    private void initializeSQLEditor() {
        if (sqlEditor == null) {
            sqlEditor = new SQLEditor(SQLEditorTabPaneID);
        }
    }

    public void newSQLEditorTab() {
        initializeSQLEditor();
        sqlEditor.newTab("SQL " + sqlEditor.getCount());
    }

    public void closeCurrentSQLEditorTab() {
        initializeSQLEditor();
        sqlEditor.closeCurrentTab();
    }

    public void openSQLEditorTab() throws IOException {
        initializeSQLEditor();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load an existed SQL file");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("SQL files", "*.sql"));
        File selectedFile = fileChooser.showOpenDialog(StageManager.get(Main.mainSceneManagerName).getOwnerStage());
        if (selectedFile != null) {
            String SQLData = new String(new FileInputStream(selectedFile).readAllBytes(), StandardCharsets.UTF_8);
            final String title = "SQL " + sqlEditor.getCount();
            sqlEditor.newTab(title);
            sqlEditor.getSqlDataHashTable().get(title).setValue(SQLData);
        }
    }

    @FXML
    public void executeCurrentSQL() throws SQLException {//StringProperty [value: select
        String sql = String.valueOf(sqlEditor.getSqlDataHashTable().get(sqlEditor.getCurrentTab().getText()));
        String currentSQL = sql.substring("StringProperty [value: ".length(), sql.length() - 1);
        String[] statements = currentSQL.split(";");
        for (var statement : statements) {
            if (statement.trim().isBlank())
                continue;
            if (statement.toLowerCase().trim().startsWith("select") || statement.toLowerCase().trim().startsWith("show")) {
                executeQuerySQL(statement.trim());
            } else {
                executeUpdateSQL(statement.trim());
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText("Result");
        alert.setResizable(true);
        alert.setContentText(sqlLogger.toString());
        alert.showAndWait();
    }

    private int executeUpdateSQL(String SQLStatement) throws SQLException {
        receiveConnection();
        Statement statement;
        statement = connection.createStatement();
        sqlLogger.add(SQLStatement, Calendar.getInstance());
        return statement.executeUpdate(SQLStatement);

    }

    public void executeQueryAndShow() {
        String queryStatement = queryTextFieldID.getText();
        Accordion accordion = new Accordion(formTitledPaneForTable(queryStatement, executeQuerySQL(queryStatement)));
        accordion.setPrefHeight(600);
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(queryStatement);
        dialog.setGraphic(accordion);
        dialog.setResizable(true);
        dialog.setHeaderText("result");
        dialog.showAndWait();

    }

    class aboutMessage {
        private String author;
        private String version;
        private String GitHub;
        private String DevEnv;
        private String introduction;

        aboutMessage(String author, String version, String GitHub, String DevEnv, String introduction) {
            this.author = author;
            this.version = version;
            this.GitHub = GitHub;
            this.DevEnv = DevEnv;
            this.introduction = introduction;
        }

        @Override
        public String toString() {
            return "aboutMessage{\n" +
                    " author='" + author + '\'' + '\n' +
                    " version='" + version + '\'' + '\n' +
                    " GitHub='" + GitHub + '\'' + '\n' +
                    " DevEnv='" + DevEnv + '\'' + '\n' +
                    " introduction='" + introduction + '\'' + '\n' +
                    '}';
        }
    }
}
