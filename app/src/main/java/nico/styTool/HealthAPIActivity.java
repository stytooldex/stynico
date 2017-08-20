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
import com.mob.mobapi.apis.Health;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class HealthAPIActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private static final int pageSize = 20; // max size
	private ListView lvHealth;
	private EditText etHealth;
	private int pageIndex;
	private int total;
	private ArrayList<HashMap<String, Object>> healthList;
	private DreamSimpleAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_health);
	    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		lvHealth = forceCast(findViewById(R.id.lvHealth));
		etHealth = forceCast(findViewById(R.id.etHealth));
		etHealth.setText(R.string.health_api_example);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		healthList = new ArrayList<HashMap<String,Object>>();
		adapter = new DreamSimpleAdapter(this, healthList);
		lvHealth.setAdapter(adapter);
	}

	public void onClick(View v) {
		pageIndex = 0;
		healthList.clear();
		adapter.notifyDataSetChanged();
		queryHealth();
	}

	private void queryHealth() {
		Health api = forceCast(MobAPI.getAPI(Health.NAME));
		String keyword = etHealth.getText().toString().trim();
		api.queryHealth(keyword, pageIndex + 1, pageSize, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = forceCast(result.get("result"));
		total = Integer.parseInt(com.mob.tools.utils.R.toString(res.get("total")));
		ArrayList<HashMap<String, Object>> list = forceCast(res.get("list"));
		if (null != list) {
			healthList.addAll(list);
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
			convertView = mInflater.inflate(R.layout.view_health_item, null);
			TextView tvTitle = forceCast(convertView.findViewById(R.id.tvTitle));
			TextView tvContent = forceCast(convertView.findViewById(R.id.tvContent));

			HashMap<String, Object> res = list.get(position);
			tvTitle.setText(com.mob.tools.utils.R.toString(res.get("title")));
			tvContent.setText(com.mob.tools.utils.R.toString(res.get("content")));
			return convertView;
		}

		private View getView2(View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ProgressBar(parent.getContext());
			}

			if (list.size() < total) {
				convertView.setVisibility(View.VISIBLE);
				queryHealth();
			} else {
				convertView.setVisibility(View.GONE);
			}

			return convertView;
		}
	}
}
