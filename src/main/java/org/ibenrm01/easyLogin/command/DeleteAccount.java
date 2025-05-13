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
import org.ibenrm01.easyLogin.setting.delete;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DeleteAccount implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("op.deleteacc")) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-permission")));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("deleteacc.usage")));
            return false;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            OfflinePlayer pls = Bukkit.getOfflinePlayer(args[0]);
            Map<String, String> places = new HashMap<>();
            places.put("username", pls.getName());
            String[] statue = delete.getInstance().deleteData(pls.getUniqueId().toString());
            if (statue[0].equals("NonExists")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.nonexists"), places)));
                return false;
            }
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("deleteacc.success"), places)));
            return true;
        } else {
            Map<String, String> place = new HashMap<>();
            place.put("username", player.getName());
            String[] statues = delete.getInstance().deleteData(player.getUniqueId().toString());
            if (statues[0].equals("NonExists")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("general.nonexists"), place)));
                return false;
            }
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("deleteacc.title")), ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("deleteacc.subtitle")), 20, 60, 20);
            player.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + Lang.getInstance().getConfig().getString("register.usage"));
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Utility.getInstance().replace(Lang.getInstance().getConfig().getString("deleteacc.success"), place)));
            return true;
        }
    }
}
