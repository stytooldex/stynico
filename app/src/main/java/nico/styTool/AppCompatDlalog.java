package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.GetServerTimeListener;
public class AppCompatDlalog extends AccessibilityService
{
    private final static String MM_PNAME = "com.tencent.mm";
    boolean hasAction = false;
    boolean locked = false;
    boolean background = false;
    private String name;
    private String scontent;
    AccessibilityNodeInfo itemNodeinfo;
    private KeyguardManager.KeyguardLock kl;
    private Handler handler = new Handler();
    private static final int NOTIFICATION_ID = 1;

    /**
     * 必须重写的方法，响应各种事件。
     * @param event
     */
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event)
    {
	Bmob.getServerTime(this, new GetServerTimeListener() {

		@Override
		public void onSuccess(long time) {
		    // TODO Auto-generated method stub
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String times = formatter.format(new Date(time * 1000L));
		    //toast("当前服务器时间为:" + times);
		}

		@Override
		public void onFailure(int code, String msg) {
		}
	    });
	SharedPreferences sharedPreferences = getSharedPreferences("nico.styTool_preferences", MODE_PRIVATE); 
	boolean isFirstRun = sharedPreferences.getBoolean("ok_c", true); 
	//Editor editor = sharedPreferences.edit(); 
	if (isFirstRun) 
	{ 
	    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
	    builder.setSmallIcon(R.mipmap.ic_launcher);
	    builder.setContentTitle("妮媌");
	    builder.setContentText("微信自动回复正在运行");
	    builder.setOngoing(true);
	    Notification notification = builder.build();
	    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    manager.notify(NOTIFICATION_ID, notification);
	}
	else 
	{ 

	}
	

        int eventType = event.getEventType();
      //  android.util.Log.d("maptrix", "get event = " + eventType);
        switch (eventType)
	{
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
           //     android.util.Log.d("maptrix", "get notification event");
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty())
		{
                    for (CharSequence text : texts)
		    {
                        String content = text.toString();
                        if (!TextUtils.isEmpty(content))
			{
                            if (isScreenLocked())
			    {
                                locked = true;
				SharedPreferences haredPreferences = getSharedPreferences("nico.styTool_preferences", MODE_PRIVATE); 
				boolean sFirstRun = haredPreferences.getBoolean("ok_a", true); 
				//Editor editor = sharedPreferences.edit(); 
				if (sFirstRun) 
				{ 
				    wakeAndUnlock();
				}
				else 
				{ 

				}
                  //              android.util.Log.d("maptrix", "the screen is locked");
                                if (isAppForeground(MM_PNAME))
				{
                                    background = false;
                 //                   android.util.Log.d("maptrix", "is mm in foreground");
                                    sendNotifacationReply(event);
                                    handler.postDelayed(new Runnable() {
					    @Override
					    public void run()
					    {
						sendNotifacationReply(event);
						if (fill())
						{
						    send();
						}
					    }
					}, 1000);
                                }
				else
				{
                                    background = true;
               //                     android.util.Log.d("maptrix", "is mm in background");
                                    sendNotifacationReply(event);
                                }
                            }
			    else
			    {
                                locked = false;
            //                    android.util.Log.d("maptrix", "the screen is unlocked");
                                // 监听到微信红包的notification，打开通知
                                if (isAppForeground(MM_PNAME))
				{
                                    background = false;
              //                      android.util.Log.d("maptrix", "is mm in foreground");
                                    sendNotifacationReply(event);
                                    handler.postDelayed(new Runnable() {
					    @Override
					    public void run()
					    {
						if (fill())
						{
						    send();
						}
					    }
					}, 1000);
                                }
				else
				{
                                    background = true;
           //                         android.util.Log.d("maptrix", "is mm in background");
                                    sendNotifacationReply(event);
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
       //         android.util.Log.d("maptrix", "get type window down event");
                if (!hasAction) break;
                itemNodeinfo = null;
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI"))
		{
                    if (fill())
		    {
                        send();
                    }
		    else
		    {
                        if (itemNodeinfo != null)
			{
                            itemNodeinfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            handler.postDelayed(new Runnable() {
				    @Override
				    public void run()
				    {
					if (fill())
					{
					    send();
					}
					back2Home();
					release();
					hasAction = false;
				    }
				}, 1000);
                            break;
                        }
                    }
                }

                //bring2Front();
                back2Home();
                release();
                hasAction = false;
                break;
        }
    }

    /**
     * 寻找窗体中的“发送”按钮，并且点击。
     */
    @SuppressLint("NewApi")
    private void send()
    {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null)
	{
            List<AccessibilityNodeInfo> list = nodeInfo
		.findAccessibilityNodeInfosByText("发送");
            if (list != null && list.size() > 0)
	    {
                for (AccessibilityNodeInfo n : list)
		{
                    n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }

            }
	    else
	    {
                List<AccessibilityNodeInfo> liste = nodeInfo
		    .findAccessibilityNodeInfosByText("Send");
                if (liste != null && liste.size() > 0)
		{
                    for (AccessibilityNodeInfo n : liste)
		    {
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
            pressBackButton();
        }

    }

    /**
     * 模拟back按键
     */
    private void pressBackButton()
    {
        Runtime runtime = Runtime.getRuntime();
        try
	{
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        }
	catch (IOException e)
	{
            e.printStackTrace();
        }
    }

    /**
     *
     * @param event
     */
    private void sendNotifacationReply(AccessibilityEvent event)
    {
        hasAction = true;
        if (event.getParcelableData() != null
	    && event.getParcelableData() instanceof Notification)
	{
            Notification notification = (Notification) event
		.getParcelableData();
            String content = notification.tickerText.toString();
            String[] cc = content.split(":");
            name = cc[0].trim();
            scontent = cc[1].trim();

         //   android.util.Log.i("maptrix", "sender name =" + name);
       //     android.util.Log.i("maptrix", "sender content =" + scontent);


            PendingIntent pendingIntent = notification.contentIntent;
            try
	    {
                pendingIntent.send();
            }
	    catch (PendingIntent.CanceledException e)
	    {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    private boolean fill()
    {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null)
	{
	    SharedPreferences sharedPreferences = getSharedPreferences("hook_Cosplay", AccessibilityService.MODE_PRIVATE); 
	    String htmly = sharedPreferences.getString("io_kii", "");
            return findEditText(rootNode, htmly);
        }
        return false;
    }


    private boolean findEditText(AccessibilityNodeInfo rootNode, String content)
    {
        int count = rootNode.getChildCount();

     //   android.util.Log.d("maptrix", "root class=" + rootNode.getClassName() + "," + rootNode.getText() + "," + count);
        for (int i = 0; i < count; i++)
	{
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null)
	    {
     //           android.util.Log.d("maptrix", "nodeinfo = null");
                continue;
            }

      //      android.util.Log.d("maptrix", "class=" + nodeInfo.getClassName());
    //        android.util.Log.e("maptrix", "ds=" + nodeInfo.getContentDescription());
            if (nodeInfo.getContentDescription() != null)
	    {
                int nindex = nodeInfo.getContentDescription().toString().indexOf(name);
                int cindex = nodeInfo.getContentDescription().toString().indexOf(scontent);
      //          android.util.Log.e("maptrix", "nindex=" + nindex + " cindex=" + cindex);
                if (nindex != -1)
		{
                    itemNodeinfo = nodeInfo;
                   // android.util.Log.i("maptrix", "find node info");
                }
            }
            if ("android.widget.EditText".equals(nodeInfo.getClassName()))
	    {
     //           android.util.Log.i("maptrix", "==================");
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
				 AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
				     true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
				       arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", content);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }

            if (findEditText(nodeInfo, content))
	    {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onInterrupt()
    {

    }

    /**
     * 判断指定的应用是否在前台运行
     *
     * @param packageName
     * @return
     */
    private boolean isAppForeground(String packageName)
    {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(packageName);

    }


    /**
     * 将当前应用运行到前台
     */
    private void bring2Front()
    {
        ActivityManager activtyManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activtyManager.getRunningTasks(3);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos)
	{
            if (this.getPackageName().equals(runningTaskInfo.topActivity.getPackageName()))
	    {
                activtyManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                return;
            }
        }
    }

    /**
     * 回到系统桌面
     */
    private void back2Home()
    {
        Intent home = new Intent(Intent.ACTION_MAIN);

        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);

        startActivity(home);
    }


    /**
     * 系统是否在锁屏状态
     *
     * @return
     */
    private boolean isScreenLocked()
    {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    private void wakeAndUnlock()
    {
        //获取电源管理器对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

        //点亮屏幕
        wl.acquire(1000);

        //得到键盘锁管理器对象
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");

        //解锁
        kl.disableKeyguard();

    }

    private void release()
    {

        if (locked && kl != null)
	{
            //android.util.Log.d("maptrix", "release the lock");
            //得到键盘锁管理器对象
            kl.reenableKeyguard();
            locked = false;
        }
    }
}
