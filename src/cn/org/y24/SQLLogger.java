/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Calendar;
import java.util.LinkedList;

public class SQLLogger {
    private LinkedList<SQLEvent> sqlEvents = new LinkedList<>();
    private StringProperty logMessage = new SimpleStringProperty();

    public StringProperty getLogMessage() {
        return logMessage;
    }

    void add(String statement, Calendar calendar) {
        sqlEvents.add(new SQLEvent(statement, calendar));
        logMessage.set(showEvent());
    }


    private String showEvent() {
        StringBuffer stringBuffer = new StringBuffer();
        for (SQLEvent sqlEvent : sqlEvents) {
            stringBuffer.append(sqlEvent.toString());
        }
        return String.valueOf(stringBuffer);
    }
}
