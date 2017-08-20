package nico.styTool;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import dump.j.o;
import dump.q.ContantValue;
import android.widget.*;

public class MeiziActivity extends Activity  implements AdapterView.OnItemClickListener
{
    private ListView listView;
    private ArrayAdapter<String> itemAdapter;

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.me_main);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	initView();

    }

    private void initView()
    {

	editText = (EditText) findViewById(R.id.mainEditText1);
	listView = (ListView) findViewById(R.id.api_id_views_listview);
	itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, ContantValue.viewItem);
	listView.setAdapter(itemAdapter);
	listView.setOnItemClickListener(this);
	
	EditText a = (EditText) findViewById(R.id.mainEditText1);
	a.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v)
		{
		    EditText a = (EditText) findViewById(R.id.mainEditText1);
		    String comment = a.getText().toString().trim();
		    if (TextUtils.isEmpty(comment))
		    {
		    BmobQuery<o> query = new BmobQuery<o>();
			query.getObject(MeiziActivity.this, "内容不能为空", new GetListener<o>() {

				@Override
				public void onFailure(int p1, String p2)
				{
				    // TODO: Implement this method
				}
				

			    @Override
			    public void onSuccess(o object) {
				// TODO Auto-generated method stub
				//Log.i("life", ""+object.getName());
			    }
			});
			//a.setError("内容不能为空");
			return;
		    }

		    ToastUtil.show(MeiziActivity.this, "复制成功", Toast.LENGTH_SHORT);
		    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		    manager.setText(comment);
		}
	    });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
	switch (position)
	{
	    case 0:startIntent("🇦");
		break;
	    case 1:startIntent("🇧");
		break;
	    case 2:startIntent("🇨");
		break;
	    case 3:startIntent("🇩");
		break;
	    case 4:startIntent("🇪");
		break;
	    case 5:startIntent("🇫");
		break;
	    case 6:startIntent("🇬");
		break;
	    case 7:startIntent("🇭");
		break;
	    case 8:startIntent("🇮");
		break;
	    case 9:startIntent("🇯");
		break;
	    case 10:startIntent("🇰");
		break;
	    case 11:startIntent("🇱");
		break;
	    case 12:startIntent("🇲");
		break;
	    case 13:startIntent("🇳");
		break;
	    case 14:startIntent("🇴");
		break;
	    case 15:startIntent("🇵");
		break;
	    case 16:startIntent("🇶");
		break;
	    case 17:startIntent("🇷");
		break;
	    case 18:startIntent("🇸");
		break;
	    case 19:startIntent("🇹");
		break;
	    case 20:startIntent("🇺");
		break;
	    case 21:startIntent("🇻");
		break;
	    case 22:startIntent("🇼");
		break;
	    case 23:startIntent("🇽");
		break;
	    case 24:startIntent("🇾");
			parent.setVisibility(View.GONE);
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(0,0); //设置item的weidth和height都为0

			//将设置好的布局属性应用到ListView/GridView等的Item上;
			parent.setLayoutParams(param);
		
		break;
	    case 25:startIntent("🇿");
		break;
	}
    }

    private void startIntent(String classes)
    {
	SpannableString spannableString=new SpannableString(classes);
	int curosr=editText.getSelectionStart();
	editText.getText().insert(curosr, spannableString);
    }
}

