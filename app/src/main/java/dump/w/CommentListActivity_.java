package dump.w;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import nico.styTool.R;

public class CommentListActivity_ extends AppCompatActivity 
{
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles={"详情","评论"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ixi);
        //Toolbar部分
		nico.styTool.StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    // TODO: Implement this method
                    finish();
                }
			});
        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setViewPager();
    }
    private void setViewPager()
	{
        fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment_1());
        fragments.add(new CommentListActivity());
        viewPager.setAdapter(viewPagerAdapter);
        //viewPager.setOnPageChangeListener(this);

		// mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);设置后Tab标签往左边靠
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

