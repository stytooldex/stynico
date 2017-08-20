package nico.styTool;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class H extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}

