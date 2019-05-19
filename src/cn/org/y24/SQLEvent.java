/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import java.util.Calendar;

enum eventType {query, update}

class eventDescriptor {
    private final Calendar happenTime;
    private final String executedStatement;

    public eventDescriptor(Calendar happenTime, String executedStatement) {
        this.happenTime = happenTime;
        this.executedStatement = executedStatement;
    }

    @Override
    public String toString() {
        return happenTime.get(Calendar.YEAR) + "/"
                + happenTime.get(Calendar.MONTH) + "/"
                + happenTime.get(Calendar.DATE) + "/"
                + happenTime.get(Calendar.HOUR) + ":"
                + happenTime.get(Calendar.MINUTE) + ":"
                + happenTime.get(Calendar.SECOND) +
                ", statement='" + executedStatement + "\'";
    }
}

public class SQLEvent {

    private final eventDescriptor descriptor;
    private final eventType type;

    SQLEvent(String statement, Calendar calendar) {
        this.descriptor = new eventDescriptor(calendar, statement);
        type = statement.toLowerCase().startsWith("select")||statement.toLowerCase().trim().startsWith("show") ? eventType.query : eventType.update;
    }

    @Override
    public String toString() {
        return "SQLEvent{" +
                "descriptor=" + descriptor +
                ", type=" + type +
                "}\n";
    }
}
