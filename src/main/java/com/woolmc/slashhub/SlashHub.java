package com.woolmc.slashhub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class SlashHub extends Plugin {

    public static Configuration config;
    public static SlashHub plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand("hub"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyCommand("lobby"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand("hubreload"));
        getLogger().info(ChatColor.translateAlternateColorCodes('&', "&bSuccessfully started!"));
        plugin = this;
    }

    public void loadConfiguration() throws IOException {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getDataFolder().mkdir();
            Files.copy(this.getResourceAsStream("config.yml"),
                    configFile.toPath());
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aConfig Created"));
        }
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
