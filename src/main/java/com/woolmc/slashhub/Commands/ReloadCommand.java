package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;


public class ReloadCommand extends Command {
    public Configuration config;
    public Main main;

    public ReloadCommand(Main main, Configuration config) {
        super("hubreload", "slashhub.reload");
        this.config = config;
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = main.config;
        if(sender.hasPermission("slashhub.reload")) {
            try {
                main.loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ReloadedPlugin")));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.NoPermission")));
        }
    }

}
