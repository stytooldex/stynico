package nico.styTool;


import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class gifa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    /** Called when the activity is first created. */
    android.support.v7.widget.Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);	
        setContentView(R.layout.apktoolmain);

	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);

	toolbar = (android.support.v7.widget.Toolbar)
	    findViewById(R.id.toolbar);
//		初始化Toolbar控件
	setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
	//toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
	//toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

    }
    @Override
    protected void onStart()
    {
	// TODO: Implement this method
	super.onStart();
	toolbar.setTitle("特殊文本生成器");
//		设置标题

//		设置副标题
	toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
	toolbar.setNavigationOnClickListener(new OnClickListener()
	    {
		@Override
		public void onClick(View p1)
		{
		    finish();
		}
	    });
//		设置导航按钮监听
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.nav_meizi)
	{
	    click();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        }
	else if (id == R.id.nav_gank)
	{
	    bclick();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        }
	else if (id == R.id.nav_about)
	{
	    cclick();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
	else if (id == R.id.nav_se)
	{
	    dclick();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        }
	else if (id == R.id.nav_blog)
	{
	    eclick();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
	else if (id == R.id.nav_blogvip)
	{
	    clickvip();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
	else if (id == R.id.nav_blogsvip)
	{
	    clicksvip();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
	else if (id == R.id.apktool_textview)
	{
	    aac_styTool();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	}
	else if (id == R.id.apktool_textviewa)
	{
	    ac_styTool();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	    //
	}
	else if (id == R.id.apktool_textviewb)
	{
	    a();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	    //
        }
	else if (id == R.id.a_upi)
	{
	    ua();
	    Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
	    //
        }
        return true;
    }
    public void ua()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="妮̶"; 
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('妮', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }
    
    public void a()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo =getResources().getString(R.string.android_gifc); 
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('n', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }

    public void ac_styTool()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo =getResources().getString(R.string.android_gifb); 
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('n', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }

    public void aac_styTool()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo =getResources().getString(R.string.android_gifa); 
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('n', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }

    public void c_styTool()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo =getResources().getString(R.string.android_gifa); 
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('n', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }
    public void clickvip()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="◤Q◢";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('Q', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }
    public void clicksvip()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="❦H❧";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('H', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }
    public void click()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="ζั͡爱 ั͡✾";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('爱', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}
	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }
    public void bclick()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="爱҉";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('爱', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}

	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }	public void cclick()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="敏ۣۖ";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('敏', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}

	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }	public void dclick()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="爱\n";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('爱', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}

	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);
    }public void eclick()
    {
	EditText et=(EditText) findViewById(R.id.mainEditText1);
	String os =et.getText().toString();
	char[] a = os.toCharArray();
	StringBuffer b = new StringBuffer("");
	String mo ="‮ ‮ ‮爱";
	for (int i=0;i < a.length;i++)
	{
	    b.append(mo.replace('爱', a[i])) ;
	    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    manager.setText(b);
	}

	TextView t=(TextView) findViewById(R.id.mainTextView1);
	t.setText(b);}}
			 


