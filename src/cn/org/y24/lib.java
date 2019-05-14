/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rigths reserved.
 */

package cn.org.y24;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class lib {
    Parent loadParent(@NotNull FXMLLoader fxmlLoader) {
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Cannot load " + fxmlLoader.getLocation());
            e.printStackTrace();
        }
        return null;
    }
}
