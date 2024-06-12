package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.utils.MessageFormatter;
import com.woolmc.slashhub.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlashHubCommand extends Command implements TabExecutor {
    public Configuration config;
    public Main main;

    private BungeeAudiences adventure;

    Component parsed;
    MiniMessage mm = MiniMessage.miniMessage();

    private final MessageFormatter messageFormatter = new MessageFormatter();

    public SlashHubCommand(Main main, BungeeAudiences bungeeAudiences, Configuration config) {
        super("slashhub", "slashhub.admin");
        this.config = config;
        this.main = main;
        adventure = bungeeAudiences;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience player = adventure.sender(sender);
        config = main.config;

        List<String> targetServers = config.getStringList("TargetServers");

        if (args.length == 0) {
            if ((!sender.hasPermission("slashhub.admin.reload") || !sender.hasPermission("slashhub.reload") || !sender.hasPermission("slashhub.admin.*")) && sender instanceof ProxiedPlayer) {
                player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
                return;
            }
            player.sendMessage(messageFormatter.Format(adventure, mm, "&b------------ &9SlashHub &b------------\n" +
                    "\n" +
                    "&8 * &9Version 1.11 &7- &9 by SkyGameZ\n" +
                    "\n" +
                    "&8 * &9Subcommands:\n" +
                    "&7    - &a/slashhub reload\n" +
                    "\n" +
                    "&b----------------------------------"));
            return;
        }

        switch (args[0]) {
            case "reload":
                if ((!sender.hasPermission("slashhub.admin.reload") || !sender.hasPermission("slashhub.reload") || !sender.hasPermission("slashhub.admin.*")) && sender instanceof ProxiedPlayer) {
                    player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
                    return;
                }

                try {
                    main.configUtils.loadConfiguration();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.ReloadedPlugin")));
                return;
//            case "add-hub":
//                if (!sender.hasPermission("slashhub.add-hub") || !sender.hasPermission("slashhub.admin.*") && sender instanceof ProxiedPlayer) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
//                    return;
//                }
//                if (!ProxyServer.getInstance().getConfig().getServers().containsKey(args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is not present on your Bungee network!"));
//                    return;
//                }
//                if (containsIgnoreCase(targetServers, args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is already added to your target servers!"));
//                    return;
//                }
//
//                main.configUtils.addYamlListObject(main.getDataFolder() + "/config.yml", "TargetServers", args[1].toLowerCase(), 1);
//                try {
//                    main.configUtils.loadConfiguration();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                return;
//            case "remove-hub":
//                if (!sender.hasPermission("slashhub.remove-hub") || !sender.hasPermission("slashhub.admin.*") && sender instanceof ProxiedPlayer) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
//                    return;
//                }
//                if (!containsIgnoreCase(targetServers, args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is not added to your target servers!"));
//                    return;
//                }
//                if (!ProxyServer.getInstance().getConfig().getServers().containsKey(args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is not present on your Bungee network!"));
//                    return;
//                }
//
//                main.configUtils.removeYamlListObject(main.getDataFolder() + "/config.yml", "TargetServers", args[1].toLowerCase());
//                try {
//                    main.configUtils.loadConfiguration();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                return;
//            case "disable-server":
//                if (!sender.hasPermission("slashhub.disable-server") || !sender.hasPermission("slashhub.admin.*") && sender instanceof ProxiedPlayer) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
//                }
//                if (!ProxyServer.getInstance().getConfig().getServers().containsKey(args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is not present on your Bungee network!"));
//                }
//                return;
//            case "enable-server":
//                if (!sender.hasPermission("slashhub.enable-server") || !sender.hasPermission("slashhub.admin.*") && sender instanceof ProxiedPlayer) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
//                }
//                if (!ProxyServer.getInstance().getConfig().getServers().containsKey(args[1])) {
//                    player.sendMessage(messageFormatter.Format(adventure, mm, "&cThat server is not present on your Bungee network!"));
//                }
//                return;
        }


    }
    boolean containsIgnoreCase(List<String> list, String value) {
        // Convert the value to lowercase for case-insensitive comparison
        value = value.toLowerCase();

        // Check if any element in the list matches the value (case-insensitive)
        String finalValue = value;
        return list.stream()
                .map(String::toLowerCase) // Convert each element to lowercase
                .anyMatch(element -> element.equals(finalValue)); // Check for equality
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> argsList = new HashSet<>();
        if (args.length == 1) {
            argsList.clear();
            argsList.add("reload");
        }
        return argsList;
    }
}
