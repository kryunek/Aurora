package net.orbitdev.aurorabungee.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;


public class StaffListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        if (AuroraBungee.getInstance().isMaintenance()) {
            event.getResponse().setVersion(new ServerPing.Protocol(CC.translate(AuroraBungee.getInstance().getConfig().getString("maintenance.server.version")), 9999));
            for (String list : AuroraBungee.getInstance().getConfig().getStringList("maintenance.server.motd"))
            event.getResponse().setDescription(CC.translate(list));
        } else {
            for (String list : AuroraBungee.getInstance().getConfig().getStringList("default.server.motd"))
                event.getResponse().setDescription(CC.translate(list));
        }
        event.setResponse(event.getResponse());
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        if (AuroraBungee.getInstance().isMaintenance()) {
            if (event.getPlayer().hasPermission("aurora.maintenance.bypass")) {
                return;
            }
            event.setCancelled(true);
            event.getPlayer().disconnect(CC.translate(AuroraBungee.getInstance().getConfig().getString("maintenance.cannot-join")));
        }
    }

    @EventHandler
    public void onPlayerDisconnect(ServerDisconnectEvent event) {
        if (event.getPlayer().hasPermission("aurora.staff")) {
            for (ProxiedPlayer staff : AuroraBungee.getInstance().getProxy().getPlayers()) {
                if (staff.hasPermission("aurora.staff")) {
                    staff.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staff.join").replace("{player}", event.getPlayer().getName()).replace("{server}", event.getPlayer().getServer().getInfo().getName())));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if (event.getPlayer().hasPermission("aurora.staff")) {
            for (ProxiedPlayer staff : AuroraBungee.getInstance().getProxy().getPlayers()) {
                if (staff.hasPermission("aurora.staff")) {
                    staff.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staff.join").replace("{player}", event.getPlayer().getName()).replace("{server}", event.getPlayer().getServer().getInfo().getName())));

                }

            }
        }
    }
    @EventHandler
    public void onPlayerDisconnect(ServerSwitchEvent event) {
        if (event.getFrom() == null){

        } else {
        if (event.getPlayer().hasPermission("aurora.staff")) {
            for (ProxiedPlayer staff : AuroraBungee.getInstance().getProxy().getPlayers()) {
                if (staff.hasPermission("aurora.staff")) {
                    staff.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staff.switch").replace("{player}", event.getPlayer().getName()).replace("{server}", event.getPlayer().getServer().getInfo().getName()).replace("{server-from}", event.getFrom().getName())));
                }
            }
        }
    }
    }
    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer)event.getSender();
        if (AuroraBungee.getInstance().getStaffchat().contains(player))
            if (event.getMessage().startsWith("/")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
                for (ProxiedPlayer staff : ProxyServer.getInstance().getPlayers()) {
                    if (staff.hasPermission("aurora.staffchat"))
                    staff.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffchat.message").replace("{player}", player.getName()).replace("{server}", player.getServer().getInfo().getName()).replace("{message}", event.getMessage())));
                }
            }
    }
}