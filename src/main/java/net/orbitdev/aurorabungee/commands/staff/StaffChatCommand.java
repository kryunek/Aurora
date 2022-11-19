package net.orbitdev.aurorabungee.commands.staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;

public class StaffChatCommand extends Command {
    public StaffChatCommand() {
        super("staffchat", "aurora.staffchat", "sc");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 0)
                if (AuroraBungee.getInstance().getStaffchat().contains(player)) {
                    (AuroraBungee.getInstance()).staffchat.remove(player);
                    player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffchat.off-message")));
                } else {
                    (AuroraBungee.getInstance()).staffchat.add(player);
                    player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffchat.on-message")));
                }
            if (args.length == 1) {
                String message1 = String.valueOf(CC.message(args, 0));
                for (ProxiedPlayer staff : ProxyServer.getInstance().getPlayers()) {
                    if (staff.hasPermission("aurora.staffchat")) ;
                    staff.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("staffchat.message").replace("{player}", player.getName()).replace("{server}", player.getServer().getInfo().getName()).replace("{message}", message1)));
                }
            }
        }
    }
}

