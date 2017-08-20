package nico.styTool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.OilPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class OilPriceAPIActivity extends AppCompatActivity implements APICallback {

    private OilPriceAdapter oilPriceAdapter;
    private ArrayList<HashMap<String, Object>> oilPriceList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oilprice);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        ListView lvResult = forceCast(findViewById(R.id.lvResult));

        //init data
        oilPriceList = new ArrayList<HashMap<String, Object>>();
        oilPriceAdapter = new OilPriceAdapter(this, oilPriceList);
        lvResult.setAdapter(oilPriceAdapter);

        //查询今日油价
        ((OilPrice) forceCast(MobAPI.getAPI(OilPrice.NAME))).queryOilPrice(OilPriceAPIActivity.this);
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        HashMap<String, Object> res = forceCast(result.get("result"));
        if (res != null && res.size() > 0) {
            ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
            for (Map.Entry<String, Object> entry : res.entrySet()) {
                tempList.add(com.mob.tools.utils.R.<HashMap<String, Object>>forceCast(entry.getValue()));
            }
            if (tempList.size() > 0) {
                oilPriceList.clear();
                oilPriceList.addAll(tempList);
                oilPriceAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }

    private static class ViewHolder {
        TextView tvProvince;
        TextView tvDieselOil0;
        TextView tvGasoline90;
        TextView tvGasoline93;
        TextView tvGasoline97;
    }

    private class OilPriceAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<HashMap<String, Object>> list;

        OilPriceAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
            inflater = LayoutInflater.from(context);
            list = res;
        }

        public int getCount() {
            if (null != list) {
                return list.size();
            }
            return 0;
        }

        public HashMap<String, Object> getItem(int position) {
            if (position < list.size()) {
                return list.get(position);
            }
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_oilprice_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvProvince = forceCast(convertView.findViewById(R.id.tvProvince));
                viewHolder.tvDieselOil0 = forceCast(convertView.findViewById(R.id.tvDieselOil0));
                viewHolder.tvGasoline90 = forceCast(convertView.findViewById(R.id.tvGasoline90));
                viewHolder.tvGasoline93 = forceCast(convertView.findViewById(R.id.tvGasoline93));
                viewHolder.tvGasoline97 = forceCast(convertView.findViewById(R.id.tvGasoline97));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = forceCast(convertView.getTag());
            }
            HashMap<String, Object> res = getItem(position);
            if (res != null) {
                viewHolder.tvProvince.setText(com.mob.tools.utils.R.toString(res.get("province")));
                viewHolder.tvDieselOil0.setText(com.mob.tools.utils.R.toString(res.get("dieselOil0")));
                viewHolder.tvGasoline90.setText(com.mob.tools.utils.R.toString(res.get("gasoline90")));
                viewHolder.tvGasoline93.setText(com.mob.tools.utils.R.toString(res.get("gasoline93")));
                viewHolder.tvGasoline97.setText(com.mob.tools.utils.R.toString(res.get("gasoline97")));
            }
            return convertView;
        }
    }
}
