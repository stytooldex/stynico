package nico.styTool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class a extends AppCompatActivity {
	private TextView volume;//显示音量的文本框
	Audio MyAudio;//自己写的用于获取音量的类
	public MyHandler myHandler;//用于传递数据给主线程更新UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adb);
        /*
         * 进行一些，初始化
         */
        volume=(TextView)findViewById(R.id.volume);
        myHandler=new MyHandler();
        MyAudio=new Audio(myHandler);
        MyAudio.getNoiseLevel();//获取音量
    }
    class MyHandler extends Handler{
    	@Override
    	public void handleMessage(Message msg) {
    		// TODO Auto-generated method stub
    		/*
    		 * 当收到message的时候，这个函数被执行
    		 * 读取message的数据，并显示到文本框中
    		 */
    		super.handleMessage(msg);
    		Bundle b=msg.getData();
    		String sound=b.getString("sound");
    		a.this.volume.setText(sound+"分贝");
    	}
    }
}
