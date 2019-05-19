/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLExecuter {
    private Connection connection;

    public SQLExecuter(Connection connection) {
        this.connection = connection;
    }

    private int currentStatment = 0;
    private ArrayList<String> sqlStatements = new ArrayList<>();

    void formStatementsFrom(final String inputString) {
        String[] stream = inputString.split(";");
        sqlStatements.clear();
        sqlStatements.addAll(Arrays.asList(stream));
    }

    void executeCurrent() throws SQLException {
        String sql = sqlStatements.get(currentStatment);
        connection.createStatement();

    }
}
