package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
/**
 * Created by luxin on 15-12-15.
 *  http://luxin.gitcafe.io
 */
public class EdiUserProfileActivity extends AppCompatActivity
{
    private EditText ediUsername;
    /** Called when the activity is first created. */
    android.support.v7.widget.Toolbar toolbar;

    private void redirectByTime() {
        new Handler().postDelayed(new Runnable() {
		@Override
		public void run() {
		    Intent intent=getIntent();
		    //ToastUtil.show(EdiUserProfileActivity.this,intent.getStringExtra("via"),Toast.LENGTH_SHORT);
		    ediUsername.setText(intent.getStringExtra("via"));
		}
	    }, 2000);}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_edi_user_profile);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	redirectByTime();
	b();
        initView();
	}
	private void b(){
	ediUsername = (EditText) findViewById(R.id.lxw_edi_user_profile_name);
	}
    
    private void initView()
    {
	toolbar = (android.support.v7.widget.Toolbar)
	    findViewById(R.id.toolbar);
	    
	setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart()
    {
	// TODO: Implement this method
	super.onStart();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_edi_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if (id == R.id.lxw_action_edi_user_profile)
	{
            Intent intent=new Intent();
            intent.putExtra("username", ediUsername.getText().toString().trim());
            setResult(UserProfileActivity.RESULT_CODE, intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
