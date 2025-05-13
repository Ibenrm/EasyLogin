package org.ibenrm01.easyLogin.setting;

import org.ibenrm01.easyLogin.EasyLogin;
import org.ibenrm01.easyLogin.connYAML.MySQL;

import java.sql.*;

public class create {

    private final static create instance = new create();

    private create() {
    }

    public String[] createUser(String ids, String password) {
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
                rs.close();
                checkStmt.close();
                conn.close();
                return new String[]{"Exists", "sudah ada id"};
            }

            rs.close();
            checkStmt.close();
            String insertSQL = "INSERT INTO playerLogin (ids, password, status) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setString(1, ids);
            insertStmt.setString(2, password);
            insertStmt.setString(3, "LogOut");
            insertStmt.executeUpdate();
            insertStmt.close();
            conn.close();
            return new String[]{"Success", "Berhasil dibuat"};
        } catch (SQLException e) {
            EasyLogin.getInstance().getLogger().warning("Failed to connect to database!");
            e.printStackTrace();
        }
        return new String[]{"error", "hubungin developer"};
    }


    public static create getInstance() {
        return instance;
    }
}
