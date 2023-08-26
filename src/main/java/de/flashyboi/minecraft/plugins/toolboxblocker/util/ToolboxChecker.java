package de.flashyboi.minecraft.plugins.toolboxblocker.util;

import de.flashyboi.minecraft.plugins.toolboxblocker.staticvar.StaticLists;
import org.geysermc.floodgate.util.DeviceOs;
import org.geysermc.geyser.session.auth.BedrockClientData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolboxChecker {
    public static boolean hasToolbox(DeviceOs deviceOs, BedrockClientData clientData) {
        String modelName = clientData.getDeviceModel();
        if (isAndroidOrGoogle(deviceOs) && modelName != null) {
            modelName = extractModelName(modelName);
            return isIllegalModelName(modelName);
        }
        return false;
    }

    private static boolean isAndroidOrGoogle(DeviceOs deviceOs) {
        String deviceOsString = deviceOs.toString();
        return deviceOsString.equalsIgnoreCase("ANDROID") || deviceOsString.equalsIgnoreCase("GOOGLE");
    }

    private static String extractModelName(String modelName) {
        Pattern pattern = Pattern.compile("^([^\s]+)");
        Matcher matcher = pattern.matcher(modelName);
        if (matcher.find()) {
            modelName = modelName.substring(matcher.start(), matcher.end());
        }
        return modelName;
    }

    private static boolean isIllegalModelName(String modelName) {
        String lowercaseModelName = modelName.toLowerCase();
        if (lowercaseModelName.equals(modelName) || StaticLists.FORBIDDEN_MODELNAMES.contains(modelName)) {
            return true;
        }
        return false;
    }
}