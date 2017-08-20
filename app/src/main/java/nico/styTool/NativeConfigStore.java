package nico.styTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class NativeConfigStore extends AppCompatActivity
{
    android.support.v7.widget.Toolbar toolbar;

    private Button d;
    private EditText et;
    private Button b;

    private EditText t1;

    private EditText t2;

    private Button id;

    private Button tid;

    private LinearLayout button;

    private LinearLayout button1;
    /*
     public void 加密(View v){
     try{
     t2.setText(jiami(t1.getText().toString()));
     }
     catch(Exception e){

     // Toast.makeText(MainActivity.this,"确认是加密！",Toast.LENGTH_LONG).show();
     }
     }
     public void 解密(View v){

     try{
     t2.setText(jiemi(t1.getText().toString()));


     }
     catch(Exception e){

     //Toast.makeText(MainActivity.this,"确认是解密！",Toast.LENGTH_LONG).show();
     }
     }*/
    private LinearLayout button2;
    private void giff()
    {

	button1 = (LinearLayout) findViewById(R.id.axpLinearLayout2);
	button2 = (LinearLayout) findViewById(R.id.axpLinearLayout1);
	Intent intent=getIntent();
	String s=intent.getStringExtra("#");
	String sr="A";
	if (s.equals(sr))
	{
	    button1.setVisibility(View.VISIBLE);

	}
	else
	{
	    button2.setVisibility(View.VISIBLE);
	}

	button = (LinearLayout) findViewById(R.id.axLinearLayout1);
	t1 = (EditText) findViewById(R.id.axEditText2);//结果
	t2 = (EditText) findViewById(R.id.axEditText1);//原

	id = (Button) findViewById(R.id.axButton1);//解
	id.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v)
		{

		    String comment = t2.getText().toString();
		    //  stringUnicode(comment);
		    SharedPreferences mySharedPreferences= getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("via_r",Bookmark.jiemi(comment));
		    editor.commit();
		    t1.setText(Bookmark.jiemi(comment));
		}

	    });

	tid = (Button) findViewById(R.id.axButton2);//解
	tid.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v)
		{

		    String comment = t2.getText().toString();
		    t1.setText(Bookmark.jiami(comment));
		    SharedPreferences mySharedPreferences= getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("via_r", "" + comment);
		    editor.commit();

		}

	    });
    }

    private TextView t;
    private void c()
    {//转U
	d = (Button) findViewById(R.id.axpButton1);
	d.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v)
		{
		    String comment = et.getText().toString().trim();
		    if (TextUtils.isEmpty(comment))
		    {
			// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
			return;
		    }
		    stringUnicode(comment);
		}
		//字符串转换unicode
		public  String stringUnicode(String string)
		{
		    StringBuffer unicode = new StringBuffer();
		    for (int i = 0; i < string.length(); i++)
		    {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		    }
		    t.setText(unicode);
		    SharedPreferences mySharedPreferences= getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("via_r", "" + unicode);
		    editor.commit();
		    return unicode.toString();
		}

	    });
    }
    private void a()
    {
	b = (Button) findViewById(R.id.axpButton2);
	b.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v)
		{

		    String comment = et.getText().toString().trim();
		    if (TextUtils.isEmpty(comment))
		    {// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
			return;
		    }
		    unicode2string(comment);
		}
		public  String unicode2string(String s)
		{
		    List<String> list =new ArrayList<String>();
		    String zz="\\\\u[0-9,a-z,A-Z]{4}";
		    Pattern pattern = Pattern.compile(zz);
		    Matcher m = pattern.matcher(s);
		    while (m.find())
		    {
			list.add(m.group());
		    }
		    for (int i=0,j=2;i < list.size();i++)
		    {
			String st = list.get(i).substring(j, j + 4);
			char ch = (char) Integer.parseInt(st, 16);
			s = s.replace(list.get(i), String.valueOf(ch));
		    }
		    t.setText(s);
		    SharedPreferences mySharedPreferences= getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("via_r", "" + s);
		    editor.commit();
		    return s;
		}
	    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.xp_ro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if (id == R.id.lxw_action_edi_user_profilea)
	{
	    SharedPreferences sharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE); 
	    String via = sharedPreferences.getString("via_r", "");
	    Intent sendIntent = new Intent();
	    sendIntent.setAction(Intent.ACTION_SEND);
	    sendIntent.putExtra(Intent.EXTRA_TEXT, via);
	    sendIntent.setType("text/plain");
	    startActivity(sendIntent);
        }if (id == R.id.action_lxw)
	{
	    SharedPreferences sharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE); 
	    String via = sharedPreferences.getString("via_r", "");
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText("" + via);
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart()
    {
	super.onStart();
	toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
	toolbar.setNavigationOnClickListener(new OnClickListener()
	    {
		@Override
		public void onClick(View p1)
		{
		    finish();
		}
	    });}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.a_xp);
StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	t = (TextView) findViewById(R.id.axpTextView1);
	et = (EditText) findViewById(R.id.axpEditText1);
	a();
	c();
	giff();
	toolbar = (android.support.v7.widget.Toolbar)
	    findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
    }}
