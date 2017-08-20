package nico.styTool;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Update extends AppCompatActivity 
{
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles={"｡ﾟ(ﾟ∩´﹏`∩ﾟ)ﾟ｡"};
    private void ti()
    {
		String urlStr = "https://styTool.top/usd.txt";
		//long a = System.currentTimeMillis();
		try
		{
			/*
			 * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
			 * <uses-permission android:name="android.permission.INTERNET" ></uses>
			 */
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(60 * 1000);
			conn.setReadTimeout(60 * 1000);
			// 取得inputStream，并进行读取
			InputStream input = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null)
			{
				sb.append(line);

			}

			//int newVersion = Integer.parseInt(sb.toString());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return ;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return ;
		}

		return ;

    }
    @Override
    protected void onStart()
    {
		super.onStart();
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
		mToolbar.setNavigationOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					finish();
				}
			});
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ig);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		//Toolbar部分
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
		mToolbar.setTitle("记事条");
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setViewPager();

    }
    private void setViewPager()
    {
		final String packname = getPackageName();
		try
		{
			PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			int code = sign.hashCode();
			if (code != 312960342)
			{	    
				ti();

			}
			else
			{
				//
			}
		}
		catch (PackageManager.NameNotFoundException e)
		{}
        fragments = new ArrayList<Fragment>();
        viewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }
    FragmentStatePagerAdapter viewPagerAdapter=new FragmentStatePagerAdapter(
        getSupportFragmentManager()) {
        @Override
        public int getCount()
		{
            return fragments.size();
        }
        public CharSequence getPageTitle(int position)
		{
            return titles[position];
        }
        @Override
        public Fragment getItem(int arg0)
		{
            return fragments.get(arg0);
        }
    };
}
