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
import com.mob.mobapi.apis.Dictionary;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;


public class DictionaryAPIActivity extends AppCompatActivity implements OnClickListener, APICallback
{
    private EditText etDictionary;
    private TextView tvName;
    private TextView tvPinyin;
    private TextView tvWubi;
    private TextView tvBushou;
    private TextView tvBihuaWithBushou;
    private TextView tvBrief;
    private TextView tvDetail;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_dictionary);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	etDictionary  = forceCast(findViewById(R.id.etDictionary));
	tvName   = forceCast(findViewById(R.id.tvName));
	tvPinyin = forceCast(findViewById(R.id.tvPinyin));
	tvWubi   = forceCast(findViewById(R.id.tvWubi));
	tvBushou = forceCast(findViewById(R.id.tvBushou));
	tvBrief  = forceCast(findViewById(R.id.tvBrief));
	tvDetail = forceCast(findViewById(R.id.tvDetail));
	tvBihuaWithBushou = forceCast(findViewById(R.id.tvBihuaWithBushou));
	etDictionary.setText("游");
	findViewById(R.id.btnSearch).setOnClickListener(this);
    }

    public void onClick(View v)
    {
	Dictionary api = (Dictionary) MobAPI.getAPI(Dictionary.NAME);
	api.queryDictionary(etDictionary.getText().toString().trim(), this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result)
    {
	HashMap<String, Object> res = forceCast(result.get("result"));
	tvName.setText(com.mob.tools.utils.R.toString(res.get("name")));
	tvPinyin.setText(com.mob.tools.utils.R.toString(res.get("pinyin")));
	tvWubi.setText(com.mob.tools.utils.R.toString(res.get("wubi")));
	tvBushou.setText(com.mob.tools.utils.R.toString(res.get("bushou")));
	tvBihuaWithBushou.setText(com.mob.tools.utils.R.toString(res.get("bihuaWithBushou")));
	tvBrief.setText(com.mob.tools.utils.R.toString(res.get("brief")));
	tvDetail.setText(com.mob.tools.utils.R.toString(res.get("detail")));
    }

    public void onError(API api, int action, Throwable details)
    {
	details.printStackTrace();
	Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }
}
