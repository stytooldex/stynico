package nico.styTool;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MenuListActivity extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String ctgId = getIntent().getStringExtra("ctgId");
		setTitle(title);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);

		// 请求和现实操作由MenuAdapter完成
		MenuAdapter adapter = new MenuAdapter(ctgId);
		setListAdapter(adapter);
		adapter.requestMenu();
	}

}
