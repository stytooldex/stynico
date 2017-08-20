package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Exchange;

import java.util.ArrayList;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class QueryAllCurrency extends AppCompatActivity implements APICallback {
    private ListView lvResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_currency);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        lvResult = forceCast(findViewById(R.id.lvResult));
        //查询主要国家货币代码
        ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryCurrency(this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        ArrayList<Map<String, Object>> res = forceCast(result.get("result"));
        SimpleAdapter adapter = new SimpleAdapter(this, res, R.layout.view_exchange_currency_item,
                new String[]{"code", "name"}, new int[]{R.id.tvCurrencyCode, R.id.tvCurrencyName});
        lvResult.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }

}
