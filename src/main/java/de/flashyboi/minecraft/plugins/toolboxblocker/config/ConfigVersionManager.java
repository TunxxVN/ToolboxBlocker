package de.flashyboi.minecraft.plugins.toolboxblocker.config;

import de.flashyboi.minecraft.plugins.toolboxblocker.ToolboxBlocker;
import de.flashyboi.minecraft.plugins.toolboxblocker.staticvar.StaticConfigVars;
import net.md_5.bungee.config.Configuration;

public class ConfigVersionManager {
    private final ToolboxBlocker toolboxBlocker;

    public ConfigVersionManager(ToolboxBlocker pluginInstance) {
        toolboxBlocker = pluginInstance;
    }

    public void checkVersion(Configuration cfg) {
        int currentConfigVersion = cfg.getInt(StaticConfigVars.CONFIG_VERSION_PATH);
        if (currentConfigVersion != StaticConfigVars.CONFIG_VERSION) {
            return;
        }
    }
}
