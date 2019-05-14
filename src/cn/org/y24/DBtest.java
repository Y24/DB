/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import java.sql.*;

class DBtest {

    private static final String dbURLPrefix = "jdbc:mysql://localhost:3306/";
    private Connection connection = null;

    DBtest(String userName, String password, String dbName) {
        String dbURL = dbURLPrefix + dbName;
        //    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        try {
            //  Class.forName(JDBC_DRIVER);
            System.out.println("Trying the connection...");
            connection = DriverManager.getConnection(dbURL, userName, password);
            if (!connection.isClosed())
                System.out.println("Connection successed");
            else
                System.out.println("Connection failed");
            System.out.println("Connection closed");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection to the " + dbURL + " failed ");
            //   e.printStackTrace();
        }

    }

    void testSelect() {
        String selectSql = "select * from book";
        try {
            Statement selectStat = connection.createStatement();
            ResultSet selectReshult = selectStat.executeQuery(selectSql);
            while (selectReshult.next()) {
                System.out.println(selectReshult.getString("ISBN"));
            }
            selectReshult.close();
            selectStat.close();
        } catch (SQLException e) {
            System.err.println("cannot create a statement");
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}