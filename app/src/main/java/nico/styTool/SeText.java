package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class SeText extends AccessibilityService implements Runnable {
    Thread th;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO 自动生成的方法存根
        if (null == event) return;
            QQpackge(event);
        
    }

    private void QQpackge(AccessibilityEvent event) {
        if ("com.tencent.mobileqq.activity.VisitorsActivity".equals(event.getClassName().toString())) {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null) {
                List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText("赞");
                for (int i = 0; i < nodes.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        nodes.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        // TODO 自动生成的方法存根

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        //接收按键事件
        return super.onKeyEvent(event);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //连接服务后,一般是在授权成功后会接收到
    }

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        try {
            Thread.sleep(2000);
            QQpackge(null);
        } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


}
