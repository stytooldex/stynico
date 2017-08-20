package nico.styTool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.LaoHuangLi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;

public class LaoHuangLiAPIActivity extends AppCompatActivity implements APICallback, OnItemSelectedListener
{
    private Spinner  spYear;
    private Spinner  spMonth;
    private Spinner  spDay;
    private TextView tvLhlAvoid;
    private TextView tvLhlJiShen;
    private TextView tvLhlSuit;
    private TextView tvLhlXiongShen;
    private TextView tvLhlDate;
    private TextView tvLhlLunar;
    private LaoHuangLi api;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_laohuangli);
	StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
	spYear  = forceCast(findViewById(R.id.spYear));
	spMonth = forceCast(findViewById(R.id.spManth));
	spDay   = forceCast(findViewById(R.id.spDay));

	spYear.setOnItemSelectedListener(this);
	spMonth.setOnItemSelectedListener(this);
	spDay.setOnItemSelectedListener(this);

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String[] dates = dateFormat.format(new java.util.Date()).split("-");
	int startyear = 1900;
	initSpinner(1900, 2099, spYear);
	initSpinner(1, 12, spMonth);
	initSpinner(1, 31, spDay);

	spYear.setSelection(Integer.parseInt(dates[0]) - startyear);
	spMonth.setSelection(Integer.parseInt(dates[1]) - 1);
	spDay.setSelection(Integer.parseInt(dates[2]) - 1);

	tvLhlAvoid     = forceCast(findViewById(R.id.tvLhlAvoid));
	tvLhlJiShen    = forceCast(findViewById(R.id.tvLhlJiShen));
	tvLhlSuit      = forceCast(findViewById(R.id.tvLhlSuit));
	tvLhlXiongShen = forceCast(findViewById(R.id.tvLhlXiongShen));
	tvLhlDate      = forceCast(findViewById(R.id.tvLhlDate));
	tvLhlLunar     = forceCast(findViewById(R.id.tvLhlLunar));

	api = (LaoHuangLi) MobAPI.getAPI(LaoHuangLi.NAME);
    }

    private String IntegerToString(int index)
    {
	if (index < 10)
	{
	    return "0" + index;
	}

	return "" + index;
    }

    private void initSpinner(int start, int end, Spinner spinner)
    {
	ArrayList<String> spinnerList = new ArrayList<String>();

	for (int i = start; i < end + 1; i++)
	{
	    spinnerList.add(IntegerToString(i));
	}

	spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.view_spinner, spinnerList));
    }

    public void onSuccess(API api, int action, Map<String, Object> result)
    {
	HashMap<String, Object> res = forceCast(result.get("result"));
	tvLhlAvoid.setText(com.mob.tools.utils.R.toString(res.get("avoid")));
	tvLhlJiShen.setText(com.mob.tools.utils.R.toString(res.get("jishen")));
	tvLhlSuit.setText(com.mob.tools.utils.R.toString(res.get("suit")));
	tvLhlXiongShen.setText(com.mob.tools.utils.R.toString(res.get("xiongshen")));
	tvLhlDate.setText(com.mob.tools.utils.R.toString(res.get("date")));
	tvLhlLunar.setText(com.mob.tools.utils.R.toString(res.get("lunar")));
    }

    public void onError(API api, int action, Throwable details)
    {
	details.printStackTrace();
	Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
			       long id)
    {
	String date = "" + spYear.getSelectedItem() + "-" + spMonth.getSelectedItem() + "-" + spDay.getSelectedItem();
	api.queryLaoHuangLi(date, this);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
