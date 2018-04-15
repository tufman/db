package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DBMenu {

    private Connection con;
    private Scanner scanner = new Scanner(System.in);

    private DBEmployee dbEmployee;
    private DBChain dbChain;

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";

    public DBMenu() throws SQLException {


        System.out.println(ANSI_RED + "##########################");
        System.out.println(" Welcome to the DB Menu ");
        System.out.println("##########################");
    }

    public void presentMainMenu() throws SQLException{

        boolean run = true;
        while (run) {
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Employees; [2] - Chains; [3] - Shops; [9] - Exit");

            int userSelection = scanner.nextInt();
            if (userSelection == 1) {
                dbEmployee = new DBEmployee();
                //presentSpecificEmployee(0);
            }
            if (userSelection == 2){
                dbChain = new DBChain();
            }
            //        if (userSelection == 3){
            //            updateEmployee();
            //        }
            //        if (userSelection == 4){
            //            deleteEmployee();
            //        }
            if (userSelection == 9) {
                System.out.println("Thank you for using DB Executer");
                run = false;
            }
        }
    }
}
