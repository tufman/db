package DB;

import java.sql.*;


public class DBEmployee extends DBBase {



    public DBEmployee() throws SQLException{
        super("Employee");
    }

    void presentUserMenu() throws SQLException{
        boolean run = true;
        while (run){
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Present All Employees; [2] - Insert a new Employee; [3] - Update an Employee; [4] - Delete an Employee; [9] - Exit");
            int userSelection = scanner.nextInt();
            if (userSelection == 1){
                presentAllEmployees();
                //presentSpecificEmployee(0);
            }
            if (userSelection == 2){
                addNewEmployee();
            }
            if (userSelection == 3){
                updateEmployee();
            }
            if (userSelection == 4){
                deleteEmployee();
            }
            if (userSelection == 9){
                run = false;
            }
        }

    }

    private void deleteEmployee() throws SQLException{
        System.out.println("Enter Employee id to delete:");
        String idToDelete = scanner.next();
        System.out.println("Are you sure you want to delete Employee ID: " + idToDelete + "? (Y/N)");
        presentSpecificEmployee(Integer.valueOf(idToDelete));
        String deleteApproval = scanner.next();
        if (deleteApproval.equalsIgnoreCase("Y") || deleteApproval.equalsIgnoreCase( "yes")) {
            String insertStr = "DELETE from employee where id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                preparedStatement.setString(1, idToDelete); // assume we have a String lastName
                preparedStatement.executeUpdate();
            }
        }
        System.out.println("Employee id " + idToDelete + " was deleted");
    }

    private void presentAllEmployees() throws SQLException{
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from employee")){
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing employees in the System");
            int cols = metadata.getColumnCount();
            for(int i=1; i<=cols; ++i) {
                System.out.print(metadata.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (rs.next()) {
                for(int i=1; i<=cols; ++i) {
                    System.out.print(rs.getObject(i) + "\t\t");
                }
                System.out.println();
            }
        }
    }

    private void addNewEmployee() throws SQLException {

        System.out.println("Enter Employee:");
        System.out.println("Please enter First Name:");
        String firstName = scanner.next();
        System.out.println("Please enter Last Name:");
        String lastName = scanner.next();

        //INSERT into employee (FN, LN) VALUES ("Shay1", "Tufman1")
        String insertStr = "INSERT into employee (first_name, last_name) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(insertStr)){
            preparedStatement.setString(1, firstName); // assume we have a String lastName
            preparedStatement.setString(2, lastName); //  // assume we have a String firstName
            preparedStatement.executeUpdate();
        }
    }

    private void updateEmployee() throws SQLException{
        System.out.println("Enter Employee id to update:");
        int idToUpdate = scanner.nextInt();
        System.out.println("Current Employee Details:");
        presentSpecificEmployee(idToUpdate);
        System.out.println("Enter New FirstName to update:");
        String newFirstNameToUpdate = scanner.next();
        System.out.println("Enter New LastName to update:");
        String newlastNameToUpdate = scanner.next();

        String insertStr = "UPDATE employee Set first_name = ?, last_name = ? where id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(insertStr)){
            preparedStatement.setString(1, newFirstNameToUpdate); // assume we have a String lastName
            preparedStatement.setString(2, newlastNameToUpdate); //  // assume we have a String firstName
            preparedStatement.setInt(3, idToUpdate); //  // assume we have a String firstName
            preparedStatement.executeUpdate();
        }
        System.out.println("New Employee Details:");
        presentSpecificEmployee(idToUpdate);

    }

    private void presentSpecificEmployee(int idToUpdate) throws SQLException{

        String insertStr = "SELECT * from employee where id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(insertStr)){
            preparedStatement.setInt(1, idToUpdate); // assume we have a String lastName
            try(ResultSet rs = preparedStatement.executeQuery()){
                ResultSetMetaData metadata = rs.getMetaData();
                int cols = metadata.getColumnCount();
                for(int i=1; i<=cols; ++i) {
                    System.out.print(metadata.getColumnName(i) + "\t\t");
                }
                System.out.println();
                while (rs.next()) {
                    for(int i=1; i<=cols; ++i) {
                        System.out.print(rs.getObject(i) + "\t\t");
                    }
                    System.out.println();
                }
            }
        }
    }
}
