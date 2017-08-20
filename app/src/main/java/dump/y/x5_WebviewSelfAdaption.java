package dump.y;
import android.content.Context;
import android.util.DisplayMetrics;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import nico.*;


public class x5_WebviewSelfAdaption
{


    /**
     * 用WebView显示图片，可使用这个参数 设置网页布局类型：
     * 1、LayoutAlgorithm.NARROW_COLUMNS ：
     * 适应内容大小
     * 2、LayoutAlgorithm.SINGLE_COLUMN:
     * 适应屏幕，内容将自动缩放
     */
    private Context context;
    public x5_WebviewSelfAdaption(Context context)
	{
        this.context = context;
    }

    public void getWebviewAdaption(WebView webView)
	{
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
		//启用地理定位
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled((Boolean) SPUtils.get(context, "if_4", true));//数据库
		webSettings.setGeolocationEnabled((Boolean) SPUtils.get(context, "if_2", true));//地理
		webSettings.setUserAgentString("" + SPUtils.get(context, "if_7", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_1_1 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3 XiaoMi/MiuiBrowser/8.9.4"));//UA
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setDisplayZoomControls(false);
		webSettings.setLoadsImagesAutomatically((Boolean) SPUtils.get(context, "if_5", true));//图片
        webSettings.setJavaScriptEnabled((Boolean) SPUtils.get(context, "if_1", true)); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom((Boolean) SPUtils.get(context, "if_3", false)); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
		// Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240)
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
		else if (mDensity == 160)
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
		else if (mDensity == 120)
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }
		else if (mDensity == DisplayMetrics.DENSITY_XHIGH)
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
		else if (mDensity == DisplayMetrics.DENSITY_TV)
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
		else
		{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }


        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


    }

}
