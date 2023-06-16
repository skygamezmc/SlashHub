package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Functions.MessageFormatter;
import com.woolmc.slashhub.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HubCommand extends Command {
    public Configuration config;
    public Main main;
    public int version;

    private BungeeAudiences adventure;

    private MessageFormatter messageFormatter = new MessageFormatter();

    Component parsed;
    MiniMessage mm = MiniMessage.miniMessage();

    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public HubCommand(Main main, Configuration config, BungeeAudiences bungeeAudiences, String[] Aliases) {
        super("hub", "slashhub.use", Aliases);
        this.config = config;
        this.main = main;
        this.version = main.version;
        adventure = bungeeAudiences;
    }

    private String format(String string) {
        if (version >= 16) {
            Matcher match = hexPattern.matcher(string);
            while (match.find()) {
                String color = string.substring(match.start(), match.end());
                string = string.replace(color, ChatColor.of(color) + "");
                match = hexPattern.matcher(string);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = main.config;
        Audience player = adventure.sender(sender);

        if (!(sender instanceof ProxiedPlayer)) {
            if (!config.getString("Messages.CannotExecuteOnConsoleMessage").isEmpty()) {
                sender.sendMessage(format(config.getString("Messages.CannotExecuteOnConsoleMessage")));
            }
            return;
        }


        if(!sender.hasPermission("slashhub.use")) {
            if (!config.getString("Messages.NoPermission").isEmpty()) {
                player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.NoPermission")));
            }
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer)sender;
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(sender.getName());

        Random rand = new Random();
        List<String> TargetServers = config.getStringList("TargetServers");
        String RandomServer = TargetServers.get(rand.nextInt(TargetServers.size()));

        if(!ProxyServer.getInstance().getConfig().getServers().containsKey(RandomServer)) {
            if (!config.getString("Messages.ServerNotFound").isEmpty()) {
                player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.ServerNotFound")));
            }
            ProxyServer.getInstance().getLogger().warning("[SlashHub] Player " + sender.getName() + " has attempted to connect to server " + config.getString("TargetServer") + " However this server was not found in your Bungeecord Config");
            return;
        }

        if (config.getStringList("TargetServers").contains(target.getServer().getInfo().getName())) {
            if (!config.getString("Messages.AlreadyOnHubMessage").isEmpty()) {
                player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.AlreadyOnHubMessage")));
            }
            return;
        }

        if (!config.getString("Messages.ConnectingMessage").isEmpty()) {
            player.sendMessage(messageFormatter.Format(adventure, mm, config.getString("Messages.ConnectingMessage")));
        }
        p.connect(ProxyServer.getInstance().getServerInfo(RandomServer));
    }
}
