package org.ibenrm01.easyLogin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ibenrm01.easyLogin.connYAML.Lang;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.setting.create;
import org.jetbrains.annotations.NotNull;

public class Register implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-permission")));
            return false;
        }
        Player p = (Player) sender;
        if (args.length < 2) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.usage")));
            return false;
        }
        if (args[0].isEmpty() || args[1].isEmpty()) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("general.no-password")));
            return false;
        }
        if (!args[1].equals(args[0])) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.must-same")));
            return false;
        }
        String[] statue = create.getInstance().createUser(p.getUniqueId().toString(), args[1]);
        if (statue[0].equals("Exists")) {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.exists")));
            return false;
        }
        if (statue[0].equals("Success")) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.title")), ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.subtitle")), 20, 60, 20);
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.pre-success")));
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + Setting.getInstance().getConfig().getString("serverName") + " " + ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', Lang.getInstance().getConfig().getString("register.success")));
            return true;
        }
        return false;
    }
}
