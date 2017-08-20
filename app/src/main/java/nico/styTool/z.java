package nico.styTool;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;
import dump.t.Fk;

//微信
public class z extends PreferenceActivity implements OnClickListener
{

    @Override
    public void onClick(View p1)
    {
		// TODO: Implement this method
    }

    Button tv_one_one;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 5201314 && resultCode == RESULT_OK)
		{
			String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
			//Toast.makeText(z.this, filePath, Toast.LENGTH_SHORT).show();
			final SharedPreferences setting = z.this.getSharedPreferences("__a_V_", 0);
			Boolean user_first = setting.getBoolean("FIRST", true);
			if (user_first)
			{

				setting.edit().putBoolean("FIRST", false).commit();
				Toast.makeText(z.this, "视频选择已生效，请设置壁纸", Toast.LENGTH_SHORT).show();


			}
			else
			{
				Toast.makeText(z.this, "视频选择已生效，请重启手机生效新显示", Toast.LENGTH_SHORT).show();

			}
			nico.SPUtils.put(z.this, "if_b_", filePath);

			// Do anything with file
		}
        if (requestCode == 1)
		{
            if (resultCode == RESULT_OK)
			{
                Uri uri = data.getData();
				//this.srcEt.setText((CharSequence)nico.GetPathFromUri4kitkat.getPath(this, uri));
				//this.srcEt.setEnabled(false);
				//this.srcFile = new File(nico.GetPathFromUri4kitkat.getPath(this, uri));
				final SharedPreferences setting = z.this.getSharedPreferences("__a_V_", 0);
				Boolean user_first = setting.getBoolean("FIRST", true);
				if (user_first)
				{

					setting.edit().putBoolean("FIRST", false).commit();
					Toast.makeText(z.this, "视频选择已生效，请设置壁纸", Toast.LENGTH_SHORT).show();


				}
				else
				{
					Toast.makeText(z.this, "视频选择已生效，请重启手机生效新显示", Toast.LENGTH_SHORT).show();

				}
				//重启app代码
				//finish();
				//android.os.Process.killProcess(android.os.Process.myPid());
				//ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				//am.killBackgroundProcesses("nico.styTool.nicoWallpaper"); // API Level至少为8才能使用
				nico.SPUtils.put(z.this, "if_b_", nico.GetPathFromUri4kitkat.getPath(this, uri));
			}}
    }

    //  public static class SettingsFragment extends PreferenceFragment {
    Preference preference2 = null;
    Preference preference3 = null;
    Preference preference1 = null;
	Preference preference20 = null;
	@Override
    protected void onRestart()
    {
        super.onRestart();
		//finish();

    }
	public boolean ajoinQQGroupdata(String key)
    {
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try
		{
			startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
// 未安装手Q或安装的版本不支持
			return false;
		}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
		addPreferencesFromResource(R.xml.pref_setting);
		final SharedPreferences setting = z.this.getSharedPreferences("__a__", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first)
		{
			AlertDialog.Builder obuilder = new AlertDialog.Builder(z.this);
			AlertDialog alertDialog = obuilder.setMessage("第二，三，四...次设置壁纸\n需要重启手机（设备）\n才能生效新视频显示\n\n部分系统需要【自启管理】")
			    .setNegativeButton("知道", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Fk feedback = new Fk();
						feedback.setContent("bilibili");
						feedback.save(z.this, new SaveListener() {

								@Override
								public void onFailure(int p1, String p2)
								{
									// TODO: Implement this method
								}

								@Override
								public void onSuccess()
								{
								}

							});
						setting.edit().putBoolean("FIRST", false).commit();

					}
			    }).setCancelable(false)
			    .create();

			alertDialog.show();
		}
		else
		{

		}
		preference1 = findPreference("ioa__");
		preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{

					AlertDialog.Builder builder = new AlertDialog.Builder(z.this);
					AlertDialog alertDialog = builder.setMessage("下载！有视频可以跳到第二步")
						.setNegativeButton("QQ群·推荐·", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(z.this, "选择自己喜欢资源下载", Toast.LENGTH_SHORT).show();
								ajoinQQGroupdata("ySV0pEgXykC5oCBFxnBC0wuEKP4FvRkJ");

							}
						}).setPositiveButton("微云·资源极少·", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(z.this, "选择自己喜欢资源下载", Toast.LENGTH_SHORT).show();
								Uri uri = Uri.parse("https://share.weiyun.com/63a4c01edb7cdf35771c936aaf28783a");
								Intent intent = new Intent(Intent.ACTION_VIEW, uri);
								startActivity(intent);
							}
						}).create();
					alertDialog.show();

					// insertDataWithOne();

					return false;
				}
			});


		preference20 = findPreference("ioa_b_");
		preference20.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					Uri uri = Uri.parse("http://m.v.qq.com/play/play.html?coverid=&vid=c0396eu3tii&ptag=2_5.5.2.11955_copy");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					return false;
				}
			});

		preference2 = findPreference("ioa_");
		preference2.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{

					AlertDialog.Builder builder = new AlertDialog.Builder(z.this);
					AlertDialog alertDialog = builder.setMessage("选择视频")
						.setNegativeButton("自带文件管理器(新)", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(z.this, "请先选择视频文件", Toast.LENGTH_SHORT).show();
								new MaterialFilePicker()
									.withActivity(z.this)
									.withRequestCode(5201314)
									.withHiddenFiles(true)
									.withTitle("选择视频文件")
									.start();
							}
						}).setPositiveButton("原文件管理器", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Intent intent = new Intent();
								intent.setType("video/*");
								intent.setAction(Intent.ACTION_GET_CONTENT);
								startActivityForResult(intent, 1);

							}
						}).create();
					alertDialog.show();

					return false;
				}
			});
		preference3 = findPreference("iob");
		preference3.setOnPreferenceClickListener(new OnPreferenceClickListener() {

				@Override
				public boolean onPreferenceClick(Preference preference)
				{
					//boolean=
					if (nico.SPUtils.contains(z.this, "if_b_"))
					{
						ComponentName componentName = new ComponentName("nico.styTool", "nico.styTool.nicoWallpaper");  
						Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);  
						intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName);  
						//intent.putExtra("test", "test");  
						startActivity(intent);  
						//finish();
					}
					else
					{
						Toast.makeText(z.this, "没有视频", Toast.LENGTH_SHORT).show();
					}


					return false;
				}
			});
    }

}
