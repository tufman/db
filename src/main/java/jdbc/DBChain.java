package jdbc;

import java.sql.*;

public class DBChain extends DBBase implements DBIterface {

    private Connection con;

    public DBChain() throws SQLException {
        super("Chain");

    }

    @Override
    public void presentUserMenu() throws SQLException {
        boolean run = true;
        while (run) {
            con = super.getCon();
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Present All Chains; [2] - Insert a new Chain; [3] - Update a Chain; [4] - Delete a Chain; [9] - Exit");
            int userSelection = scanner.nextInt();
            if (userSelection == 1) {
                presentAllChains();

            }
            if (userSelection == 2) {
                addChain();
            }
            if (userSelection == 3) {
                updateChain();
            }
            if (userSelection == 4) {
                deleteChain();
            }
            if (userSelection == 9) {
                run = false;
            }
        }

    }

    private void addChain() throws SQLException {
        System.out.println("Enter Chain name:");
        String chainName = scanner.next();
        System.out.println("Enter Chain description:");
        String chainDesc = scanner.next();

        String insertStr = "INSERT into chain (name, description) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, chainName);
            preparedStatement.setString(2, chainDesc);
            preparedStatement.executeUpdate();
        }
    }

    private void presentAllChains() throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from chain")) {
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing chains in the System");
            int cols = metadata.getColumnCount();
            for (int i = 1; i <= cols; ++i) {
                System.out.print(metadata.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= cols; ++i) {
                    System.out.print(rs.getObject(i) + "\t\t");
                }
                System.out.println();
            }
        }
    }

    public void displayAllChains() throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from chain")) {
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing chains in the System");
            int cols = metadata.getColumnCount();
            for (int i = 1; i <= cols; ++i) {
                System.out.print(metadata.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= cols; ++i) {
                    System.out.print(rs.getObject(i) + "\t\t");
                }
                System.out.println();
            }
        }

    }

    private void updateChain() throws SQLException {
        System.out.println("Enter chain id to update:");
        int idToUpdate = scanner.nextInt();
        System.out.println("Current chain Details:");
        presentSpecificChain(idToUpdate);
        System.out.println("Enter  chain name to update:");
        String newChainName = scanner.next();
        System.out.println("Enter description to update:");
        String newChainDescription = scanner.next();

        String insertStr = "UPDATE chain Set name =?, description=? where id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, newChainName);
            preparedStatement.setString(2, newChainDescription);
            preparedStatement.setInt(3, idToUpdate);
            preparedStatement.executeUpdate();
        }
        System.out.println("New chain Details:");
        presentSpecificChain(idToUpdate);

    }

    private void presentSpecificChain(int idToUpdate) throws SQLException {

        String insertStr = "SELECT * from chain where id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setInt(1, idToUpdate); // assume we have a String lastName
            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData metadata = rs.getMetaData();
                int cols = metadata.getColumnCount();
                for (int i = 1; i <= cols; ++i) {
                    System.out.print(metadata.getColumnName(i) + "\t\t");
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= cols; ++i) {
                        System.out.print(rs.getObject(i) + "\t\t");
                    }
                    System.out.println();
                }
            }
        }
    }

    private void deleteChain() throws SQLException {
        System.out.println("Enter Chain id to delete:");
        String idToDelete = scanner.next();
        System.out.println("Are you sure you want to delete Chain ID: " + idToDelete + "? (Y/N)");
        presentSpecificChain(Integer.valueOf(idToDelete));
        String deleteApproval = scanner.next();
        if (deleteApproval.equalsIgnoreCase("Y") || deleteApproval.equalsIgnoreCase("yes")) {
            String insertStr = "DELETE from chain where id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                preparedStatement.setString(1, idToDelete); // assume we have a String lastName
                preparedStatement.executeUpdate();
                System.out.println("chain id " + idToDelete + " was deleted");
            }
        }
        if (deleteApproval.equalsIgnoreCase("N") || deleteApproval.equalsIgnoreCase("no")) {
            System.out.println("Chain id " + idToDelete + " was not deleted - canceled by user");

        }
    }
}
