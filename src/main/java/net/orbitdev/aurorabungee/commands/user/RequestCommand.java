package net.orbitdev.aurorabungee.commands.user;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.Cooldown;
import net.orbitdev.aurorabungee.utils.CC;

import java.util.stream.Collectors;

public class RequestCommand extends Command {

    public RequestCommand() {
        super("request", "aurora.request", "helpop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage((CC.translate(AuroraBungee.getInstance().getConfig().getString("no-console"))));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 0) {
            player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("request.usage")));
            return;
        }

        if (!Cooldown.checkCooldown(player, "requestCooldown", AuroraBungee.getInstance().getConfig().getInt("request.cooldown")  * 1000L - System.currentTimeMillis())) {
            long cooldownCount = Cooldown.getCooldown(player, "requestCooldown") / 1000L + 1L;
            String cooldown = String.valueOf(cooldownCount);
            player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("request.cooldown-message").replace("{cooldown}", cooldown)));
            return;
        }
        String reason = String.valueOf(CC.message(args, 0));
        TextComponent message = new TextComponent(CC.translate(AuroraBungee.getInstance().getConfig().getString("request.staff-message").replace("{requester}", player.getName()).replace("{server}", player.getServer().getInfo().getName()).replace("{reason}", reason)));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(CC.translate(AuroraBungee.getInstance().getConfig().getString("report.click-message").replace("{server}", player.getServer().getInfo().getName())))));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + player.getServer().getInfo().getName()));

        AuroraBungee.getInstance().getProxy().getPlayers()
                .stream()
                .filter(staff -> staff.hasPermission("aurora.staff"))
                .collect(Collectors.toList())
                .forEach(staff -> staff.sendMessage(message));

        player.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("request.sent-message")));
        Cooldown.setCooldown(player, "requestCooldown", AuroraBungee.getInstance().getConfig().getInt("request.cooldown") * 1000L);
    }
}