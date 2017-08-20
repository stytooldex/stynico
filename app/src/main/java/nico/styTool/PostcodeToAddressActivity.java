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
import com.mob.mobapi.apis.Postcode;

import java.util.ArrayList;
import java.util.Map;

public class PostcodeToAddressActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private EditText etPostcode;
	private TextView tvProvince;
	private TextView tvCity;
	private TextView tvDistrict;
	private TextView tvAddress;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postcode_p2a);
		etPostcode = (EditText) findViewById(R.id.etPostcode);
		findViewById(R.id.btnSearch).setOnClickListener(this);
		tvProvince = (TextView) findViewById(R.id.tvProvince);
		tvCity = (TextView) findViewById(R.id.tvCity);
		tvDistrict = (TextView) findViewById(R.id.tvDistrict);
		tvAddress = (TextView) findViewById(R.id.tvAddress);
	}

	public void onClick(View v) {
		// 获取API，请求指定邮编对应的地址
		Postcode api = (Postcode) MobAPI.getAPI(Postcode.NAME);
		String postcode = etPostcode.getText().toString().trim();
		api.postcodeToAddress(postcode, this);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(API api, int action, Map<String, Object> result) {
		result = (Map<String, Object>) result.get("result");
		String province = com.mob.tools.utils.R.toString(result.get("province"));
		String city = com.mob.tools.utils.R.toString(result.get("city"));
		String district = com.mob.tools.utils.R.toString(result.get("district"));
		ArrayList<String> list = (ArrayList<String>) result.get("address");
		StringBuilder address = new StringBuilder();
		for (String add : list) {
			address.append('\n').append(add);
		}
		address.deleteCharAt(0);

		tvProvince.setText(province);
		tvCity.setText(city);
		tvDistrict.setText(district);
		tvAddress.setText(address);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

}
