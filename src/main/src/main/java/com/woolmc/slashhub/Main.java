package com.woolmc.slashhub;

import com.woolmc.slashhub.Commands.HubCommand;
import com.woolmc.slashhub.Commands.ReloadCommand;
import com.woolmc.slashhub.Updater.UpdateChecker;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public final class Main extends Plugin {

    public Configuration config;
    public int version;

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

        try {
            loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config.getFloat("Config-Version") != 1.0) {
            getLogger().info("§4------------------------------------");
            getLogger().info("");
            getLogger().info("§7          * §eWARNING!§7*");
            getLogger().info("§7     * §cConfig out of date!§7*");
            getLogger().info("§7  * §cPlease get new config version!§7 *");
            getLogger().info("");
            getLogger().info("§4------------------------------------");

            return;
        }

        String[] HubAliases = new String[config.getStringList("CommandAliases").size()];
        config.getStringList("CommandAliases").toArray(HubAliases);

        String[] split = getProxy().getVersion().split(":")[2].split("-")[0].split("\\.");
        version = Integer.parseInt(split[1]);

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(this, config, adventure, HubAliases));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand(this, adventure, config));
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
                getLogger().info("§7    * §9Version " + getDescription().getVersion() + " §7*");
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