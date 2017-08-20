package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class crc_md extends AppCompatActivity
{

    private long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
	if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
	{   
	    if ((System.currentTimeMillis() - exitTime) > 2000)
	    {  
		//Toast.makeText(getApplicationContext(), "再按一次返回主页", Toast.LENGTH_SHORT).show();                                
		exitTime = System.currentTimeMillis();   
	    }
	    else
	    {
		//finish();
	    }
	    return true;   
	}
	return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//getSupportActionBar().hide();
        setContentView(R.layout.activity_cpu);
	////new Handler().postDelayed(new Runnable() {
	//	@Override
	//	public void run() {
	//	    finish();
	//	}
	//    }, 5000);
    //}
    }}


