/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
<tabs>
<Tab text="SQL 0">
<content>
<AnchorPane minHeight="0.0"minWidth="0.0"prefHeight="180.0"
        prefWidth="200.0">
<children>
<TextArea layoutX="14.0"layoutY="14.0"prefHeight="200.0"
        prefWidth="200.0"style="-fx-border-color: black;"
        AnchorPane.bottomAnchor="20.0"
        AnchorPane.leftAnchor="14.0"
        AnchorPane.rightAnchor="14.0"
        AnchorPane.topAnchor="10.0">
<font>
<Font size="18.0"/>
</font>
</TextArea>
</children>
</AnchorPane>
</content>
</Tab>
</tabs>
*/
public class SQLEditor {
    private int count = 0;
    private TabPane root;
    /*  private ArrayList<String> titles =new ArrayList<>();
        HashMap<String, Tab> tabHashMap=new ;*/
    private Tab currentTab;

    public int getCount() {
        return count;
    }

    public SQLEditor(TabPane root) {
        this.root = root;
        root.getSelectionModel().selectedItemProperty().addListener((observableValue, old, current) -> currentTab = current);
    }

    protected Tab loadTab(String title) {
        try {
            Tab tab = new FXMLLoader(getClass().getResource("SQLEditorTab.fxml")).load();
            tab.setText(title);
            return tab;
        } catch (IOException e) {
            System.err.println("Cannot load SQLEditorTab.fxml");
        }
        return new Tab();
    }

    public void newTab(String title) {
        Tab newTab = loadTab(title);
        root.getTabs().add(newTab);
        count++;
        root.getSelectionModel().select(newTab);
    }

    public boolean closeCurrentTab() {
        if (root.getTabs().isEmpty()) {
            return false;
        } else {
            root.getTabs().remove(currentTab);
            return true;
        }
    }

}
