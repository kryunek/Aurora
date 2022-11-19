package net.orbitdev.aurorabungee.commands.user;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;
import net.orbitdev.aurorabungee.utils.Cooldown;

public class StreamCommand extends Command {
    public StreamCommand() {
        super("stream", "aurora.stream", "directo");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 0) {
                sender.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("stream.usage")));
                return;
            }
            if (!Cooldown.checkCooldown(player, "streamCooldown", AuroraBungee.getInstance().getConfig().getInt("stream.cooldown") * 1000L - System.currentTimeMillis())) {
                long cooldownCount = Cooldown.getCooldown(player, "streamCooldown") / 1000L + 1L;
                String cooldown = String.valueOf(cooldownCount);
                player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("stream.cooldown-message").replace("{cooldown}", cooldown)));
                return;
            }
            if (args.length == 1) {
                String message1 = String.valueOf(CC.message(args, 0));
                for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                    for (String list : AuroraBungee.getInstance().getConfig().getStringList("stream.message")) {
                        players.sendMessage(CC.translate(list).replace("{player}", player.getName()).replace("{server}", player.getServer().getInfo().getName()).replace("{link}", message1));
                        Cooldown.setCooldown(player, "streamCooldown", AuroraBungee.getInstance().getConfig().getInt("stream.cooldown") * 1000L);
                    }
                }
            }
        }
    }
}
