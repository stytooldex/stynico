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
import com.mob.mobapi.apis.Mobile;

import java.util.HashMap;
import java.util.Map;

public class MobileAPIActivity extends AppCompatActivity implements OnClickListener, APICallback
{
    private EditText etPhone;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvCityCode;
    private TextView tvMobileNumber;
    private TextView tvZipCode;
    private TextView tvOperator;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mobile);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	etPhone = (EditText) findViewById(R.id.etPhone);
	tvProvince = (TextView) findViewById(R.id.tvProvince);
	tvCity = (TextView) findViewById(R.id.tvCity);
	tvCityCode = (TextView) findViewById(R.id.tvCityCode);
	tvMobileNumber = (TextView) findViewById(R.id.tvMobileNumber);
	tvZipCode = (TextView) findViewById(R.id.tvZipCode);
	tvOperator = (TextView) findViewById(R.id.tvOperator);
	findViewById(R.id.btnSearch).setOnClickListener(this);
    }

    public void onClick(View v)
    {
	// 获取API实例，请求手机号码归属地
	Mobile api = (Mobile) MobAPI.getAPI(Mobile.NAME);
	api.phoneNumberToAddress(etPhone.getText().toString().trim(), this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result)
    {
	@SuppressWarnings("unchecked")
	    HashMap<String, Object> address = (HashMap<String, Object>) result.get("result");
	tvProvince.setText(com.mob.tools.utils.R.toString(address.get("province")));
	tvCity.setText(com.mob.tools.utils.R.toString(address.get("city")));
	tvCityCode.setText(com.mob.tools.utils.R.toString(address.get("cityCode")));
	tvMobileNumber.setText(com.mob.tools.utils.R.toString(address.get("mobileNumber")));
	tvZipCode.setText(com.mob.tools.utils.R.toString(address.get("zipCode")));
	tvOperator.setText(com.mob.tools.utils.R.toString(address.get("operator")));
    }

    public void onError(API api, int action, Throwable details)
    {
	details.printStackTrace();
	Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }

}
