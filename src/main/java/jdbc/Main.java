package jdbc;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        DBMenu dbMenu = new DBMenu();
        dbMenu.presentMainMenu();
    }
}

