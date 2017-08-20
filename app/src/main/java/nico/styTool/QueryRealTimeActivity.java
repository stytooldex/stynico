package nico.styTool;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class QueryRealTimeActivity extends ListActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.exchange_api_title_real_time);
        getListView().setBackgroundColor(0xffffffff);
        getListView().setDivider(new ColorDrawable(0xff7f7f7f));
        getListView().setDividerHeight(1);

        // 请求和现实操作由RealTimeExchangeAdapter完成
        RealTimeExchangeAdapter adapter = new RealTimeExchangeAdapter();
        setListAdapter(adapter);
        adapter.requestData();
    }
}
