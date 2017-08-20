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
import com.mob.mobapi.apis.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mob.tools.utils.R.forceCast;
public class CalendarAPIActivity extends AppCompatActivity implements APICallback, OnItemSelectedListener
{
    private Spinner  spYear;
    private Spinner  spMonth;
    private Spinner  spDay;
    private TextView tvCalZodiac;
    private TextView tvCalAvoid;
    private TextView tvCalDate;
    private TextView tvCalHoliday;
    private TextView tvCalLunar;
    private TextView tvCalLunarYear;
    private TextView tvCalSuit;
    private TextView tvCalWeekday;
    private Calendar api;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_calendar);
	
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

	tvCalZodiac    = forceCast(findViewById(R.id.tvCalZodiac));
	tvCalAvoid     = forceCast(findViewById(R.id.tvCalAvoid));
	tvCalDate      = forceCast(findViewById(R.id.tvCalDate));
	tvCalHoliday   = forceCast(findViewById(R.id.tvCalHoliday));
	tvCalLunar     = forceCast(findViewById(R.id.tvCalLunar));
	tvCalLunarYear = forceCast(findViewById(R.id.tvCalLunarYear));
	tvCalSuit      = forceCast(findViewById(R.id.tvCalSuit));
	tvCalWeekday   = forceCast(findViewById(R.id.tvCalWeekday));

	api = forceCast(MobAPI.getAPI(Calendar.NAME));
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
	tvCalZodiac.setText(com.mob.tools.utils.R.toString(res.get("zodiac")));
	tvCalAvoid.setText(com.mob.tools.utils.R.toString(res.get("avoid")));
	tvCalDate.setText(com.mob.tools.utils.R.toString(res.get("date")));
	tvCalHoliday.setText(com.mob.tools.utils.R.toString(res.get("holiday")));
	tvCalLunar.setText(com.mob.tools.utils.R.toString(res.get("lunar")));
	tvCalLunarYear.setText(com.mob.tools.utils.R.toString(res.get("lunarYear")));
	tvCalSuit.setText(com.mob.tools.utils.R.toString(res.get("suit")));
	tvCalWeekday.setText(com.mob.tools.utils.R.toString(res.get("weekday")));
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
	api.queryCalendar(date, this);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
