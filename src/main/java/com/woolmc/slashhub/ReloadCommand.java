package com.woolmc.slashhub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;


public class ReloadCommand extends Command {
    public Configuration config;

    public ReloadCommand(String HubReload) {
        super(HubReload, "slashhub.reload");
        config = SlashHub.config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = SlashHub.config;
        if(sender.hasPermission("slashhub.reload")) {
            try {
                SlashHub.plugin.loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ReloadedPlugin")));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
        }
    }

}
