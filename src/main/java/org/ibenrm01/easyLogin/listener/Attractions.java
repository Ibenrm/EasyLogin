package org.ibenrm01.easyLogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.ibenrm01.easyLogin.connYAML.Setting;
import org.ibenrm01.easyLogin.setting.search;

public class Attractions implements Listener {

    private boolean onMoves = Setting.getInstance().getConfig().getBoolean("no_move");
    private boolean onBreaks = Setting.getInstance().getConfig().getBoolean("no_break");
    private boolean onChats = Setting.getInstance().getConfig().getBoolean("no_chat");

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        String[] statue = search.getInstance().searchData(player.getUniqueId().toString());
        if (statue[0].equals("NonExists")) {
            e.setCancelled(onMoves);
        } else if (statue[0].equals("Success")) {
            if (statue[3].equals("LoggedIn")) {
                e.setCancelled(!onMoves);
            } else {
                e.setCancelled(onMoves);
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        String[] statue = search.getInstance().searchData(player.getUniqueId().toString());
        if (statue[0].equals("NonExists")) {
            e.setCancelled(onChats);
        } else if (statue[0].equals("Success")) {
            if (statue[3].equals("LoggedIn")) {
                e.setCancelled(!onChats);
            } else {
                e.setCancelled(onChats);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        String[] statue = search.getInstance().searchData(player.getUniqueId().toString());
        if (statue[0].equals("NonExists")) {
            e.setCancelled(onBreaks);
        } else if (statue[0].equals("Success")) {
            if (statue[3].equals("LoggedIn")) {
                e.setCancelled(!onBreaks);
            } else {
                e.setCancelled(onBreaks);
            }
        }
    }
}
