package com.woolmc.slashhub;

import com.woolmc.slashhub.Commands.HubCommand;
import com.woolmc.slashhub.Commands.ReloadCommand;
import com.woolmc.slashhub.Updater.UpdateChecker;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public final class Main extends Plugin {

    public Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] HubAliases = new String[config.getStringList("CommandAliases").size()];
        config.getStringList("CommandAliases").toArray(HubAliases);

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(this, config, HubAliases));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand(this, config));
        getLogger().info(getDescription().getVersion());
        try {
            UpdateChecker updateChecker = new UpdateChecker(this);
            if (updateChecker.updateRequired()) {
                getLogger().info("§b----------------------------");
                getLogger().info("");
                getLogger().info("§7  * §9SlashHub by SkyGameZ §7*");
                getLogger().info("§7   * §9Update available! §7*");
                getLogger().info("");
                getLogger().info("§b----------------------------");
            } else {
                getLogger().info("§b----------------------------");
                getLogger().info("");
                getLogger().info("§7  * §9SlashHub by SkyGameZ §7*");
                getLogger().info("§7    * §9Version 1.6.1 §7*");
                getLogger().info("");
                getLogger().info("§b----------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
