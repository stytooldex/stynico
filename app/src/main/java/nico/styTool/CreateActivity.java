package nico.styTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by apple on 15/9/22.
 */
public class CreateActivity extends Activity implements View.OnClickListener
{

    private android.support.v7.widget.AppCompatEditText editRoom, editPassword, editNickname;
    //private TextView textLocation;
    private Button buttonCreate;
    // private Double lat,lon;
    private String roomId, password, nickname;
    private Information information;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	Window window = getWindow();//第一行
	window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,//第二行
			WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//第三行
        setContentView(R.layout.activity_create);
        init();
        //设置监听
        buttonCreate.setOnClickListener(this);
    }

    private void init()
    {
        editNickname = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_nickname);
        editRoom = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_room_id);
        editPassword = (android.support.v7.widget.AppCompatEditText) findViewById(R.id.edit_password);
        buttonCreate = (Button) findViewById(R.id.button_create);
        //textLocation = (TextView) findViewById(R.id.text_location);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
	{
            case R.id.button_create:
                nickname = editNickname.getText().toString();
                roomId = editRoom.getText().toString();
                password = editPassword.getText().toString();

                //判断是否为空值
                if (roomId.equals(""))
		{
                    Toast.makeText(CreateActivity.this, "请输入你的房间名", Toast.LENGTH_SHORT).show();
                }
		else if (nickname.equals(""))
		{
                    Toast.makeText(CreateActivity.this, "请输入你的id", Toast.LENGTH_SHORT).show();
                }
		else
		{

                    BmobQuery<Login> query = new BmobQuery<Login>();
                    //查询room_id叫“room_id(代表的String)”的数据
                    query.addWhereEqualTo("room_id", roomId);
                    //返回10条数据，如果不加上这条语句，默认返回10条数据
                    query.setLimit(10);
                    //执行查询方法
                    query.findObjects(this, new FindListener<Login>() {
			    @Override
			    public void onSuccess(List<Login> object)
			    {
				// TODO Auto-generated method stub
				if (object.size() == 0)
				{
				    //实例化
				    Login login = new Login();
				    //判端是否要加密码
				    if (!password.equals(""))
				    {
					//传入密码
					login.setPassword(password);
				    }

				    //设置要传入的内容
				    login.setNickname(nickname);
				    login.setRoom_id(roomId);
				    //开始传入
				    login.save(CreateActivity.this, new SaveListener() {

					    @Override
					    public void onSuccess()
					    {
						// TODO Auto-generated method stub
						//Toast.makeText(CreateActivity.this, "成功", Toast.LENGTH_SHORT).show();

						Toast.makeText(CreateActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						//把昵称和房间号保存到Application
						information = (Information) getApplication();
						information.setRoomId(roomId);
						information.setNickname(nickname);
						information.setDis("0");
						Intent intentToChat = new Intent(CreateActivity.this, ChatActivity.class);
						startActivity(intentToChat);


					    }


					    @Override
					    public void onFailure(int code, String arg0)
					    {
						// TODO Auto-generated method stub
						//   Toast.makeText(CreateActivity.this, "失败", Toast.LENGTH_SHORT).show();
					    }
					});
				}
				else
				{
				    Toast.makeText(CreateActivity.this, "已创建 请换个房间名", Toast.LENGTH_SHORT).show();
				}

			    }

			    @Override
			    public void onError(int code, String msg)
			    {
				// TODO Auto-generated method stub
				//  Toast.makeText(CreateActivity.this, "系统错误，请重试", Toast.LENGTH_SHORT).show();
			    }
			});

                }
                break;
        }
    }}

    

