package com.woolmc.slashhub.Commands;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class LobbyCommand extends Command {
    public Configuration config;

    public LobbyCommand(String lobby) {
        super(lobby, "slashhub.use");
        config = Main.config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            config = Main.config;
            ProxiedPlayer p = (ProxiedPlayer)sender;
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(sender.getName());
            if(sender.hasPermission("slashhub.use")) {
                if(ProxyServer.getInstance().getConfig().getServers().containsKey(config.getString("TargetServer"))) {
                    if (!target.getServer().getInfo().getName().equals(config.getString("TargetServer"))) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ConnectingMessage")));
                        p.connect(ProxyServer.getInstance().getServerInfo(config.getString("TargetServer")));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("AlreadyOnHubMessage")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ServerNotFound")));
                    ProxyServer.getInstance().getLogger().warning("[SlashHub] Player " + sender.getName() + " has attempted to connect to server " + config.getString("TargetServer") + " However this server was not found in your Bungeecord Config");
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("CannotExecuteOnConsoleMessage")));
        }
    }
}
