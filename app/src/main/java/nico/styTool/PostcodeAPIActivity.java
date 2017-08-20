package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class PostcodeAPIActivity extends AppCompatActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postcode);
		findViewById(R.id.btnP2A).setOnClickListener(this);
		findViewById(R.id.btnA2P).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnP2A: startActivity(new Intent(this, PostcodeToAddressActivity.class)); break;
			case R.id.btnA2P: startActivity(new Intent(this, AddressToPostcodeActivity.class)); break;
		}
	}

}
