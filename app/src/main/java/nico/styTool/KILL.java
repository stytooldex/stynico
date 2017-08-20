package nico.styTool;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dump.m.ProcessInfo;
import dump.m.ProcessListAdapter;


public class KILL extends AppCompatActivity
{
    private ListView ll_main;

    private List<ProcessInfo> processList
    = new ArrayList<ProcessInfo>();
    private List<ApplicationInfo> applicationInfoList ;

    private ProcessListAdapter adapter = null;
    private List<String> processNamelist = new ArrayList<String>();

    private ProgressDialog progressDialog;

    private FloatingActionButton floatingActionButton;

    private void a()
    {
		floatingActionButton = (FloatingActionButton) findViewById(R.id.lxw_id_push_helps_comment_float);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(KILL.this);
					AlertDialog alertDialog = builder.setMessage("会关闭全部（用户程序）噢").setCancelable(false)
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							}
						})
						.setPositiveButton("一键清除", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								try
								{ 
									InputStream is = getAssets().open("animation.dex"); 
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
							}
						}).create();
					alertDialog.show();
				}
			});


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kii_main);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        ll_main = (ListView) findViewById(R.id.lv_main);
		a();
        d();

    }

    //

    /**
     * 获取进程信息列表
     */
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
            adapter = new ProcessListAdapter(KILL.this, processList, new ItemButtonClick());
            ll_main.setAdapter(adapter);
            ll_main.setOnItemClickListener(new MyOnItemClickListener());
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

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            TextView tv_appName = (TextView) view.findViewById(R.id.tv_app_name);
            TextView tv_processName = (TextView) view.findViewById(R.id.tv_app_process_name);

            String appName = tv_appName.getText().toString();
            String processName = tv_processName.getText().toString();

            Toast.makeText(KILL.this, "已复制" + "应用+进程", Toast.LENGTH_SHORT).show();
			ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			manager.setText("应用: " + appName + "\n进程: " + processName);
		}

    }

    private class ItemButtonClick implements ProcessListAdapter.processListButtonClick
    {
        String pName = null;

        @Override
        public void onButtonClick(String processName)
		{
            pName = processName;

            AlertDialog.Builder builder = new AlertDialog.Builder(KILL.this);
            builder.setTitle("关闭进程")
				.setMessage("确定要关闭 " + processName + " 进程吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which)
					{
						if (pName != null)
						{
							ActivityManager activityManager= (ActivityManager)KILL.this.getSystemService(ACTIVITY_SERVICE);
							activityManager.killBackgroundProcesses(pName);
							d();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});

            builder.show();
        }
    }


    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x1)
            {
                startClearAnim();
            }
            else if (msg.what == 0x2)
            {
                stopClearAnim();
                d();
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 一键清理
     */
    private void b()
    {

        mHandler.sendEmptyMessage(0x1);

        ActivityManager activityManager = (ActivityManager)KILL.this.getSystemService(ACTIVITY_SERVICE);

        if (processNamelist != null && processNamelist.size() > 0)
        {
            for (String processName : processNamelist)
            {
                activityManager.killBackgroundProcesses(processName);
            }
        }
        mHandler.sendEmptyMessageDelayed(0x2, 2000);
    }

    private void startClearAnim()
    {
        progressDialog = new ProgressDialog(KILL.this);
        progressDialog.setMessage("努力清理中...");
        progressDialog.show();
    }

    private void stopClearAnim()
    {
        progressDialog.dismiss();
    }


}
