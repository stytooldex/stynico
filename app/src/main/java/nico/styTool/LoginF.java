package nico.styTool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by apple on 15/9/20.
 */
public class LoginF extends Activity implements View.OnClickListener
{

    // private EditText editRoom,editNickname;
    private Button buttonCreate;
    private String password,roomlon,roomlat;
    private Double lat,lon;
    private Information information;
    ListView listView;
    //private ProgressDialog mProgressDialog;
    static List<Login> weibos = new ArrayList<Login>();
    MyAdapter adapter;

    @Override
    protected void onRestart()
    {
        super.onRestart();
		findWeibos();
		amc();
    }

    private void amc()
    {
		BmobQuery<Login> bmobQuery = new BmobQuery<Login>();
		bmobQuery.count(this, Login.class, new CountListener() {

				@Override
				public void onSuccess(int count)
				{
					TextView key = (TextView) findViewById(R.id.activitylogButton1);
					key.setText("房间：" + count); 
				}
				@Override
				public void onFailure(int code, String msg)
				{
				}
			});
    }
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Window window = getWindow();//第一行
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,//第二行
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//第三行 
        setContentView(R.layout.activity_log);
		findWeibos();
		adapter = new MyAdapter(this);
        // 初始化控件
        init();
		amc();
        //设置监听
        buttonCreate.setOnClickListener(this);

    }
    private void findWeibos()
    {
		//MyUser user = BmobUser.getCurrentUser(LoginF.this, MyUser.class);
		BmobQuery<Login> query = new BmobQuery<Login>();
		//query.addWhereEqualTo("author", user);	// 查询当前用户的所有微博
		query.order("-updatedAt");
		query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(LoginF.this, new FindListener<Login>() {
				@Override
				public void onSuccess(List<Login> object)
				{
					// TODO Auto-generated method stub
					weibos = object;
					adapter.notifyDataSetChanged();
					//  mProgressDialog.dismiss();
				}

				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					//   toast("查询失败:" + msg);
				}
			});
		//mProgressDialog = ProgressDialog.show(getActivity(), null, getResources().getString(R.string.del_));
    }
    private void init()
    {
        buttonCreate = (Button)findViewById(R.id.button_create);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
    }
    private class MyAdapter extends BaseAdapter
    {

		private LayoutInflater mInflater;

		private Context mContext;

		public MyAdapter(Context context)
		{
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		class ViewHolder
		{
			TextView tv_content;
			TextView tv_author;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
//			return bmobObjects.size();
			return weibos.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			final ViewHolder holder;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.list_item_weibo, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			final Login weibo = weibos.get(position);
			holder.tv_author.setText(weibo.getCreatedAt());
			final String str = weibo.getRoom_id();
			holder.tv_content.setText(str);
			convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{

						//Toast.makeText(LoginF.this, weibo.getObjectId(), Toast.LENGTH_SHORT).show();
						LayoutInflater inflater = LayoutInflater.from(LoginF.this);
						View view = inflater.inflate(R.layout.a_d_, null);
						final android.support.v7.widget.AppCompatEditText a = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.edit_nickname);
						final android.support.v7.widget.AppCompatEditText b = (android.support.v7.widget.AppCompatEditText) view.findViewById(R.id.edit_room_id);
						b.setText(weibo.getRoom_id());
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginF.this);
						builder.setView(view).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									Intent intent=getIntent();
									final String roomId = b.getText().toString().trim();
									final String nickname = intent.getStringExtra("#");
									BmobQuery<Login> query = new BmobQuery<Login>();
									//查询room_id叫“room_id(代表的String)”的数据
									query.addWhereEqualTo("room_id", roomId);
									//返回10条数据，如果不加上这条语句，默认返回10条数据
									query.setLimit(10);
									//执行查询方法
									query.findObjects(LoginF.this, new FindListener<Login>() {
											@Override
											public void onSuccess(List<Login> object)
											{
												// TODO Auto-generated method stub
												if (object.size() < 100 && object.size() >= 1)
												{
													//判断是否有密码
													Boolean judge=false;
													Boolean judgeName=false;//默认关
													Boolean judgeLocation=false;

													for (Login login : object)
													{
														password = login.getPassword();
														roomlon = login.getLon();
														roomlat = login.getLat();
														if (login.getNickname().equals(nickname))
														{
															judgeName = false;

														}
														if (!(password == null))
														{
															judge = true;
														}
														if (!(roomlon == null))
														{
															judgeLocation = true;
														}

													}
													if (judgeName)
													{
														//Toast.makeText(LoginF.this, "id已存在，请重新输入", Toast.LENGTH_SHORT).show();
													}
													else
													{

														if (!judge)
														{
															//实例化
															Login login = new Login();
															//设置要传入的内容
															login.setNickname(nickname);
															login.setRoom_id(roomId);
															//开始传入
															login.save(LoginF.this, new SaveListener() {

																	@Override
																	public void onSuccess()
																	{
																		// TODO Auto-generated method stub
																		Toast.makeText(LoginF.this, "登录成功", Toast.LENGTH_SHORT).show();
																		//把昵称和房间号保存到Application
																		information = (Information) getApplication();
																		information.setRoomId(roomId);
																		information.setNickname(nickname);
																		Intent intentToChat = new Intent(LoginF.this, ChatActivity.class);
																		startActivity(intentToChat);

																	}


																	@Override
																	public void onFailure(int code, String arg0)
																	{
																		// TODO Auto-generated method stub
																		Toast.makeText(LoginF.this, "登录失败", Toast.LENGTH_SHORT).show();
																	}
																});
														}
														else
														{
															final boolean finalJudgeLoaction=judgeLocation;
															final android.support.v7.widget.AppCompatEditText editPassword=new android.support.v7.widget.AppCompatEditText(LoginF.this);
															new AlertDialog.Builder(LoginF.this).setTitle("请输入房间密码").setView(editPassword).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
																	@Override
																	public void onClick(DialogInterface dialog, int which)
																	{
																		if (editPassword.getText().toString().equals(password))
																		{
																			if (finalJudgeLoaction)
																			{
																				//  initGPS();
																			}
																			else
																			{
																				Login login = new Login();
																				//设置要传入的内容
																				//nickname = null;
																				login.setNickname(nickname);
																				//roomId = null;
																				login.setRoom_id(roomId);
																				login.setPassword(password);
																				//开始传入
																				login.save(LoginF.this, new SaveListener() {

																						@Override
																						public void onSuccess()
																						{
																							// TODO Auto-generated method stub
																							Toast.makeText(LoginF.this, "登录成功", Toast.LENGTH_SHORT).show();
																							//把昵称和房间号保存到Application
																							information = (Information) getApplication();
																							information.setRoomId(roomId);
																							information.setNickname(nickname);
																							Intent intentToChat = new Intent(LoginF.this, ChatActivity.class);
																							startActivity(intentToChat);

																						}


																						@Override
																						public void onFailure(int code, String arg0)
																						{
																							// TODO Auto-generated method stub
																							Toast.makeText(LoginF.this, "登录失败", Toast.LENGTH_SHORT).show();
																						}
																					});
																			}
																		}
																		else
																		{
																			Toast.makeText(LoginF.this, "密码错误", Toast.LENGTH_SHORT).show();
																		}

																	}
																}).setNegativeButton(android.R.string.no, null).show();
															Toast.makeText(LoginF.this, "请输入密码", Toast.LENGTH_SHORT).show();
														}
													}

												}
												else if (object.size() == 100)
												{
													Toast.makeText(LoginF.this, "此房间以满员，请联系妮媌官方", Toast.LENGTH_SHORT).show();
												}
												else if (object.size() == 0)
												{
													Toast.makeText(LoginF.this, "此房间不存在", Toast.LENGTH_SHORT).show();
												}

											}

											@Override
											public void onError(int code, String msg)
											{
												// TODO Auto-generated method stub
												//  Toast.makeText(LoginF.this, "系统错误，请重试", Toast.LENGTH_SHORT).show();
											}
										});}

							}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									return;
								}
							}).setCancelable(false).create().show();

					}
				});

			return convertView;
		}
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
		{
            case R.id.button_create:
                Intent intent=new Intent(LoginF.this, CreateActivity.class);
                startActivity(intent);
                break;
        }
    }

    LocationListener locationLinstener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location)
		{
            lat = location.getLatitude();
            lon = location.getLongitude();
            String currentPosition=+location.getLatitude() + "" + location.getLongitude();
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginF.this);
            dialog.setMessage(currentPosition);
            dialog.setPositiveButton("进入",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1)
					{

					}
				});
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1)
					{
						arg0.dismiss();
					}
				});
            dialog.show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
		{
        }

        @Override
        public void onProviderEnabled(String provider)
		{
        }

        @Override
        public void onProviderDisabled(String provider)
		{
        }
    };

}
