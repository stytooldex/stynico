package dump.w;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import nico.styTool.R;

public class CommentListActivity extends Fragment
{

	ListView listView;
	static EditText et_content;
	TextView btn_publish;
	private static void startIntent(String classes)
    {
		SpannableString spannableString=new SpannableString(classes);
		int curosr=et_content.getSelectionStart();
		et_content.getText().insert(curosr, spannableString);
    }
	private void publishComment(String content)
	{
		nico.styTool.MyUser user = BmobUser.getCurrentUser(getActivity(), nico.styTool.MyUser.class);
		if (user == null)
		{
			Intent intent = new Intent(getActivity(), nico.styTool.app_th.class);
			startActivity(intent);
			//toast("发表评论前请先登陆");
			return;
		}
		else if (TextUtils.isEmpty(content))
		{
			//toast("发表评论不能为空");
			return;
		}
		final Comment_ comment = new Comment_();
		comment.setContent(content);
		comment.setPost(weibo);
		comment.setSignature(android.os.Build.MODEL + System.getProperty("line.separator"));
		comment.setUser(user);
		comment.save(getActivity(), new SaveListener() {

				@Override
				public void onSuccess()
				{
					// TODO Auto-generated method stub
					findComments();
					et_content.setText("");
					//toast("评论成功");
				}

				@Override
				public void onFailure(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("评论失败");
				}
			});}

	private void findComments()
	{
		BmobQuery<Comment_> query = new BmobQuery<Comment_>();
		// pointer类型
		query.addWhereEqualTo("post", new BmobPointer(weibo));		
		query.include("user,post.author");
		query.findObjects(getActivity(), new FindListener<Comment_>() {

				@Override
				public void onSuccess(List<Comment_> object)
				{
					// TODO Auto-generated method stub
					comments = object;
					adapter.notifyDataSetChanged();
					et_content.setText("");
				}

				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					//toast("查询失败:"+msg);
				}
			});}
	static List<Comment_> comments = new ArrayList<Comment_>();
	MyAdapter adapter;
	Post_ weibo = new Post_();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: Implement this method
        View view=inflater.inflate(R.layout.activity_comment_, null);

		weibo.setObjectId(nico.SPUtils.get(getActivity(), "mes", "") + "");
		adapter = new MyAdapter(getActivity());
		et_content = (EditText) view.findViewById(R.id.et_content);
		btn_publish = (TextView) view.findViewById(R.id.btn_publish);
		listView = (ListView) view.findViewById(R.id.listview);
		listView.setAdapter(adapter);

		btn_publish.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					publishComment(et_content.getText().toString());
				}
			});
		findComments();
        return view;
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
			return comments.size();
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

			final Comment_ comment = comments.get(position);

			if (comment.getUser() != null)
			{
				holder.tv_author.setText("" + comment.getUser().getUsername());
				holder.tv_.setText("" + comment.getCreatedAt() + "｜" + comment.getSignature());

			}

			final String str = comment.getContent();

			holder.tv_content.setText(str);
			convertView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v)
					{
						startIntent("@" + comment.getUser().getUsername() + " ");
					}
				});

			convertView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					//返回值代表是否已经处理结束，后面是否需要再处理
					public boolean onLongClick(View v)
					{
						final String[] os = {"复制评论"};
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						AlertDialog alert = builder.setTitle("用户操作")
							.setItems(os, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									switch (which)
									{
										case 0:
											ClipboardManager manager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
											manager.setText(comment.getContent() + "");
											break;

									}
									////showToast("你选择了" + os[which]);
								}
							}).create();
						alert.show();
						//true事件处理结束，后面不需要再处理
						return true;
					}
				});
			return convertView;
		}
	}

}
