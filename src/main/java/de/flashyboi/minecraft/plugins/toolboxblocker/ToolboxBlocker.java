package de.flashyboi.minecraft.plugins.toolboxblocker;

import de.flashyboi.minecraft.plugins.toolboxblocker.config.ConfigManager;
import de.flashyboi.minecraft.plugins.toolboxblocker.config.ConfigVersionManager;
import de.flashyboi.minecraft.plugins.toolboxblocker.events.PlayerHandshake;
import de.flashyboi.minecraft.plugins.toolboxblocker.staticvar.StaticConfigVars;
import de.flashyboi.minecraft.plugins.toolboxblocker.staticvar.StaticEmbeds;
import de.flashyboi.minecraft.plugins.toolboxblocker.util.DiscordWebhook;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.geysermc.floodgate.util.DeviceOs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.logging.Logger;

public final class ToolboxBlocker extends Plugin {
    private static ToolboxBlocker instance;
    private static Configuration config;
    private Logger log;

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        // Plugin startup logic
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerHandshake());
        initiateConfig();
        ConfigVersionManager configVersionManager = new ConfigVersionManager(this);
        configVersionManager.checkVersion(config);

        DiscordWebhook.sendCommand(StaticEmbeds.START_EMBED, ConfigManager.getConfigValue(StaticConfigVars.WEBHOOK_URL_PATH, ""));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initiateConfig() {
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File configFile = new File(dataFolder, "config.yml");
            if (!configFile.exists()) {
                try (InputStream inputStream = getResourceAsStream("config.yml")) {
                    Files.copy(inputStream, configFile.toPath());
                }
            }

            ConfigurationProvider configProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            config = configProvider.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ToolboxBlocker getInstance() {
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }
}