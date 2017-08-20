package nico.styTool;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dump.m.ProcessInfo;
import dump.m.ProcessListAdapter;

public class smali_layout_apktool extends Activity
{
    /** Called when the activity is first created. */

	private List<ProcessInfo> processList= new ArrayList<ProcessInfo>();
    private List<ApplicationInfo> applicationInfoList ;

    private ProcessListAdapter adapter = null;

    private WebView mWebview;
    private List<String> processNamelist = new ArrayList<String>();
    @Override
    protected void onDestroy()
    {
        if (mWebview != null)
		{
			mWebview.removeAllViews();
			mWebview.destroy();
            releaseAllWebViewCallback();
			mWebview.removeView(mWebview);
			mWebview.stopLoading();
			mWebview.removeAllViews();
			mWebview = null;
		//	mWebview.loadUrl("file:///android_asset/Cache_dex/Cache_558c96bd2a6ea1a5");
        }
        super.onDestroy();
    }

    public void releaseAllWebViewCallback()
    {
        if (android.os.Build.VERSION.SDK_INT < 16)
		{
            try
			{
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            }
			catch (NoSuchFieldException e)
			{
                if (BuildConfig.DEBUG)
				{
                    e.printStackTrace();
                }
            }
			catch (IllegalAccessException e)
			{
                if (BuildConfig.DEBUG)
				{
                    e.printStackTrace();
                }
            }
        }
		else
		{
            try
			{
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null)
				{
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            }
			catch (NoSuchFieldException e)
			{
                if (BuildConfig.DEBUG)
				{
                    e.printStackTrace();
                }
            }
			catch (ClassNotFoundException e)
			{
                if (BuildConfig.DEBUG)
				{
                    e.printStackTrace();
                }
            }
			catch (IllegalAccessException e)
			{
                if (BuildConfig.DEBUG)
				{
                    e.printStackTrace();
                }
            }
        }
    }
	private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x1)
            {
				// startClearAnim();
            }
            else if (msg.what == 0x2)
            {
				//  stopClearAnim();
                d();
            }
            super.handleMessage(msg);
        }
    };


	private void d()
    {
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

        //获取所有将运行中的进程
        List<ActivityManager.RunningAppProcessInfo> runningAppList  = activityManager.getRunningAppProcesses();

        PackageManager packageManager  = this.getPackageManager();

        //获取所有包信息
        applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);


        if (processList != null && processList.size() > 0)
            processList.clear();

        if (processNamelist != null && processNamelist.size() > 0)
            processNamelist.clear();

        for (ActivityManager.RunningAppProcessInfo process : runningAppList)
        {
            if (process.processName.indexOf(this.getPackageName()) < 0)
            {   //过滤本应用包名
                ProcessInfo p = new ProcessInfo();

                ApplicationInfo appInfo = getApplicationInfoByProcessName(process.processName);
                if (appInfo == null)
                {
                    //有些应用的守护进程并没有目标应用对应,此时返回null
                }
                else
                {
                    p.setLabelIcon(appInfo.loadIcon(packageManager));
                    p.setLabelName(appInfo.loadLabel(packageManager).toString());
                    p.setProcessName(appInfo.processName);

                    processNamelist.add(appInfo.processName);
                    processList.add(p);
                }
            }
        }

        if (adapter == null)
        {
			//    adapter = new ProcessListAdapter(KILL.this, processList, new ItemButtonClick());
			//  ll_main.setAdapter(adapter);
            //ll_main.setOnItemClickListener(new MyOnItemClickListener());
        }
        else
        {
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 根据进程名获取应用信息
     * @param processNames
     * @return
     */
    private ApplicationInfo getApplicationInfoByProcessName(String processNames)
    {
        if (applicationInfoList == null || applicationInfoList.size() < 1)
            return null;

        for (ApplicationInfo applicationInfo : applicationInfoList)
        {
            if (applicationInfo.processName.equals(processNames) && (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)
			//只显示第三方的应用进程,不显示系统应用
			//要显示所有应用进程,删去(applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0 即可
                return applicationInfo;
        }
        return null;
    }
	private void b()
    {

        mHandler.sendEmptyMessage(0x1);

        ActivityManager activityManager = (ActivityManager)smali_layout_apktool.this.getSystemService(ACTIVITY_SERVICE);

        if (processNamelist != null && processNamelist.size() > 0)
        {
            for (String processName : processNamelist)
            {
                activityManager.killBackgroundProcesses(processName);
            }
        }
        mHandler.sendEmptyMessageDelayed(0x2, 2000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout);
		try
		{ 
			InputStream is = getAssets().open("Cache_dex/Cache_558c96bd2a6ea1a5"); 
			int size = is.available(); 
			byte[] buffer = new byte[size]; 
			is.read(buffer); 
			is.close(); 
			String text = new String(buffer, "UTF-8"); 
			b();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e); 
		} 
		AlertDialog.Builder builder = new AlertDialog.Builder(smali_layout_apktool.this);
		AlertDialog alertDialog = builder.setMessage("正在清理...\n可能会让你手机卡顿\n直到妮媌也清【一分钟左右】\n\n功能不一定兼容全部ROM·另外也可以当跑分·").setCancelable(false)
			.setPositiveButton("最小化", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent intent = new Intent();
					intent.setAction("android.intent.action.MAIN");
					intent.addCategory("android.intent.category.HOME");
					smali_layout_apktool.this.startActivity(intent);
				}
			}).create();
		alertDialog.show();
        mWebview = new WebView(this);
		mWebview.setVisibility(View.GONE);
        WebSettings webSettings = mWebview.getSettings();
        mWebview.loadUrl(null+"");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
		//webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
					view.loadUrl(url);
					return true;
				}
			});
        setContentView(mWebview);
    }}

