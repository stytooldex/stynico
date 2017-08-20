package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.WxArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class WxArticleAPIActivity extends AppCompatActivity implements APICallback, AdapterView.OnItemClickListener {

    private SimpleAdapter categoryAdapter;
    private ArrayList<HashMap<String, Object>> categoryList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxarticle);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        ListView lvResult = forceCast(findViewById(R.id.lvResult));
        lvResult.setOnItemClickListener(this);

        //init data
        categoryList = new ArrayList<HashMap<String, Object>>();
        categoryAdapter = new SimpleAdapter(this, categoryList, R.layout.view_wxarticle_category_item, new String[]{"cid", "name"}, new int[]{R.id.tvCid, R.id.tvName});
        lvResult.setAdapter(categoryAdapter);

        //查询分类信息
        ((WxArticle) forceCast(MobAPI.getAPI(WxArticle.NAME))).queryCategory(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        HashMap<String, Object> item = forceCast(categoryAdapter.getItem(position));
        //goto wx articles
        Intent intent = new Intent(this, WxArticleListAPIActivity.class);
        intent.putExtra("name", (String) item.get("name"));
        intent.putExtra("cid", (String) item.get("cid"));
        Toast.makeText(this, item.get("name") +"\n"+ item.get("cid"), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        ArrayList<HashMap<String, Object>> res = forceCast(result.get("result"));
        if (null != res && res.size() > 0) {
            categoryList.clear();
            categoryList.addAll(res);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
    }
}
