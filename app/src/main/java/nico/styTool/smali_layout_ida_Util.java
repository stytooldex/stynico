package nico.styTool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class smali_layout_ida_Util extends Fragment implements NavigationView.OnNavigationItemSelectedListener,OnClickListener
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
			case R.id.smalilayout3tilTextView1:  
				Intent intent8 = new Intent(getActivity(), MainActivity.class);
				startActivity(intent8);
				break;       
			case R.id.smalilayout3tilTextView2:  
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				AlertDialog alertDialog = builder.setMessage("方法：开启>找到妮媌［二个有一个是！点击］\n下面有个［解除（MIUI）授权25秒］\n一般在右上角开启就可以了").setCancelable(false)
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{

						}
					}).setPositiveButton("开启", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface d, int which)
						{
							Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
							startActivity(intent);
						}
					}).create();
				alertDialog.show();
				break;          
			case R.id.smalilayout3tilTextView3:  
				Intent a = new Intent(getActivity(), sizeActivity.class);
				startActivity(a);
				break;       
			case R.id.smalilayout3tilTextView4:  
				Intent b = new Intent(getActivity(), dump.x.Main.class);
				startActivity(b);
				break;       
			case R.id.smalilayout3tilTextView5:  
				Intent intent1 = new Intent(getActivity(), NativeConfigStore.class);
				intent1.putExtra("#", "Ab");
				startActivity(intent1);
				break;       
			case R.id.smalilayout3tilTextView6:  
				Intent c = new Intent(getActivity(), KILL.class);
				startActivity(c);
				break;       
			case R.id.smalilayout3tilTextView7:  
				Intent i = new Intent(getActivity(), jni_string.class);
				startActivity(i);
				break;       
			case R.id.smalilayout3tilTextView8:  
				Intent w = new Intent(getActivity(), smali_layout_shell.class);
				startActivity(w);
				break;       
			case R.id.smalilayout3tilTextView9:  
				Intent u = new Intent(getActivity(), io_styTool.class);
				startActivity(u);
				break;       
			case R.id.smalilayout3tilTextView10:  

				break;       
			case R.id.smalilayout3tilTextView11:  
				Intent l = new Intent(getActivity(), a.class);
				startActivity(l);
				break; 
			case R.id.smalilayout3tilTextView12:  
				Intent li = new Intent(getActivity(), buff_ext.class);
				startActivity(li);
				break; 
			case R.id.smalilayout3tilTextView13:  
				Intent _li = new Intent(getActivity(), api_o.class);
				startActivity(_li);
				break; 
			default:  
				break;  
		}  
    }  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: Implement this method
        View view=inflater.inflate(R.layout.smali_layout_3_til, null);
		view.findViewById(R.id.smalilayout3tilTextView1).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView2).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView3).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView4).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView5).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView6).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView7).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView8).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView9).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView10).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView11).setOnClickListener(this);  
		view.findViewById(R.id.smalilayout3tilTextView12).setOnClickListener(this);

		view.findViewById(R.id.smalilayout3tilTextView13).setOnClickListener(this);

		return view;
    }}
