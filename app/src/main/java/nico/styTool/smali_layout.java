package nico.styTool;

import android.app.Application;
import android.content.Context;

public class smali_layout extends Application {
  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
  //  MultiDex.install(this);
	  //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//调用recreate()使设置生效
	//  recreate();
  }
}
