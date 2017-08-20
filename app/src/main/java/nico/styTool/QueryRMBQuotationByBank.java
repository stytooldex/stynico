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
import com.mob.mobapi.apis.Exchange;

import java.util.ArrayList;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class QueryRMBQuotationByBank extends AppCompatActivity implements APICallback, View.OnClickListener {
    private Button btnICBC;
    private Button btnCMB;
    private Button btnCCB;
    private Button btnBOC;
    private Button btnBCM;
    private Button btnABC;
    private TextView tvTittle;
    private ListView lvResult;
    private SimpleAdapter adapter;

    private ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rmb_quotation);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        btnICBC = forceCast(findViewById(R.id.btnICBC));
        btnCMB = forceCast(findViewById(R.id.btnCMB));
        btnCCB = forceCast(findViewById(R.id.btnCCB));
        btnBOC = forceCast(findViewById(R.id.btnBOC));
        btnBCM = forceCast(findViewById(R.id.btnBCM));
        btnABC = forceCast(findViewById(R.id.btnABC));
        tvTittle = forceCast(findViewById(R.id.tvTittle));
        lvResult = forceCast(findViewById(R.id.lvResult));

        btnICBC.setOnClickListener(this);
        btnCMB.setOnClickListener(this);
        btnCCB.setOnClickListener(this);
        btnBOC.setOnClickListener(this);
        btnBCM.setOnClickListener(this);
        btnABC.setOnClickListener(this);

        //init data
        adapter = new SimpleAdapter(this, dataList, R.layout.view_exchange_rmb_quotation_item,
                new String[]{"bankName", "bank", "currencyName", "currencyCode", "fBuyPri", "mBuyPri", "fSellPri", "mSellPri", "bankConversionPri", "date", "time"},
                new int[]{R.id.tvBankName, R.id.tvBankCode, R.id.tvCurrencyName, R.id.tvCurrencyCode, R.id.tvFBuyPri, R.id.tvMBuyPri, R.id.tvFSellPri, R.id.tvMSellPri, R.id.tvBankConversionPri, R.id.tvDate, R.id.tvTime});
        lvResult.setAdapter(adapter);
    }

    private void setBtnEnable(boolean enable) {
        btnICBC.setEnabled(enable);
        btnCMB.setEnabled(enable);
        btnCCB.setEnabled(enable);
        btnBOC.setEnabled(enable);
        btnBCM.setEnabled(enable);
        btnABC.setEnabled(enable);
    }

    public void onClick(View view) {
        setBtnEnable(false);
        if (view.getId() == R.id.btnICBC) {
            tvTittle.setText(R.string.exchange_api_title_bank_icbc);
            //查询工商银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("0", this);
        } else if (view.getId() == R.id.btnCMB) {
            tvTittle.setText(R.string.exchange_api_title_bank_cmb);
            //查询招商银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("1", this);
        } else if (view.getId() == R.id.btnCCB) {
            tvTittle.setText(R.string.exchange_api_title_bank_ccb);
            //查询建设银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("2", this);
        } else if (view.getId() == R.id.btnBOC) {
            tvTittle.setText(R.string.exchange_api_title_bank_boc);
            //查询中国银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("3", this);
        } else if (view.getId() == R.id.btnBCM) {
            tvTittle.setText(R.string.exchange_api_title_bank_bcm);
            //查询交通银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("4", this);
        } else if (view.getId() == R.id.btnABC) {
            tvTittle.setText(R.string.exchange_api_title_bank_abc);
            //查询农业银行汇率
            ((Exchange) forceCast(MobAPI.getAPI(Exchange.NAME))).queryRMBQuotation("5", this);
        }
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        ArrayList<Map<String, Object>> res = forceCast(result.get("result"));
        if (res == null) {
            res = new ArrayList<Map<String, Object>>();
        }
        dataList.clear();
        dataList.addAll(res);
        adapter.notifyDataSetChanged();
        setBtnEnable(true);
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
        setBtnEnable(true);
    }

}
