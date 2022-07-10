package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HubCommand extends Command {
    public Configuration config;
    public Main main;

    public HubCommand(Main main, Configuration config, String[] Aliases) {

        super("hub", "slashhub.use", Aliases);
        this.config = config;
        this.main = main;
        System.out.println(config.getStringList("CommandAliases"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = main.config;
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.CannotExecuteOnConsoleMessage")));
            return;
        }

        if(!sender.hasPermission("slashhub.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.NoPermission")));
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer)sender;
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(sender.getName());

        Random rand = new Random();
        List<String> TargetServers = config.getStringList("TargetServers");
        String RandomServer = TargetServers.get(rand.nextInt(TargetServers.size()));

        if(!ProxyServer.getInstance().getConfig().getServers().containsKey(RandomServer)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ServerNotFound")));
            ProxyServer.getInstance().getLogger().warning("[SlashHub] Player " + sender.getName() + " has attempted to connect to server " + config.getString("TargetServer") + " However this server was not found in your Bungeecord Config");
            return;
        }

        if (config.getStringList("TargetServers").contains(target.getServer().getInfo().getName())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.AlreadyOnHubMessage")));
            return;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ConnectingMessage")));
        p.connect(ProxyServer.getInstance().getServerInfo(RandomServer));
    }
}
