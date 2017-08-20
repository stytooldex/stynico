package nico.styTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class api_o extends AppCompatActivity
{

	public void ok(View view)
	{
		Intent intent = new Intent(api_o.this, FxService.class);
		startService(intent);
		finish();
		if (dump.v.testThread.checkAlertWindowsPermission(this))
		{
			//Toast.makeText(this, "不需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "需要开启悬浮窗权限", Toast.LENGTH_LONG).show();
		}

	}
	public void no(View view)
	{
		Intent intent = new Intent(api_o.this, FxService.class);
		stopService(intent);
	}
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.a_qwp);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		final SharedPreferences setting = getSharedPreferences("xposed_l", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first)
		{
			/*
			 AlertDialog.Builder obuilder = new AlertDialog.Builder(this);
			 AlertDialog alertDialog = obuilder.setMessage("本功能来自我一个想法...\n每次在录【王者荣耀】时候\n防不胜防会出现内存不足\n然后我就1v5...\n【作者ID】：月色媌")
			 .setNegativeButton("露娜", new DialogInterface.OnClickListener() {
			 @Override
			 public void onClick(DialogInterface dialog, int which)
			 {
			 setting.edit().putBoolean("FIRST", false).commit();

			 }
			 }).setCancelable(false)
			 .create();

			 alertDialog.show();
			 */
		}
		else
		{

		}
	}}
