package nico.styTool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.Glide;

/**
 * Created by luxin on 15-12-15.
 *  http://luxin.gitcafe.io
 */
public class HelpsMainAdapter extends BaseAdapter
{

    //private final static String TAG = "HelpsMainAdapter";

    private Context mContext;
    private List<Helps_a> mData;
    private LayoutInflater inflater;
    private int mColor;
    
    public HelpsMainAdapter(Context context, List<Helps_a> list)
    {
        this.mContext = context;
        this.mData = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
	{
            convertView = inflater.inflate(R.layout.lxw_item_helps, null);
            holder = new ViewHolder();
            //holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.lxw_id_item_helps_include_avertor);
            holder.userimg = (ImageView) convertView.findViewById(R.id.lxw_id_item_helps_userimg);
            holder.username = (TextView) convertView.findViewById(R.id.lxw_id_item_helps_username);
            holder.personality = (TextView) convertView.findViewById(R.id.lxw_id_item_helps_user_personality);
            holder.creatTime = (TextView) convertView.findViewById(R.id.lxw_id_item_helps_create_time);
            holder.content = (TextView) convertView.findViewById(R.id.lxw_id_item_helps_content);
	    holder.frameLayout = (LinearLayout) convertView.findViewById(R.id.lxwitemhelpsLinearLayout1);
	    holder.gridView = (CardView) convertView.findViewById(R.id.lxwitemhelpsCardView1);
	     /*holder.contentImg = (ImageView) holder.frameLayout.findViewById(R.id.lxw_id_item_helps_content_img);
	     */
            convertView.setTag(holder);
        }
	else
	{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.userimg.setImageResource(R.mipmap.ic_launcher);
        //holder.contentImg.setImageResource(R.drawable.pictures_no);

	/*
	 holder.contentImg.setVisibility(View.GONE);
	 holder.gridView.setVisibility(View.GONE);
	 holder.contentImg.setFocusable(false);
	 holder.gridView.setFocusable(false);
	 */
        Helps_a helps = mData.get(position);
	//       Log.e(TAG, "===helps=====createAt=" + helps.getCreatedAt());

        MyUser myUser = helps.getUser();

        if (myUser == null)
	{
	    //           Log.e(TAG, "====myUser is null===");
        }
	if (myUser.getAuvter() != null)
	{
            //String auvterPath = "http://file.bmob.cn/" + myUser.getAuvter().getUrl();
            //Glide.with(mContext).load(auvterPath).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userimg);
            ImageLoader.getmInstance().loaderImage(Constant.USERIMG + myUser.getAuvter().getUrl(), holder.userimg, true);
        }


        holder.username.setText(myUser.getUsername());
	/*
	boolean Run = helps.getPhontofile(); 
	//Editor editor = sharedPreferences.edit(); 
	if (Run) 
	{ 
	    holder.gridView.setVisibility(View.VISIBLE);
	}
	else 
	{ 
	    holder.gridView.setVisibility(View.GONE);
	}
	*/
	
        if (myUser.getPersonality() != null)
	{
            holder.personality.setText(myUser.getPersonality().toString().length() > 16 ?myUser.getPersonality().substring(0, 22) + "...": myUser.getPersonality().toString());
        }
        holder.creatTime.setText(getCreateTimes(helps.getCreatedAt()));

       // SpannableString spannableString=getSpannableString(helps.getContent(), mContext);
       // holder.content.setText(spannableString);

	SharedPreferences sharedPreferences = mContext.getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE); 
	boolean isFirstRun = sharedPreferences.getBoolean("if_bi", true); 
	//Editor editor = sharedPreferences.edit(); 
	if (isFirstRun) 
	{ 
	    Random random = new Random();
	    mColor = 0xff000000 | random.nextInt(0xffffff);
	    holder.frameLayout.setBackgroundColor(mColor);
	    //StatusBarUtil.setColor(ColorStatusBarActivity.this, mColor, mAlpha);
	//
	}
	else 
	{ 

	}
        return convertView;
    }


    private class ViewHolder
    {
        ImageView userimg;
        TextView username;
        TextView personality;
        TextView creatTime;
	LinearLayout frameLayout;
        TextView content;
	CardView gridView;
    }

    //createAt=2015-12-17 15:26:45

    private String getCreateTimes(String dates)
    {
        Date old = toDate(dates);
        Date nowtime = new Date(System.currentTimeMillis());
        long values = nowtime.getTime() - old.getTime();
        values = values / 1000;
	// Log.e(TAG, "====values  time===" + values);
        if (values < 60 && values > 0)
	{
            return  values + "秒前";
        }
        if (values > 60 && values < 60 * 60)
	{
            return values / 60 + "分钟前";
        }
        if (values < 60 * 60 * 24 && values > 60 * 60)
	{
            return values / 3600 + "小时前";
        }
        if (values < 60 * 60 * 24 * 2 && values > 60 * 60 * 24)
	{
            return "昨天";
        }
        if (values < 60 * 60 * 3 * 24 && values > 60 * 60 * 24 * 2)
	{
            return  "前天";
        }
        if (values < 60 * 60 * 24 * 30 && values > 60 * 60 * 24 * 3)
	{
            return  values / (60 * 60 * 24) + "天前";
        }
        if (values < 60 * 60 * 24 * 365 && values > 60 * 60 * 24 * 30)
	{
	    return nowtime.getMonth() - old.getMonth() + "个月前";
        }
        return  values / (60 * 60 * 24 * 30 * 365) + "年前";
    }

    private Date toDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date1 = null;
        try
	{
            date1 = format.parse(date);
            //  Log.e(TAG,"===date==="+date1);
        }
	catch (ParseException e)
	{
            e.printStackTrace();
        }
        return date1;
    }

}
