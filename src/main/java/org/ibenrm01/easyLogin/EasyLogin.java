package org.ibenrm01.easyLogin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.ibenrm01.easyLogin.command.DeleteAccount;
import org.ibenrm01.easyLogin.command.Login;
import org.ibenrm01.easyLogin.command.RePassword;
import org.ibenrm01.easyLogin.command.Register;
import org.ibenrm01.easyLogin.connYAML.Lang;
import org.ibenrm01.easyLogin.connYAML.MySQL;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.listener.Attractions;
import org.ibenrm01.easyLogin.listener.JoinQuit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class EasyLogin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("has been enabled!");

        MySQL.getInstance().load();
        Setting.getInstance().load();
        Lang.getInstance().load(Setting.getInstance().getConfig().getString("lang"));

        getServer().getPluginManager().registerEvents(new JoinQuit(), this);
        getServer().getPluginManager().registerEvents(new Attractions(), this);
        getCommand("login").setExecutor(new Login());
        getCommand("register").setExecutor(new Register());
        getCommand("deleteacc").setExecutor(new DeleteAccount());
        getCommand("repassword").setExecutor(new RePassword());

        String[] datas = MySQL.getInstance().getDatabase();
        String password = (datas[2] == null || datas[2].contains("kosong")) ? "" : datas[2];
        startConn(datas[3], password, datas[0], datas[4], datas[1]);
    }

    public void startConn(String user, String pass, String host, String port, String database) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            getLogger().info("Connected to database successfully!");

            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS playerLogin(ids varchar(100), password varchar(100), status varchar(100))";
            statement.execute(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            getLogger().warning("Failed to connect to database!");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("has been disabled");
        MySQL.getInstance().save();
    }

    public static EasyLogin getInstance() {
        return getPlugin(EasyLogin.class);
    }
}
