package org.ibenrm01.easyLogin.connYAML;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.easyLogin.EasyLogin;

import java.io.File;

public class MySQL {

    private final static MySQL instance = new MySQL();

    private File file;
    private YamlConfiguration config;

    private String host;
    private String database;
    private String password;
    private String username;
    private Integer port;

    private MySQL() {
    }

    public void load() {
        file = new File(EasyLogin.getInstance().getDataFolder(), "connect.yml");
        if (!file.exists()) {
            EasyLogin.getInstance().saveResource("connect.yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        host = config.getString("host");
        database = config.getString("database");
        password = config.getString("passWord");
        username = config.getString("userName");
        port = config.getInt("port");
    }

    public String[] getDatabase() {
        return new String[]{host, database, password, username, String.valueOf(port)};
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MySQL getInstance() {
        return instance;
    }
}
