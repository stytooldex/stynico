package nico.styTool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Cook;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.utils.Hashon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class MenuAdapter extends BaseAdapter implements APICallback {
	private static final int PAGE_SIZE = 20;
	private String ctgId;
	private int pageIndex;
	private int total;
	private ArrayList<HashMap<String, Object>> menus;

	public MenuAdapter(String ctgId) {
		this.ctgId = ctgId;
		menus = new ArrayList<HashMap<String,Object>>();
	}

	/** 请求下一个页面的数据 */
	public void requestMenu() {
		Cook api = forceCast(MobAPI.getAPI(Cook.NAME));
		api.searchMenu(ctgId, null, pageIndex + 1, PAGE_SIZE, this);
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
		Hashon hashon = new Hashon();
		for (HashMap<String, Object> res : resultList) {
			HashMap<String, Object> recipe = forceCast(res.get("recipe"));
			if (recipe == null) {
				recipe = new HashMap<String, Object>();
			}

			String tmp = forceCast(recipe.get("ingredients"));
			if (tmp != null) {
				recipe.put("ingredients", hashon.fromJson("{\"list\":" + tmp + "}").get("list"));
			}
			tmp = forceCast(recipe.get("method"));
			if (tmp != null) {
				recipe.put("method", hashon.fromJson("{\"list\":" + tmp + "}").get("list"));
			}
		}
		menus.addAll(resultList);

		// 显示数据
		notifyDataSetChanged();
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(api.getContext(), R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public int getCount() {
		if (menus.size() == 0) {
			return 0;
		} else if (menus.size() == total) {
			return menus.size();
		} else {
			return menus.size() + 1;
		}
	}

	public HashMap<String, Object> getItem(int position) {
		return menus.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return position < menus.size() ? 0 : 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < menus.size()) {
			return getView1(position, convertView, parent);
		} else {
			return getView2(convertView, parent);
		}
	}

	@SuppressWarnings("unchecked")
	private View getView1(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.view_cook_menu_item, null);

			AsyncImageView aivImg = (AsyncImageView) convertView.findViewById(R.id.aivImg);
			aivImg.setScaleToCropCenter(true);
			int width = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
			int height = width * 375 / 500;
			aivImg.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		}

		HashMap<String, Object> data = getItem(position);
		HashMap<String, Object> recipe = (HashMap<String, Object>) data.get("recipe");
		ArrayList<String> ingredients = (ArrayList<String>) recipe.get("ingredients");
		StringBuilder sb = new StringBuilder();
		if (ingredients != null) {
			for (String i : ingredients) {
				sb.append('\n').append(i);
			}
			sb.deleteCharAt(0);
		}
		ArrayList<HashMap<String, Object>> method = (ArrayList<HashMap<String, Object>>) recipe.get("method");
		StringBuilder sb2 = new StringBuilder();
		if (method != null) {
			for (HashMap<String, Object> m : method) {
				sb2.append('\n').append(m.get("step"));
			}
			sb2.deleteCharAt(0);
		}
		String img = (String) recipe.get("img");

		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvSumary = (TextView) convertView.findViewById(R.id.tvSumary);
		TextView tvIngredients = (TextView) convertView.findViewById(R.id.tvIngredients);
		TextView tvMethods = (TextView) convertView.findViewById(R.id.tvMethods);
		AsyncImageView aivImg = (AsyncImageView) convertView.findViewById(R.id.aivImg);
		tvName.setText(com.mob.tools.utils.R.toString(data.get("name")));
		tvSumary.setText(com.mob.tools.utils.R.toString(recipe.get("sumary")));
		tvIngredients.setText(sb);
		tvMethods.setText(sb2);
		if (img != null) {
			aivImg.setVisibility(View.VISIBLE);
			aivImg.execute(img, 0);
		} else {
			aivImg.setVisibility(View.GONE);
		}

		return convertView;
	}

	private View getView2(View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ProgressBar(parent.getContext());
		}

		if (menus.size() < total) {
			convertView.setVisibility(View.VISIBLE);
			requestMenu();
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

}
