package nico.styTool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.j.o;
/**
 * Created by luxin on 15-12-10.
 *  http://luxin.gitcafe.io
 */
public class l extends AppCompatActivity implements View.OnClickListener
{

    //  private static final String TAG = "";
    /** Called when the activity is first created. */
    android.support.v7.widget.Toolbar toolbar;
    private String numStrTmp = "";
    private String numStr = "";
    private int[] numArray = new int[4];
    private int[] colorArray = new int[6];
    private String SHARE_APP_TAG = "shell_git";
    private AppCompatEditText ediContent;
    private HorizontalScrollView scrollPicContent;
    private LinearLayout layPicContent;
    BmobPushManager bmobPushManager;
    private LinearLayout btnCamera;
    private LinearLayout btnEmotion;
    private LinearLayout btnSend;
    private MyUser zk = null;
    private ViewPager emojPager;
    private boolean isOpen = false;

    private ArrayList<GridView> mGridViews;

    private static final int REQUEST_CODE = 1;

    private ProgressDialog mProgressDialog;

    private List<String> filePhotos;

    private long exitTime = 0;

    private void s()
    {//myUser.getObjectId()
	//c3ad9d5867
	MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
	if (myUser != null)
	{
	    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
	    query.getObject(this, myUser.getObjectId(), new GetListener<MyUser>() {

		    @Override
		    public void onSuccess(MyUser object)
		    {
			Integer s=object.getScore();
			Integer sr=502;
			if (s.equals(sr))
			{
			    Toast.makeText(l.this, "请更新到新版", Toast.LENGTH_SHORT).show();
			    finish();
			}
			else
			{
			}
		    }
		    @Override
		    public void onFailure(int code, String msg)
		    {
			// TODO Auto-generated method stub
		    }
		});
	}
	else
	{

	}
    }
    public void initNum()
    {
	numStr = "";
	numStrTmp = "";
	for (int i = 0; i < numArray.length; i++)
	{
	    int numIntTmp = new Random().nextInt(10);
	    numStrTmp = String.valueOf(numIntTmp);
	    numStr = numStr + numStrTmp;
	    numArray[i] = numIntTmp;
	}
    }


    public int randomAngle()
    {
	return 20 * (new Random().nextInt(5) - new Random().nextInt(3));
    }

    public int randomColor()
    {
	colorArray[0] = 0xFF000000; // BLACK
	colorArray[1] = 0xFFFF00FF; // MAGENTA
	colorArray[2] = 0xFFFF0000; // RED
	colorArray[3] = 0xFF00FF00; // GREEN
	colorArray[4] = 0xFF0000FF; // BLUE
	colorArray[5] = 0xFF00FFFF; // CYAN
	//colorArray[6] = 0xFFFFFF00; // YELLOW:看不清楚

	int randomColorId = new Random().nextInt(6);
	return colorArray[randomColorId];
    }


    public static Bitmap getBitmapFromView(View view, int width, int height)
    {
	int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
	int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
	view.measure(widthSpec, heightSpec);
	view.layout(0, 0, width, height);
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	view.draw(canvas);

	return bitmap;
    }


    private void r()
    {
        BmobQuery<o> query = new BmobQuery<o>();
	query.getObject(this, "b8e2da92f1", new GetListener<o>() {

		@Override
		public void onSuccess(o object)
		{
		    String s=object.getContent();
		    String sr="fi";
		    if (s.equals(sr))
		    {

			Toast.makeText(getApplicationContext(), "请更新到新版", Toast.LENGTH_SHORT).show();
			finish();
		    }
		    else
		    {


		    }}
		//				.setCancelable(false).


		@Override
		public void onFailure(int code, String msg)
		{
		    // finish();
		}
	    });

    }
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
		finish();
	    }
	    return true;   
	}
	return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_help);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	final String packname = getPackageName();
	try
	{
	    PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
	    Signature[] signs = packageInfo.signatures;
	    Signature sign = signs[0];
	    int code = sign.hashCode();
	    if (code != 312960342)
	    {	    
		finish();
	    }
	    else
	    {
		zk = BmobUser.getCurrentUser(this, MyUser.class);
	    }
	}
	catch (PackageManager.NameNotFoundException e)
	{}

	Time t = new Time();
	t.setToNow();

	// TODO Auto-generated method stub


	SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
	Boolean user_first = setting.getBoolean("FIRST", true);
	if (user_first)
	{//第一次
	    setting.edit().putBoolean("FIRST", false).commit();
	    //qqqecw();
	}
	else
	{

	}
	initView();
        initEvent();
	s();
	r();
	bmobPushManager = new BmobPushManager(this);

    }

    
    private void initView()
    {
	toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
        ediContent = (AppCompatEditText) findViewById(R.id.id_lxw_push_content);
        scrollPicContent = (HorizontalScrollView) findViewById(R.id.id_lxw_push_scrollPicContent);
        layPicContent = (LinearLayout) findViewById(R.id.id_lxw_push_layPicContent);
        btnCamera = (LinearLayout) findViewById(R.id.id_lxw_push_btn_btnCamera);
        btnEmotion = (LinearLayout) findViewById(R.id.id_lxw_push_btn_btnEmotion);
        btnSend = (LinearLayout) findViewById(R.id.btnSend);
        emojPager = (ViewPager) findViewById(R.id.id_lxw_push_emoj_viewpager);
	SharedPreferences sharedPreferencesb = getSharedPreferences("fba", AppCompatActivity.MODE_PRIVATE); 
	String r = sharedPreferencesb.getString("stytoolpro", "");
	ediContent.setText(r);
    }
    private void initEvent()
    {
        btnCamera.setOnClickListener(this);
        btnEmotion.setOnClickListener(this);
        emojPager.setOnClickListener(this);
        btnSend.setOnClickListener(this);


        ediContent.setOnTouchListener(new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
		    if (isOpen)
		    {
			openKeyBoard();
			isOpen = false;
			showEmotion(isOpen);
		    }
		    return false;
		}
	    });
	ediContent.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
		{
		    //正在修改
		}
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
		{
		    //修改之前
		}
		@Override
		public void afterTextChanged(Editable editable)
		{

		    String r = ediContent.getText().toString().trim();
		    SharedPreferences mySharedPreferences= getSharedPreferences("fba", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("stytoolpro", r);
		    editor.commit();
		    String s=r;
		    String sr="签到";
		    if (s.equals(sr))
		    {

			BmobQuery<MyUser> query = new BmobQuery<MyUser>();
			query.getObject(l.this, zk.getObjectId(), new GetListener<MyUser>() {

				@Override
				public void onSuccess(MyUser object)
				{
				    Intent intent=getIntent();
				    String 卡哇伊=object.getHol();
				    String 膜蛤=intent.getStringExtra("r");
				    if (卡哇伊.equals(膜蛤))
				    {

				    }
				    else
				    {
					MyUser myUser = BmobUser.getCurrentUser(l.this, MyUser.class);
					if (myUser != null)
					{
					    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
					    query.getObject(l.this, myUser.getObjectId(), new GetListener<MyUser>() {

						    @Override
						    public void onSuccess(MyUser object)
						    {
							Integer count = object.getSex();
							count++;
							//ToastUtil.show(proguardFiles.this, Integer.valueOf(count) + "", Toast.LENGTH_SHORT);
							object.setSex(Integer.valueOf(count));
							object.update(l.this, object.getObjectId(), new UpdateListener() {
								@Override
								public void onSuccess()
								{
								   // ToastUtil.show(l.this, "签到成功", Toast.LENGTH_SHORT);
								    final MyUser bmobUser = BmobUser.getCurrentUser(l.this, MyUser.class);
								    if (bmobUser != null)
								    {
									Intent intent=getIntent();
									MyUser newUser = new MyUser();
									newUser.setHol(intent.getStringExtra("r"));
									newUser.update(l.this, bmobUser.getObjectId(), new UpdateListener() {

										@Override
										public void onSuccess()
										{
										    // TODO Auto-generated method stub
										    // testGetCurrentUser();
										    //ToastUtil.show(l.this, "信息更新成功", Toast.LENGTH_SHORT);
										    ToastUtil.show(l.this, "签到成功", Toast.LENGTH_SHORT);
										}


										@Override
										public void onFailure(int code, String msg)
										{
										    // TODO Auto-generated method stub
										    //toast("更新用户信息失败:" + msg);
										}
									    });
								    }
								    else
								    {
									//toast("本地用户为null,请登录。");
								    }


								}

								@Override
								public void onFailure(int i, String s)
								{
								    // mProgressDialog.dismiss();
								   // ToastUtil.show(l.this, "信息更新失败" + "\n" + i + "\n" + s, Toast.LENGTH_SHORT);
								    // Log.e(TAG,"===faile==="+s);
								}
							    });
						    }
						    @Override
						    public void onFailure(int code, String msg)
						    {
							//Toast.makeText(l.this, "3", Toast.LENGTH_SHORT).show();
							// TODO Auto-generated method stub
						    }
						});
					}
					else
					{

					}
				    }
				}
				@Override
				public void onFailure(int code, String msg)
				{
				    // TODO Auto-generated method stub
				}
			    });

		    }
		    else
		    {
			//Toast.makeText(l.this, "不相等", Toast.LENGTH_SHORT).show();
		    }
		}
	    });

	ediContent.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
		    return false;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
		    return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode)
		{

		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
		    return false;
		}
	    });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
	{
            case R.id.id_lxw_push_btn_btnCamera:
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = builder.setMessage("已停止图片功能了噢")
		    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		    }).create();
		alertDialog.show();
		//  Intent intent = new Intent(this, ChoseImgActivity.class);
		//    startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.id_lxw_push_btn_btnEmotion:
		// Log.e(TAG, "=============>emotion");
                isOpen = !isOpen;
                showEmotion(isOpen);
                break;
            case R.id.btnSend:
		openKeyBoard();
		pushHelp();
                break;
        }
    }

    private void showEmotion(boolean isOpen)
    {
        if (isOpen)
	{
	    //  hidenkeyBoard();
            openKeyBoard();
            emojPager.setVisibility(View.VISIBLE);
        }
	else
	{
            emojPager.setVisibility(View.GONE);
        }
    }


    public void openKeyBoard()
    {

        InputMethodManager imm = (InputMethodManager) this
	    .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive())
	{
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
				InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 隐藏软键盘
     */
    private void hidenkeyBoard()
    {
        if (this.getCurrentFocus() != null)
	{
            ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    


    @Override
    protected void onStart()
    {
	// TODO: Implement this method
	super.onStart();
	toolbar.setTitle("发布动态");
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
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        s();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
	//   refresUI();
    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg)
	{
	    if (msg.what == 0x110)
	    {
		//  mProgressDialog.dismiss();
	    }
        }
    };

    private void pushHelp()
    {

        final String content = ediContent.getText().toString().trim();

        if (TextUtils.isEmpty(content))
	{
            return;
        }
	mProgressDialog = ProgressDialog.show(this, null, "正在上传");
	new Thread(){
            @Override
            public void run()
	    {
                List<String> list = new ArrayList<String>(ImageChoseAdapter.mSelectImg);
                if (list != null && list.size() > 0)
		{
                    getCacheImgFiles(l.this, list);
		    //   uploader(filePhotos, content);
                }
		else
		{
                    saveText(content);
                }
                mHandler.sendEmptyMessage(0x110);
            }
        }.start();
        // savePulish(title, content, list);

    }
    private void saveText(String content)
    {
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        Helps_a helps = new Helps_a();
        helps.setUser(user);
        helps.setContent(content);
        helps.setState(0);
	helps.setPhontofile(false);
        helps.save(this, new SaveListener() {
		@Override
		public void onSuccess()
		{

		    mProgressDialog.dismiss();
		    finish();
		    SharedPreferences mySharedPreferences= getSharedPreferences("fba", AppCompatActivity.MODE_PRIVATE);
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putString("stytoolpro", null);
		    editor.commit();
		}

		@Override
		public void onFailure(int i, String s)
		{
		    mProgressDialog.dismiss();
		}
	    });
    }

    private void getCacheImgFiles(Context context, List<String> list)
    {
        filePhotos = new ArrayList<String>();
        for (String path:list)
	{
            filePhotos.add(compressBitmap(context, path));
        }
    }


    /**
     *压缩指定路径图片，并将其保存在缓存目录中，并获取到缓存后的图片路径
     * @param context
     * @param path
     * @return
     */
    private String compressBitmap(Context context, String path)
    {
        Bitmap bitmap=compressBitmapFromFile(path);
        File srcFile=new File(path);
        String desPath= getImageCacheDir(context) + srcFile.getName();
        File file=new File(desPath);
        try
	{
            FileOutputStream fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
        }
	catch (FileNotFoundException e)
	{
            e.printStackTrace();
        }
        return desPath;
    }

    /**
     * 获取图片缓存路径
     * @param context
     * @return
     */
    private String getImageCacheDir(Context context)
    {
        String cachepath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
	{
            cachepath = context.getExternalCacheDir().getPath();
        }
	else
	{
            cachepath = context.getCacheDir().getPath();
        }
        return cachepath;
    }


    /**
     * 基于质量的压缩算法,保证图片大小小于200k
     * @param bitmap
     * @return
     */
    private Bitmap compressBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options=100;
        while (baos.toByteArray().length / 1024 > 200)
	{
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream byins=new ByteArrayInputStream(baos.toByteArray());
        Bitmap bm=BitmapFactory.decodeStream(byins, null, null);
        return bm;
    }

    /**
     *压缩指定路径的图片，并得到图片对象
     * @param path
     * @return
     */
    private Bitmap compressBitmapFromFile(String path)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap=BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        int width=options.outWidth;
        int height=options.outHeight;

        float widthRadio=480f;
        float heightRadio=800f;
        int inSampleSize=1;
        if (width > height && width > widthRadio)
	{
            inSampleSize = (int) (width * 1.0f / widthRadio);
        }
	else if (width < height && height > heightRadio)
	{
            inSampleSize = (int) (height * 1.0f / heightRadio);
        }
        if (inSampleSize <= 0)
	{
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        bitmap = BitmapFactory.decodeFile(path, options);
        return compressBitmap(bitmap);
    }

}

