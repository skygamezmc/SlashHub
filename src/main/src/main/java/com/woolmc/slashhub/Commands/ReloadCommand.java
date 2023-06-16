package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Functions.MessageFormatter;
import com.woolmc.slashhub.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReloadCommand extends Command {
    public Configuration config;
    public Main main;
    public int version;

    private BungeeAudiences adventure;

    Component parsed;
    MiniMessage mm = MiniMessage.miniMessage();

    private MessageFormatter messageFormatter = new MessageFormatter();

    public ReloadCommand(Main main, BungeeAudiences bungeeAudiences, Configuration config) {
        super("hubreload", "slashhub.reload");
        this.config = config;
        this.main = main;
        this.version = main.version;
        adventure = bungeeAudiences;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience player = adventure.sender(sender);

        config = main.config;
        if(sender.hasPermission("slashhub.reload")) {
            try {
                main.loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.ReloadedPlugin")));
        } else {
            player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
        }
    }

}
