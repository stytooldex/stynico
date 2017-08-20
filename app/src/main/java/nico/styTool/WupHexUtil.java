package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class WupHexUtil extends AppCompatActivity
{
android.support.v7.widget.Toolbar toolbar;

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
});}
//		设置导航按钮监听
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setContentView(R.layout.main);

toolbar = (android.support.v7.widget.Toolbar)
findViewById(R.id.toolbar);
//		初始化Toolbar控件
setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
//toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
//toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色



}}
