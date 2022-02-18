package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;


public class ReloadCommand extends Command {
    public Configuration config;

    public ReloadCommand(String HubReload) {
        super(HubReload, "slashhub.reload");
        config = Main.config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = Main.config;
        if(sender.hasPermission("slashhub.reload")) {
            try {
                Main.plugin.loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ReloadedPlugin")));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
        }
    }

}
