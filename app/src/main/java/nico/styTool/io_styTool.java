package nico.styTool;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class io_styTool extends AppCompatActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.io_root);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	ListView sa=(ListView)findViewById(R.id.mainListView1);
	try
	{
	    ActivityManager qw=(ActivityManager)io_styTool.this.getSystemService(ACTIVITY_SERVICE);
	    ArrayList<String> ary=new ArrayList<String>();
	    List<ActivityManager.RunningAppProcessInfo> act=qw.getRunningAppProcesses();
	    int i=1;
	    for (ActivityManager.RunningAppProcessInfo a:act)
	    {
		ary.add((i++) + ": " + a.processName + "\n" + " id " + a.pid + " uid " + a.uid);
	    }
	    ArrayAdapter shi=new ArrayAdapter<String>(io_styTool.this, android.R.layout.simple_list_item_1, ary);
	    sa.setAdapter(shi);
	}
	catch (Exception e)
	{
	    Toast.makeText(io_styTool.this, e.toString(), Toast.LENGTH_LONG).show();
	}
    }}
