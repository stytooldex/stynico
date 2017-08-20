package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;


public class ExchangeAPIActivity extends AppCompatActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_exchange);
	    StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
		findViewById(R.id.btnRMBQuotation).setOnClickListener(this);
		findViewById(R.id.btnCurrency).setOnClickListener(this);
		findViewById(R.id.btnByCode).setOnClickListener(this);
		findViewById(R.id.btnRealTime).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnRMBQuotation: startActivity(new Intent(this, QueryRMBQuotationByBank.class)); break;
			case R.id.btnCurrency: startActivity(new Intent(this, QueryAllCurrency.class)); break;
			case R.id.btnByCode: startActivity(new Intent(this, QueryExchangeRateByCode.class)); break;
			case R.id.btnRealTime: startActivity(new Intent(this, QueryRealTimeActivity.class)); break;
		}
	}

}
