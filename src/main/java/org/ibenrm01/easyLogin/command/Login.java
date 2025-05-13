package org.ibenrm01.easyLogin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ibenrm01.easyLogin.connYAML.Lang;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.setting.edit;
import org.ibenrm01.easyLogin.setting.search;
import org.jetbrains.annotations.NotNull;

public class Login implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-permission")));
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.usage")));
            return false;
        }
        if (args[0].isEmpty()) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-password")));
            return false;
        }
        String[] statue = search.getInstance().searchData(p.getUniqueId().toString());
        if (statue[0].equals("NonExists")) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.nonexists")));
            return false;
        } else {
            if (!statue[2].equals(args[0])) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.wrong-password")));
                return false;
            } else if (statue[3].equals("LoggedIn")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.already")));
                return false;
            }
            String[] editStatue = edit.getInstance().editData(p.getUniqueId().toString(), "loggedin", "LoggedIn", "");
            if (editStatue[0].equals("Success")) {
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.title")), ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.subtitle")), 20, 60, 20);
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("login.success")));
            }
        }
        return true;
    }
}
