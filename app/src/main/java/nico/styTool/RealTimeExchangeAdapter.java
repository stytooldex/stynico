package nico.styTool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class RealTimeExchangeAdapter extends BaseAdapter implements APICallback {
	private static final int PAGE_SIZE = 50;
	private int pageIndex;
	private int totalPage;
	private ArrayList<HashMap<String, Object>> list;

	public RealTimeExchangeAdapter() {
		list = new ArrayList<HashMap<String,Object>>();
	}

	/** 请求下一个页面的数据 */
	public void requestData() {
		Exchange api = forceCast(MobAPI.getAPI(Exchange.NAME));
		api.queryRealTime(pageIndex + 1, PAGE_SIZE, this);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(API api, int action, Map<String, Object> result) {
		// 解析数据
		result = (Map<String, Object>) result.get("result");
		try {
			int curPage = com.mob.tools.utils.R.parseInt(com.mob.tools.utils.R.toString(result.get("page")));
			totalPage = com.mob.tools.utils.R.parseInt(com.mob.tools.utils.R.toString(result.get("totalPage")));
			if (curPage != pageIndex + 1) {
				return;
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

		// 追加数据
		pageIndex++;
		ArrayList<HashMap<String, Object>> resultList = forceCast(result.get("resultList"));
		list.addAll(resultList);

		// 显示数据
		notifyDataSetChanged();
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(api.getContext(), R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public int getCount() {
		if (list.size() == 0) {
			return 0;
		} else if (pageIndex == totalPage) {
			return list.size();
		} else {
			return list.size() + 1;
		}
	}

	public HashMap<String, Object> getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return position < list.size() ? 0 : 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < list.size()) {
			return getView1(position, convertView, parent);
		} else {
			return getView2(convertView, parent);
		}
	}

	@SuppressWarnings("unchecked")
	private View getView1(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.view_exchange_real_time_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvBuyPic = forceCast(convertView.findViewById(R.id.tvBuyPic));
			viewHolder.tvClosePri = forceCast(convertView.findViewById(R.id.tvClosePri));
			viewHolder.tvCode = forceCast(convertView.findViewById(R.id.tvCode));
			viewHolder.tvCurrency = forceCast(convertView.findViewById(R.id.tvCurrency));
			viewHolder.tvDate = forceCast(convertView.findViewById(R.id.tvDate));
			viewHolder.tvDiffAmo = forceCast(convertView.findViewById(R.id.tvDiffAmo));
			viewHolder.tvDiffPer = forceCast(convertView.findViewById(R.id.tvDiffPer));
			viewHolder.tvHighPic = forceCast(convertView.findViewById(R.id.tvHighPic));
			viewHolder.tvLowPic = forceCast(convertView.findViewById(R.id.tvLowPic));
			viewHolder.tvOpenPri = forceCast(convertView.findViewById(R.id.tvOpenPri));
			viewHolder.tvRange = forceCast(convertView.findViewById(R.id.tvRange));
			viewHolder.tvSellPic = forceCast(convertView.findViewById(R.id.tvSellPic));
			viewHolder.tvYesterdayPic = forceCast(convertView.findViewById(R.id.tvYesterdayPic));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = forceCast(convertView.getTag());
		}

		HashMap<String, Object> data = getItem(position);
		viewHolder.tvBuyPic.setText(String.valueOf(data.get("buyPic")));
		viewHolder.tvClosePri.setText(String.valueOf(data.get("closePri")));
		viewHolder.tvCode.setText(String.valueOf(data.get("code")));
		viewHolder.tvCurrency.setText(String.valueOf(data.get("currency")));
		viewHolder.tvDate.setText(String.valueOf(data.get("date")));
		viewHolder.tvDiffAmo.setText(String.valueOf(data.get("diffAmo")));
		viewHolder.tvDiffPer.setText(String.valueOf(data.get("diffPer")));
		viewHolder.tvHighPic.setText(String.valueOf(data.get("highPic")));
		viewHolder.tvLowPic.setText(String.valueOf(data.get("lowPic")));
		viewHolder.tvOpenPri.setText(String.valueOf(data.get("openPri")));
		viewHolder.tvRange.setText(String.valueOf(data.get("range")));
		viewHolder.tvSellPic.setText(String.valueOf(data.get("sellPic")));
		viewHolder.tvYesterdayPic.setText(String.valueOf(data.get("yesDayPic")));
		return convertView;
	}

	private View getView2(View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ProgressBar(parent.getContext());
		}

		if (pageIndex < totalPage) {
			convertView.setVisibility(View.VISIBLE);
			requestData();
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView tvBuyPic;
		TextView tvClosePri;
		TextView tvCode;
		TextView tvCurrency;
		TextView tvDate;
		TextView tvDiffAmo;
		TextView tvDiffPer;
		TextView tvHighPic;
		TextView tvLowPic;
		TextView tvOpenPri;
		TextView tvRange;
		TextView tvSellPic;
		TextView tvYesterdayPic;
	}

}
