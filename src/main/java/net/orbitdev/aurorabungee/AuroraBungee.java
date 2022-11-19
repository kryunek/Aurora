package net.orbitdev.aurorabungee;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.orbitdev.aurorabungee.commands.admin.*;
import net.orbitdev.aurorabungee.commands.staff.StaffChatCommand;
import net.orbitdev.aurorabungee.commands.staff.StaffListCommand;
import net.orbitdev.aurorabungee.commands.user.HubCommand;
import net.orbitdev.aurorabungee.commands.user.ReportCommand;
import net.orbitdev.aurorabungee.commands.user.RequestCommand;
import net.orbitdev.aurorabungee.commands.user.StreamCommand;
import net.orbitdev.aurorabungee.listener.ServerListener;
import net.orbitdev.aurorabungee.listener.StaffListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

@Getter
public final class AuroraBungee extends Plugin {

    public Configuration config;

    @Getter
    private static AuroraBungee instance;

    public ArrayList<ProxiedPlayer> staffchat = new ArrayList<>();
    private boolean maintenance;

    @Override
    public void onEnable() {
        instance = this;

        // Admin
        getProxy().getPluginManager().registerCommand(this, new UserInfoCommand());
        getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand());
        getProxy().getPluginManager().registerCommand(this, new FindCommand());
        getProxy().getPluginManager().registerCommand(this, new AlertCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffAlertCommand());
        getProxy().getPluginManager().registerCommand(this, new GeoIpCommand());

        // Staff
        getProxy().getPluginManager().registerCommand(this, new StaffListCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand());

        // User
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerCommand(this, new RequestCommand());
        getProxy().getPluginManager().registerCommand(this, new StreamCommand());

        // Listeners
        getProxy().getPluginManager().registerListener(this, new StaffListener());
        getProxy().getPluginManager().registerListener(this, new ServerListener());
        this.createConfig();
    }

    private void createConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getLogger().info("Config.yml not found, creating!");
            try {
                Files.copy(getResourceAsStream("config.yml"), file.toPath(), new java.nio.file.CopyOption[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadConfig(file);
    }

    private void loadConfig(File file) {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        getProxy().getPluginManager().unregisterCommands(this);
        getProxy().getPluginManager().unregisterListeners(this);

        instance = null;
    }

    public boolean toggleMaintenance() {
        return maintenance = !maintenance;
    }

    public ArrayList<ProxiedPlayer> getStaffchat() {
        return this.staffchat;
    }

}
