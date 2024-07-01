package main.java.com.teamcostco.model.manager;

import java.util.prefs.Preferences;

public class SettingsManager {
    private static final String AUTO_LOCK_ENABLED = "autoLockEnabled";
    private static final String AUTO_LOCK_TIME = "autoLockTime";
    private static final Preferences prefs = Preferences.userNodeForPackage(SettingsManager.class);

    public static void saveAutoLockSettings(boolean enabled, String time) {
        prefs.putBoolean(AUTO_LOCK_ENABLED, enabled);
        prefs.put(AUTO_LOCK_TIME, time);
    }

    public static boolean isAutoLockEnabled() {
        return prefs.getBoolean(AUTO_LOCK_ENABLED, false);
    }

    public static String getAutoLockTime() {
        return prefs.get(AUTO_LOCK_TIME, "10초");
    }
}