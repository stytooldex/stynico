package nico.styTool;
//妮可妮可妮

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import dump.a.ShellUtils;

public class smali_layout_shell extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener
{
    private String M = "f";
    private String V = "a";
    private String J = "l";
    private String U = "s";
    private String F = "e";
    private String ٴ = "" + M + V + J + U + F + "";
    private String A = "app" + ".";
    private String B = "greyshirts" + ".";
    private String C = "sslcaptur";
    private String D = "finish().";
    private String E = "finish(framework)";
    private String ʭ = D + E + "ʭ";
    private String ĵ =A + B + C + "e";
    //private  MyUser user;
    private String h = "d";
    private String j = "5";
    private String k = "b";
    private String L = "7";
    private String m = "c";
    private String n = "9";
    private String o = "6";
    private String p = "f";
    private String q = "7";
    private String r = "9";
    private String s = "c";
    private String t = "e";
    private String u = "c";
    private String v = "f";
    private String w = "a";
    private String x = "9";
    private String y = "5";
    private String z = "7337750c";
    private String Xooos = "6.7";
    private String a="reboot recovery";//重启到Recovery界面
    private String b="reboot bootloader";//重启到bootloader界面
    private String c="reboot";//重启手机
    private String d="remount";//重新挂载文件系统
    private String e="erase boot";//擦除boot分区
    private String f="erase system";//擦除system分区
    private String g="erase cache";//擦除cache分区
    private String i="erase userdata";//擦除userdata分区
    //private String a="reboot recovery";//
    private boolean l=true;
    private boolean mazing=true;
    private boolean nico_a=true;
    private boolean isRoot=true;
    private boolean isNeedResultMsg=true;
    private void n()
    {}
    public void reboot()
    {
	String cmd = "su -c reboot";
        try
	{
	    Runtime.getRuntime().exec(cmd);
        }
	catch (IOException e)
	{
	    e.printStackTrace();
	    new AlertDialog.Builder(smali_layout_shell.this).setTitle("Error").setMessage(
		e.getMessage()).setPositiveButton("OK", null).show();
        }

    }
    public void m()
    {
	try
	{  

            Process process = Runtime.getRuntime().exec("su");  
            DataOutputStream out = new DataOutputStream(  
		process.getOutputStream());  
            out.writeBytes("reboot -p\n");  
            out.writeBytes("exit\n");  
            out.flush();  
        }
	catch (IOException e)
	{  
	    e.printStackTrace();
	    new AlertDialog.Builder(smali_layout_shell.this).setTitle("Error").setMessage(
		e.getMessage()).setPositiveButton("OK", null).show();
        }  
    }
    private void j()
    {
	//判断busybox存在与否
	File f1=new File("/system/xbin/busybox"); 
	if (f1.exists()) 
	{ 
	    //存在
	    Toast.makeText(smali_layout_shell.this, "检测busybox:已安装", Toast.LENGTH_LONG).show();
	} 
	else
	{
	    File f2=new File("/system/bin/busybox"); 
	    if (f2.exists()) 
	    {
		//存在
		Toast.makeText(smali_layout_shell.this, "检测busybox:已安装", Toast.LENGTH_LONG).show();
	    }
	    else
	    {
		Toast.makeText(smali_layout_shell.this, "检测busybox:未安装", Toast.LENGTH_LONG).show();
	    }
	}

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smali_layout_1);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	j();

	NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
	//ShellUtils.execCommand(a,isRoot,isNeedResultMsg);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.a)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("重启到bootloader界面")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {

		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(b, isRoot, isNeedResultMsg);
		    }
		})
		.create();
	    alert.show();
	}
	else if (id == R.id.b)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("重新挂载文件系统")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {

		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(d, isRoot, isNeedResultMsg);
		    }
		})
		.create();
	    alert.show();
        }
	else if (id == R.id.c)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("重启到Recovery界面")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {

		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(a, isRoot, isNeedResultMsg);


		    }
		})
		.create();
	    alert.show();
        }
	else if (id == R.id.d)
	{
	    LayoutInflater inflater = LayoutInflater.from(this);
	    View view = inflater.inflate(R.layout.lxw_push_helps_comment, null);
	    final EditText ediComment = (EditText) view.findViewById(R.id.lxw_id_push_helps_comment_edi);
	    ediComment.setError(null);

	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			String comment = ediComment.getText().toString().trim();
			if (TextUtils.isEmpty(comment))
			{
			    //ediComment.setError("内容不能为空");
			    // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
			    return;
			}
			ShellUtils.execCommand("fastboot flash recovery " + comment, isRoot, isNeedResultMsg);
			//	push(comment, myUser);
		    }
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			return;
		    }
		}).create().show();
	    //ShellUtils.execCommand(a,isRoot,isNeedResultMsg);

        }
	else if (id == R.id.e)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("重启手机")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {


		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(c, isRoot, isNeedResultMsg);


		    }
		})
		.create();
	    alert.show();

	}
	else if (id == R.id.g)
	{
	    //路过ShellUtils.execCommand(a,isRoot,isNeedResultMsg);
	    LayoutInflater inflater = LayoutInflater.from(this);
	    View view = inflater.inflate(R.layout.smali_layout_2, null);
	    final EditText ediComment = (EditText) view.findViewById(R.id.smalilayout2EditText1);
	    ediComment.setError(null);

	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			String comment = ediComment.getText().toString().trim();
			if (TextUtils.isEmpty(comment))
			{
			    //ediComment.setError("内容不能为空");
			    // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
			    return;
			}
			ShellUtils.execCommand(comment, isRoot, isNeedResultMsg);
			//	push(comment, myUser);
		    }
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			return;
		    }
		}).create().show();
	}
	else if (id == R.id.i)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("擦除boot分区")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(e, isRoot, isNeedResultMsg);
		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(e, isRoot, isNeedResultMsg);


		    }
		})
		.create();
	    alert.show();
	}
	else if (id == R.id.j)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("擦除system分区")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {


		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(f, isRoot, isNeedResultMsg);

		    }
		})
		.create();
	    alert.show();
	}
	else if (id == R.id.k)
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("擦除cache分区")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {

		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(g, isRoot, isNeedResultMsg);

		    }
		})
		.create();
	    alert.show();
	}
	else if (id == R.id.p)
	{

	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    AlertDialog alert = builder.setMessage("擦除userdata分区")
		.setPositiveButton("取消", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {

		    }
		}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which)
		    {
			ShellUtils.execCommand(i, isRoot, isNeedResultMsg);


		    }
		})
		.create();
	    alert.show();
	}

        return true;
    }}
