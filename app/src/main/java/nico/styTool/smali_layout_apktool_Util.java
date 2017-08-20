package nico.styTool;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class smali_layout_apktool_Util extends Fragment implements NavigationView.OnNavigationItemSelectedListener,OnClickListener
{

    private SharedPreferences sp;
    @Override
    public void onClick(View p1)
    {
		// TODO: Implement this method
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem p1)
    {
		// TODO: Implement this method
		return false;
    }
    private boolean Ɨ(Context context, String packageName)
    {
		final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++)
		{
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: Implement this method
        View view=inflater.inflate(R.layout.smali_layout_2_til, null);

		TextView a = (TextView) view.findViewById(R.id.smalilayout2tilTextView1);
		a.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					MyUser myUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
					if (myUser != null)
					{
						Intent intent1 = new Intent(getActivity(), LxwBlogActivity.class);
						startActivity(intent1);
					}
					else
					{
						Intent intent1 = new Intent(getActivity(), app_th.class);
						startActivity(intent1);
					}
				}
			});TextView b = (TextView) view.findViewById(R.id.smalilayout2tilTextView2);
		b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View via)
				{//标准
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = inflater.inflate(R.layout.bilibilib, null);
					final EditText ediComment1 = (EditText) view.findViewById(R.id.bilibilibEditText1);

					final EditText ediComment = (EditText) view.findViewById(R.id.bilibilibEditText2);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(view)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							}
						}).setPositiveButton("生成", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								String commentx = ediComment.getText().toString().trim();
								String comment = ediComment1.getText().toString().trim();
								if (TextUtils.isEmpty(comment))
								{
									// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
									return;
								}

								ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
								manager.setText("{uin:" + commentx + ",nick:+" + comment + ",who:1}");
								ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);

							}

						}).setCancelable(false).create().show();

				}
			});TextView c = (TextView) view.findViewById(R.id.smalilayout2tilTextView3);
		c.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v01)
				{//标准
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = inflater.inflate(R.layout.b_bilibili, null);
					final EditText ediComment1 = (EditText) view.findViewById(R.id.bbilibiliEditText1);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(view)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{

							}
						}).setPositiveButton("发起会话", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								String comment = ediComment1.getText().toString().trim();
								if (TextUtils.isEmpty(comment))
								{
									return;
								}
								Intent intent = new Intent();
								intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + comment));
								startActivity(intent);
							}

						}).setCancelable(false).create().show();
				}
			});TextView d = (TextView) view.findViewById(R.id.smalilayout2tilTextView4);
		d.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v1)
				{
					AlertDialog.Builder builderv = new AlertDialog.Builder(getActivity());
					AlertDialog alertDialog = builderv.setMessage("使用教程\n复制成功>“打开QQ（进入需要发送假红包聊天）\n点击红包图标（正常发包）\n点击口令红包\n粘贴假红包代码到（设置口令）然后塞钱\n\n注:新版代码iOS手机没有测试（注意）\n安卓非使用（辅助-xposed）会领不了\n如果对方使用（辅助-xposed）会把领走哦\n小提醒:由于旧版代码失效取消输入框自定义").setCancelable(false)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							}
						})
						.setPositiveButton("复制", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								try
								{ 
									InputStream is = getActivity().getAssets().open("animation.dex"); 
									int size = is.available(); 
									byte[] buffer = new byte[size]; 
									is.read(buffer); 
									is.close(); 
									String text = new String(buffer, "UTF-8"); 
									Toast.makeText(getActivity(), R.string.dex_apktool, Toast.LENGTH_SHORT).show();
									ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
									manager.setText("" + text);
								}
								catch (IOException e)
								{
									throw new RuntimeException(e); 
								} 
							}
						}).create();
					alertDialog.show();
				}
			});TextView e = (TextView) view.findViewById(R.id.smalilayout2tilTextView5);
		e.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v4)
				{
					if (android.os.Build.VERSION.SDK_INT > 17)
					{
						MyUser myUseru = BmobUser.getCurrentUser(getActivity(), MyUser.class);
						if (myUseru != null)
						{
							//原登录
							LayoutInflater inflater = LayoutInflater.from(getActivity());
							View view = inflater.inflate(R.layout.item_feedback, null);


							//指日
							CheckBox as = (CheckBox) view.findViewById(R.id.itemfeedbackCheckBox1);//回复
							CheckBox ai = (CheckBox) view.findViewById(R.id.itemfeedbackCheckBox2);//QQ
							CheckBox an = (CheckBox) view.findViewById(R.id.itemfeedbackCheckBox3);//微信
							Button fil = (Button) view.findViewById(R.id.b_nl);
							Button fio = (Button) view.findViewById(R.id.b_as);
							SharedPreferences sharedPreferences = getActivity().getSharedPreferences("hook_Cosplay", AppCompatActivity.MODE_PRIVATE); 
							String i = sharedPreferences.getString("io_kii", "");

							sp = getActivity().getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
							boolean isProtecting = sp.getBoolean("ok_a", false);
							if (isProtecting)
							{
								as.setChecked(true);
							}
							as.setOnCheckedChangeListener(new OnCheckedChangeListener() {
									@Override
									public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
									{
										if (isChecked)
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_a", true);
											editor.commit();
										}
										else
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_a", false);
											editor.commit();
										}
									}
								});
							sp = getActivity().getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
							boolean sProtecting = sp.getBoolean("ok_b", false);
							if (sProtecting)
							{
								ai.setChecked(true);
							}
							ai.setOnCheckedChangeListener(new OnCheckedChangeListener() {
									@Override
									public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
									{
										if (isChecked)
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_b", true);
											editor.commit();
										}
										else
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_b", false);
											editor.commit();
										}
									}
								});
							sp = getActivity().getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
							boolean Protecting = sp.getBoolean("ok_c", false);
							if (Protecting)
							{
								an.setChecked(true);
							}
							an.setOnCheckedChangeListener(new OnCheckedChangeListener() {
									@Override
									public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
									{
										if (isChecked)
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_c", true);
											editor.commit();
										}
										else
										{
											SharedPreferences.Editor editor = sp.edit();
											editor.putBoolean("ok_c", false);
											editor.commit();
										}
									}
								});



							fil.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v8)
									{
										Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
										startActivity(intent);
									}
								});
							fio.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v8)
									{
										Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
										startActivity(intent);
									}
								});
							/*    a.setOnClickListener(new View.OnClickListener() {
							 @Override
							 public void onClick(View v8)
							 {
							 String comment = b.getText().toString().trim();
							 if (TextUtils.isEmpty(comment))
							 {
							 // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
							 return;
							 }
							 ToastUtil.show(getActivity(), "保存成功", Toast.LENGTH_SHORT);
							 SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("hook_Cosplay", AppCompatActivity.MODE_PRIVATE);
							 SharedPreferences.Editor editor = mySharedPreferences.edit();
							 editor.putString("io_kii", "" + comment);
							 editor.commit();

							 }
							 });*/
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							builder.setView(view)
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which)
									{

									}
								}).setPositiveButton("开放模式", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										ToastUtil.show(getActivity(), "适配QQ|微信", Toast.LENGTH_SHORT);
									}
								}).setCancelable(false).create().show();
						}
						else
						{
							Intent intent = new Intent(getActivity(), app_th.class);
							startActivity(intent);

						}
					}
					else
					{
						ToastUtil.show(getActivity(), "系统低于4.3不支持", Toast.LENGTH_SHORT);

					}
				}
			});TextView f = (TextView) view.findViewById(R.id.smalilayout2tilTextView6);
		f.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v0)
				{//标准

					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = inflater.inflate(R.layout.a_gey, null);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(view)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{

							}
						}).setPositiveButton("去开启", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								//if (!nico.GetPathFromUri4kitkat.isAccessibilitySettingsOn(getContext())) {
								// 引导至辅助功能设置页面
								startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
								//} else {
								//	ToastUtil.show(getActivity(), "已经开启", Toast.LENGTH_SHORT);
								//	}
							}
						}).setCancelable(false).create().show();
				}
			});TextView g = (TextView) view.findViewById(R.id.smalilayout2tilTextView7);
		g.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v6)
				{//标准
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = inflater.inflate(R.layout.adx_bilibili, null);
					final EditText ediComment1 = (EditText) view.findViewById(R.id.adxbilibiliEditText1);
					final EditText ediComment = (EditText) view.findViewById(R.id.adxbilibiliEditText2);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(view)
						.setNeutralButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{

							}
						}).setNegativeButton("打赏金额", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								String commento = ediComment.getText().toString().trim();
								if (TextUtils.isEmpty(commento))
								{
									return;
								}
								ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
								manager.setText("[em]e10033[/em]{uin:2742,nick: 打赏了" + commento + "元红包}");
								ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);

							}
						}).setPositiveButton("打赏东西", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								String comment = ediComment1.getText().toString().trim();
								if (TextUtils.isEmpty(comment))
								{
									return;
								}
								ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
								manager.setText("[em]e10033[/em]{uin:2742,nick: 打赏了}{uin:2742,nick:" + comment + ",who:1}");
								ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);

							}
						}).setCancelable(false).create().show();
				}
			});TextView i = (TextView) view.findViewById(R.id.smalilayout2tilTextView8);
		i.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v8)
				{
					/*

					 */
					AlertDialog.Builder buiider = new AlertDialog.Builder(getActivity());
					AlertDialog alert = buiider.setMessage("打开QQ想要刷的群或QQ并长按输入框粘贴\n发送\n或者使用 直接发送").setCancelable(false)

						.setNeutralButton("直接发送", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								try
								{ 
									InputStream is = getActivity().getAssets().open("classes_cRC32.apk"); 
									int size = is.available(); 
									byte[] buffer = new byte[size]; 
									is.read(buffer); 
									is.close(); 
									String text = new String(buffer, "UTF-8");
									Intent sendIntent = new Intent();
									sendIntent.setAction(Intent.ACTION_SEND);
									sendIntent.putExtra(Intent.EXTRA_TEXT, text);
									sendIntent.setType("text/plain");
									startActivity(sendIntent);
									ToastUtil.show(getActivity(), "QQ请选择【发送给好友】\n微信请选择【发送给朋友】", Toast.LENGTH_SHORT);
								}
								catch (IOException e)
								{
									throw new RuntimeException(e); 
								} 

							}
						})
						.setNegativeButton("复制卡屏【新】", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								try
								{ 
									InputStream is = getActivity().getAssets().open("classes_cRC32.apk"); 
									int size = is.available(); 
									byte[] buffer = new byte[size]; 
									is.read(buffer); 
									is.close(); 
									String text = new String(buffer, "UTF-8");
									ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
									manager.setText(text);
									ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);
								}
								catch (IOException e)
								{
									throw new RuntimeException(e); 
								} 
							}
						}).setPositiveButton("复制刷屏【原】", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
								manager.setText("by:styTool\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
								Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();

							}
						}).create();
					alert.show();

				}
			});
		TextView mz = (TextView) view.findViewById(R.id.smalilayout2tilTextView13);
		mz.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v8)//加人
				{
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = inflater.inflate(R.layout.api_yt, null);
					final EditText br = (EditText) view.findViewById(R.id.apiytEditText1);
					//br.setError(null);
					SharedPreferences sharedPreferencesb = getActivity().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE); 
					String ar = sharedPreferencesb.getString("t", "");
					br.setText(ar);
					br.addTextChangedListener(new TextWatcher() {
							@Override
							public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
							{
								//正在修改
							}
							@Override
							public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
							{
								//修改之前
							}
							@Override
							public void afterTextChanged(Editable editable)
							{

								String r = br.getText().toString().trim();
								SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE);
								SharedPreferences.Editor editor = mySharedPreferences.edit();
								editor.putString("t", r);
								editor.commit();
							}
						});
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{


							}
						}).create().show();

				}
			});
		TextView me = (TextView) view.findViewById(R.id.smalilayout2tilTextView14);
		me.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v8)
				{
					Intent intent5 = new Intent(getActivity(), MeiziActivity.class);
					getActivity().startActivity(intent5);
				}
			});

        return view;
    }}
