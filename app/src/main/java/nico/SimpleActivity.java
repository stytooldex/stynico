package nico;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import dump.f.xp_;
import dump.j.o;
import dump.k.i_a;
import dump.s.SmsWatcher;
import dump.w.WeiboListActivity;
import nico.styTool.BatteryWrapper;
import nico.styTool.Constant;
import nico.styTool.EnergyWrapper;
import nico.styTool.FJActivity;
import nico.styTool.GankIoActivity;
import nico.styTool.HCActivity;
import nico.styTool.MeasureUtil;
import nico.styTool.MyUser;
import nico.styTool.ProviderUi;
import nico.styTool.R;
import nico.styTool.RobotChatActivity;
import nico.styTool.RollPagerView;
import nico.styTool.StatusBarUtil;
import nico.styTool.Update;
import nico.styTool.UserProfileActivity;
import nico.styTool.Viewhtml;
import nico.styTool.app_th;
import nico.styTool.iApp;
import nico.styTool.lua;
import nico.styTool.wlflActivity;
import nico.styTool.z;

import android.widget.*;

import nico.styTool.DownloadService;
import dump.t.*;
import cn.bmob.v3.listener.*;

import android.support.v4.widget.*;
import android.graphics.*;

/**
 * Created by zhuchenxi on 2016/12/13.
 */

public class SimpleActivity extends AppCompatActivity
{
    int count = 0;

    private static ProgressBar progBar;

    MyReceiver receiver;

    private Button ediComment;// 广播

    private void tab()
	{
        TextView vj = (TextView) findViewById(R.id.appbarmainTextView1);
        vj.setText("" + nico.SPUtils.get(SimpleActivity.this, "bfi", "设置一个备忘录*"));

    }

    /**
     * 开始下载
     */
    void startDownloadService()
	{
        if (DownloadService.getInstance() != null
			&& DownloadService.getInstance().getFlag() != 0)
		{
            Toast.makeText(this, "已经在下载", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent it = new Intent(this, DownloadService.class);
        it.putExtra("flag", "start");
        it.putExtra("url", "http://imtt.dd.qq.com");
        it.putExtra("filetype", "fio.apk");// 文件后缀名（注意要确认你下载的文件类型）
        startService(it);
    }

    class MyReceiver extends BroadcastReceiver
	{

        @Override
        public void onReceive(Context context, Intent intent)
		{
            String action = intent.getAction();
            if (action.equals(ACTION_DOWNLOAD_PROGRESS))
			{// 下载中显示进度
                int pro = intent.getExtras().getInt("progress");
                progBar.setProgress(pro);
            }
			else if (action.equals(ACTION_DOWNLOAD_SUCCESS))
			{
                // 下载成功
                Toast.makeText(SimpleActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                final File f = (File) intent.getExtras().getSerializable("file");
                ediComment.setVisibility(View.VISIBLE);
                ediComment.setText("文件已保存");
                openFile(f);

            }
			else if (action.equals(ACTION_DOWNLOAD_FAIL))
			{// 下载失败
                Toast.makeText(SimpleActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 打开文件
     *
     * @param f
     */
    private void openFile(File f)
	{
        final MyUser bmobUser = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
        if (bmobUser != null)
		{
            MyUser newUser = new MyUser();
            newUser.setAddress("激活");
            newUser.update(SimpleActivity.this, bmobUser.getObjectId(), new UpdateListener() {

					@Override
					public void onSuccess()
					{
					}

					@Override
					public void onFailure(int code, String msg)
					{
						Toast.makeText(SimpleActivity.this, "认证失败，请重试（" + code + "）", Toast.LENGTH_SHORT).show();
					}
				});
        }
		else
		{
            //toast("本地用户为null,请登录。");

        }
        FK__ feedback = new FK__();
        feedback.setContent((String) BmobUser.getObjectByKey(this, "objectId"));
        feedback.save(SimpleActivity.this, new SaveListener() {

				@Override
				public void onFailure(int p1, String p2)
				{

				}

				@Override
				public void onSuccess()
				{

					Toast.makeText(SimpleActivity.this, "已关闭弹屏", Toast.LENGTH_SHORT).show();
					final SharedPreferences setting = SimpleActivity.this.getSharedPreferences("Viewpa__m", 0);
					setting.edit().putBoolean("FIRST", false).commit();

				}

			});
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//将一个新的view置于台前
        intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");//打开apk格式的文件
        this.startActivity(intent);

    }

    public static final String ACTION_DOWNLOAD_PROGRESS = "my_download_progress";// 下载中
    public static final String ACTION_DOWNLOAD_SUCCESS = "my_download_success";// 成功
    public static final String ACTION_DOWNLOAD_FAIL = "my_download_fail";// 失败

    @Override
    protected void onStart()
	{
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DOWNLOAD_PROGRESS);
        filter.addAction(ACTION_DOWNLOAD_SUCCESS);
        filter.addAction(ACTION_DOWNLOAD_FAIL);
        registerReceiver(receiver, filter);// 注册广播
    }

    @Override
    protected void onStop()
	{
        super.onStop();
        unregisterReceiver(receiver);// 注销广播
    }

    private String hexString = "0123456789abcdef";

    /* 
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */
    public String jiami(String str)
	{
//根据默认编码获取字节数组 
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
//将字节数组中每个字节拆解成2位16进制整数 
        for (int i = 0; i < bytes.length; i++)
		{
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }

        //加密
        String st1 = "";
        String rreg[] = {"a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String rre[] = {".", ":", "+", "}", "?", "$", " ……", "%", "_", "～", "&", "#", "•", ">", "、", "￥"};

        for (int a = 0; a < 16; a++)
		{

            if (a == 0)
			{
                st1 = sb.toString().replace(rreg[a], rre[a]);
            }
            st1 = st1.replace(rreg[a], rre[a]);


        }

        return st1;
    }

    private void qqdex()
	{

        ajoinQQGroupdata("ySV0pEgXykC5oCBFxnBC0wuEKP4FvRkJ");
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

    private void qqb()
	{

        ajoinQQGroup("o0KRNRCzvzr76etbbj2cijqKtIq_f6dK");
    }

    public boolean ajoinQQGroup(String key)
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

    private void qqa()
	{

        joinQQGroup("ROrrIRie5VeJL2WiJZmvCODc68JGEWyw");
    }

    public boolean joinQQGroup(String key)
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

    AlertDialog mdialog = null;
    private void jko()
	{

        final SharedPreferences setting = SimpleActivity.this.getSharedPreferences("Viewpa__m", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first)
		{

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.a_boo, null);
            ediComment = (Button) view.findViewById(R.id.button4);
            progBar = (ProgressBar) view.findViewById(R.id.progressBar1);

            ediComment.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						startDownloadService();
					}
				});

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("厚脸无耻求支援")
				.setView(view)
				.setNeutralButton("关闭弹屏", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Toast.makeText(SimpleActivity.this, "初始化关闭失败！请联系作者", Toast.LENGTH_SHORT).show();
						//setting.edit().putBoolean("FIRST", false).commit();
					}
				})
				.setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

					}
				}).setNegativeButton("领取现金红包", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						final String[] os = {"妮媌帐号已经激活过", "下载查券宝并且安装使用", "QQ号最后两位（0.**）＋妮媌注册日期（0.日）－领取当天（0.日）＝领取余额】", "活动时间 2017.7.14-2017.8.16", "说明：领取前提需要（个人资料截图和查券宝截图）", "添加我:2652649464"};
						AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
						AlertDialog alert = builder.setTitle("免费领取现金红包条件")
                            .setPositiveButton("推荐朋友免费领取", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
								{
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "嗨 妮媌多顶集合功能，快速上手\n简：一目了然\n美：十全十美\n http://www.coolapk.com/apk/nico.styTool");
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
								{

                                }
                            })
                            .setItems(os, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
								{

                                }
                            })
                            .setCancelable(false).create();
						alert.show();

					}
				}).setCancelable(false)
				.create().show();
        }
		else
		{

        }

        if (DownloadService.getInstance() != null)
		{
            progBar.setProgress(DownloadService.getInstance().getProgress());// 获取DownloadService下载进度
        }
        receiver = new MyReceiver();
	}

    public void makeRootDirectory(String filePath)
	{
        File file = null;
        try
		{
            file = new File(filePath);
            if (!file.exists())
			{
                file.mkdir();
            }
        }
		catch (Exception e)
		{
            Toast.makeText(this, "初始化失败！部分功能可能无法使用", Toast.LENGTH_SHORT).show();
            // Log.i("error:", e+"");
        }
    }

    private void xft()
	{

        BmobQuery<i_a> query = new BmobQuery<i_a>();
        query.getObject(SimpleActivity.this, "03bf357e85", new GetListener<i_a>() {

				@Override
				public void onSuccess(i_a object)
				{
					String s = object.getContent();
					String sr = Constant.a_mi + "\n" + Constant.a_miui;
					if (s.equals(sr))
					{
                        /*
                         BmobQuery<o> query = new BmobQuery<o>();
						 query.getObject(proguard.this, "f0557d23fa", new GetListener<o>() {

						 @Override
						 public void onSuccess(o object)
						 {
						 SharedPreferences mySharedPreferences= getSharedPreferences("fba", AppCompatActivity.MODE_PRIVATE);
						 SharedPreferences.Editor editor = mySharedPreferences.edit();
						 editor.putString("key", object.getContent());
						 editor.commit();
						 }
						 @Override
						 public void onFailure(int code, String msg)
						 {

						 }
						 });//1*/
					}
					else
					{
						LayoutInflater inflater = LayoutInflater.from(SimpleActivity.this);
						View view = inflater.inflate(R.layout.cpl_main, null);
						final TextView a = (TextView) view.findViewById(R.id.cplmainTextView1);
						a.setText(object.getContent());
						AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
						builder.setView(view)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
								{
                                    BmobQuery<o> query = new BmobQuery<o>();
                                    query.getObject(SimpleActivity.this, "8304c235d2", new GetListener<o>() {

											@Override
											public void onSuccess(o object)
											{
												String s = "520";
												String sr = object.getContent();
												if (s.equals(sr))
												{
													Uri uri = Uri.parse("http://www.coolapk.com/apk/nico.styTool");
													Intent intent = new Intent(Intent.ACTION_VIEW, uri);
													startActivity(intent);
												}
												else
												{
													Uri uri = Uri.parse(object.getContent());
													Intent intent = new Intent(Intent.ACTION_VIEW, uri);
													startActivity(intent);
												}

											}

											@Override
											public void onFailure(int code, String msg)
											{
												Uri uri = Uri.parse("http://www.coolapk.com/apk/nico.styTool");
												Intent intent = new Intent(Intent.ACTION_VIEW, uri);
												startActivity(intent);
											}
										});


                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{


								}
							}).setCancelable(false).create().show();

					}
				}
				//				.setCancelable(false).


				@Override
				public void onFailure(int code, String msg)
				{

				}
			});

    }

    @Override
    protected void onResume()
	{
        super.onResume();
        //MyUser myUserw = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
        MyUser myUserw = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
        if (myUserw != null)
		{
            TextView userName = (TextView) findViewById(R.id.appbarmainTextView3);
            userName.setText(myUserw.getUsername());
            userName.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						MyUser myUserw = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
						if (myUserw != null)
						{
							Intent intent = new Intent(SimpleActivity.this, UserProfileActivity.class);
							startActivity(intent);
						}
						else
						{
							Intent intent = new Intent(SimpleActivity.this, app_th.class);
							startActivity(intent);
							Toast.makeText(SimpleActivity.this, "登录帐号显示更多功能", Toast.LENGTH_SHORT).show();
						}
					}
				});
        }
		else
		{

        }
        //   refresUI();
        //Toast.makeText(SimpleActivity.this, "设备", Toast.LENGTH_SHORT).show();
        xft();
    }


    private String getSDTotalSize()
	{
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(SimpleActivity.this, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    private String getSDAvailableSize()
	{
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(SimpleActivity.this, blockSize * availableBlocks);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getSystemDefaultSms()
	{
        return Telephony.Sms.getDefaultSmsPackage(this);
    }

    public void setDefaultSms(String packageName)
	{

        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
        startActivity(intent);

    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    private String getRomTotalSize()
	{
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(SimpleActivity.this, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    private String getRomAvailableSize()
	{
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(SimpleActivity.this, blockSize * availableBlocks);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
		{
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        jko();
        tab();

        SharedPreferences i = getSharedPreferences("HelloKitty320", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0)
		{//第一次
            makeRootDirectory(Environment.getExternalStorageDirectory().getPath() + "/Android/styTool");
            i.edit().putBoolean("FIRST", false).commit();
            Bmob.initialize(this, "71bedab5e874d985166b68ac4ab988bb");
            AppCompatDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle(Constant.a_mi)
				.setMessage(Constant.a_miui)
				.setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{

					}
				}).setCancelable(false).create();
            alertDialog.show();

        }
		else
		{

            SharedPreferences sharedPreferences = getSharedPreferences("nico.styTool_preferences", MODE_PRIVATE);
            boolean isFirstRun = sharedPreferences.getBoolean("if_a", false);
            //Editor editor = sharedPreferences.edit();
            if (isFirstRun)
			{
                //上
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.lox, null);
                final EditText ediComment = (EditText) view.findViewById(R.id.loxEditText1);
                //ediComment.setError(null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							String comment = ediComment.getText().toString().trim();
							if (TextUtils.isEmpty(comment))
							{
								//ediComment.setError("内容不能为空");
								// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
								finish();
								return;
							}
							SharedPreferences sharedPreferencesb = getSharedPreferences("nico.styTool_preferences", AppCompatActivity.MODE_PRIVATE);
							String r = sharedPreferencesb.getString("if_b", "");
							String s = comment;
							String sr = r;
							if (s.equals(sr))
							{
								//Toast.makeText(d.this, "相等", Toast.LENGTH_SHORT).show();
							}
							else
							{
								finish();
							}
							//   push(comment, myUser);
						}
					}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							finish();
						}
					}).setCancelable(false).create().show();

                //下
            }
			else
			{
            }
            //Intent intent = new Intent(i.this, PictureActivity.class);
            //   startActivity(intent);
        }
    }

    public void b520(View view)//设置
    {

        BmobQuery<i_a> bmobQuery = new BmobQuery<i_a>();
        bmobQuery.count(this, i_a.class, new CountListener() {

				@Override
				public void onSuccess(int count)
				{
					Intent b = new Intent(SimpleActivity.this, nico.styTool.smali_layout_apktool.class);
					startActivity(b);
				}

				@Override
				public void onFailure(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("count对象个数失败："+msg);
					AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
					AlertDialog alertDialog = builder.setMessage("需要网络初始化插件\n日志:" + code)
                        .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
							{

                            }
                        }).create();
					alertDialog.show();
				}
			});
    }

    public void Bin1(View view)//设置
    {
        Intent inten = new Intent(SimpleActivity.this, GankIoActivity.class);
        startActivity(inten);

    }

    public void Bin2(View view)
	{
        mh();

    }

    public void Bin3(View view)
	{
        Intent intent = new Intent(SimpleActivity.this, Viewhtml.class);
        startActivity(intent);
        /*
         AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
		 AlertDialog alertDialog = builder.setMessage("请选择操作")
		 .setNeutralButton("视频转换到gif", new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface dialog, int which)
		 {


		 }
		 })
		 .setNegativeButton("图片分解", new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface dialog, int which)
		 {
		 Intent intent5 = new Intent(SimpleActivity.this, FJActivity.class);
		 SimpleActivity.this.startActivity(intent5);
		 }
		 }).setPositiveButton("图片合成", new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface dialog, int which)
		 {
		 Intent intent5 = new Intent(SimpleActivity.this, HCActivity.class);
		 SimpleActivity.this.startActivity(intent5);
		 }
		 }).create();
		 alertDialog.show();
		 */
    }

    public void Bin4(View view)
	{
        Intent intent = new Intent(SimpleActivity.this, RobotChatActivity.class);
        startActivity(intent);

    }

    public void Bin5(View view)
	{
        Intent intent = new Intent(SimpleActivity.this, z.class);
        startActivity(intent);

    }

    public void Bin6(View view)
	{

        Intent intent = new Intent(SimpleActivity.this, nico.niconiconi.class);
        startActivity(intent);
    }

    public void Bin7(View view)
	{
        LayoutInflater nflater = LayoutInflater.from(this);
        View ew = nflater.inflate(R.layout.a_dddr, null);
        final EditText nt = (EditText) ew.findViewById(R.id.adddrEditText1);

        AlertDialog.Builder uilder = new AlertDialog.Builder(this);
        uilder.setView(ew).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					String comment = nt.getText().toString().trim();
					if (TextUtils.isEmpty(comment))
					{
						//ediComment.setError("内容不能为空");
						//ToastUtil.show(SimpleActivity.this, "内容不能为空", Toast.LENGTH_SHORT);
						return;
					}
					new Handler().postDelayed(new Runnable() {
							@Override
							public void run()
							{
								tab();
							}
						}, 1200);
					nico.SPUtils.put(SimpleActivity.this, "bfi", comment);
					xp_ feedback = new xp_();
					feedback.setContent(comment + "");
					feedback.save(SimpleActivity.this, new SaveListener() {

							@Override
							public void onFailure(int p1, String p2)
							{
								//Toast.makeText(getActivity(), "举报失败", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onSuccess()
							{

							}

						});
					//	push(comment, myUser);
				}
			}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					return;
				}
			}).create().show();

    }

    public void A1(View view)//设置
    {
        MyUser myUserw = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
        if (myUserw != null)
		{
            Intent intent = new Intent(SimpleActivity.this, Update.class);
            startActivity(intent);
        }
		else
		{
            Intent intent = new Intent(SimpleActivity.this, app_th.class);
            startActivity(intent);
            Toast.makeText(SimpleActivity.this, "需要登录帐号同步到云端", Toast.LENGTH_SHORT).show();
        }


    }

    public void A2(View view)//设置
    {
        Intent intent = new Intent(SimpleActivity.this, ProviderUi.class);
        startActivity(intent);

    }

    public void A3(View view)//设置
    {

        Intent ntent = new Intent(SimpleActivity.this, iApp.class);
        startActivity(ntent);

    }

    public void adb(View view)
	{

        Intent ntent = new Intent(SimpleActivity.this, WeiboListActivity.class);
        startActivity(ntent);

    }

    public void bxp(View view)
	{
        Intent intent1 = new Intent(this, dump.y.x5_MainActivity.class);
        intent1.putExtra("#", "http://m.iqiyi.com/");
        startActivity(intent1);
    }

    public void A4(View view)//设置
    {
        Intent intent1 = new Intent(this, dump.y.x5_MainActivity.class);
        intent1.putExtra("#", "http://shitu.baidu.com/");
        startActivity(intent1);
    }

    private void mh()
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
        AlertDialog alertDialog = builder.setMessage("请选择你要加的群")

			.setNeutralButton("4群", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					qqdex();

				}
			})
			.setNegativeButton("2群（满）", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					qqb();
				}
			}).setPositiveButton("1群（满）", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					qqa();
				}
			}).create();
        alertDialog.show();
    }
}
