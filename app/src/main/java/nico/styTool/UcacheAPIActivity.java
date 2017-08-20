package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class UcacheAPIActivity extends AppCompatActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucache);
		findViewById(R.id.btnPut).setOnClickListener(this);
		findViewById(R.id.btnGet).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnPut: startActivity(new Intent(this, UcachePutActivity.class)); break;
			case R.id.btnGet: startActivity(new Intent(this, UcacheGetActivity.class)); break;
		}
	}
}
