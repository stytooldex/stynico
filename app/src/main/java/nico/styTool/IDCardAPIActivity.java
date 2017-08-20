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
import com.mob.mobapi.apis.IDCard;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class IDCardAPIActivity extends AppCompatActivity implements OnClickListener, APICallback
{
    private EditText etCardNumber;
    private TextView tvArea;
    private TextView tvBirthday;
    private TextView tvSex;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_idcard);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	etCardNumber = forceCast(findViewById(R.id.etCardNumber));
	tvArea       = forceCast(findViewById(R.id.tvArea));
	tvBirthday   = forceCast(findViewById(R.id.tvBirthday));
	tvSex        = forceCast(findViewById(R.id.tvSex));
	etCardNumber.setText("45102519800411512X"); // for example
	findViewById(R.id.btnSearch).setOnClickListener(this);
    }

    public void onClick(View v)
    {
	// 获取API实例，查询身份证信息
	IDCard api = forceCast(MobAPI.getAPI(IDCard.NAME));
	api.queryIDCard(etCardNumber.getText().toString().trim(), this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result)
    {
	HashMap<String, Object> address = forceCast(result.get("result"));
	tvArea.setText(com.mob.tools.utils.R.toString(address.get("area")));
	tvBirthday.setText(com.mob.tools.utils.R.toString(address.get("birthday")));
	tvSex.setText(com.mob.tools.utils.R.toString(address.get("sex")));
    }

    public void onError(API api, int action, Throwable details)
    {
	details.printStackTrace();
	Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }
}
