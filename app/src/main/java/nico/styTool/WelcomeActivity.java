package nico.styTool;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
 
public class WelcomeActivity extends AppCompatActivity {
	FloatingActionButton fabBtn;
	/** Called when the activity is first created. */
	android.support.v7.widget.Toolbar toolbar;
	private  void  findViews(){
		
	} 
	private void initInstances() {
		fabBtn = (FloatingActionButton) findViewById(R.id.fab);
		fabBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText editText = (EditText)findViewById(R.id.tv_no);
					String strurl = String.valueOf(editText.getText());
					ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					manager.setText(""+"\n");
					
				}
			});
	}
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.welcome);
		findViews();
		initInstances();
		toolbar = (android.support.v7.widget.Toolbar)
			findViewById(R.id.toolbar);
//		初始化Toolbar控件
		setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
	//-/	toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
	//	toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

    }

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		toolbar.setTitle("红包生成");
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
	}}
