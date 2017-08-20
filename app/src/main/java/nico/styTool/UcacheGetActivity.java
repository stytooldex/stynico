package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class UcacheGetActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private EditText etTable;
	private EditText etKey;
	private TextView tvKey;
	private TextView tvValue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucache_get);
		etTable = forceCast(findViewById(R.id.etTable));
		etKey   = forceCast(findViewById(R.id.etKey));
		tvKey = forceCast(findViewById(R.id.tvKey));
		tvValue   = forceCast(findViewById(R.id.tvValue));

		etTable.setText("mobile");
		etKey.setText("bW9iaWxl");
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		Ucache api = (Ucache) MobAPI.getAPI(Ucache.NAME);
		String table = etTable.getText().toString().trim();
		String key   = etKey.getText().toString().trim();
		api.queryUcacheGet(table, key, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = forceCast(result.get("result"));
		tvKey.setText(com.mob.tools.utils.R.toString(res.get("k")));
		tvValue.setText(com.mob.tools.utils.R.toString(res.get("v")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
