package nico.styTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;


public class SplashActivity extends AppCompatActivity {
    // private final static Long TIME = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.s_splash);
        boolean isFirstRun = (boolean) nico.SPUtils.get(this, "if_c_1", false);
        if (isFirstRun) {
            // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        SharedPreferences setting = SplashActivity.this.getSharedPreferences("play_", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            //MediaPlayer mPlayer = MediaPlayer.create(SplashActivity.this, R.raw.bio);
            //mPlayer.start();
            //Toast.makeText(SplashActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
            setting.edit().putBoolean("FIRST", false).commit();

        } else {
            boolean sFirstRun = (boolean) nico.SPUtils.get(this, "if_styTool", false);
            if (sFirstRun) {
                MediaPlayer mPlayer = MediaPlayer.create(SplashActivity.this, R.raw.bio);
                mPlayer.start();
            } else {
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, nico.SimpleActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000L);
    }

    @Override
    public void onBackPressed() {
        // Splash界面不允许使用back键
    }
}
