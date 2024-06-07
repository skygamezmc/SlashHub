package com.woolmc.slashhub.utils;

import com.woolmc.slashhub.Main;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class ConfigUtils {
    Main plugin;
    public ConfigUtils(Main plugin) {
        this.plugin = plugin;
    }

    public void loadConfiguration() throws IOException {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdir();
            Files.copy(plugin.getResourceAsStream("config.yml"),
                    configFile.toPath());
            plugin.getLogger().info("§aGenerated SlashHub Config v1.1");
        }
        plugin.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }


// figure out later. i suck at this and need to get this update out


//    public void addYamlListObject(String filePath, String key, String valueToAdd, int layersDeep) {
//        boolean foundKey = false;
//        boolean inList = false;
//        int whiteSpace = layersDeep * 2;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            StringBuilder updatedContent = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.trim().toLowerCase().startsWith(key.toLowerCase() + ":")) {
//                    foundKey = true;
//                    updatedContent.append(line).append(System.lineSeparator());
//                    continue;
//                }
//
//                if (foundKey && line.trim().startsWith("-")) {
//                    inList = true;
//                }
//
//                if (inList && !line.trim().startsWith("-")) {
//                    // Insert new value here
//                    updatedContent.append(repeatChar(' ', whiteSpace)).append("- \"").append(valueToAdd).append("\"").append(System.lineSeparator());
//                    inList = false; // Reset
//                }
//
//                updatedContent.append(line).append(System.lineSeparator());
//            }
//
//            if (!foundKey) {
//                // Key not found, add it at the end of the file
//                updatedContent.append(System.lineSeparator()).append(key).append(":").append(System.lineSeparator());
//                updatedContent.append(repeatChar(' ', whiteSpace)).append("- \"").append(valueToAdd).append("\"").append(System.lineSeparator());
//            }
//
//            // Write the updated content back to the file
//            try (FileWriter writer = new FileWriter(filePath)) {
//                writer.write(updatedContent.toString());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeYamlListObject(String filePath, String key, String valueToRemove) {
//        boolean foundKey = false;
//        boolean foundValue = false;
//        int removeLineNumber = -1;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            int lineNumber = 0;
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (foundKey && !foundValue) {
//                    String trimmedLine = line.trim();
//                    if (!trimmedLine.isEmpty() && trimmedLine.charAt(0) == '-') {
//                        if (trimmedLine.contains(valueToRemove)) {
//                            removeLineNumber = lineNumber;
//                            foundValue = true;
//                        }
//                    }
//                } else if (line.toLowerCase().trim().startsWith(key.toLowerCase() + ":")) {
//                    foundKey = true;
//                }
//                lineNumber++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (foundKey && removeLineNumber != -1) {
//            removeLine(filePath, removeLineNumber);
//        }
//    }
//
//    // Utility method to get byte offset of a specific line in a file
//    private long getLineOffset(String filePath, int lineNumber) throws IOException {
//        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
//            long offset = 0;
//            for (int i = 0; i < lineNumber - 1; i++) {
//                offset += file.readLine().length() + System.lineSeparator().length();
//            }
//            return offset;
//        }
//    }
//
//     int countLeadingWhitespace(String str) {
//        int count = 0;
//        for (int i = 0; i < str.length(); i++) {
//            if (Character.isWhitespace(str.charAt(i))) {
//                count++;
//            } else {
//                break;
//            }
//        }
//        return count;
//    }
//
//    String repeatChar(char c, int count) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < count; i++) {
//            sb.append(c);
//        }
//        return sb.toString();
//    }
//
//    public void removeLine(String filename, int lineNumber) {
//        try {
//            File file = new File(filename);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            StringBuffer inputBuffer = new StringBuffer();
//            String line;
//            int currentLine = 1;
//
//            while ((line = reader.readLine()) != null) {
//                if (currentLine != lineNumber++) {
//                    inputBuffer.append(line);
//                    inputBuffer.append('\n');
//                }
//                currentLine++;
//            }
//            reader.close();
//
//            // Write the modified content back to the file
//            FileOutputStream fileOut = new FileOutputStream(filename);
//            fileOut.write(inputBuffer.toString().getBytes());
//            fileOut.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
