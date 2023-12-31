package sample.admin;

import java.sql.*;

public class databaseConnection {
    public Connection connection;


    public Connection getConnection() {
        String dbname = "saloonmanagement";
        String user = "admin";
        String pwd = "1234" ;
        String url = "jdbc:mysql://localhost/" + dbname;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(url,user,pwd);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return connection;

    }
}
