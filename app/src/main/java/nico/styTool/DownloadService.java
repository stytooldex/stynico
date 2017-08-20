package nico.styTool;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service
{
	static final int Flag_Init = 0; // 初始状态
	static final int Flag_Down = 1; // 下载状态
	static final int Flag_Pause = 2; // 暂停状态
	static final int Flag_Done = 3; // 完成状态
	String url;//下载地址
	String filetype;//文件类型
	private int progress = 0;// 下载进度
	public int getProgress()
	{
		return progress;
	}
	private int flag;// 下载状态标志
	public int getFlag()
	{
		return flag;
	}
	DownThread mThread;
	Downloader downloader;
	private static DownloadService instance;
	public static DownloadService getInstance()
	{
		return instance;
	}
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		//Log.i("xuxu", "service.........onCreate");
		instance = this;
		flag = Flag_Init;
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		String msg = intent.getExtras().getString("flag");//通过标识判断下载状态
		url = intent.getExtras().getString("url");
		filetype = intent.getExtras().getString("filetype");
		if (mThread == null)
		{
			mThread = new DownThread();
		}
		if (downloader == null)
		{
			downloader = new Downloader(this, url, filetype);
		}
		downloader.setDownloadListener(downListener);

		if (msg.equals("start"))
		{
			startDownload();
		} 
		else if (msg.equals("pause")) 
		{
			downloader.pause();
		}
		else if (msg.equals("resume")) 
		{
			downloader.resume();
		} 
		else if (msg.equals("stop"))
		{
			downloader.cancel();
			stopSelf();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	private void startDownload()
	{
		if (flag == Flag_Init || flag == Flag_Pause)
		{
			if (mThread != null && !mThread.isAlive())
			{
				mThread = new DownThread();
			}
			mThread.start();
		}
	}

	@Override
	public void onDestroy()
	{
		//Log.e("xuxu", "service...........onDestroy");
		try
		{
			flag = 0;
			mThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		mThread = null;
		super.onDestroy();
	}

	class DownThread extends Thread
	{

		@Override
		public void run()
		{

			if (flag == Flag_Init || flag == Flag_Done)
			{
				flag = Flag_Down;
			}

			downloader.start();
		}
	}

	/**
	 * 下载监听
	 */
	private DownloadListener downListener = new DownloadListener() {

		int fileSize;
		Intent intent = new Intent();

		@Override
		public void onSuccess(File file)
		{
			intent.setAction(nico.SimpleActivity.ACTION_DOWNLOAD_SUCCESS);
			intent.putExtra("progress", 100);
			intent.putExtra("file", file);
			sendBroadcast(intent);
		}

		@Override
		public void onStart(int fileByteSize)
		{
			fileSize = fileByteSize;
			flag = Flag_Down;
		}

		@Override
		public void onResume()
		{
			flag = Flag_Down;
		}

		@Override
		public void onProgress(int receivedBytes)
		{
			if (flag == Flag_Down)
			{
				progress = (int)((receivedBytes / (float)fileSize) * 100);
				intent.setAction(nico.SimpleActivity.ACTION_DOWNLOAD_PROGRESS);
				intent.putExtra("progress", progress);
				sendBroadcast(intent);

				if (progress == 100)
				{
					flag = Flag_Done;
				}
			}
		}

		@Override
		public void onPause()
		{
			flag = Flag_Pause;
		}

		@Override
		public void onFail()
		{
			intent.setAction(nico.SimpleActivity.ACTION_DOWNLOAD_FAIL);
			sendBroadcast(intent);
			flag = Flag_Init;
		}

		@Override
		public void onCancel()
		{
			progress = 0;
			flag = Flag_Init;
		}
	};
}
