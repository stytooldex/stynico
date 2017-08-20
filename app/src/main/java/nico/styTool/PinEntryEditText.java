package nico.styTool;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by macrobull on 12/28/15.
 * 通知监听服务
 */
//NLService
public class PinEntryEditText extends NotificationListenerService
{

    static private boolean mBinding = false;    //绑定状态
    static private boolean mInGame = false;    //发现红包

    static private PowerManager powerMan;
    static private PowerManager.WakeLock wakeLock;
    static private KeyguardManager keyMan;
    static private KeyguardManager.KeyguardLock keyLock;

    static public boolean getBindStatus()
    {
	return mBinding;
    }

    /**
     * 是否在通知中发现红包
     *
     * @return
     */
    static public boolean catchTheGame()
    {
	boolean ret = mInGame;
	mInGame = false; // 仅用一次, 清除标志
	return ret;
    }

    /**
     * 恢复屏幕关闭和锁屏
     */
    static public void releaseLock()
    {
	//Log.d("wakelock", String.valueOf(wakeLock.isHeld()));
	if (wakeLock.isHeld()) wakeLock.release(); //解除屏幕常亮
	keyLock.reenableKeyguard();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
	IBinder mIBinder = super.onBind(intent);
	mBinding = true;

	powerMan = (PowerManager) getSystemService(POWER_SERVICE);
	wakeLock = powerMan.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "WakeLock");
	keyMan = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
	keyLock = keyMan.newKeyguardLock("KeyLock");

	// #TODO 换掉这些deprecated方法,

	return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
	boolean mOnUnbind = super.onUnbind(intent);
	mBinding = false;
	return mOnUnbind;
    }

    /**
     * 通知接收事件
     *
     * @param sbn StatusBarNotification
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
	if (!(sbn.getPackageName().equals(getResources().getString(R.string.target_pname))|| sbn.getPackageName().equals(getResources().getString(R.string.target_pname_parallel))))
	    return; // 过滤应用: 微信和双开/w\

	Notification notification = sbn.getNotification();
	String text;
	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
	{
	    text = notification.extras.getString("android.text"); // 全文本更靠谱
	}
	else
	{
	    text = notification.tickerText.toString(); // API 19- 使用tickerText
	}

	if (text == null) return;
	//Log.d("text", text);

	if (!text.matches(getResources().getString(R.string.notify_pattern))) return; // 过滤关键词

	//Log.d("contentIntent", notification.contentIntent.toString());
	try
	{
	    sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)); // 收起通知, 防止一些手机上展开通知妨碍操作的问题
	    //Log.d("wakelock", String.valueOf(wakeLock.isHeld()));
	    // #FIXME bug??? 锁屏点击通知竟然进入聊天列表界面...狗带, 于是先解锁, 要增加PM和KM
	    if (!wakeLock.isHeld())
	    { // 用wakelock判定屏幕是点亮还是锁屏
		keyLock.disableKeyguard(); // 解除锁屏
		wakeLock.acquire(); // 点亮屏幕
		try
		{ // 等待解锁屏幕
		    while (!powerMan.isScreenOn())
		    { // 好像能用
			//Log.d("keyguard", String.valueOf(keyMan.inKeyguardRestrictedInputMode())); // #FIXME 并不能反映是否已解锁
			//Log.d("keyguard", String.valueOf(powerMan.isScreenOn())); // #FIXME 并不能反映是否已解锁
//						Log.d("keyguard", String.valueOf(powerMan.isInteractive())); // isScreenOn是deprecated, 但是isInteractive是API20+...
			//Log.d("keyguard", "locked");
			Thread.sleep(250); // 极糟糕的workaround
		    }
		}
		catch (Exception e)
		{
		    //
		}
	    }
	    mInGame = true; // 标记:有红包
	    notification.contentIntent.send(this, 0, new Intent()); // 点击通知
	}
	catch (PendingIntent.CanceledException e)
	{
	    //Log.w("pendingIntent", "Sending pendingIntent failed.");
	}
    }

    /**
     * 通知移除事件, 应Android Lint指示, API21- 必需重载
     *
     * @param sbn StatusBarNotification
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn)
    {
	//Log.d("onNotificationRemoved", "!");
    }
}

