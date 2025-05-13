package org.ibenrm01.easyLogin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ibenrm01.easyLogin.Utility;
import org.ibenrm01.easyLogin.connYAML.Lang;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.setting.edit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RePassword implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("op.repass")) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-permission")));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("repass.usage")));
            return false;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            OfflinePlayer pls = Bukkit.getOfflinePlayer(args[0]);
            Map<String, String> places = new HashMap<>();
            places.put("username", pls.getName());
            places.put("newpass", args[1]);
            String[] statue = edit.getInstance().editData(pls.getUniqueId().toString(), "newpassword", "loggedin", args[1]);
            if (statue[0].equals("NonExists")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.nonexists"), places)));
                return true;
            }
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("repass.success"), places)));
            return true;
        } else {
            Map<String, String> places = new HashMap<>();
            places.put("username", player.getName());
            places.put("newpass", args[1]);
            String[] statues = edit.getInstance().editData(player.getUniqueId().toString(), "newpassword", "loggedin", args[1]);
            if (statues[0].equals("NonExists")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.nonexists"), places)));
                return true;
            }
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("repass.title")), ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("repass.subtitle")), 20, 60, 20);
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("repass.success"), places)));
            return true;
        }
    }
}
