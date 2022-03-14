package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class HubCommand extends Command {
    public Configuration config;

    public HubCommand(String hub) {
        super(hub, "slashhub.use");
        config = Main.config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        config = Main.config;
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

        if(!ProxyServer.getInstance().getConfig().getServers().containsKey(config.getString("TargetServer"))) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ServerNotFound")));
            ProxyServer.getInstance().getLogger().warning("[SlashHub] Player " + sender.getName() + " has attempted to connect to server " + config.getString("TargetServer") + " However this server was not found in your Bungeecord Config");
            return;
        }

        if (target.getServer().getInfo().getName().equals(config.getString("TargetServer"))) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.AlreadyOnHubMessage")));
            return;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ConnectingMessage")));
        p.connect(ProxyServer.getInstance().getServerInfo(config.getString("TargetServer")));
    }
}

