package net.orbitdev.aurorabungee.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.CC;
import net.orbitdev.aurorabungee.utils.Country;

public class GeoIpCommand extends Command {

    public GeoIpCommand() {
        super("geoip", "aurora.admin", "country");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("geoip.usage")));
            return;
        }
        ProxiedPlayer target = AuroraBungee.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage((CC.translate(AuroraBungee.getInstance().getConfig().getString("geoip.not-found"))));
            return;
        }
        String IP = target.getAddress().getAddress().getHostAddress();
        String country = Country.getCountry(IP);
        TextComponent message = new TextComponent(CC.translate(AuroraBungee.getInstance().getConfig().getString("geoip.message").replace("{player}", target.getName()).replace("{country}", country)));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(CC.translate(AuroraBungee.getInstance().getConfig().getString("geoip.click-message").replace("{country}", country)))));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.google.com/maps/place/" + country));
        sender.sendMessage(message);
    }
}