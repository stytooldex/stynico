package dump.y;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import nico.styTool.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import nico.*;
import android.support.design.widget.*;
import android.content.*;

public class x5_MainActivity extends AppCompatActivity
{

    private String url_content;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    HProgressBarLoading mTopProgress;
    private WebView webview;
    private boolean isVisible = true;
    //URL地址
    private String url = "";
    //URL网页标题
    private String title = "";
    private FavAndHisManager favAndHisManager;
    private long exitTime = 0;

    private FloatingActionButton floatingActionButton;
    public void b1(View view)
	{
        if (webview.canGoBack())
		{
            webview.goBack();
        }
		else
		{
            if ((System.currentTimeMillis() - exitTime) > 2000)
			{
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
			else
			{

                finish();
            }
        }
    }

    public void b2(View view)
	{

        webview.goForward(); //前进
    }

    public void b3(View view)
	{
        LinearLayout btnTwo = (LinearLayout) findViewById(R.id.mainLinearLayout1);
        if (isVisible)
		{
            isVisible = false;
            btnTwo.setVisibility(View.VISIBLE);
            //isVisible=true;
        }
		else
		{
            isVisible = true;
            btnTwo.setVisibility(View.GONE);

        }

    }

    public void b4(View view)
	{
        favAndHisManager.addFavorite(title, url);
        favAndHisManager.getAllFavorites();
    }

    public void b5(View view)
	{
		Intent intent=getIntent();
        webview.loadUrl(intent.getStringExtra("#") + "");
    }

	public void v47(View view)
	{
        webview.reload(); //刷新
    }


    public void v2(View view)
	{
        startActivityForResult(new Intent(x5_MainActivity.this, FavAndHisActivity.class), 0);
    }

    public void v3(View view)
	{
        if ((System.currentTimeMillis() - exitTime) > 2000)
		{
            webview.pauseTimers();
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
		else
		{
            finish();

        }
    }

    public void v4(View view)
	{
        Intent inten = new Intent(x5_MainActivity.this, GankIoActivity.class);
        startActivity(inten);
    }


    private void openImageChooserActivity()
	{
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "选择文件"), FILE_CHOOSER_RESULT_CODE);
    }

    private byte[] Bitmap2Bytes(Bitmap bm)
	{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private void hideProgressWithAnim()
	{
        AnimationSet animation = getDismissAnim(x5_MainActivity.this);
        animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation)
				{
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					mTopProgress.setVisibility(View.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}
			});
        mTopProgress.startAnimation(animation);
    }

    /**
     * 获取消失的动画
     *
     * @param context
     * @return
     */
    private AnimationSet getDismissAnim(Context context)
	{
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }

    @Override
    public void onBackPressed()
	{
        if (webview.canGoBack())
		{
            // webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.goBack();
            //  iua.setVisibility(View.GONE);
        }
		else
		{

            if ((System.currentTimeMillis() - exitTime) > 2000)
			{
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
			else
			{

                finish();

            }
        }
    }
	private String getClipboardText()
	{
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = "";
        try
		{
            if (clipboard != null && clipboard.hasText())
			{
                CharSequence tmpText = clipboard.getText();
                clipboard.setText(tmpText);
                if (tmpText != null && tmpText.length() > 0)
				{
                    text = tmpText.toString().trim();
                }
            }
        }
		catch (Exception e)
		{
            e.printStackTrace();
            text = "";
        }
        return text;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
		getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
				@Override
				public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
				{
					ArrayList<View> outView = new ArrayList<View>();
					getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
					int size = outView.size();
					if (outView != null && outView.size() > 0)
					{
						outView.get(0).setVisibility(View.GONE);
					}
				}
			});
        setContentView(R.layout.x5_main);
		final SharedPreferences setting = x5_MainActivity.this.getSharedPreferences("__a__oi", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first)
		{
			AlertDialog.Builder obuilder = new AlertDialog.Builder(x5_MainActivity.this);
			AlertDialog alertDialog = obuilder.setMessage("点进一个vip视频\n出现图标即可播放\n出现问题请点其他线路")
			    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						setting.edit().putBoolean("FIRST", false).commit();
					}
			    }).setCancelable(false)
			    .create();

			alertDialog.show();
		}
		else
		{

		}
		if (getClipboardText().startsWith("http:/") || getClipboardText().startsWith("https:/"))
		{
			LinearLayout container = (LinearLayout) findViewById(R.id.x5mainLinearLayout1);
			Snackbar.make(container, "发现复制链接", Snackbar.LENGTH_SHORT)
				.setAction("打开链接", new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						webview.loadUrl(getClipboardText());
					}
				})
				.show();
			//Toast.makeText(this, "再按", Toast.LENGTH_SHORT).show();
			//	return;
		}
		else
		{
			//Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			//	return;
		}
        //getSupportActionBar().hide();
        final EditText editText2 = (EditText) findViewById(R.id.mainEditText2);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
				{
					if (i == EditorInfo.IME_ACTION_SEARCH)
					{
						// 先隐藏键盘
						((InputMethodManager) editText2.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(x5_MainActivity.this.getCurrentFocus().getWindowToken(),
													 InputMethodManager.HIDE_NOT_ALWAYS);
						editText2.clearFocus();
						final String urlString = editText2.getText().toString();
						webview.loadUrl("http://m.baidu.com/s?ie=utf-8&rn=30&tn=baiduhome_pg&oq=%E5%BC%A0%E7%B1%BD%E6%B2%90&rsv_enter=0&wd=" + urlString);
						//httpData();
						return true;
					}
					return false;
				}
			});
//
        final EditText editText = (EditText) findViewById(R.id.mainEditText1);
		//editText.setText(url);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
				{
					if (i == EditorInfo.IME_ACTION_SEARCH)
					{
						// 先隐藏键盘
						((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(x5_MainActivity.this.getCurrentFocus().getWindowToken(),
													 InputMethodManager.HIDE_NOT_ALWAYS);
						editText.clearFocus();
						final String urlString = editText.getText().toString();
						if (urlString.startsWith("http://") || urlString.startsWith("https://"))
						{
							webview.loadUrl(urlString);
							//	return;
						}
						else
						{
							webview.loadUrl("http://" + urlString);
							//	return;
						}


						//httpData();
						return true;
					}
					return false;
				}
			});

        floatingActionButton = (FloatingActionButton) findViewById(R.id.lxw_id_push_helps_comment_float);
        mTopProgress = (HProgressBarLoading) findViewById(R.id.top_progress);
        this.favAndHisManager = new FavAndHisManager(getApplicationContext());
        webview = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webview.getSettings();
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null)
		{
            webview.loadUrl(data + "");
        }
		else
		{

			Intent nt=getIntent();
			webview.loadUrl(nt.getStringExtra("#") + "");
        }
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
            cookieManager.setAcceptThirdPartyCookies(webview, true);
        }
		else
		{
            cookieManager.setAcceptCookie(true);
        }
        new x5_WebviewSelfAdaption(this).getWebviewAdaption(webview);

        webview.setDownloadListener(new MyDownLoadListener());
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setWebViewClient(new MyWebViewClient());

    }

    private class MyDownLoadListener implements DownloadListener
	{
        @Override
        public void onDownloadStart(final String r, String userAgent, String contentDisposition, String mimetype, long contentLength)
		{


        }
    }

    private class MyWebChromeClient extends WebChromeClient
	{
        public void openFileChooser(ValueCallback<Uri> valueCallback)
		{
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType)
		{
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture)
		{
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
		{
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress)
		{
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100)
			{
                // 网页加载完成
                mTopProgress.setVisibility(View.GONE);
            }
			else
			{
                // 加载中
                hideProgressWithAnim();
                mTopProgress.setNormalProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        //配置权限（同样在WebChromeClient中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback callback)
		{
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
		{
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title)
		{
            super.onReceivedTitle(view, title);
            x5_MainActivity.this.title = title;
            //webUrlTitle.setText(title);
        }

    }

    private class MyWebViewClient extends WebViewClient
	{


		// @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
		{
            handler.proceed();//接受信任所有网站的证书，使用此方法可以解决淘宝，天猫等网站无法正常在本地加载的问题
            //Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();

        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url)
		{
            //Log.i(TAG, "shouldInterceptRequest url=" + url + ";threadInfo" + Thread.currentThread());
            WebResourceResponse response = null;
            if (url.contains("avatar.php?"))
			{
                try
				{
                    final PipedOutputStream out = new PipedOutputStream();
                    PipedInputStream in = new PipedInputStream(out);
                    ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
							@Override
							public void onLoadingStarted(String s, View view)
							{
							}

							@Override
							public void onLoadingFailed(String s, View view, FailReason failReason)
							{
							}

							@Override
							public void onLoadingCancelled(String s, View view)
							{
							}

							@Override
							public void onLoadingComplete(String s, View view, Bitmap bitmap)
							{
								if (bitmap != null)
								{
									try
									{
										out.write(Bitmap2Bytes(bitmap));
										out.close();
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						});
                    response = new WebResourceResponse("image/png", "UTF-8", in);
                }
				catch (Exception e)
				{
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        public void onPageFinished(WebView view, final String url)
		{
            //view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            x5_MainActivity.this.url = url;//记得
			if (url.startsWith("http://m.iqiyi.com/v") || url.startsWith("http://www.iqiyi.com/v"))
			{
				Toast.makeText(x5_MainActivity.this, "支持播放", Toast.LENGTH_SHORT).show();
                floatingActionButton.setVisibility(View.VISIBLE);
				floatingActionButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v)
						{
							final SharedPreferences setting = x5_MainActivity.this.getSharedPreferences("__a__x5xposeo", 0);
							Boolean user_first = setting.getBoolean("FIRST", true);
							if (user_first)
							{
								AlertDialog.Builder obuilder = new AlertDialog.Builder(x5_MainActivity.this);
								AlertDialog alertDialog = obuilder.setMessage("少年要看视频要付出小代价")
									.setNegativeButton("推荐给朋友", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which)
										{
											Intent sendIntent = new Intent();
											sendIntent.setAction(Intent.ACTION_SEND);
											sendIntent.putExtra(Intent.EXTRA_TEXT, "嗨 妮媌多顶集合功能，快速上手\n简：一目了然\n美：十全十美\n还能免费看付费电影哦！\n http://www.coolapk.com/apk/nico.styTool");
											sendIntent.setType("text/plain");
											startActivity(sendIntent);
											setting.edit().putBoolean("FIRST", false).commit();
										}
									}).setCancelable(false)
									.create();

								alertDialog.show();
							}
							else
							{
								webview.loadUrl("http://api/?url=" + url);
							}
						}
					});

				//	return;
			}
			else
			{

                floatingActionButton.setVisibility(View.GONE);
				////webview.loadUrl("http://" + urlString);
				//	return;
			}
			EditText editText = (EditText) findViewById(R.id.mainEditText1);
			if (url.startsWith("http://") || url.startsWith("https://"))
			{
				editText.setText(url);
			}
			else
			{
				editText.setText("");
			}
            String date = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date()).toString();
            favAndHisManager.addHistory(title, url, Long.parseLong(date));
        }

        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
		{
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
			{
                //当Sdk版本大于21时才能使用此方法
                view.loadUrl(request.getUrl().toString());
            }
            return super.shouldOverrideUrlLoading(view, String.valueOf(request));
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url_b)
        {
            view.loadUrl(url_content);
            //MainActivity.this.bmob = url;
            if (url_b.startsWith("http://") || url_b.startsWith("https://") || url_b.startsWith("file:///"))
            {

            }
            else
            {
                SPUtils.put(x5_MainActivity.this, "_v", url_b);
                AlertDialog.Builder builder = new AlertDialog.Builder(x5_MainActivity.this);
                AlertDialog alert = builder.setTitle("Reit！")
					.setMessage("网址启动第三方软件")
					.setNegativeButton("允许", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							Uri uri = Uri.parse(SPUtils.get(x5_MainActivity.this, "_v", "") + "");
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
						}
					})
					.setPositiveButton("不允许", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
						}
					}).create();
                alert.show();

            }

            //Toast.makeText(MainActivity.this,url+"", Toast.LENGTH_SHORT).show();
            // Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();
            return super.shouldOverrideUrlLoading(view, url_b);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
		{
            case -1:
                //不做任何处理
                break;
            case 0:
                webview.loadUrl(data.getStringExtra("url"));
                break;
        }
        if (requestCode == FILE_CHOOSER_RESULT_CODE)
		{
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null)
			{
                onActivityResultAboveL(requestCode, resultCode, data);
            }
			else if (uploadMessage != null)
			{
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent)
	{
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK)
		{
            if (intent != null)
			{
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null)
				{
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++)
					{
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}

