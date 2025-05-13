package org.ibenrm01.easyLogin.connYAML;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.easyLogin.EasyLogin;

import java.io.File;

public class Lang {

    private final static Lang instance = new Lang();

    private File file;
    private YamlConfiguration config;


    private Lang() {

    }

    public void load(String language) {
        file = new File(EasyLogin.getInstance().getDataFolder(), "lang/" + language + ".yml");
        if (!file.exists()) {
            EasyLogin.getInstance().saveResource("lang/" + language + ".yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static Lang getInstance() {
        return instance;
    }
}
