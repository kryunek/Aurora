package net.orbitdev.aurorabungee.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.orbitdev.aurorabungee.AuroraBungee;

public class ServerListener implements Listener {


    public void onStopServer(ServerDisconnectEvent e) {
        ProxiedPlayer player = (ProxiedPlayer) e.getPlayer();
        player.connect(ProxyServer.getInstance().getServerInfo(AuroraBungee.getInstance().getConfig().getString("hub.server")));

    }
}

