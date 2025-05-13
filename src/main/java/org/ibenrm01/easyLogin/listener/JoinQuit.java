package org.ibenrm01.easyLogin.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.ibenrm01.easyLogin.Utility;
import org.ibenrm01.easyLogin.connYAML.Lang;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.setting.edit;
import org.ibenrm01.easyLogin.setting.search;

import java.util.HashMap;
import java.util.Map;

public class JoinQuit implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Map<String, String> places = new HashMap<>();
        places.put("username", p.getName());
        Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.player-join"), places)));
        String[] statue = search.getInstance().searchData(p.getUniqueId().toString());
        if (statue[0].equals("NonExists")) {
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.usage")));
        } else {
            if (!statue[3].equals("LoggedIn")) {
                p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.usage")));
            } else {
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.title")), ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.subtitle")), 20, 60, 20);
            }
        }
    }

    @EventHandler
    public boolean onQuitEvent(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Map<String, String> places = new HashMap<>();
        places.put("username", p.getName());
        String[] statue = edit.getInstance().editData(p.getUniqueId().toString(), "loggedin", "LogOut", "");
        if (!statue[0].equals("NonExists")) {
            if (statue[0].equals("Success")) {
                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.player-quit"), places)));
                return true;
            }
        } else {
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.player-quit"), places)));
            return true;
        }
        return true;
    }
}
