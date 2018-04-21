package jdbc;

import java.sql.*;

public class DBStore extends DBBase implements DBIterface {

    private Connection con;

    public DBStore() throws SQLException {
        super("Store");

    }

    @Override
    public void presentUserMenu() throws SQLException {
        boolean run = true;
        while (run) {
            con = super.getCon();
            System.out.println(ANSI_RESET + "Please select:");
            System.out.println("[1] - Present All Stores; [2] - Insert a new Store; [3] - Update a Store; [4] - Delete a Store; [9] - Exit");
            int userSelection = scanner.nextInt();
            if (userSelection == 1) {
                presentAllStores();

            }
            if (userSelection == 2) {
                addStore();
            }
            if (userSelection == 3) {
                updateStore();
            }
            if (userSelection == 4) {
               deleteStore();
            }
            if (userSelection == 9) {
                run = false;
            }
        }

    }

    private void addStore() throws SQLException {
        System.out.println("Enter Store name:");
        String storeName = scanner.next();
        System.out.println("Enter Store address :");
        int storeAdressNum = scanner.nextInt();
        System.out.println("Enter chain id for store :");
        int chain_Id = scanner.nextInt();
        System.out.println("Enter Store number :");
        int store_Num = scanner.nextInt();

        String insertStr = "INSERT into store (name, adress,chain_id,store_num) VALUES (?, ?,?,?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, storeName);
            preparedStatement.setInt(2, storeAdressNum);
            preparedStatement.setInt(3, chain_Id);
            preparedStatement.setInt(4, store_Num);
            preparedStatement.executeUpdate();
        }
    }

    private void presentAllStores() throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from store")) {
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println(ANSI_GREEN + " All existing stores in the System");
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

    private void updateStore() throws SQLException {
        System.out.println("Enter Store id to update:");
        int idToUpdate = scanner.nextInt();
        System.out.println("Current Store Details:");
        presentSpecificStore(idToUpdate);


        System.out.println("Enter Store name:");
        String storeName = scanner.next();
        System.out.println("Enter Store address to update :");
        int storeAdressNum = scanner.nextInt();
        System.out.println("Enter chain id for store to update:");
        int chain_Id = scanner.nextInt();
        System.out.println("Enter Store number to update :");
        int store_Num = scanner.nextInt();


        String insertStr = "UPDATE store Set name = ?, adress = ?, chain_id = ?, store_num= ?  WHERE id=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setString(1, storeName);
            preparedStatement.setInt(2, storeAdressNum);
            preparedStatement.setInt(3, chain_Id);
            preparedStatement.setInt(4, store_Num);
            preparedStatement.setInt(5, idToUpdate);
            preparedStatement.executeUpdate();
        }
        System.out.println("New Store Details:");
        presentSpecificStore(idToUpdate);

    }

    private void presentSpecificStore(int idToUpdate) throws SQLException {

        String insertStr = "SELECT * from store where id = ?";
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

    private void deleteStore() throws SQLException{
        System.out.println("Enter Store id to delete:");
        String idToDelete = scanner.next();
        System.out.println("Are you sure you want to delete store ID: " + idToDelete
                + " \n(Y/N)?");
        presentSpecificStore(Integer.valueOf(idToDelete));
        String deleteApproval = scanner.next();
        if (deleteApproval.equalsIgnoreCase("Y") || deleteApproval.equalsIgnoreCase( "yes")) {
            String insertStr = "DELETE from store where id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                preparedStatement.setString(1, idToDelete); // assume we have a String lastName
                preparedStatement.executeUpdate();
                System.out.println("Store id " + idToDelete + " was deleted");
            }
            }if (deleteApproval.equalsIgnoreCase("N") || deleteApproval.equalsIgnoreCase( "no")) {
            System.out.println("Store id " + idToDelete + " was not deleted - canceled by user");
        }

    }
}
