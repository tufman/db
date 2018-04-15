package jdbc;

import java.sql.*;

public class DBChain extends DBBase {

    public DBChain() throws SQLException {
        super("Chain");
    }

    void presentUserMenu() throws SQLException{
        boolean run = true;
        while (run){
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Present All Chains; [2] - Insert a new Chain; [3] - Update a Chain; [4] - Delete a Chain; [9] - Exit");
            int userSelection = scanner.nextInt();
            if (userSelection == 1){
                presentAllChains();
                //presentSpecificEmployee(0);
            }
            if (userSelection == 2){
                addChain();
            }
            if (userSelection == 3){
                //TODO updateChain();
            }
            if (userSelection == 4){
                //TODO deleteChain();
            }
            if (userSelection == 9){
                run = false;
            }
        }

    }

    private void addChain() throws SQLException{
        System.out.println("Enter Chain name:");
        String chainName = scanner.next();
        System.out.println("Enter Chain description:");
        String chainDesc = scanner.next();

        String insertStr = "INSERT into chain (name, description) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(insertStr)){
            preparedStatement.setString(1, chainName); // assume we have a String lastName
            preparedStatement.setString(2, chainDesc); //  // assume we have a String firstName
            preparedStatement.executeUpdate();
        }
    }

    private void presentAllChains() throws SQLException{
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from chain")){
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing chains in the System");
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
