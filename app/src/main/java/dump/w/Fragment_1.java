package dump.w;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.t.Fk_;
import nico.styTool.MyUser;
import nico.styTool.R;

public class Fragment_1 extends Fragment
{Post_ weibo = new Post_();

    private int currentCommentNum;
    private int currentLikeNum;
	public void queryOne()
	{
		BmobQuery<Post_> query = new BmobQuery<Post_>();
		query.getObject(getActivity(), "" + nico.SPUtils.get(getActivity(), "mes", ""), new GetListener<Post_>() {

				@Override
				public void onSuccess(final Post_ object)
				{

					currentLikeNum = object.getLikeNum().intValue();
					final MyUser bmobUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
					//currentLikeNum++;
					Post_ newUser = new Post_();
					newUser.setLikeNum(currentLikeNum + 1);
					newUser.update(getActivity(), weibo.getObjectId(), new UpdateListener() {

							@Override
							public void onSuccess()
							{
							}
							@Override
							public void onFailure(int code, String msg)
							{
							}
						});
					TextView text = (TextView) getActivity().findViewById(R.id.asaTextView2);//标题
					text.setText(object.getTitle() + "");//标题

					TextView text1 = (TextView) getActivity().findViewById(R.id.asaTextView1);//id
					text1.setText(nico.SPUtils.get(getActivity(), "mes_", "") + "");//id

					TextView text2 = (TextView) getActivity().findViewById(R.id.asaTextView3);//内容
					text2.setText(object.getContent() + "");//内容

					//Log.i("life", ""+object.getName());

					LinearLayout button = (LinearLayout) getActivity().findViewById(R.id.asaLinearLayout1);//内容
					button.setOnLongClickListener(new View.OnLongClickListener() {
							@Override
							//返回值代表是否已经处理结束，后面是否需要再处理
							public boolean onLongClick(View v)
							{

								final String[] os = {"复制全部", "编辑内容", "举报帖子", "分享", "删除","管理员删除"};
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								AlertDialog alert = builder.setTitle("管理员操作")
									.setItems(os, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which)
										{
											switch (which)
											{
												case 0:
													ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
													manager.setText("标题:" + object.getTitle() + "\n内容:" + object.getContent());
													break;
												case 1:
													if (bmobUser.getObjectId().equals(object.getAuthor().getObjectId() + ""))
													{
														LayoutInflater inflater = LayoutInflater.from(getActivity());
														View view = inflater.inflate(R.layout.aw_q, null);
														final EditText ediComment = (EditText) view.findViewById(R.id.awqEditText1);
														ediComment.setText(object.getContent());
														AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
														builder.setView(view).setPositiveButton("发布", new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which)
																{
																	String comment = ediComment.getText().toString().trim();
																	if (TextUtils.isEmpty(comment))
																	{
																		// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
																		return;
																	}
																	Post_ newUser = new Post_();
																	newUser.setContent(comment);
																	newUser.update(getActivity(), weibo.getObjectId(), new UpdateListener() {

																			@Override
																			public void onSuccess()
																			{
																				queryOne();
																			}
																			@Override
																			public void onFailure(int code, String msg)
																			{
																			}
																		});
																}
															}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which)
																{
																	return;
																}
															}).create().show();
														//Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
													}
													else
													{
														//Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
													}

													break;
												case 2:
													Fk_ feedback = new Fk_();
													feedback.setContent("" + object.getTitle());
													feedback.save(getActivity(), new SaveListener() {

															@Override
															public void onFailure(int p1, String p2)
															{
																Toast.makeText(getActivity(), "举报失败", Toast.LENGTH_SHORT).show();
															}

															@Override
															public void onSuccess()
															{
																Toast.makeText(getActivity(), "举报成功", Toast.LENGTH_SHORT).show();
																BmobSMS.requestSMSCode(getActivity(), "13414957848", "注册模板", new RequestSMSCodeListener() {

																		@Override
																		public void done(Integer smsId, BmobException ex)
																		{

																		}
																	});
															}

														});
													break;
												case 3:
													Intent sendIntent = new Intent();
													sendIntent.setAction(Intent.ACTION_SEND);
													sendIntent.putExtra(Intent.EXTRA_TEXT, object.getContent() + "");
													sendIntent.setType("text/plain");
													startActivity(sendIntent);
													break;
												case 4:
													if (bmobUser.getObjectId().equals(object.getAuthor().getObjectId() + ""))
													{
														Post_ p2 = new Post_();
														p2.setObjectId(nico.SPUtils.get(getActivity(), "mes", "") + "");
														p2.delete(getActivity(), new DeleteListener() {
																@Override
																public void onSuccess()
																{
																	getActivity().finish();
																	Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
																}
																@Override
																public void onFailure(int code, String msg)
																{
																	Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
																}
															});
														//Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
													}
													else
													{
														//Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
													}

													break;
												case 5:
													String objectId = (String) BmobUser.getObjectByKey(getActivity(), "objectId");
													if (objectId.equals("fcdee930ae"))
													{
														Post_ p = new Post_();
														p.setObjectId(nico.SPUtils.get(getActivity(), "mes", "") + "");
														p.delete(getActivity(), new DeleteListener() {
																@Override
																public void onSuccess()
																{
																	getActivity().finish();
																	Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
																}
																@Override
																public void onFailure(int code, String msg)
																{
																	Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
																}
															});
													}
													else
													{
														//Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
													}
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
				}
				@Override
				public void onFailure(int code, String msg)
				{
					// TODO Auto-generated method stub
					//Log.i("life", "onFailure = "+code+",msg = "+msg);
				}
			});
	}

	//  TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: Implement this method
        View view=inflater.inflate(R.layout.as_a, null);
		weibo.setObjectId(nico.SPUtils.get(getActivity(), "mes", "") + "");
		queryOne();
        return view;
    }
}
