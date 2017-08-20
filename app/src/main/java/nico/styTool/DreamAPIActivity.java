package nico.styTool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Dream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class DreamAPIActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private static final int pageSize = 20; // max is 20
	private ListView lvDream;
	private EditText etDream;
	private int pageIndex;
	private int total;
	private ArrayList<HashMap<String, Object>> dreamList;
	private DreamSimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_dream);
	    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		lvDream = forceCast(findViewById(R.id.lvDream));
		etDream = forceCast(findViewById(R.id.etDream));
		etDream.setText(R.string.dream_api_example);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		dreamList = new ArrayList<HashMap<String,Object>>();
		adapter = new DreamSimpleAdapter(this, dreamList);
		lvDream.setAdapter(adapter);
	}

	public void onClick(View v) {
		pageIndex = 0;
		dreamList.clear();
		adapter.notifyDataSetChanged();
		queryDream();
	}

	private void queryDream() {
		Dream api = forceCast(MobAPI.getAPI(Dream.NAME));
		String keyword = etDream.getText().toString().trim();
		api.queryDream(keyword, pageIndex + 1, pageSize, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = forceCast(result.get("result"));
		total = Integer.parseInt(com.mob.tools.utils.R.toString(res.get("total")));
		ArrayList<HashMap<String, Object>> list = forceCast(res.get("list"));
		if (null != list) {
			dreamList.addAll(list);
			adapter.notifyDataSetChanged();
		}
		++pageIndex;
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private class DreamSimpleAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<HashMap<String, Object>> list;

		DreamSimpleAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
			mInflater = LayoutInflater.from(context);
			list = res;
		}

		public int getCount() {
			if (list.size() == 0) {
				return 0;
			} else if (list.size() == total) {
				return list.size();
			} else {
				return list.size() + 1;
			}
		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (position < list.size()) {
				return getView1(position, convertView, parent);
			} else {
				return getView2(convertView, parent);
			}
		}

		private View getView1(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.view_dream_item, null);
			TextView tvName  = forceCast(convertView.findViewById(R.id.tvName));
			TextView tvDetail = forceCast(convertView.findViewById(R.id.tvDetail));

			HashMap<String, Object> res = list.get(position);
			tvName.setText(com.mob.tools.utils.R.toString(res.get("name")));
			tvDetail.setText(com.mob.tools.utils.R.toString(res.get("detail")));
			return convertView;
		}

		private View getView2(View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ProgressBar(parent.getContext());
			}

			if (list.size() < total) {
				convertView.setVisibility(View.VISIBLE);
				queryDream();
			} else {
				convertView.setVisibility(View.GONE);
			}

			return convertView;
		}
	}
}
