package net.orbitdev.aurorabungee.commands.staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;

import java.util.ArrayList;
import java.util.List;

public class StaffListCommand extends Command {

    public StaffListCommand() {
        super("stafflist", "aurora.stafflist", "staffs");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage((CC.translate(AuroraBungee.getInstance().getConfig().getString("no-console"))));
            return;
        }
        List<String> staffs = new ArrayList<>();
        AuroraBungee.getInstance().getProxy().getPlayers()
                .stream()
                .filter(ProxiedPlayer::isConnected).filter(staffM -> staffM.hasPermission("aurora.staff"))
                .forEach(player -> staffs.add(player.getName())
                );
        List<String> players = new ArrayList<>();
        AuroraBungee.getInstance().getProxy().getPlayers()
                .stream()
                .forEach(player -> players.add(player.getName())
                );
        String playerssize =String.valueOf(players.size());
        String staffssize =String.valueOf(staffs.size());
        String stafflist =String.valueOf(staffs);
        for (String list : AuroraBungee.getInstance().getConfig().getStringList("stafflist.message"))
            sender.sendMessage(CC.translate(list).replace("{players-total}", playerssize).replace("{staffs-total}", staffssize).replace("{staffs}", stafflist));;
    }
}

