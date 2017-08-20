package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomAPIActivity extends AppCompatActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom);
		findViewById(R.id.btnManual).setOnClickListener(this);
		findViewById(R.id.btnAutomatic).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnManual: startActivity(new Intent(this, CustomAPIFullManualActivity.class)); break;
			case R.id.btnAutomatic: startActivity(new Intent(this, CustomAPISemiAutomaticActivity.class)); break;
		}
	}

}
