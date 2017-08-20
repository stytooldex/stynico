package nico.styTool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import dump.j.o;
public class lua_web extends AppCompatActivity
{

    private WebView wv_web;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wv_web = (WebView) findViewById(R.id.wv_web);
        //初始化网络设置
        initWebViewSettings();
        //初始化网路数据
        initWebView();
    }

    private void initWebViewSettings()
    {
        WebSettings webSettings = wv_web.getSettings();
        //可以有缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置支持页面js可用
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置允许访问文件数据
        webSettings.setAllowFileAccess(true);
        //可以使用localStorage
        webSettings.setDomStorageEnabled(true);
        //可以有数据库
        webSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
    }

    /**
     * 初始化网路数据:
     */
    private void initWebView()
    {
	BmobQuery<o> query = new BmobQuery<o>();
	query.getObject(lua_web.this, "957aaf7e08", new GetListener<o>() {

		@Override
		public void onSuccess(o object)
		{

		    wv_web.loadUrl(object.getContent());
		    wv_web.setWebViewClient(new MyWebViewClient());
		    wv_web.setDownloadListener(new MyDownLoadListener());
		}
		
		@Override
		public void onFailure(int code, String msg)
		{
		}
	    });}//1
    /**
     * webView渲染类
     */
    private class MyWebViewClient extends WebViewClient
    {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
	    if (url.startsWith("http:") || url.startsWith("https:"))
	    {
		return false;
	    }
	    else if (url.startsWith(WebView.SCHEME_TEL) ||
		     url.startsWith("sms:") ||
		     url.startsWith(WebView.SCHEME_MAILTO) ||
		     url.startsWith(WebView.SCHEME_GEO) ||
		     url.startsWith("maps:"))
	    {
		try
		{
		    Intent intent = new Intent(Intent.ACTION_VIEW);
		    intent.setData(Uri.parse(url));
		    startActivity(intent);
		}
		catch (android.content.ActivityNotFoundException e)
		{
		}
	    }
	    return true;
	}
    }

    /**
     * webView下载类
     */
    private class MyDownLoadListener implements DownloadListener
    {
	@Override
	public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
	{
	}
    }}
