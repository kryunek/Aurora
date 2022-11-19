package net.orbitdev.aurorabungee.commands.user;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub", "aurora.hub", "lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage((CC.translate(AuroraBungee.getInstance().getConfig().getString("no-console"))));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer)sender;
        if (player.getServer().getInfo().getName().equals(AuroraBungee.getInstance().getConfig().getString("hub.server"))) {
            player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("hub.already-connected")));
        } else {
            player.connect(ProxyServer.getInstance().getServerInfo(AuroraBungee.getInstance().getConfig().getString("hub.server")));
        }
        return;
    }
}
