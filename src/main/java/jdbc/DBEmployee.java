package jdbc;

import java.sql.*;
import java.util.Scanner;


public class DBEmployee extends DBBase implements DBIterface {

    private Connection con;


    public DBEmployee(Scanner scanner) throws SQLException {

        super("Employee",scanner);

    }

    @Override
    public void presentUserMenu() throws SQLException {
        boolean run = true;
        while (run) {
            con = super.getCon();
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Present All Employees; [2] - Insert a new Employee; [3] - Update an Employee; [4] - Delete an Employee; [5] - Get All Employees for chain; [9] - Exit");
            int userSelection = Integer.valueOf(scanner.nextLine());
            if (userSelection == 1) {
                presentAllEmployees();

            }
            if (userSelection == 2) {
                addNewEmployee();
            }
            if (userSelection == 3) {
                updateEmployee();
            }
            if (userSelection == 4) {
                deleteEmployee();
            }
            if (userSelection == 5) {
                getEmployeeByChainID();
            }
            if (userSelection == 9) {
                run = false;
            }
        }

    }


    private void deleteEmployee() throws SQLException {
        System.out.println("Enter Employee id to delete:");
        String idToDelete = scanner.nextLine();
        System.out.println("Are you sure you want to delete Employee ID: " + idToDelete + "?  (Y/N)");
        presentSpecificEmployee(Integer.valueOf(idToDelete));
        String deleteApproval = scanner.nextLine();
        if (deleteApproval.equalsIgnoreCase("Y") || deleteApproval.equalsIgnoreCase("yes")) {
            String insertStr = "DELETE from employee where id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                preparedStatement.setString(1, idToDelete); // assume we have a String lastName
                preparedStatement.executeUpdate();
                System.out.println("Employee id " + idToDelete + " was deleted");
            }
        }
        if (deleteApproval.equalsIgnoreCase("N") || deleteApproval.equalsIgnoreCase("no")) {
            System.out.println("Chain id " + idToDelete + " was not deleted - canceled by user");
        }
    }

    private void presentAllEmployees() throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from employee")) {
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing employees in the System");
            int cols = metadata.getColumnCount();
            for (int i = 1; i <= cols; ++i) {

                //System.out.print(metadata.getColumnName(i) + "\t\t");
                System.out.printf("%15s",metadata.getColumnName(i));
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= cols; ++i) {
                  //  System.out.print(rs.getObject(i) + "\t\t");
                    System.out.printf("%15s",rs.getObject(i));// + "\t\t");
                }
                System.out.println();

            }
        }
    }

    private void addNewEmployee() throws SQLException {

        System.out.println("Enter Employee:");
        System.out.println("Please enter First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Please enter Role Number: (1/2)");
        int role = Integer.valueOf(scanner.nextLine());
        System.out.println("Please enter chain id/Management id(17) for Employee:");
        int chainId = Integer.valueOf(scanner.nextLine());
        System.out.println("Please enter Store ID ");
        int store_id = Integer.valueOf(scanner.nextLine());

        String insertStr = "INSERT into employee (first_name, last_name, role,chain_id,store_id ) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, role);
            preparedStatement.setInt(4, chainId);
            preparedStatement.setInt(5, store_id);
            preparedStatement.executeUpdate();
        }
    }

    private void updateEmployee() throws SQLException {
        System.out.println("Enter Employee id to update:");
        int idToUpdate = Integer.valueOf(scanner.nextLine());
        System.out.println("Current Employee Details:");
        presentSpecificEmployee(idToUpdate);
        System.out.println("Enter New FirstName to update:");
        String newFirstNameToUpdate = scanner.nextLine();
        System.out.println("Enter New LastName to update:");
        String newlastNameToUpdate = scanner.nextLine();
        System.out.println("Enter role to update:");
        int role = Integer.valueOf(scanner.nextLine());
        System.out.println("Please enter chain id/Management id(17) for Employee to update:");
        int chainId = Integer.valueOf(scanner.nextLine());
        System.out.println("Enter storeid to update:");
        int storeId = Integer.valueOf(scanner.nextLine());

        String insertStr = "UPDATE employee Set first_name =?, last_name=? ,role =?, chain_id=?,store_id=? where id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, newFirstNameToUpdate);
            preparedStatement.setString(2, newlastNameToUpdate);
            preparedStatement.setInt(3, role);
            preparedStatement.setInt(4, chainId);
            preparedStatement.setInt(5, storeId);
            preparedStatement.setInt(6, idToUpdate);
            preparedStatement.executeUpdate();
        }
        System.out.println("New Employee Details:");
        presentSpecificEmployee(idToUpdate);

    }

    private void presentSpecificEmployee(int idToUpdate) throws SQLException {

        String insertStr = "SELECT * from employee where id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setInt(1, idToUpdate);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData metadata = rs.getMetaData();
                int cols = metadata.getColumnCount();
                for (int i = 1; i <= cols; ++i) {
                    System.out.printf("%15s",metadata.getColumnName(i));
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= cols; ++i) {
                        System.out.printf("%15s",rs.getObject(i));
                    }
                    System.out.println();
                }
            }
        }
    }

        private void getEmployeeByChainID() throws SQLException {
            System.out.println("Please enter chain id/Management id(17) for Employee:");
            int chainId = Integer.valueOf(scanner.nextLine());
            String sqlStr = "SELECT * FROM employee where chain_id= ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sqlStr)) {
                preparedStatement.setInt(1, chainId);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    ResultSetMetaData metadata = rs.getMetaData();
                    int cols = metadata.getColumnCount();
                    for (int i = 1; i <= cols; ++i) {
                        System.out.printf("%15s",metadata.getColumnName(i));
                    }
                    System.out.println();
                    while (rs.next()) {
                        for (int i = 1; i <= cols; ++i) {
                            System.out.printf("%15s",rs.getObject(i));
                        }
                        System.out.println();
                    }
                }
            }
        }
}


