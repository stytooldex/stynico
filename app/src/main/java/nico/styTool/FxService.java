package nico.styTool;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import android.net.*;

public class FxService extends Service 
{
	private boolean isUpdateInfo;
	private Service cx;
	//定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;

	private Context context;  
	Button mFloatView;

	private String getRomAvailableSize()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }
//	private static final String TAG = "FxService";
	private String getSDAvailableSize()
    {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }
	public Handler mHandler=new Handler()  
    {

		private Context context;  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
				case 1:  
					//	Toast.makeText(context,"提示内容",Toast.LENGTH_SHORT).show();
					//	button.setText(R.string.text2);  
					break;  
				default:  
					break;        
            }  
            super.handleMessage(msg);  
        }  
    };  
	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		//	Log.i(TAG, "oncreat");
		createFloatView();
		//initBackThread();

		mTimeHandler.sendEmptyMessageDelayed(0, 500);

        //Toast.makeText(FxService.this, "create FxService", Toast.LENGTH_LONG);		
	}
	//@Override

	Handler mTimeHandler = new Handler() {

		public void handleMessage(android.os.Message msg)
		{

			if (msg.what == 0)
			{
				mFloatView.setText("SD卡剩余:" + getSDAvailableSize() + "\n" + "手机剩余:" + getRomAvailableSize());

				//tvTime.setText(TimeHelper.formatter_m.format(new Date(System.currentTimeMillis())));

				sendEmptyMessageDelayed(0, 500);

			}

		}

	};

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}


	private void createFloatView()
	{
		wmParams = new WindowManager.LayoutParams();
		//获取WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		//设置window type
		wmParams.type = LayoutParams.TYPE_PHONE; 
		//设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888; 
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = 
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
			LayoutParams.FLAG_NOT_FOCUSABLE;
//          LayoutParams.FLAG_NOT_TOUCHABLE;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.CENTER; 

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        mWindowManager.addView(mFloatLayout, wmParams);

        mFloatView = (Button)mFloatLayout.findViewById(R.id.float_id);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
															  View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
							 .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        mFloatView.setOnTouchListener(new OnTouchListener() 
			{

				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					// TODO Auto-generated method stub
					//getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
					wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
					//Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
					//Log.i(TAG, "RawX" + event.getRawX());
					//	Log.i(TAG, "X" + event.getX());
					//25为状态栏的高度
					wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 25;
					// Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
					//  Log.i(TAG, "RawY" + event.getRawY());
					//   Log.i(TAG, "Y" + event.getY());
					//刷新
					mWindowManager.updateViewLayout(mFloatLayout, wmParams);
					return false;
				}
			});	

        mFloatView.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View v) 
				{
					dump.l.loveviayou.a(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/A.mp4"));
					// TODO Auto-generated method stub
					//Toast.makeText(FxService.this, "onClick", Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		//mCheckMsgThread.quit();
		if (mFloatLayout != null)
		{
			mWindowManager.removeView(mFloatLayout);
		}
	}

}
