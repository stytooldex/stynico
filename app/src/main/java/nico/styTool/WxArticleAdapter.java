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
import com.mob.mobapi.apis.WxArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class WxArticleAdapter extends BaseAdapter implements APICallback {
	private static final int PAGE_SIZE = 20;
	private String cid;
	private int pageIndex;
	private int total;
	private ArrayList<HashMap<String, Object>> list;

	public WxArticleAdapter(String cid) {
		this.cid = cid;
		list = new ArrayList<HashMap<String,Object>>();
	}

	/** 请求下一个页面的数据 */
	public void requestData() {
		WxArticle api = forceCast(MobAPI.getAPI(WxArticle.NAME));
		api.searchArticleList(cid, pageIndex + 1, PAGE_SIZE, this);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(API api, int action, Map<String, Object> result) {
		// 解析数据
		result = (Map<String, Object>) result.get("result");
		try {
			int curPage = com.mob.tools.utils.R.parseInt(com.mob.tools.utils.R.toString(result.get("curPage")));
			total = com.mob.tools.utils.R.parseInt(com.mob.tools.utils.R.toString(result.get("total")));
			if (curPage != pageIndex + 1) {
				return;
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

		// 追加数据
		pageIndex++;
		ArrayList<HashMap<String, Object>> resultList = forceCast(result.get("list"));
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
		} else if (list.size() == total) {
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
			convertView = View.inflate(parent.getContext(), R.layout.view_wxarticle_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCid = forceCast(convertView.findViewById(R.id.tvCid));
			viewHolder.tvId = forceCast(convertView.findViewById(R.id.tvId));
			viewHolder.tvPubTime = forceCast(convertView.findViewById(R.id.tvPubTime));
			viewHolder.tvSourceUrl = forceCast(convertView.findViewById(R.id.tvSourceUrl));
			viewHolder.tvTitle = forceCast(convertView.findViewById(R.id.tvTitle));
			viewHolder.tvSubTitle = forceCast(convertView.findViewById(R.id.tvSubTitle));
			viewHolder.tvThumbnails = forceCast(convertView.findViewById(R.id.tvThumbnails));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = forceCast(convertView.getTag());
		}

		HashMap<String, Object> data = getItem(position);
		viewHolder.tvCid.setText(String.valueOf(data.get("cid")));
		viewHolder.tvId.setText(String.valueOf(data.get("id")));
		viewHolder.tvPubTime.setText(String.valueOf(data.get("pubTime")));
		viewHolder.tvSourceUrl.setText(String.valueOf(data.get("sourceUrl")));
		viewHolder.tvTitle.setText(String.valueOf(data.get("title")));
		viewHolder.tvSubTitle.setText(String.valueOf(data.get("subTitle")));
		viewHolder.tvThumbnails.setText(String.valueOf(data.get("thumbnails")));
		return convertView;
	}

	private View getView2(View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ProgressBar(parent.getContext());
		}

		if (list.size() < total) {
			convertView.setVisibility(View.VISIBLE);
			requestData();
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView tvCid;
		TextView tvId;
		TextView tvPubTime;
		TextView tvSourceUrl;
		TextView tvTitle;
		TextView tvSubTitle;
		TextView tvThumbnails;
	}

}
