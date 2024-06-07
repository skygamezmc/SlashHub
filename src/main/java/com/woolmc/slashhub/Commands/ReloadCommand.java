package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.utils.MessageFormatter;
import com.woolmc.slashhub.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;


public class ReloadCommand extends Command {
    public Configuration config;
    public Main main;

    private BungeeAudiences adventure;

    Component parsed;
    MiniMessage mm = MiniMessage.miniMessage();

    private final MessageFormatter messageFormatter = new MessageFormatter();

    public ReloadCommand(Main main, BungeeAudiences bungeeAudiences, Configuration config) {
        super("hubreload", "slashhub.reload");
        this.config = config;
        this.main = main;
        adventure = bungeeAudiences;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience player = adventure.sender(sender);

        config = main.config;
        if(sender.hasPermission("slashhub.reload")) {
            try {
                main.configUtils.loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.ReloadedPlugin")));
        } else {
            player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
        }
        return;
    }

}
