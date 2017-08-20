package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;

import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class UcachePutActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private EditText etTable;
	private EditText etKey;
	private EditText etValue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucache_put);
		etTable = forceCast(findViewById(R.id.etTable));
		etKey   = forceCast(findViewById(R.id.etKey));
		etValue = forceCast(findViewById(R.id.etValue));

		etTable.setText("mobile");
		etKey.setText("bW9iaWxl");
		etValue.setText("e21vYmlsZTE6IjE0NzgyODY3MjM4In0");
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		Ucache api = (Ucache) MobAPI.getAPI(Ucache.NAME);
		String table = etTable.getText().toString().trim();
		String key   = etKey.getText().toString().trim();
		String value = etValue.getText().toString().trim();
		api.queryUcachePut(table, key, value, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		Toast.makeText(this, R.string.sucess, Toast.LENGTH_SHORT).show();
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
