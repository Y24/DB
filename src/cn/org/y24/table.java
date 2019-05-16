/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class table {
    private ObservableList<SimpleStringProperty> data;

    public table() {
        data = new ReadOnlyListWrapper<>();
    }

    public StringProperty get(int index) {
        return data.get(index);

    }

    public void set(String s, int index) {
        this.data.add(index, new SimpleStringProperty(s));
    }
}
