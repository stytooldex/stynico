package nico;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.mob.mobapi.MobAPI;

import java.util.ArrayList;

import nico.styTool.R;
import nico.styTool.StatusBarUtil;
import nico.styTool.smali_layout_apktool_Util;
import nico.styTool.smali_layout_ida_Util;
import nico.styTool.smali_layout_shell_Util;

import android.view.*;
import android.support.v7.app.*;
import android.content.*;

import com.bmob.*;
import com.bmob.btp.callback.*;

import android.app.ProgressDialog;
import android.widget.*;

public class niconiconi extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    android.support.v7.widget.Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles = {"简介", "服务", "栏目"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ffff_j);
        //Toolbar部分

        String a = "11268";
        String b = "0258";
        String ciass = "f05";
        String Ɨ = a + b + ciass + "9";
        MobAPI.initSDK(this, "" + Ɨ);
        final SharedPreferences setting = niconiconi.this.getSharedPreferences("_bug_a_button_", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {

            //setting.edit().putBoolean("FIRST", false).commit();

        } else {

        }
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));

        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
        toolbar.setTitle("");
//		设置标题
//		设置副标题
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager();
    }

    private void setViewPager() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new smali_layout_ida_Util());
        fragments.add(new smali_layout_apktool_Util());
        fragments.add(new smali_layout_shell_Util());
        viewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }

    FragmentStatePagerAdapter viewPagerAdapter = new FragmentStatePagerAdapter(
            getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }
    };
}
