package au.com.iag.incidenttracker.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MenuLinearLayout extends LinearLayout {
    public MenuLinearLayout(Context context) {
        super(context);
    }

    public MenuLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}