package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            System.out.println("[1] - Present All Stores; [2] - Insert a new Store; [3] - Update a Store; [4] - Delete a Store;\n [5] - Present Stores By Mall id; [6] - Present Stores By Mall Group; [7] - Display All Store Details;  [9] - Exit");
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
            if (userSelection == 5) {
                displayStoresByMallId();
            }
            if (userSelection == 6) {
                displayStoresByMallGroupId();
                //TODO:
                //need to understand ???//
            }
            if (userSelection == 7) {
                getAllStoreDetails();
                //TODO:
                //need to undestand request //
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

    private Map<String, Object> showSpecificStore(int storeId) throws SQLException {
        Map<String, Object> storeDetails = new HashMap<>();
        String insertStr = "SELECT * from store where id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setInt(1, storeId); // assume we have a String lastName
            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData metadata = rs.getMetaData();
                int cols = metadata.getColumnCount();

                rs.next();
                for (int i = 1; i <= cols; ++i) {

                    // System.out.print(metadata.getColumnName(i) + "\t\t");

                    storeDetails.put(metadata.getColumnName(i), rs.getObject(i));
                }
               /* for (int i = 1; i <= cols; ++i) {
                        System.out.print(rs.getObject(i) + "\t\t");
                    }
                    System.out.println();*/

            }
        }

        return storeDetails;
    }


    private void deleteStore() throws SQLException {
        System.out.println("Enter Store id to delete:");
        String idToDelete = scanner.next();
        System.out.println("Are you sure you want to delete store ID: " + idToDelete
                + " \n(Y/N)?");
        presentSpecificStore(Integer.valueOf(idToDelete));
        String deleteApproval = scanner.next();
        if (deleteApproval.equalsIgnoreCase("Y") || deleteApproval.equalsIgnoreCase("yes")) {
            String insertStr = "DELETE from store where id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                preparedStatement.setString(1, idToDelete); // assume we have a String lastName
                preparedStatement.executeUpdate();
                System.out.println("Store id " + idToDelete + " was deleted");
            }
        }
        if (deleteApproval.equalsIgnoreCase("N") || deleteApproval.equalsIgnoreCase("no")) {
            System.out.println("Store id " + idToDelete + " was not deleted - canceled by user");
        }

    }


    private void displayStoresByMallId() throws SQLException {
        System.out.println("Enter mall id :");
        int idToFind = scanner.nextInt();
        String insertStr = "Select *  from store LEFT JOIN mall2store ON  store.id = store_id WHERE mall_id = ? ;";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setInt(1, idToFind);
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

    private void displayStoresByMallGroupId() throws SQLException {
        ArrayList<Integer> listOfMallWithMallGroup = new ArrayList<>();
        System.out.println("Enter mall Group id :");
        int idToFind = scanner.nextInt();
        String insertStr = "SELECT id  from mall where mall_group = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
            preparedStatement.setInt(1, idToFind);
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
                        listOfMallWithMallGroup.add(rs.getInt(1));
                    }
                    System.out.println();
                }

                for (Integer myint : listOfMallWithMallGroup) {

                    String insertStr2 = "Select *  from store LEFT JOIN mall2store ON  store.id = store_id WHERE mall_id = ? ;";
                    try (PreparedStatement preparedStatement2 = con.prepareStatement(insertStr2)) {
                        preparedStatement2.setInt(1, myint);
                        try (ResultSet rs2 = preparedStatement2.executeQuery()) {
                            ResultSetMetaData metadata2 = rs2.getMetaData();
                            int cols2 = metadata2.getColumnCount();
                            for (int i = 1; i <= cols2; ++i) {
                                System.out.print(metadata2.getColumnName(i) + "\t\t");
                            }
                            System.out.println();
                            while (rs2.next()) {
                                for (int i = 1; i <= cols2; ++i) {
                                    System.out.print(rs.getObject(i) + "\t\t");
                                }
                                System.out.println();
                            }

                        }

                    }

                }
            }
        }
    }

    private void getAllStoreDetails() throws SQLException {
        System.out.println("To view all Store Details  - Enter Store id :");
        int storeId = scanner.nextInt();
        Map<String, Object> storeDetails = showSpecificStore(storeId);
        System.out.println("Store Details : ");
        for (Map.Entry<String, Object> columnName : storeDetails.entrySet()) {
            if (columnName.getKey().equals("id")) {
                System.out.println("Store ID: " + columnName.getValue());
            }
            if (columnName.getKey().equals("name")) {
                System.out.println("Store Name : " + columnName.getValue());
            }
            if (columnName.getKey().equals("store_num")) {
                System.out.println("Store Number : " + columnName.getValue());
            }
            if (columnName.getKey().equals("adress")) {
                String searchAdressViaId = "SELECT adress from adress  where id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(searchAdressViaId)) {
                    preparedStatement.setInt(1, (Integer) columnName.getValue());
                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        ResultSetMetaData metadata = rs.getMetaData();

                        int cols = metadata.getColumnCount();
                        for (int i = 1; i <= cols; ++i) {
                         //   System.out.print(metadata.getColumnName(i) + "\t\t");

                        }
                        //System.out.println();
                        while (rs.next()) {
                            for (int i = 1; i <= cols; ++i) {
                                //System.out.print(rs.getObject(i) + "\t\t");
                                System.out.println("Address for Store ID ["+ storeId +"]  is : "+rs.getObject(i));
                            }
                            System.out.println();
                        }
                    }
                }
            }if (columnName.getKey().equals("chain_id")) {
                String searchValue = "SELECT name  from chain  where id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(searchValue)) {
                    preparedStatement.setInt(1, (Integer) columnName.getValue());
                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        ResultSetMetaData metadata = rs.getMetaData();

                        int cols = metadata.getColumnCount();
                        for (int i = 1; i <= cols; ++i) {

                        }
                        System.out.println();
                        while (rs.next()) {
                            for (int i = 1; i <= cols; ++i) {
                              System.out.println("Chain Name for Store ID ["+ storeId +"]  is : "+rs.getObject(i));

                            }

                        }
                    }
                }

            }

        }

    }
}





             /*   //System.out.println("Employees for Store id " + storeId);
                String searchEmployeePerStoreId = "SELECT *  from employee  where store_id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(searchEmployeePerStoreId)) {
                    preparedStatement.setInt(1, storeId);
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

                System.out.println("Address for Store id " + storeId);
                String insertStr = "SELECT adress  from adress  where id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(insertStr)) {
                    preparedStatement.setInt(1, storeId);
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
        }
    }*/



