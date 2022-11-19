package net.orbitdev.aurorabungee.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;

import java.util.stream.Collectors;

public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", "aurora.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(CC.translate((AuroraBungee.getInstance().toggleMaintenance() ? AuroraBungee.getInstance().getConfig().getString("maintenance.on-message") : AuroraBungee.getInstance().getConfig().getString("maintenance.off-message"))));
        if (AuroraBungee.getInstance().isMaintenance()) {
            for (ProxiedPlayer players : AuroraBungee.getInstance().getProxy().getPlayers()) {
                if (players.hasPermission("aurora.maintenance.bypass")) {
                } else {
                    players.disconnect(CC.translate(AuroraBungee.getInstance().getConfig().getString("maintenance.cannot-join")));
                }
            }
        }
    }
}