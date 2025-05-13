package org.ibenrm01.easyLogin.connYAML;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ibenrm01.easyLogin.EasyLogin;

import java.io.File;

public class Setting {

    private final static Setting instance = new Setting();

    private File file;
    private YamlConfiguration config;

    private Setting() {
    }

    public void load() {
        file = new File(EasyLogin.getInstance().getDataFolder(), "settings.yml");
        if (!file.exists()) {
            EasyLogin.getInstance().saveResource("settings.yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static Setting getInstance() {
        return instance;
    }
}
