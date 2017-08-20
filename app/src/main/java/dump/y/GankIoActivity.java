package dump.y;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import nico.styTool.R;

public class GankIoActivity extends PreferenceActivity
{
    /**自定义布局B**/
    Preference preference1 = null;
    Context mContext = null;
    Preference preference2 = null;
    Preference preference3 = null;

    @Override
    public void onBackPressed()
	{
		finish();
		Toast.makeText(this, "如有设置改动·需要重启软件生效·", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_setting);

	addPreferencesFromResource(R.xml.ganklo_);
	
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
    {
	SharedPreferences sp = preference.getSharedPreferences();
	boolean ON_OFF = sp.getBoolean("auto_send_message", false);
	boolean next_screen = sp.getBoolean("next_screen_checkbox_preference", false);
	String text = sp.getString("auto_send_message_text", "");
	String listtext = sp.getString("auto_send_message_frequency", "");
	return true;
    }
}
