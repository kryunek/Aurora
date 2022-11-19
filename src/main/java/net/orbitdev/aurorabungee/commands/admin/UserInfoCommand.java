package net.orbitdev.aurorabungee.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.orbitdev.aurorabungee.AuroraBungee;
import net.orbitdev.aurorabungee.utils.Country;
import net.orbitdev.aurorabungee.utils.CC;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        super("userinfo", "aurora.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("userinfo.usage")));
            return;
        }

        ProxiedPlayer target = AuroraBungee.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(CC.translate(AuroraBungee.getInstance().getConfig().getString("userinfo.not-found")));
            return;
        }
        String IP = target.getAddress().getAddress().getHostAddress();
        String UUID =String.valueOf(target.getUUID());
        String ping =String.valueOf(target.getPing());
        String country = Country.getCountry(IP);
        String server = target.getServer().getInfo().getName();
        for (String list : AuroraBungee.getInstance().getConfig().getStringList("userinfo.message"))
            sender.sendMessage(CC.translate(list).replace("{player}", target.getName()).replace("{UUID}", UUID).replace("{ping}", ping).replace("{IP}", IP).replace("{country}", country).replace("{server}", server));
    }
}