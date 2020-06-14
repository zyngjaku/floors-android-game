package io.github.zyngjaku.floors.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    public static void query() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:msql://mysql.agh.edu.pl/zyngier2";
            Connection conn = DriverManager.getConnection(url, "zyngier2", "XLyfHv87RCN0QfsF");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO ranking VALUES (1,'test',1)");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}


