package com.woolmc.slashhub.Functions;

import com.woolmc.slashhub.Main;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {

    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public Component Format(BungeeAudiences adventure, MiniMessage mm, String text) {
        Component parsed = null;
        String preformattedText;

        preformattedText = text
                .replace("&0", "<black>")
                .replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>")
                .replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>")
                .replace("&5", "<dark_purple>")
                .replace("&6", "<gold>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&9", "<blue>")
                .replace("&a", "<green>")
                .replace("&b", "<aqua>")
                .replace("&c", "<red>")
                .replace("&d", "<light_purple>")
                .replace("&e", "<yellow>")
                .replace("&f", "<white>")
                .replace("&k", "<obfuscated>")
                .replace("&l", "<bold>")
                .replace("&m", "<strikethrough>")
                .replace("&n", "<underlined>")
                .replace("&o", "<italic>")
                .replace("&r", "<reset>");

        Matcher match = hexPattern.matcher(preformattedText);
        while (match.find()) {
            String color = preformattedText.substring(match.start(), match.end());
            preformattedText = preformattedText.replace(color, "<" + color + ">");
            match = hexPattern.matcher(preformattedText);
        }

        parsed = mm.deserialize(preformattedText);

        return parsed;
    }
}
