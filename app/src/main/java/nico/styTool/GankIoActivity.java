package nico.styTool;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.widget.Toast;

public class GankIoActivity extends PreferenceActivity
{
    /**自定义布局B**/
    Preference preference1 = null;
    Context mContext = null;
    Preference preference2 = null;
    Preference preference3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_setting);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		addPreferencesFromResource(R.xml.ganklo);
		preference1 = findPreference("ioa");
		preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					AppCompatDialog alertDialog = new AlertDialog.Builder(GankIoActivity.this).
						setTitle(Constant.a_mi).
						setMessage(Constant.a_miui).
						setPositiveButton("介绍", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which)
							{

								AlertDialog.Builder builder = new AlertDialog.Builder(GankIoActivity.this);
								AlertDialog alert = builder.setMessage("嗨 妮媌多顶集合功能，快速上手\n简：一目了然\n美：十全十美\n http://www.coolapk.com/apk/nico.styTool")
									.setNegativeButton("OK", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which)
										{

										}
									})
									.create();
								alert.show();
							}
						}).
						setNegativeButton("日志", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which)
							{

								AlertDialog.Builder builder = new AlertDialog.Builder(GankIoActivity.this);
								AlertDialog alert = builder.setMessage(Constant.a_ios)
									.setNegativeButton("确定", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which)
										{
										}
									})
									.create();
								alert.show();
							}
						}).
						create();
					alertDialog.show();
					return false;
				}
			});
		preference2 = findPreference("ioc");
		preference2.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					final SharedPreferences setting = GankIoActivity.this.getSharedPreferences("Viewpa__m", 0);
					setting.edit().putBoolean("FIRST", false).commit();
					Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, "嗨 妮媌多顶集合功能，快速上手\n简：一目了然\n美：十全十美\n http://www.coolapk.com/apk/nico.styTool");
					sendIntent.setType("text/plain");
					startActivity(sendIntent);
					return false;
				}
			});
		preference3 = findPreference("iob");
		preference3.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{

					AlertDialog.Builder builder = new AlertDialog.Builder(GankIoActivity.this);
					AlertDialog alert = builder.setMessage("如果你喜欢妮媌\n请随意捐赠，给我买棒棒糖")
						.setPositiveButton("复制微信帐号", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(GankIoActivity.this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
								ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
								manager.setText("bfl80822265");
							}
						}).setNegativeButton("复制QQ帐号", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(GankIoActivity.this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
								ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
								manager.setText("2284467793");


							}
						})
						.create();
					alert.show();
					return false;
				}
			});
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
