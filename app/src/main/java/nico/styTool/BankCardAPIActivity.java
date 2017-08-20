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
import com.mob.mobapi.apis.BankCard;

import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class BankCardAPIActivity extends AppCompatActivity implements OnClickListener, APICallback {
	private EditText etBankCardNumber;
	private TextView tvBank;
	private TextView tvBankBin;
	private TextView tvBandbinNumber;
	private TextView tvBankCarName;
	private TextView tvBankCardNumber;
	private TextView tvBankCardType;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_bankcard);
	    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		etBankCardNumber = forceCast(findViewById(R.id.etBankCardNumber));
		tvBank           = forceCast(findViewById(R.id.tvBank));
		tvBankBin        = forceCast(findViewById(R.id.tvBankBin));
		tvBandbinNumber  = forceCast(findViewById(R.id.tvBandbinNumber));
		tvBankCarName    = forceCast(findViewById(R.id.tvBankCarName));
		tvBankCardNumber = forceCast(findViewById(R.id.tvBankCardNumber));
		tvBankCardType   = forceCast(findViewById(R.id.tvBankCardType));
		etBankCardNumber.setText("6228482898203884775");
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		// 获取API实例，查询银行卡信息
		BankCard api = forceCast(MobAPI.getAPI(BankCard.NAME));
		api.queryBankCard(etBankCardNumber.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = forceCast(result.get("result"));
		tvBank.setText(com.mob.tools.utils.R.toString(res.get("bank")));
		tvBankBin.setText(com.mob.tools.utils.R.toString(res.get("bin")));
		tvBandbinNumber.setText(com.mob.tools.utils.R.toString(res.get("binNumber")));
		tvBankCarName.setText(com.mob.tools.utils.R.toString(res.get("cardName")));
		tvBankCardNumber.setText(com.mob.tools.utils.R.toString(res.get("cardNumber")));
		tvBankCardType.setText(com.mob.tools.utils.R.toString(res.get("cardType")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
