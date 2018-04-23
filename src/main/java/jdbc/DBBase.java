package jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DBBase {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";

    private Connection con;
    Scanner scanner;




    public DBBase(String title , Scanner scanner) throws SQLException{
        MysqlDataSource dataSource = new MysqlDataSource();

        System.out.println(ANSI_RED + "##########################");
        System.out.println(" Welcome to " + title + " Menu ");
        System.out.println("##########################");
        // Set dataSource Properties
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("study");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        con = dataSource.getConnection();
        this.scanner = scanner; 
        presentUserMenu();
    }


    public Connection getCon() {
        return con;
    }

    void presentUserMenu() throws SQLException{
        System.out.println("In case you see this message, you should override this function... :)");
    }

}

