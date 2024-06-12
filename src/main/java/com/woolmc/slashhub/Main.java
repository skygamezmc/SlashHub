package com.woolmc.slashhub;

import com.woolmc.slashhub.Commands.HubCommand;
import com.woolmc.slashhub.Commands.SlashHubCommand;
import com.woolmc.slashhub.Updater.UpdateChecker;
import com.woolmc.slashhub.utils.ConfigUtils;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.woolmc.slashhub.Metrics.Metrics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public final class Main extends Plugin {

    public Configuration config;
    public ConfigUtils configUtils;

    private BungeeAudiences adventure;

    @NonNull
    public BungeeAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.adventure = BungeeAudiences.create(this);

        configUtils = new ConfigUtils(this);
        try {
            configUtils.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config.getFloat("Config-Version") != 1.1f) {
            getLogger().info("§4------------------------------------");
            getLogger().info("");
            getLogger().info("§7     * §cConfig out of date!§7*");
            getLogger().info("§7  * §cPlease regenerate your config!§7 *");
            getLogger().info("");
            getLogger().info("§4------------------------------------");
            return;
        }
        int pluginId = 18799;
        Metrics metrics = new Metrics(this, pluginId);


        String[] HubAliases = new String[config.getStringList("CommandAliases").size()];
        config.getStringList("CommandAliases").toArray(HubAliases);

        if (config.getBoolean("RequireUsePermission")) {
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(this, config, adventure, HubAliases, true));
        } else{
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(this, config, adventure, HubAliases));
        }

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SlashHubCommand(this, adventure, config));
        try {
            UpdateChecker updateChecker = new UpdateChecker(this, 99803);
            if (updateChecker.isUpdateRequired()) {
                getLogger().info("§b----------------------------");
                getLogger().info("");
                getLogger().info("§7  * §9SlashHub by SkyGameZ §7*");
                getLogger().info("§7    * §9Version " + getDescription().getVersion() + " §7*");
                getLogger().info("§7   * §9Update available! §7*");
                getLogger().info("");
                getLogger().info("§b----------------------------");
            } else {
                getLogger().info("§b----------------------------");
                getLogger().info("");
                getLogger().info("§7  * §9SlashHub by SkyGameZ §7*");
                getLogger().info("§7    * §9Version " + getDescription().getVersion() + " §7*");
                getLogger().info("");
                getLogger().info("§b----------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("§b----------------------------");
        getLogger().info("");
        getLogger().info("§7  * §9Shutting down SlashHub §7*");
        getLogger().info("");
        getLogger().info("§b----------------------------");

        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}