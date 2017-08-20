package nico;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by ZhuMing on 2017/7/12.
 * 自定义 填充屏幕的VideoView
 */
public class SampleApplication extends VideoView {
    public SampleApplication(Context context) {
        super(context);
    }

    public SampleApplication(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SampleApplication(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        setMeasuredDimension(width, height);
    }
}
