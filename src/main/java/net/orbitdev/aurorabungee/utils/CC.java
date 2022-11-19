package net.orbitdev.aurorabungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CC {

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static String message(String[] args, int x) {
        StringBuilder builder = new StringBuilder();

        for (int i = x; i < args.length; ++i) {
            builder.append(args[i]);
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}
