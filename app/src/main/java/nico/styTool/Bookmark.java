package nico.styTool;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Bookmark
{
    /* 
     * 16进制数字字符集 
     */ 
    private static String hexString="0123456789abcdef"; 
    /* 
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */ 
    public static String jiami(String str) 
    { 
//根据默认编码获取字节数组 
	byte[] bytes=str.getBytes(); 
	StringBuilder sb=new StringBuilder(bytes.length * 2); 
//将字节数组中每个字节拆解成2位16进制整数 
	for (int i=0;i < bytes.length;i++) 
	{ 
	    sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4)); 
	    sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0)); 
	} 

	//加密
	String st1="";
	String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
	String rre[] = {"{","}","(",")️",";",",","-",".","_","=","|","&","•","!","[","<"};

	for (int a=0;a < 16;a++)
	{

	    if (a == 0)
	    {
		st1 = sb.toString().replace(rreg[a], rre[a]);
	    }
	    st1 = st1.replace(rreg[a], rre[a]);



	}

	return st1; 
    } 
    /* 
     * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
     */ 
    public static String jiemi(String st1) 
    { 
	//解码
	String bytes="";
	String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
	String rre[] = {"{","}","(",")️",";",",","-",".","_","=","|","&","•","!","[","<"};

	for (int a=0;a < 16;a++)
	{

	    if (a == 0)
	    {
		bytes = st1.replace(rre[a], rreg[a]);
	    }
	    bytes = bytes.replace(rre[a], rreg[a]);

	}
	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length() / 2); 
//将每2位16进制整数组装成一个字节 
	for (int i=0;i < bytes.length();i += 2) 
	    baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)))); 
	return new String(baos.toByteArray()); 
    } 


    private final String DBNAME="MyBookmarkDB";
    private final String TABLE_NAME="mybookmark";

    private SQLiteDatabase m_db=null;
    private ArrayList<HashMap<String,String>> m_list=null;

    public void initDB(Activity activity)
    {
	this.openMyBookmark(activity);
	try
	{
	    m_db.execSQL("CREATE TABLE mybookmark(Title TEXT,Url TEXT)");
	}
	catch (SQLException  e)
	{
	    Log.e("BOOKMARK", e.getMessage());
	}
	this.closeMyBookmark();

	m_list = new ArrayList<HashMap<String,String>>();
    }

    public void insert(final String Title, final String Url)
    {
	String sql="INSERT INTO " + TABLE_NAME + " values (\"" + Title + "\",\"" + Url + "\")";
	try
	{
	    m_db.execSQL(sql);
	}
	catch (SQLException  e)
	{
	    Log.e("BOOKMARK", e.getMessage());
	}
    }

    public ArrayList<HashMap<String,String>> getList()
    {
	m_list.clear();
	Cursor cur = m_db.rawQuery("select * from " + TABLE_NAME, null);
	if (cur != null)
	{
	    if (cur.moveToFirst())
	    {
		do
		{
		    HashMap<String,String> map=new HashMap<String,String>();
		    map.put("Title", cur.getString(0));
		    map.put("Url", cur.getString(1));
		    m_list.add(map);

		}while(cur.moveToNext());
	    }
	}
	return m_list;
    }

    public void openMyBookmark(Activity activity)
    {
	try
	{
	    m_db = activity.openOrCreateDatabase(DBNAME, 0, null);
	}
	catch (SQLiteException e)
	{
	    m_db = null;
	}
    }

    public void closeMyBookmark()
    {
	m_db.close();
    }

}
