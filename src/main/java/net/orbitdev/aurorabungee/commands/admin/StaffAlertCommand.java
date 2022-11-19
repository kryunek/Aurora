package net.orbitdev.aurorabungee.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;

import java.util.stream.Collectors;

public class StaffAlertCommand extends Command {

    public StaffAlertCommand() {
        super("staffalert", "aurora.admin", "staffannounce");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage((CC.translate(AuroraBungee.getInstance().getConfig().getString("no-console"))));
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffalert.usage")));
            return;
        }
        String message1 = String.valueOf(CC.message(args, 0));
        TextComponent message = new TextComponent(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffalert.message").replace("{message}", message1) ));


        AuroraBungee.getInstance().getProxy().getPlayers()
                .stream()
                .filter(staff -> staff.hasPermission("aurora.staff"))
                .collect(Collectors.toList())
                .forEach(players -> players.sendMessage(message));
    }
}
