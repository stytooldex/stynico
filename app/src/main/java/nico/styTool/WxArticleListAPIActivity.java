package nico.styTool;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


//你们真的好讨厌

public class WxArticleListAPIActivity extends ListActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        String cid = getIntent().getStringExtra("cid");
        setTitle(getString(R.string.wxarticle_api_title_list, name));
        getListView().setBackgroundColor(0xffffffff);
        getListView().setDivider(new ColorDrawable(0xff7f7f7f));
        getListView().setDividerHeight(1);
        WxArticleAdapter adapter = new WxArticleAdapter(cid);
        setListAdapter(adapter);
        adapter.requestData();
    }

}
