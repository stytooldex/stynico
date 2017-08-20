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
import com.mob.mobapi.apis.MobileLucky;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;


public class MobileLuckyAPIActivity extends AppCompatActivity implements OnClickListener, APICallback
{
    private EditText etMobileLucky;
    private TextView tvConclusion;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mobilelucky);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	etMobileLucky = forceCast(findViewById(R.id.etMobileLuck));
	tvConclusion  = forceCast(findViewById(R.id.tvConclusion));
	etMobileLucky.setText("13888888888");
	findViewById(R.id.btnSearch).setOnClickListener(this);
    }

    public void onClick(View v)
    {
	// 获取API实例，查询手机号凶吉信息
	MobileLucky api = (MobileLucky) MobAPI.getAPI(MobileLucky.NAME);
	api.queryMobileLucky(etMobileLucky.getText().toString().trim(), this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result)
    {
	HashMap<String, Object> res = forceCast(result.get("result"));
	tvConclusion.setText(com.mob.tools.utils.R.toString(res.get("conclusion")));
    }

    public void onError(API api, int action, Throwable details)
    {
	details.printStackTrace();
	Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }
}
