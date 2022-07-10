package com.woolmc.slashhub.Updater;


import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
    private URL url;
    private String newVersion;
    private Plugin localPlugin;
    private BufferedReader apiReader;

    public UpdateChecker(Plugin plugin) throws MalformedURLException {
        url = new URL("https://api.spigotmc.org/legacy/update.php?resource=99803");
        localPlugin = plugin;
    }

    public boolean updateRequired() throws IOException {
        URLConnection con = url.openConnection();
        apiReader = (new BufferedReader(new InputStreamReader(con.getInputStream())));
        newVersion = apiReader.readLine();
        apiReader.close();
        return !localPlugin.getDescription().getVersion().equals(newVersion);
    }
}
