package nico.styTool;
//妮可妮可妮

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
//implements Navigation
public class smali_layout_shell_Util extends Fragment implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
{

    @Override
    public boolean onNavigationItemSelected(MenuItem p1)
    {
	// TODO: Implement this method
	return false;
    }



    public void onClick(View view)
    {  
	switch (view.getId())
	{  
	    case R.id.smalilayout1tilButton1:  
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View zview = inflater.inflate(R.layout.bilibilic, null);
		final EditText ediComment1 = (EditText) zview.findViewById(R.id.bilibilicEditText1);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(zview)
		    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
			}
		    }).setPositiveButton("生成", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
			    String comment = ediComment1.getText().toString().trim();
			    if (TextUtils.isEmpty(comment))
			    {
				// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
				return;
			    }
			    SharedPreferences mySharedPreferences= getActivity().getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
			    SharedPreferences.Editor editor = mySharedPreferences.edit();
			    editor.putString(Constant.id, "http://www.baidu-x.com/?q=" + comment);
			    editor.commit();

			    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			    AlertDialog alertDialog = builder.setMessage("请选择").setCancelable(false)
				.setNegativeButton("复制", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which)
				    {
					SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE); 
					String aname = sharedPreferences.getString(Constant.id, "");
					ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
					manager.setText(aname + "");
				    }
				}).setPositiveButton("打开", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which)
				    {
					SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE); 
					String aname = sharedPreferences.getString(Constant.id, "");
					Uri uri = Uri.parse(aname);  
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
					startActivity(intent);     
				    }
				}).create();
			    alertDialog.show();

			}


		    }).setCancelable(false).create().show();

		break;       
	    case R.id.smalilayout1tilButton2:  
		Intent intent1 = new Intent(getActivity(), CookAPIActivity.class);
		startActivity(intent1);
		break;          
	    case R.id.smalilayout1tilButton3:  

		Intent inten = new Intent(getActivity(), PostcodeAPIActivity.class);
		startActivity(inten);
		break;       
	    case R.id.smalilayout1tilButton4:  
		Intent intent = new Intent(getActivity(), MobileAPIActivity.class);
		startActivity(intent);
		break;       
	    case R.id.smalilayout1tilButton5:  
		Intent inte = new Intent(getActivity(), IDCardAPIActivity.class);
		startActivity(inte);
		break;       
	    case R.id.smalilayout1tilButton6:  
		Intent intent9 = new Intent(getActivity(), MobileLuckyAPIActivity.class);
		startActivity(intent9);
		break;       
	    case R.id.smalilayout1tilButton7:  
		Intent intent7 = new Intent(getActivity(), BankCardAPIActivity.class);
		startActivity(intent7);
		break;       
	    case R.id.smalilayout1tilButton8:  
		Intent intent3 = new Intent(getActivity(), CalendarAPIActivity.class);
		startActivity(intent3);
		break;       
	    case R.id.smalilayout1tilButton9:  
		Intent intent5 = new Intent(getActivity(), LaoHuangLiAPIActivity.class);
		startActivity(intent5);
		break;       
	    case R.id.smalilayout1tilButton10:  
		Intent intent2 = new Intent(getActivity(), HealthAPIActivity.class);
		startActivity(intent2);
		break;       
	    case R.id.smalilayout1tilButton11:  
		Intent intent8 = new Intent(getActivity(), MarriageAPIActivity.class);
		startActivity(intent8);
		break;       
	    case R.id.smalilayout1tilButton12:  
		Intent intent6 = new Intent(getActivity(), HistoryAPIActivity.class);
		startActivity(intent6);
		break;       
	    case R.id.smalilayout1tilButton13:  
		Intent intent13 = new Intent(getActivity(), DreamAPIActivity.class); 	
		startActivity(intent13);
		break;       
	    case R.id.smalilayout1tilButton14:  
		Intent intent11 = new Intent(getActivity(), IdiomAPIActivity.class);
		startActivity(intent11);
		break;       
	    case R.id.smalilayout1tilButton15:  
		Intent intent15 = new Intent(getActivity(), DictionaryAPIActivity.class);
		startActivity(intent15);
		break;       
	    case R.id.smalilayout1tilButton16:  
		Intent intent10 = new Intent(getActivity(), HoroscopeAPIActivity.class);
		startActivity(intent10);
		break;       
	    case R.id.smalilayout1tilButton17:  
		Intent intent16 = new Intent(getActivity(), OilPriceAPIActivity.class);
		startActivity(intent16);
		break;       
	    case R.id.smalilayout1tilButton18:  
		Intent intent18 = new Intent(getActivity(), LotteryAPIActivity.class);
		startActivity(intent18);
		break;       
	    case R.id.smalilayout1tilButton19:  
		Intent intent30 = new Intent(getActivity(), WxArticleAPIActivity.class);
		startActivity(intent30);
		break;       
	    case R.id.smalilayout1tilButton20:  
		Intent intent20 = new Intent(getActivity(), BoxOfficeAPIActivity.class);
		startActivity(intent20);
		break;       
	    case R.id.smalilayout1tilButton21:  
		Intent intent70 = new Intent(getActivity(), GoldAPIActivity.class);
		startActivity(intent70);
		break;       
	    case R.id.smalilayout1tilButton22:  
		Intent intent60 = new Intent(getActivity(), ExchangeAPIActivity.class);
		startActivity(intent60);
		break;       
	    case R.id.smalilayout1tilButton23:  
		Intent intent173 = new Intent(getActivity(), QueryByCityNameActivity.class);
		startActivity(intent173);
		break;
	    default:  
		break;  
	}  
    }  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view=inflater.inflate(R.layout.smali_layout_1_til, null);
	
	view.findViewById(R.id.smalilayout1tilButton1).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton2).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton3).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton4).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton5).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton6).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton7).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton8).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton9).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton10).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton11).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton12).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton13).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton14).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton15).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton16).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton17).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton18).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton19).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton20).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton21).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton22).setOnClickListener(this);  
	view.findViewById(R.id.smalilayout1tilButton23).setOnClickListener(this);  
	
	return view;
    }
    //newNavigationView.OnNavigationItemSelectedListener
   }
