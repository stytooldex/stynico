package nico.styTool;

import android.content.Context;
import android.preference.PreferenceManager;

public class DefaultSharedPreferences {
    public static void save(Context context, boolean isshow) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("is_show_window", isshow).commit();
    }

    public static boolean read(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("is_show_window", true);
    }
}
