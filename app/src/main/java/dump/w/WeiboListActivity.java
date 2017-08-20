package dump.w;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import dump.k.i_a;
import nico.styTool.R;

public class WeiboListActivity extends AppCompatActivity
{

	ListView listView;
	Button btn_publish;
	public static String TOP_STATES = "TOP";
	static List<Post_> weibos = new ArrayList<Post_>();
	MyAdapter adapter;
    //private nico.styTool.MyUser myUser = null;
	private void xft()
	{

		BmobQuery<i_a> query = new BmobQuery<i_a>();
		query.getObject(WeiboListActivity.this, "03bf357e85", new GetListener<i_a>() {

				@Override
				public void onSuccess(i_a object)
				{
					String s = object.getContent();
					String sr = nico.styTool.Constant.a_mi + "\n" + nico.styTool.Constant.a_miui;
					if (s.equals(sr))
					{

					}
					else
					{
						nico.styTool.ToastUtil.show(WeiboListActivity.this, "版本不一致，请更新", Toast.LENGTH_SHORT);
						finish();
					}
				}
				//				.setCancelable(false).


				@Override
				public void onFailure(int code, String msg)
				{

				}
			});

	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.mainmenu, menu);

		android.support.v7.widget.SwitchCompat switchShop=(android.support.v7.widget.SwitchCompat) 
			menu.findItem(R.id.myswitch).getActionView().findViewById(R.id.switchForActionBar);
		switchShop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton btn, boolean isChecked)
				{
					if (isChecked)
					{ //开店申请
						findWeibos_();
						setTitle("显示个人");
						//UI.toast(getApplicationContext(), "开店啦");
					}
					else
					{ //关店申请
						setTitle("显示全部");
						findWeibos();
					}
				}
			});
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
		{
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weibo_);
		nico.styTool.StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		setTitle("显示全部");
		final SharedPreferences setting = WeiboListActivity.this.getSharedPreferences("data_false", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first)
		{
			final String[] os = {"点某一个就会排列第一", "已激活帐号支持(20000字)特权", "未激活帐号最多(68字)", "共享广告可计划【论坛 分享资源】", "注意骗子，玩的开心♂"};
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			AlertDialog alert = builder.setTitle("共享广告规则")
                .setItems(os, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
					{

                    }
                }).setCancelable(false).create();
			alert.show();

			setting.edit().putBoolean("FIRST", false).commit();
		}
		else
		{

		}
		xft();
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null)
		{
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		adapter = new MyAdapter(this);
		//et_content = (EditText) findViewById(R.id.et_content);
		btn_publish = (Button) findViewById(R.id.btn_publish);
		listView = (ListView) findViewById(R.id.listview);

		//  itemAdapter = new SessionItemAdapter(this);
		listView.setAdapter(adapter);

		btn_publish.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{

					nico.styTool.MyUser myUserw = BmobUser.getCurrentUser(WeiboListActivity.this, nico.styTool.MyUser.class);
					if (myUserw != null)
					{
						LayoutInflater inflater = LayoutInflater.from(WeiboListActivity.this);
						View view = inflater.inflate(R.layout.ad_ssss, null);
						final EditText b1 = (EditText) view.findViewById(R.id.adssssEditText1);//标题
						final EditText b2 = (EditText) view.findViewById(R.id.adssssEditText2);//
						final nico.styTool.MyUser myUser = BmobUser.getCurrentUser(WeiboListActivity.this, nico.styTool.MyUser.class);
						BmobQuery<nico.styTool.MyUser> query = new BmobQuery<nico.styTool.MyUser>();
						query.getObject(WeiboListActivity.this, myUser.getObjectId(), new GetListener<nico.styTool.MyUser>() {


								@Override
								public void onSuccess(nico.styTool.MyUser object)
								{

									String s="" + object.getEmailVerified();
									String sr="true";
									if (s.equals(sr))
									{
										b2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20000) });
										//Toast.makeText(WeiboListActivity.this, "已激活帐号支持(20000字)特权", Toast.LENGTH_SHORT).show();
										//这
									}
									else
									{
										//Toast.makeText(WeiboListActivity.this, "未激活帐号最多(68字)", Toast.LENGTH_SHORT).show();
									}
								}
								@Override
								public void onFailure(int code, String msg)
								{
								}
							});
						AlertDialog.Builder builder = new AlertDialog.Builder(WeiboListActivity.this);
						builder.setView(view).setPositiveButton("发布", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									String comment = b1.getText().toString().trim();
									if (TextUtils.isEmpty(comment))
									{

										return;
									}
									publishWeibo(b2.getText().toString(), comment);
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									return;
								}
							}).create().show();
					}
					else
					{
						Intent intent = new Intent(WeiboListActivity.this, nico.styTool.app_th.class);
						startActivity(intent);
					}
					// TODO Auto-generated method stub

				}
			});

		findWeibos();
	}
	private void findWeibos_()
	{
		nico.styTool.MyUser user = BmobUser.getCurrentUser(this, nico.styTool.MyUser.class);
		BmobQuery<Post_> query = new BmobQuery<Post_>();
		query.addWhereEqualTo("author", user);	// 查询当前用户的所有微博
		query.order("-updatedAt");
		query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(this, new FindListener<Post_>() {
				@Override
				public void onSuccess(List<Post_> object)
				{
					// TODO Auto-generated method stub
					weibos = object;
					adapter.notifyDataSetChanged();
					//et_content.setText("");
				}

				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("查询失败:"+msg);
				}
			});
	}

	/**
	 * 查询微博
	 */
	private void findWeibos()
	{
		BmobQuery<Post_> query = new BmobQuery<Post_>();
		query.order("-updatedAt");
		query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(this, new FindListener<Post_>() {
				@Override
				public void onSuccess(List<Post_> object)
				{
					// TODO Auto-generated method stub
					weibos = object;
					adapter.notifyDataSetChanged();
					//et_content.setText("");
				}

				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("查询失败:"+msg);
				}
			});

		//等价于下面的sql语句查询
//		String sql = "select include author,* from Post where author = pointer('_User', "+"'"+user.getObjectId()+"')";
//		new BmobQuery<Post>().doSQLQuery(this, sql, new SQLQueryListener<Post>(){
//			
//			@Override
//			public void done(BmobQueryResult<Post> result, BmobException e) {
//				// TODO Auto-generated method stub
//				if(e ==null){
//					List<Post> list = (List<Post>) result.getResults();
//					if(list!=null && list.size()>0){
//						weibos = list;
//						adapter.notifyDataSetChanged();
//						et_content.setText("");
//					}else{
//						Log.i("smile", "查询成功，无数据返回");
//					}
//				}else{
//					Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
//				}
//			}
//		});
	}

	/**
	 * 发布微博，发表微博时关联了用户类型，是一对一的体现
	 */
	private void publishWeibo(String content, String bi)
	{
		nico.styTool.MyUser user = BmobUser.getCurrentUser(this, nico.styTool.MyUser.class);
		if (user == null)
		{
			Intent intent = new Intent(this, nico.styTool.app_th.class);
			startActivity(intent);
			//toast("发布微博前请先登陆");
			return;
		}
		else if (TextUtils.isEmpty(content))
		{
			//toast("发布内容不能为空");
			return;
		}
		// 创建微博信息
		Post_ weibo = new Post_();
		weibo.setTitle(bi);
		weibo.setLikeNum(0);
		weibo.setContent(content);
		weibo.setSignature(android.os.Build.MODEL + System.getProperty("line.separator"));
		weibo.setAuthor(user);
		weibo.save(this, new SaveListener() {

				@Override
				public void onSuccess()
				{
					// TODO Auto-generated method stub
					//toast("发布成功");
					findWeibos();
				}

				@Override
				public void onFailure(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("发表失败");
				}
			});
	}

	private static class MyAdapter extends BaseAdapter
	{

		private LayoutInflater mInflater;

		private Context mContext;

		public MyAdapter(Context context)
		{
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		static class ViewHolder
		{
			TextView tv_content;
			TextView tv_author;
			TextView tv_;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
//			return bmobObjects.size();
			return weibos.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			final ViewHolder holder;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.a_bbbw, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				holder.tv_ = (TextView) convertView.findViewById(R.id.abbbwTextView1);

				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			final Post_ weibo = weibos.get(position);

			holder.tv_author.setText("" + weibo.getCreatedAt());

			final String str = weibo.getTitle();

			holder.tv_content.setText(str);

			holder.tv_.setText("浏览" + Integer.toString(weibo.getLikeNum()) + "次｜" + weibo.getSignature());

			convertView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mContext, CommentListActivity_.class);
						//intent.putExtra("objectId", weibo.getObjectId());
						mContext.startActivity(intent);
						String content=weibo.getObjectId();
						nico.SPUtils.put(mContext, "mes_", weibo.getAuthor().getUsername());
						nico.SPUtils.put(mContext, "mes", content);
					}
				});

			return convertView;
		}
	}

}
