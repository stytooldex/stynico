package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Gold;

import java.util.ArrayList;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class GoldAPIActivity extends AppCompatActivity implements APICallback, View.OnClickListener {
    private Button btnFuture;
    private Button btnSpot;
    private TextView tvTittle;
    private ListView lvResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        btnFuture = forceCast(findViewById(R.id.btnFuture));
        btnSpot = forceCast(findViewById(R.id.btnSpot));
        tvTittle = forceCast(findViewById(R.id.tvTittle));
        lvResult = forceCast(findViewById(R.id.lvResult));

        btnFuture.setOnClickListener(this);
        btnSpot.setOnClickListener(this);
    }

    private void setBtnEnable(boolean enable) {
        btnFuture.setEnabled(enable);
        btnSpot.setEnabled(enable);
    }

    public void onClick(View view) {
        setBtnEnable(false);
        if (view.getId() == R.id.btnFuture) {
            tvTittle.setText(R.string.gold_api_title_future);
            //查询期货黄金
            ((Gold) forceCast(MobAPI.getAPI(Gold.NAME))).queryFuture(this);
        } else {
            tvTittle.setText(R.string.gold_api_title_spot);
            //查询现货黄金
            ((Gold) forceCast(MobAPI.getAPI(Gold.NAME))).querySpot(this);
        }
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        ArrayList<Map<String, Object>> res = forceCast(result.get("result"));
        if (res != null && res.size() > 0) {
            switch (action) {
                case Gold.ACTION_FUTURE: showFutureResult(res); break;
                case Gold.ACTION_SPOT: showSpotResult(res); break;
            }
        }
        setBtnEnable(true);
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
        setBtnEnable(true);
    }

    private void showFutureResult(ArrayList<Map<String, Object>> result) {
        //期货黄金
        SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_gold_future_list_item,
                new String[]{ "name", "buyNumber", "buyPic", "closePri", "code", "date", "dynamicSettle", "highPic", "limit", "lowPic", "openPri", "positionNum", "sellNumber", "sellPic", "totalVol", "yesDayPic", "yesDaySettle"},
                new int[]{ R.id.tvName, R.id.tvBuyNumber, R.id.tvBuyPic, R.id.tvClosePri, R.id.tvCode, R.id.tvDate, R.id.tvDynamicSettle, R.id.tvHighPic, R.id.tvLimit, R.id.tvLowPic, R.id.tvOpenPri, R.id.tvPositionNum, R.id.tvSellNumber, R.id.tvSellPic, R.id.tvTotalVol, R.id.tvYesterdayPic, R.id.tvYesterdaySettle });
        lvResult.setAdapter(adapter);
    }

    private void showSpotResult(ArrayList<Map<String, Object>> result) {
        //现货黄金
        SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_gold_spot_list_item,
                new String[]{ "name", "closePri", "highPic", "limit", "lowPic", "openPri", "time", "totalTurnover", "totalVol", "yesDayPic", "variety"},
                new int[]{ R.id.tvName, R.id.tvClosePri, R.id.tvHighPic, R.id.tvLimit, R.id.tvLowPic, R.id.tvOpenPri, R.id.tvTime, R.id.tvTotalTurnover, R.id.tvTotalVol, R.id.tvYesterdayPic, R.id.tvVariety });
        lvResult.setAdapter(adapter);
}
}
