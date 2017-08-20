package nico.styTool;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.widget.*;

/**
 * Created by luxin on 15-12-13.
 *  http://luxin.gitcafe.io
 */
public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    //private final static String TAG = "UserProfileActivity";

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;

    /** Called when the activity is first created. */
    android.support.v7.widget.Toolbar toolbar;

    private RelativeLayout Img;
    private ImageView userImg;
    private RelativeLayout username;

    private RelativeLayout sex;
    private TextView usernameText;

    private  TextView email;

    private RelativeLayout personality;
    private TextView personalityText;

    private Button btn;

    private boolean isChange = false;

    private String filePath = null;

    private MyUser myUser = null;
    private boolean isUsername = false;

    private boolean isChangeUsername = false;

    private ProgressDialog mProgressDialog;

    private boolean isChangeUserImgNo=true;

    private  MyUser user;
    private TextView a;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_user_profile);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        myUser = BmobUser.getCurrentUser(this, MyUser.class);
        user = new MyUser();
        initView();
        initEvent();
        initData();
    }
    private void initData()
    {
        if (myUser.getAuvter() != null)
		{
			// Log.e(TAG, "===getAuvtero file url====" + myUser.getAuvter().getUrl());
            String auvterPath = "http://file.bmob.cn/" + myUser.getAuvter().getUrl();
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(auvterPath, userImg, true);
        }
        //Log.e(TAG,"====sex===="+myUser.getSex());
		/*
		 if (myUser.getSex().equals(0))
		 {
		 sexText.setText("男");
		 }
		 else
		 {
		 sexText.setText("女");
		 }
		 */
		a.setText(myUser.getCreatedAt());
		personalityText.setText(myUser.getPersonality());
        usernameText.setText(myUser.getUsername());
        email.setText(myUser.getEmail());

    }


    private void initView()
    {
		toolbar = (android.support.v7.widget.Toolbar)
			findViewById(R.id.toolbar);
//		初始化Toolbar控件
		setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
		//toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
		//	toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

        username = (RelativeLayout) findViewById(R.id.lxw_user_profile_username);
        usernameText = (TextView) findViewById(R.id.lxw_id_user_profile_username_text);

        email = (TextView) findViewById(R.id.lxw_id_user_profile_email);

        Img = (RelativeLayout) findViewById(R.id.lxw_user_profile_img);
        userImg = (ImageView) findViewById(R.id.lxw_id_user_profile_userimg);
        sex = (RelativeLayout) findViewById(R.id.lxw_user_profile_sex);

        personality = (RelativeLayout) findViewById(R.id.lxw_user_profile_personality);
        personalityText = (TextView) findViewById(R.id.lxw_user_profile_personality_text);
		a = (TextView) findViewById(R.id.lxwuserprofileTextView1);

        btn = (Button) findViewById(R.id.lxw_user_profile_btn_save);
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.getObject(this, myUser.getObjectId(), new GetListener<MyUser>() {

				@Override
				public void onSuccess(MyUser object)
				{
					TextView sexText = (TextView) findViewById(R.id.lxw_id_user_profile_sex_text);
					Integer ios = object.getSex();
					sexText.setText(Integer.toString(ios));

					if (object.getAddress().equals("激活"))
					{
						CheckBox cb = (CheckBox) findViewById(R.id.lxwuserprofileCheckBox2);
						cb.setChecked(true);//选中
					}
					else
					{

					}
					String s="" + object.getEmailVerified();
					String sr="true";
					if (s.equals(sr))
					{
						CheckBox b = (CheckBox) findViewById(R.id.lxwuserprofileCheckBox1);
						b.setChecked(true);//选中
					}
					else
					{
						//Toast.makeText(WeiboListActivity.this, "未激活帐号最多(68字)", Toast.LENGTH_SHORT).show();
					}

				}
				@Override
				public void onFailure(int code, String msg)
				{
					// TODO Auto-generated method stub
				}
			});

    }

    private void initEvent()
    {
        Img.setOnClickListener(this);
        username.setOnClickListener(this);
        sex.setOnClickListener(this);
        btn.setOnClickListener(this);
        personality.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.lxw_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        int id = item.getItemId();
        if (id == R.id.lxw_action_menu_logout)
		{
			BmobUser.logOut(this);
            finish();
            return true;
        }
        if (id == R.id._menu_logout)
		{
			TelephonyManager telephonyManager=(TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
			String imei="" + telephonyManager.getDeviceId();
			//String last6chars = imei.remove(0,imei.length-6);
			int n=4;
			final String b=imei.substring(imei.length() - n, imei.length());
			final int count = Integer.parseInt(b) + 36;
			//Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
			final MyUser myUser = BmobUser.getCurrentUser(UserProfileActivity.this, MyUser.class);
			BmobQuery<MyUser> query = new BmobQuery<MyUser>();
			query.getObject(UserProfileActivity.this, myUser.getObjectId(), new GetListener<MyUser>() {


					@Override
					public void onSuccess(MyUser object)
					{

						String s="" + object.getEmailVerified();
						String sr="true";
						if (s.equals(sr))
						{
							Toast.makeText(UserProfileActivity.this, "帐号已经激活过", Toast.LENGTH_SHORT).show();
							//这
						}
						else
						{
							LayoutInflater inflater = LayoutInflater.from(UserProfileActivity.this);
							View view = inflater.inflate(R.layout.aew, null);
							final EditText ediComment = (EditText) view.findViewById(R.id.aewEditText1);
							AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
							builder.setView(view).setPositiveButton("免费激活", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										String comment = ediComment.getText().toString().trim();
										if (TextUtils.isEmpty(comment))
										{
											//ediComment.setError("内容不能为空");
											ToastUtil.show(UserProfileActivity.this, "没有填号码", Toast.LENGTH_SHORT);
											return;
										}
										String s=String.valueOf(count);
										String sr=comment;
										if (s.equals(sr))
										{

											//MyUser newUser = new MyUser();
											myUser.setEmailVerified(true);
											myUser.update(UserProfileActivity.this, myUser.getObjectId(), new UpdateListener() {

													@Override
													public void onSuccess()
													{
														// TODO Auto-generated method stub
														//testGetCurrentUser();
														Toast.makeText(UserProfileActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
													}

													@Override
													public void onFailure(int code, String msg)
													{
														// TODO Auto-generated method stub
														//toast("更新用户信息失败:" + msg);
													}
												});		
											//Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
										}
										else
										{

											Toast.makeText(UserProfileActivity.this, "号码没有转换过", Toast.LENGTH_SHORT).show();
										}
										//push(comment, myUser);
									}
								}).setNegativeButton("复制激活码", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										Toast.makeText(UserProfileActivity.this, "请加入官方群下载转码工具", Toast.LENGTH_SHORT).show();
										ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
										manager.setText(b + "");


									}
								}).create().show();
						}
					}
					@Override
					public void onFailure(int code, String msg)
					{
						// TODO Auto-generated method stub
						Toast.makeText(UserProfileActivity.this, "貌似没有网络", Toast.LENGTH_SHORT).show();
					}
				});

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
		{
            case R.id.lxw_user_profile_img:
				//  takePic();
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				AlertDialog alertDialog = builder.setMessage("已停止换头像了噢")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{

						}
					}).create();
				alertDialog.show();
                break;
            case R.id.lxw_user_profile_username:
                isUsername = true;
				BmobQuery<MyUser> query = new BmobQuery<MyUser>();
				query.getObject(this, myUser.getObjectId(), new GetListener<MyUser>() {

						@Override
						public void onSuccess(MyUser object)
						{
							Intent intent=new Intent();
							intent.putExtra("via", object.getUsername());
							intent.setClass(UserProfileActivity.this, EdiUserProfileActivity.class);
							startActivityForResult(intent, REQUEST_CODE);
						}
						@Override
						public void onFailure(int code, String msg)
						{
							// TODO Auto-generated method stub
						}
					});

                break;
            case R.id.lxw_user_profile_btn_save:
                mProgressDialog = ProgressDialog.show(this, null, "正在保存，请稍候...");
                if (filePath != null)
				{
                    uploaderAvertor(filePath);
                }
				else
				{
                    updateProfile();
                }
                break;
            case R.id.lxw_user_profile_personality:
                isUsername = false;
				BmobQuery<MyUser> queryz = new BmobQuery<MyUser>();
				queryz.getObject(this, myUser.getObjectId(), new GetListener<MyUser>() {

						@Override
						public void onSuccess(MyUser object)
						{
							Intent intent=new Intent();
							intent.putExtra("via", object.getPersonality());
							intent.setClass(UserProfileActivity.this, EdiUserProfileActivity.class);
							startActivityForResult(intent, REQUEST_CODE);
						}
						@Override
						public void onFailure(int code, String msg)
						{
							// TODO Auto-generated method stub
						}
					});
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE)
		{
            Bundle bundle = data.getExtras();
            if (isUsername)
			{
                usernameText.setText(bundle.getString("username"));
                isChangeUsername = true;
            }
			else
			{
                personalityText.setText(bundle.getString("username"));
            }
            isChange = true;
            btnVisibility();
		}}
    /*
     private void changeSex()
     {
     AlertDialog.Builder builder = new AlertDialog.Builder(this);
     final String[] sexs = {"男", "女"};
     AlertDialog alert = null;
     alert = builder.setTitle("请选择性别")
     .setItems(sexs, new DialogInterface.OnClickListener() {
     @Override
     public void onClick(DialogInterface dialog, int which)
     {
     sexText.setText(sexs[which]);
     isChange = true;
     btnVisibility();
     }
     }).create();
     alert.show();
     }*/


    private void uploaderAvertor(String file)
    {
        File path = new File(file);
		//  Log.e(TAG, "=====uploade_avertor___success===>" + path.getAbsolutePath());
        final BmobFile bmobFile = new BmobFile(path);
        bmobFile.upload(this, new UploadFileListener() {
				@Override
				public void onSuccess()
				{
					user.setAuvter(bmobFile);
					//   Log.e(TAG, "=====uploade_avertor___success===>" + bmobFile.getUrl());
					updateProfile();
				}

				@Override
				public void onFailure(int i, String s)
				{

					// Log.e(TAG, "=====uploade_avertor___onfailure===>" + s);
				}
			});
    }

    private void updateProfile()
    {
        String username = usernameText.getText().toString().trim();
		// String sex = sexText.getText().toString().trim();
        String personality = personalityText.getText().toString().trim();
        int sexInt = 0;
        if (TextUtils.isEmpty(username))
		{
            return;
        }
		/*
		 if (sex.equals("男"))
		 {
		 sexInt = 0;
		 }
		 else
		 {
		 sexInt = 1;
		 }
		 */

        if (isChangeUsername)
		{
            if (!username.equals(myUser.getUsername()))
			{
                user.setUsername(username);
            }
        }
        user.setSex(sexInt);
        user.setPersonality(personality);
        user.update(this, myUser.getObjectId(), new UpdateListener() {
				@Override
				public void onSuccess()
				{
					mProgressDialog.dismiss();
					ToastUtil.show(UserProfileActivity.this, "信息更新成功", Toast.LENGTH_SHORT);
					btn.setVisibility(View.GONE);
				}

				@Override
				public void onFailure(int i, String s)
				{
					mProgressDialog.dismiss();
					ToastUtil.show(UserProfileActivity.this, "信息更新失败", Toast.LENGTH_SHORT);
					// Log.e(TAG,"===faile==="+s);
				}
			});
    }

    private void btnVisibility()
    {
        if (isChange)
		{
            btn.setVisibility(View.VISIBLE);
        }
		else
		{
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart()
    {
		// TODO: Implement this method
		super.onStart();
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
    protected void onRestart()
    {
        super.onRestart();
        if (isChangeUserImgNo && !isChange && !isChangeUsername)
		{
            initData();
        }
    }
}
