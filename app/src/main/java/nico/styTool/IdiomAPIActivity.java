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
import com.mob.mobapi.apis.Idiom;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class IdiomAPIActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private EditText etIdiom;
	private TextView tvName;
	private TextView tvPinyin;
	private TextView tvPretation;
	private TextView tvSource;
	private TextView tvSample;
	private TextView tvSampleFrom;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_idiom);
	    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		etIdiom      = forceCast(findViewById(R.id.etIdiom));
		tvName       = forceCast(findViewById(R.id.tvName));
		tvPinyin     = forceCast(findViewById(R.id.tvPinyin));
		tvPretation  = forceCast(findViewById(R.id.tvPretation));
		tvSource     = forceCast(findViewById(R.id.tvSource));
		tvSample     = forceCast(findViewById(R.id.tvSample));
		tvSampleFrom = forceCast(findViewById(R.id.tvSampleFrom));
		etIdiom.setText("狐假虎威");
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		Idiom api = (Idiom) MobAPI.getAPI(Idiom.NAME);
		api.queryIdiom(etIdiom.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = forceCast(result.get("result"));
		tvName.setText(com.mob.tools.utils.R.toString(res.get("name")));
		tvPinyin.setText(com.mob.tools.utils.R.toString(res.get("pinyin")));
		tvPretation.setText(com.mob.tools.utils.R.toString(res.get("pretation")));
		tvSource.setText(com.mob.tools.utils.R.toString(res.get("source")));
		tvSample.setText(com.mob.tools.utils.R.toString(res.get("sample")));
		tvSampleFrom.setText(com.mob.tools.utils.R.toString(res.get("sampleFrom")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
