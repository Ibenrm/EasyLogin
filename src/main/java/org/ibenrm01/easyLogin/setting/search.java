package org.ibenrm01.easyLogin.setting;

import org.ibenrm01.easyLogin.EasyLogin;
import org.ibenrm01.easyLogin.connYAML.MySQL;

import java.sql.*;

public class search {
    private final static search instance = new search();

    private search() {
    }

    public String[] searchData(String ids) {
        String[] datas = MySQL.getInstance().getDatabase();
        String pass = (datas[2] == null || datas[2].contains("kosong")) ? "" : datas[2];
        String url = "jdbc:mysql://" + datas[0] + ":" + datas[4] + "/" + datas[1];
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, datas[3], pass);
            String checkSQL = "SELECT COUNT(*) FROM playerLogin WHERE ids = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setString(1, ids);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                String updateSQL = "SELECT * FROM playerLogin WHERE ids = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setString(1, ids);
                ResultSet resultSet = updateStmt.executeQuery();
                if (resultSet.next()) {
                    String id = resultSet.getString("ids");
                    String passwords = resultSet.getString("password");
                    String status = resultSet.getString("status");
                    updateStmt.close();
                    rs.close();
                    checkStmt.close();
                    conn.close();
                    return new String[]{"Success", id, passwords, status};
                }
                return new String[]{"NonExists", "ID tidak ditemukan"};
            } else {
                rs.close();
                checkStmt.close();
                conn.close();
                return new String[]{"NonExists", "ID tidak ditemukan"};
            }

        } catch (SQLException ex) {
            EasyLogin.getInstance().getLogger().warning("Failed to connect to database!");
            ex.printStackTrace();
        }

        return new String[]{"Error", "Hubungin developer"};
    }

    public static search getInstance() {
        return instance;
    }
}
