package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.CustomAPI;
import com.mob.mobapi.MobAPI;
import com.mob.tools.utils.Hashon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CustomAPISemiAutomaticActivity extends AppCompatActivity implements APICallback, OnItemSelectedListener, OnClickListener {
	private int reqCounter;
	private Spinner spAPI;
	private Spinner spPath;
	private LinearLayout llParams;
	private List<Map<String, Object>> APIs;
	private List<Map<String, Object>> urlList;
	private String path;
	private TextView tvJson;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_semi_automatic);
		spAPI = (Spinner) findViewById(R.id.spAPI);
		spAPI.setOnItemSelectedListener(this);
		spPath = (Spinner) findViewById(R.id.spPath);
		spPath.setOnItemSelectedListener(this);
		llParams = (LinearLayout) findViewById(R.id.llParams);
		findViewById(R.id.btnSearch).setOnClickListener(this);
		tvJson = (TextView) findViewById(R.id.tvJson);

		getAPIList();
	}

	private void getAPIList() {
		new Thread() {
			public void run() {
				try {
					final List<Map<String, Object>> list = MobAPI.queryAPIs();
					runOnUiThread(new Runnable() {
						public void run() {
							onAPIListGot(list);
						}
					});
				} catch (final Throwable e) {
					runOnUiThread(new Runnable() {
						public void run() {
							onError(null, 0, e);
						}
					});
				}
			}
		}.start();
	}

	private void onAPIListGot(List<Map<String, Object>> APIs) {
		if (APIs == null) {
			APIs = new ArrayList<Map<String, Object>>();
		}
		this.APIs = APIs;

		ArrayList<String> APINames = new ArrayList<String>();
		for (Map<String, Object> API : APIs) {
			APINames.add(com.mob.tools.utils.R.toString(API.get("name")));
		}
		Collections.sort(APINames);
		spAPI.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, APINames));
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.equals(spAPI)) {
			String name = (String) spAPI.getSelectedItem();
			for (Map<String, Object> API : APIs) {
				if (name.equals(API.get("name"))) {
					urlList = (List<Map<String, Object>>) API.get("urlList");
					ArrayList<String> urlDesp = new ArrayList<String>();
					for (Map<String, Object> url : urlList) {
						urlDesp.add(com.mob.tools.utils.R.toString(url.get("description")));
					}
					Collections.sort(urlDesp);
					spPath.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, urlDesp));
					break;
				}
			}
		} else if (parent.equals(spPath)) {
			String description = (String) spPath.getSelectedItem();
			for (Map<String, Object> url : urlList) {
				if (description.equals(url.get("description"))) {
					String urlStr = com.mob.tools.utils.R.toString(url.get("url"));
					path = urlStr.replace("http://apicloud.mob.com", "");
					List<Map<String,Object>> params = (List<Map<String,Object>>) url.get("params");
					llParams.removeAllViews();
					for (Map<String,Object> param : params) {
						if ("key".equals(param.get("name")) && "appkey".equals(param.get("description"))) {
							continue;
						}
						View llParam = View.inflate(this, R.layout.view_custom_param_auto, null);
						TextView tvKey = (TextView) llParam.findViewById(R.id.tvKey);
						tvKey.setText(com.mob.tools.utils.R.toString(param.get("name")));
						EditText etValue = (EditText) llParam.findViewById(R.id.etValue);
						etValue.setHint(com.mob.tools.utils.R.toString(param.get("description")));
						llParams.addView(llParam);
					}
				}
			}
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onClick(View v) {
		HashMap<String, String> reqParams = new HashMap<String, String>();
		for (int i = 0, size = llParams.getChildCount(); i < size; i++) {
			View item = llParams.getChildAt(i);
			TextView tvKey = (TextView) item.findViewById(R.id.tvKey);
			String key = tvKey.getText().toString().trim();
			EditText etValue = (EditText) item.findViewById(R.id.etValue);
			String value = etValue.getText().toString().trim();
			if (value.length() > 0) {
				reqParams.put(key, value);
			}
		}
		reqCounter++;
		int action = reqCounter;
		CustomAPI api = MobAPI.getCustomAPI();
		api.get(path, reqParams, action, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		Hashon hason = new Hashon();
		String json = hason.fromHashMap((HashMap<String, Object>) result);
		tvJson.setText(hason.format(json));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

}
