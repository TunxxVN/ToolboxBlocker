package de.flashyboi.minecraft.plugins.toolboxblocker.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    public static void sendCommand(String command, String webhookURL) {
        if (!webhookURL.isEmpty()) {
            try {
                URL url = new URL(webhookURL);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
                connection.setDoOutput(true);
                
                try (OutputStream outputStream = connection.getOutputStream()) {
                    String preparedCommand = prepareCommand(command);
                    outputStream.write(preparedCommand.getBytes(StandardCharsets.UTF_8));
                }
                
                connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String prepareCommand(String command) {
        // Handle backslashes.
        String preparedCommand = command.replaceAll("\\\\", "");
        if (preparedCommand.endsWith(" *")) {
            preparedCommand = preparedCommand.substring(0, preparedCommand.length() - 2) + "*";
        }
        return preparedCommand;
    }
}