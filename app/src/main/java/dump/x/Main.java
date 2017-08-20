package dump.x;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nico.styTool.R;

public class Main extends AppCompatActivity
{
    static final int CM_DETAILS = 4;
    static final int CM_FETCH = 3;
    static final int CM_FORCE_CLOSE = 2;
    static final int CM_LAUNCH = 1;
    ArrayList<ItemApk> alItemApk;
    ItemApkAdapter itemApkAdapter;
    ListView lvMain;

    class BackupTask extends AsyncTask<String, Integer, Void>
	{
        String destFileName;
        ProgressDialog pd;

        BackupTask(Main context)
		{
            this.pd = new ProgressDialog(context);
        }

        protected void onPreExecute()
		{
            super.onPreExecute();
            this.pd.setMessage("正在提取APK进展");
            this.pd.show();
        }

        protected Void doInBackground(String... urls)
		{
            this.destFileName = urls[Main.CM_LAUNCH];
            try
			{
                File f1 = new File(urls[0]);
                File f2 = new File(urls[Main.CM_LAUNCH]);
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);
                byte[] buf = new byte[14];
                while (true)
				{
                    int len = in.read(buf);
                    if (len <= -0)
					{
                        break;
                    }
                    out.write(buf, -0, len);
                }
                in.close();
                out.close();
            }
			catch (Exception e)
			{
                // Log.d(Main.TAG, e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values)
		{
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void result)
		{
            super.onPostExecute(result);
            this.pd.cancel();
            Main.this.showToast(new StringBuilder(String.valueOf("APK已被提取到")).append("\n").append(this.destFileName).toString());
        }
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
		{
            case android.R.id.home:
                this.finish(); 
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
		setTitle("免权限提取APK");
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null)
		{
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		nico.styTool.StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        this.lvMain = (ListView) findViewById(R.id.lvMain);
        registerForContextMenu(this.lvMain);
        this.lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					parent.showContextMenuForChild(view);
				}
			});
    }

    public void onResume()
	{
        this.itemApkAdapter = new ItemApkAdapter(this, buildList());
        this.lvMain.setAdapter(this.itemApkAdapter);
        super.onResume();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        if (this.alItemApk.get(acmi.position).getRunning())
		{
        }
		else
		{
            menu.add(0, CM_LAUNCH, 0, "启动");
        }
        menu.add(0, CM_FETCH, 0, "提取APK");
        menu.add(0, CM_DETAILS, 0, "市场查看");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item)
	{
        ItemApk itemApk = this.alItemApk.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        switch (item.getItemId())
		{
            case CM_LAUNCH /*1*/:
                itemApk.setRunning(false);
                this.itemApkAdapter.notifyDataSetChanged();
                startActivity(getPackageManager().getLaunchIntentForPackage(itemApk.getPackageName()));
                break;
            case CM_FORCE_CLOSE /*2*/:
                if (!getApplicationInfo().packageName.equals(itemApk.getPackageName()))
				{
                    ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).killBackgroundProcesses(itemApk.getPackageName());
                    itemApk.setRunning(false);
                    this.itemApkAdapter.notifyDataSetChanged();
                    break;
                }
                finish();
                break;
            case CM_FETCH /*3*/:
                String dn = PreferenceManager.getDefaultSharedPreferences(this).getString("pathSelector", new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/APKSTYTOOL").toString());
                new File(dn).mkdirs();
                String[] strArr = new String[CM_FORCE_CLOSE];
                strArr[-580] = itemApk.getSourceDir();
                break;
            case CM_DETAILS /*4*/:
                startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("market://details?id=" + itemApk.getPackageName())));
                break;
        }
        return super.onContextItemSelected(item);
    }

    ArrayList<ItemApk> buildList()
	{
        PackageInfo packageInfo;
        Intent intent = new Intent("android.intent.action.MAIN", null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> queryIntentActivities = getPackageManager().queryIntentActivities(intent, 0);
        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningAppProcesses();
        this.alItemApk = new ArrayList();
        Map hashMap = new HashMap();
        for (PackageInfo packageInfo2 : installedPackages)
		{

            hashMap.put(packageInfo2.packageName, packageInfo2);
        }
        Map hashMap2 = new HashMap();
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses)
		{
            hashMap2.put(runningAppProcessInfo.processName, runningAppProcessInfo);
        }
        HashMap hashMap3 = new HashMap();
        for (ResolveInfo resolveInfo : queryIntentActivities)
		{
            String str = resolveInfo.activityInfo.packageName;
            if (hashMap3.get(str) == null)
			{
                hashMap3.put(str, resolveInfo);
                PackageInfo packageInfo2 = (PackageInfo) hashMap.get(str);
                if (packageInfo2 != null)
				{
                    ItemApk itemApk = new ItemApk();
                    itemApk.setPackageName(packageInfo2.packageName);
                    itemApk.setDisplayName(packageInfo2.applicationInfo.loadLabel(getPackageManager()).toString());
                    itemApk.setSourceDir(packageInfo2.applicationInfo.sourceDir);
                    itemApk.setDataDir(packageInfo2.applicationInfo.dataDir);
                    itemApk.setVersionName(packageInfo2.versionName);
                    itemApk.setVersionCode(packageInfo2.versionCode);
                    itemApk.setIcon(packageInfo2.applicationInfo.loadIcon(getPackageManager()));
                    itemApk.setSize((int) new File(packageInfo2.applicationInfo.sourceDir).length());
                    if (hashMap2.get(str) != null)
					{
                        itemApk.setRunning(true);
                    }
                    this.alItemApk.add(itemApk);
                }
				else
				{
                    //Log.i(TAG, "no such package: " + str);
                }
            }
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean valueOf = Boolean.valueOf(defaultSharedPreferences.getBoolean("caseSensitive", false));
        Boolean valueOf2 = Boolean.valueOf(defaultSharedPreferences.getBoolean("descending", false));
        Boolean valueOf3 = Boolean.valueOf(defaultSharedPreferences.getString("sortBy", "名称").equals("大小"));
        Comparator displayNameDescendComparator = (valueOf3.booleanValue() || valueOf.booleanValue() || valueOf2.booleanValue()) ? (valueOf3.booleanValue() || valueOf.booleanValue() || !valueOf2.booleanValue()) ? (valueOf3.booleanValue() || !valueOf.booleanValue() || valueOf2.booleanValue()) ? (!valueOf3.booleanValue() && valueOf.booleanValue() && valueOf2.booleanValue()) ? new ItemApk.DisplayNameDescendComparator() : (!valueOf3.booleanValue() || valueOf2.booleanValue()) ? new ItemApk.SizeDescendComparator() : new ItemApk.SizeAscendComparator() : new ItemApk.DisplayNameAscendComparator() : new ItemApk.DisplayNameDescendComparatorIC() : new ItemApk.DisplayNameAscendComparatorIC();
        Collections.sort(this.alItemApk, displayNameDescendComparator);
        return this.alItemApk;
    }

    public void showToast(final String toast)
	{
        runOnUiThread(new Runnable() {
				public void run()
				{
					Toast.makeText(Main.this, toast + "", Toast.LENGTH_SHORT).show();
				}
			});
    }
}
