package nico.styTool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
/**
 * Created by apple on 15/9/20.
 */
public class ChatActivity extends Activity implements View.OnClickListener
{
    private ViewPager emojPager;
    private boolean isOpen = false;
    private ArrayList<GridView> mGridViews;
    //private Information information;
    private ListView mMsgs;
    private List<Content> mDatas;
    private   EditText mInputMsg;
    private Button mSendMsg;
    private String latestTime,objectId;
    //private TextView chatTitle;
    private String lastContent="";
    private String lastCreatedAt = "2016-12-10 11:07:37";

    private ProgressDialog mProgressDialog;
    BmobPushManager<BmobInstallation> bmobPush;

    private TextWatcher textWatcher;

    public  void setProhibitEmoji(EditText et)
    {
        InputFilter[] filters = { getInputFilterProhibitEmoji() ,getInputFilterProhibitSP()};
        et.setFilters(filters);
    }

    public  InputFilter getInputFilterProhibitEmoji()
    {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend)
			{
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++)
				{
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint))
					{
                        buffer.append(codePoint);
                    }
					else
					{
						//  ToastUtil.show("群组昵称不能含有第三方表情");
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned)
				{
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
											sp, 0);
                    return sp;
                }
				else
				{
                    return buffer;
                }
            }
        };
        return filter;
    }


    public  boolean getIsEmoji(char codePoint)
    {
        return codePoint != 0x26a1;
    }


    public  InputFilter getInputFilterProhibitSP()
    {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend)
			{
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++)
				{
                    char codePoint = source.charAt(i);
                    if (!getIsSP(codePoint))
					{
                        buffer.append(codePoint);
                    }
					else
					{
						//   ToastUtil.show("群组昵称不能含有特殊字符");
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned)
				{
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
											sp, 0);
                    return sp;
                }
				else
				{
                    return buffer;
                }
            }

			private boolean getIsSP(char codePoint)
			{
				// TODO: Implement this method
				return false;
			}
        };
        return filter;
    }
    

    @Override
    protected void onRestart()
    {
        super.onRestart();
		//webview.loadUrl("file:///android_asset/classes8.html");
		mMsgs.setSelection(mMsgs.getBottom());

		mMsgs.smoothScrollToPosition(mMsgs.getCount() - 1);//移动到尾部

    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
		//BmobPush.setDebugMode(true);
		//bmobPush = new BmobPushManager<BmobInstallation>(this);
		//StatusBarUtil.setStatusBarLightMode(this, getResources().getColor(R.color.colorAccent));
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAc));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        lastCreatedAt = df.format(new Date());// new Date()为获取当前系统时间
        lastCreatedAt = lastCreatedAt + " 00:00:00";

        //获取Application
        //information = (Information) getApplication();
        //实例化数组
        mDatas = new ArrayList<Content>();
        //实例化控件
        rinitView();
        //设置房间名
		//  chatTitle.setText(information.getRoomId());//修行
        //设置监听
        mSendMsg.setOnClickListener(this);


        Thread thread=new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					Intent intent=getIntent();
					//实时监听
					//adapter = new ChatMessageAdapter(ChatActivity.this, mDatas, intent.getStringExtra("#"));
					//mMsgs.setAdapter(adapter);
					while (true)
					{
						try
						{
							// Toast.makeText(ChatActivity.this,"10",Toast.LENGTH_SHORT).show();

							//判断是否有新消息定义一个Boolean
							CheckMessage();
							Thread.sleep(1600);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}

					}
				}
			});
        thread.start();

    }

    protected void onStop()
    {
        super.onStop();
    }

    private void CheckMessage()
    {

        BmobQuery<Content> query = new BmobQuery<Content>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("room_id", "校园情色");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(120);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
		{
            Date lastDate = sdf.parse(lastCreatedAt);
            query.addWhereGreaterThan("createdAt", new BmobDate(lastDate));
            query.order("createdAt");
        }
		catch (ParseException e)
		{
            e.printStackTrace();
        }
        //执行查询方法
        query.findObjects(this, new FindListener<Content>() {
				@Override
				public void onSuccess(List<Content> object)
				{
					mProgressDialog.dismiss();

					// TODO Auto-generated method stub
					for (Content content : object)
					{
						//获取上一次最近时间

						Content content1=new Content();
						content1.setNickname(content.getNickname());
						content1.setRoom_id(content.getRoom_id());
						content1.setContent(content.getContent());
						//content1.setTime(content.getCreatedAt());
						//更新最近的接受到的聊天消息的时间
						if (lastContent.equals(content.getContent()) && lastCreatedAt.equals(content.getCreatedAt()))
						{
							//  Toast.makeText(ChatActivity.this,"1",Toast.LENGTH_SHORT).show();

						}
						else if (GetNumberFromString.change(lastCreatedAt) >= GetNumberFromString.change(content.getCreatedAt()))
						{


						}
						else
						{
							//   Toast.makeText(ChatActivity.this,"12",Toast.LENGTH_SHORT).show();
							new Handler().postDelayed(new Runnable() {
									@Override
									public void run()
									{

										mMsgs.smoothScrollToPosition(mMsgs.getCount() - 1);//移动到尾部
										mMsgs.setSelection(mMsgs.getBottom());

									}
								}, 1000);
							//保存最近的时间
							lastCreatedAt = content.getCreatedAt();
							mDatas.add(content1);
							lastContent = content1.getContent();
						}


					}

				//	adapter.notifyDataSetChanged();

				}
				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					//Toast.makeText(ChatActivity.this, "获取消息失败", Toast.LENGTH_SHORT).show();
				}
			});


    }
    int count=0;

    private void rinitView()
    {
		SharedPreferences setting = getSharedPreferences("xi__", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first)
		{
			//setting.edit().putBoolean("FIRST", false).commit();
			Intent ntent=getIntent();
			final String mess=ntent.getStringExtra("#") + "已加入聊天";
			if (mess.equals(""))
			{
				//  Toast.makeText(ChatActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent=getIntent();
				String nickname=intent.getStringExtra("#");
				String roomId="校园情色";
				//上传消息
				Content content = new Content();
				content.setRoom_id(roomId);
				content.setNickname(nickname);
				content.setContent(mess);
				content.save(ChatActivity.this, new SaveListener() {

						@Override
						public void onSuccess()
						{

							//滚动到底部
							new Handler().postDelayed(new Runnable() {
									@Override
									public void run()
									{
										BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
										query.addWhereEqualTo("deviceType", "android");
										bmobPush.setQuery(query);
										bmobPush.pushMessage(mess);
									}
								}, 5000);
							//Toast.makeText(ChatActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onFailure(int code, String arg0)
						{
							// TODO Auto-generated method stub
							// 添加失败
						}
					});}
		}
		else
		{
		}
		ImageView la = (ImageView)findViewById(R.id.activitychatImageView2);
		la.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v8)
				{
					SharedPreferences setting = ChatActivity.this.getSharedPreferences("_a_", 0);
					Boolean user_first = setting.getBoolean("FIRST", true);
					if (user_first)
					{
						setting.edit().putBoolean("FIRST", false).commit();
						AlertDialog.Builder obuilder = new AlertDialog.Builder(ChatActivity.this);
						AlertDialog alertDialog = obuilder.setMessage("玩游戏吗？\n6秒内！能够解决一切压力")
							.setNegativeButton("知道", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{

								}
							}).setCancelable(false)
							.create();

						alertDialog.show();
					}
					else
					{
						final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
						final LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
						final View view = inflater.inflate(R.layout.a_jko, null);
						final dump.u.CircleButton ediComment = (dump.u.CircleButton) view.findViewById(R.id.ajkoCircleButton1);
						ediComment.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v8)
								{
									SharedPreferences imySharedPreferences= ChatActivity.this.getSharedPreferences("test", Activity.MODE_PRIVATE);
									SharedPreferences.Editor veditor = imySharedPreferences.edit();
									veditor.putString("via__", String.valueOf(count));
									veditor.commit();
									String fff =String.valueOf(count);
									count++;

								}});
						alertDialog.setView(view)

							.setCancelable(false)
							.create();
						final AlertDialog dia = alertDialog.show();
						new Handler().postDelayed(new Runnable() {
								@Override
								public void run()
								{

									dia.dismiss();
									// alertDialog.notify();
									//alertDialog.Create().dismiss();
									/*
									 btnTwo = (Button) findViewById(R.id.api_id_button_btntwo);
									 btnTwo.setOnClickListener(new View.OnClickListener() {
									 @Override
									 public void onClick(View v) {

									 }
									 });*/
									SharedPreferences sharedPreferences = ChatActivity.this.getSharedPreferences("test", Activity.MODE_PRIVATE); 
									String r = sharedPreferences.getString("via__", "");
									final String mess="+" + r + "s🐸";
									if (mess.equals(""))
									{
										//  Toast.makeText(ChatActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
									}
									else
									{
										Intent intent=getIntent();
										String nickname=intent.getStringExtra("#");
										String roomId="校园情色";
										//上传消息
										Content content = new Content();
										content.setRoom_id(roomId);
										content.setNickname(nickname);
										content.setContent(mess);
										content.save(ChatActivity.this, new SaveListener() {

												@Override
												public void onSuccess()
												{
													SharedPreferences imySharedPreferences= ChatActivity.this.getSharedPreferences("test", Activity.MODE_PRIVATE);
													SharedPreferences.Editor veditor = imySharedPreferences.edit();
													veditor.putString("via__", String.valueOf(0));
													veditor.commit();
													BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
													query.addWhereEqualTo("deviceType", "android");
													bmobPush.setQuery(query);
													bmobPush.pushMessage(mess);


													//Toast.makeText(ChatActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
												}

												@Override
												public void onFailure(int code, String arg0)
												{
													// TODO Auto-generated method stub
													// 添加失败
												}
											});}

								}
							}, 6000);

						// .show();

					}

				}
			});

		FloatingActionButton l = (FloatingActionButton)findViewById(R.id.smalilayout2tilFloatingActionButton1);
		l.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v8)
				{
					mMsgs.smoothScrollToPosition(mMsgs.getCount() - 1);//移动到尾部
					mMsgs.setSelection(mMsgs.getBottom());
				}
			});
		emojPager = (ViewPager) findViewById(R.id.id_lxw_push_emoj_viewpager);
		emojPager.setOnClickListener(this);
		// chatTitle=(TextView)findViewById(R.id.chat_title);
		mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
		ImageView btnTwo = (ImageView) findViewById(R.id.activitychatImageView1);
        btnTwo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
                    isOpen = !isOpen;
					//showEmotion(isOpen);
				}
			});

		mInputMsg = (EditText) findViewById(R.id.id_input_msg);
		//mInputMsg.addTextChangedListener(textWatcher);
		// setProhibitEmoji(mInputMsg);
		mInputMsg.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					if (isOpen)
					{
						// openKeyBoard();
						isOpen = false;
					//	showEmotion(isOpen);
					}
					return false;
				}
			});
		mSendMsg = (Button) findViewById(R.id.id_send_msg);

		mProgressDialog = ProgressDialog.show(ChatActivity.this, null, "刷新ing...");
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
		{
            case R.id.id_send_msg:
                final String mess=mInputMsg.getText().toString();
                if (mess.equals(""))
				{
					//  Toast.makeText(ChatActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
                }
				else
				{
					Intent intent=getIntent();
                    String nickname=intent.getStringExtra("#");
                    String roomId="校园情色";
                    //上传消息
                    Content content = new Content();
                    content.setRoom_id(roomId);
                    content.setNickname(nickname);
                    content.setContent(mess);
                    content.save(ChatActivity.this, new SaveListener() {

							@Override
							public void onSuccess()
							{
								BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
								query.addWhereEqualTo("deviceType", "android");
								bmobPush.setQuery(query);
								bmobPush.pushMessage(mess);
								mInputMsg.setText("");
								mSendMsg.setEnabled(false);
								//滚动到底部
								new Handler().postDelayed(new Runnable() {
										@Override
										public void run()
										{
											mSendMsg.setEnabled(true);
											// webview.loadUrl("file:///android_asset/classes8.html");
											//   mMsgs.smoothScrollToPosition(mMsgs.getCount() - 1);//移动到尾部
											mMsgs.setSelection(mMsgs.getBottom());

										}
									}, 3000);
								//Toast.makeText(ChatActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFailure(int code, String arg0)
							{
								// TODO Auto-generated method stub
								// 添加失败
							}
						});
                }

                break;
        }
    }
}
