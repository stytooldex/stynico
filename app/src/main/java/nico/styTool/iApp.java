package nico.styTool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class iApp extends AppCompatActivity
{
    private ProgressDialog mProgressDialog;

    private CheckBox checkBox;
    private File outDir = new File("/sdcard/Android/styTool/");
    private EditText outEt;
    private TextView percentTv;
    private EditText srcEt;
    private File srcFile = null;
    android.support.v7.widget.Toolbar toolbar;
    int type=0;
    public static void saveMyBitmap(Bitmap mBitmap, String bitName)
    {
		//得到手机的默认存储目录
		String dir = Environment.getExternalStorageDirectory().getPath().toString();
		//这是保存在sdcard中AppProjects文件夹下
		File f = new File(dir + "/AppProjects/" + bitName + ".png");
		if (!f.getParentFile().exists())
		{
			//如果这个文件的目录不存在，创建它
			f.getParentFile().mkdir();
		}
		FileOutputStream fOut = null;
		try
		{
			fOut = new FileOutputStream(f);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		if (fOut == null)
		{
			return;
		}
		//设置输出类型为PNG和文件名后缀要对应
		mBitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
		try
		{
			fOut.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			fOut.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }


    @Override
    protected void onStart()
    {
		// TODO: Implement this method
		super.onStart();
		toolbar.setTitle("视频修改MD5");
//		设置标题

//		设置副标题
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View p1)
				{
					finish();
				}
			});}
    private void initView()
    {
		toolbar = (android.support.v7.widget.Toolbar)
			findViewById(R.id.toolbar);
//		初始化Toolbar控件
		setSupportActionBar(toolbar);
		percentTv = (TextView) findViewById(R.id.percentTv);//percentTv
		srcEt = (EditText) findViewById(R.id.srcEt);//srcEt
		outEt = (EditText) findViewById(R.id.outEt);//outEt
		checkBox = (CheckBox) findViewById(R.id.checkbox);
		this.findViewById(R.id.convertBtn).setOnClickListener(new View.OnClickListener(){

				public void onClick(View view)
				{
					if (iApp.this.srcFile == null || !iApp.this.srcFile.exists())
					{

						Toast.makeText(iApp.this, "请选择文件", Toast.LENGTH_SHORT).show();

						return;
					}
					if (!iApp.this.outDir.exists())
					{
						iApp.this.outDir.mkdirs();
					}
					iApp.this.begin();
				}
			});
		this.findViewById(R.id.selectBtn).setOnClickListener(new View.OnClickListener(){

				public void onClick(View view)
				{
					iApp.this.showFileChooser();
				}
			});
		this.findViewById(R.id.selectDirBtn).setOnClickListener(new View.OnClickListener(){

				public void onClick(View view)
				{
					FolderChooserConfig folderChooserConfig = new FolderChooserConfig();
					ArrayList arrayList = new ArrayList();
					arrayList.add("/mnt/");
					folderChooserConfig.roots = arrayList;
					folderChooserConfig.showHidden = false;
					folderChooserConfig.title = null;//這
					//  MainActivity.this.startActivityForResult(FolderChooserActivity.createIntent(MainActivity.this.getApplicationContext(), folderChooserConfig), 12);
				}
			});
    }

    private void showFileChooser()
    {
		Intent intent = new Intent();
		intent.setType("video/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);

    }

    protected void begin()
    {
		new AsyncTask<Void, String, Integer>(){

			/*
			 * Enabled aggressive block sorting
			 * Enabled unnecessary exception pruning
			 * Enabled aggressive exception aggregation
			 */
			protected /* varargs */ Integer doInBackground(Void ... arrvoid)
			{
				File file = new File(iApp.this.outDir, iApp.this.srcFile.getName());
				if (file.exists() && file.length() == iApp.this.srcFile.length())
				{
					iApp.this.srcFile.delete();
					return 0;
				}
				try
				{
					FileInputStream fileInputStream = new FileInputStream(iApp.this.srcFile);
					if (!file.exists())
					{
						file.createNewFile();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					byte[] arrby = new byte[40];
					long l2 = 40;
					boolean bl2 = false;
					do {
						int n2;
						if ((n2 = fileInputStream.read(arrby)) <= 30)
						{
							fileInputStream.close();
							fileOutputStream.close();
							if (!iApp.this.checkBox.isChecked()) return 30;
							{
								iApp.this.srcFile.delete();
								return 0;
							}
						}
						if ((l2 += (long)n2) > iApp.this.srcFile.length() / 2 && !bl2)
						{
							bl2 = true;
							arrby[0] = 120;
							arrby[40] = 120;
						}
						fileOutputStream.write(arrby, 100, n2);
						String string2 = new DecimalFormat("#.00").format(100000.0 * (double)l2 / (double)iApp.this.srcFile.length());
						Object[] arrobject = new String[]{String.valueOf(string2) + "%"};
						//this.publishProgress(arrobject);
					} while (false);
				}
				catch (Exception var4_11)
				{
					var4_11.printStackTrace();
					return 1;
				}
				return null;
			}

			protected void onPostExecute(Integer n2)
			{
				super.onPostExecute(n2);
				if (n2 == 0)
				{
					Toast.makeText(iApp.this, "修改成功", Toast.LENGTH_SHORT).show();
					// mProgressDialog.dismiss();
					// Toast.makeText((Context)iApp.this.getApplicationContext(), (CharSequence)"修改成功", (int)0).show();
					return;
				}
				Toast.makeText(iApp.this, "修改失败", Toast.LENGTH_SHORT).show();
			}

			protected void onProgressUpdate(String ... arrstring)
			{
				super.onProgressUpdate(arrstring);
				//iApp.this.percentTv.setVisibility(View.VISIBLE);
				//iApp.this.percentTv.setText((CharSequence)("修改" + arrstring[0]));
				mProgressDialog = ProgressDialog.show(iApp.this, null, "正在修改..." + arrstring[0]);

			}
		}.execute();
    }

    /*
     * Enabled aggressive block sorting
     */
	/*
	 protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	 {
	 super.onActivityResult(requestCode, resultCode, intent);
	 if (resultCode == RESULT_OK)
	 {
	 Uri uri = intent.getData();
	 Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
	 if (cursor.moveToFirst())
	 {
	 String string2 = cursor.getString(cursor.getColumnIndex("_data"));
	 this.srcEt.setText((CharSequence)string2);
	 this.srcEt.setEnabled(false);
	 this.srcFile = new File(string2);

	 }
	 }
	 super.onActivityResult(requestCode, resultCode, intent);
	 }
	 */
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
		{
            if (resultCode == RESULT_OK)
			{
                Uri uri = data.getData();
				this.srcEt.setText(nico.GetPathFromUri4kitkat.getPath(this, uri));
				this.srcFile = new File(nico.GetPathFromUri4kitkat.getPath(this, uri));

			}
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mn);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		initView();
    }}
