package nico.styTool;

import android.app.Application;
import android.content.Context;

/**
 * Created by apple on 15/9/23.
 */
public class Information extends Application
{
    private String Nickname;
    private String RoomId;
    private String Dis;
    private String Dyis;

	private Context mContext;

	private Context context;
    public String getis()
	{
        return Dyis;
    }

    public void setis(String tdis)
	{
        Dyis = tdis;
    }
    public String getDis()
	{
        return Dis;
    }

    public void setDis(String dis)
	{
        Dis = dis;
    }

    public String getNickname()
	{
        return Nickname;
    }

    public void setNickname(String nickname)
	{
        Nickname = nickname;
    }

    public String getRoomId()
	{
        return RoomId;
    }

    public void setRoomId(String roomId)
	{
        RoomId = roomId;
    }
	/*
	public Information(Context context, List<Comment> list)
    {
        this.mContext = context;
    }
	*/
	public void Overwrite()
	{
	}
    public void onCreate()
	{/*
		if (nico.SPUtils.get(context, "theme", "dayTheme").equals("dayTheme"))
		{
			//默認是白天主題
			setTheme(R.style.dayTheme);
		}
		else
		{
			//否则是晚上主題
			setTheme(R.style.nightTheme);
		}*/
        super.onCreate();
        setRoomId("未能获取");
        setNickname("未能获取");

    }

}
