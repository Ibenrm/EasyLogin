package org.ibenrm01.easyLogin.setting;

import org.ibenrm01.easyLogin.EasyLogin;
import org.ibenrm01.easyLogin.connYAML.MySQL;

import javax.annotation.Nullable;
import java.sql.*;

public class edit {
    private final static edit instance = new edit();

    private edit() {
    }

    public String[] editData(String ids, String type, @Nullable String status, @Nullable String password) {
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
                if (type.equals("newpassword")) {
                    String updateSQL = "UPDATE playerLogin SET password = ? WHERE ids = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                    updateStmt.setString(1, password);
                    updateStmt.setString(2, ids);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                    rs.close();
                    checkStmt.close();
                    conn.close();
                    return new String[]{"Success", "Password berhasil diubah"};
                } else if (type.equals("loggedin")) {
                    String updateSQLs = "UPDATE playerLogin SET status = ? WHERE ids = ?";
                    PreparedStatement updateStmts = conn.prepareStatement(updateSQLs);
                    updateStmts.setString(1, status);
                    updateStmts.setString(2, ids);
                    updateStmts.executeUpdate();
                    updateStmts.close();
                    rs.close();
                    checkStmt.close();
                    conn.close();
                    return new String[]{"Success", "Status login dirubah"};
                }

            } else {
                rs.close();
                checkStmt.close();
                conn.close();
                return new String[]{"NonExists", "ID tidak ditemukan"};
            }
        } catch (SQLException e) {
            EasyLogin.getInstance().getLogger().warning("Failed to connect to database!");
            e.printStackTrace();
        }
        return new String[]{"error", "hubungin developer"};
    }

    public static edit getInstance() {
        return instance;
    }
}
