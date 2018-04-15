package jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DBBase {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";

    Connection con;
    Scanner scanner = new Scanner(System.in);


    public DBBase(String title) throws SQLException{
        MysqlDataSource dataSource = new MysqlDataSource();

        System.out.println(ANSI_RED + "##########################");
        System.out.println(" Welcome to " + title + " Menu ");
        System.out.println("##########################");
        // Set dataSource Properties
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("mysql1");
        dataSource.setUser("root");
        dataSource.setPassword("Aa123456");

        con = dataSource.getConnection();

        presentUserMenu();
    }

    void presentUserMenu() throws SQLException{
        System.out.println("In case you see this message, you should override this function... :)");
    }

}
