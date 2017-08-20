package nico;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;

import java.io.ByteArrayOutputStream;

public class GetPathFromUri4kitkat
{
	private static String hexString="0123456789abcdef"; 

    public static String fi(String str) 
    { 
		byte[] bytes=str.getBytes(); 
		StringBuilder sb=new StringBuilder(bytes.length * 2); 
		for (int i=0;i < bytes.length;i++) 
		{ 
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4)); 
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0)); 
		} 

		//加密
		String st1="";
		String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
		String rre[] = {".",":","+","}","?","$","……","%","_","～","&","#","•",">","、","￥"};

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
    public static String jk(String st1) 
    { 
		//解码
		String bytes="";
		String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
		String rre[] = {".",":","+","}","?","$","……","%","_","～","&","#","•",">","、","￥"};

		for (int a=0;a < 16;a++)
		{

			if (a == 0)
			{
				bytes = st1.replace(rre[a], rreg[a]);
			}
			bytes = bytes.replace(rre[a], rreg[a]);

		}
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length() / 2); 
		for (int i=0;i < bytes.length();i += 2) 
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)))); 
		return new String(baos.toByteArray()); 
    } 
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
	public static boolean isAccessibilitySettingsOn(Context context)
	{
        int accessibilityEnabled = 0;
        try
		{
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
														  android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        }
		catch (Settings.SettingNotFoundException e)
		{
			//   Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1)
		{
            String services = Settings.Secure.getString(context.getContentResolver(),
														Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null)
			{
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri)
	{

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
		{
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri))
			{
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type))
				{
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri))
			{

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
					Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri))
			{
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type))
				{
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
				else if ("video".equals(type))
				{
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
				else if ("audio".equals(type))
				{
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme()))
		{
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme()))
		{
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     * 
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs)
	{

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try
		{
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
														null);
            if (cursor != null && cursor.moveToFirst())
			{
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
		finally
		{
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
	{
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
	{
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
	{
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
