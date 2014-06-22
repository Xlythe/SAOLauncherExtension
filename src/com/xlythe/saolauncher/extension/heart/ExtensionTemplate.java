package com.xlythe.saolauncher.extension.heart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xlythe.saolauncher.extension.heart.extension.ExtensionInterface;
import com.xlythe.saolauncher.extension.heart.theme.Theme;
import com.xlythe.saolauncher.extension.heart.theme.ThemedView;
import com.xlythe.saolauncher.extension.heart.theme.UIUtil;

public abstract class ExtensionTemplate implements ExtensionInterface {
    private ThemedView mArrow;
    private View mLayout;
    private LinearLayout.LayoutParams mArrowParams;
    private LinearLayout.LayoutParams mLayoutParams;
    private Context mContext;
    private String mSide;
    private Typeface mTypeface;

    @Override
    public void setTheme(String packageName, Typeface typeface) {
        Theme.buildResourceMap(com.xlythe.saolauncher.extension.heart.R.class);
        Theme.setPackageName(packageName);
        mTypeface = typeface;
    }

    @Override
    public void setSide(String side) {
        mSide = side;
    }

    @Override
    public View getView(final Context context) {
        mContext = context;
        Theme.setFont(mContext, mTypeface);

        // Wrap in a frame so we have full control over our layout
        ViewGroup frame = new LinearLayout(context);
        View.inflate(context, R.layout.main, frame);

        // Create the arrow
        frame.addView(createArrow(), 0);

        // We don't want to dismiss the app on a silly miss-tap
        mLayout = frame.findViewById(R.id.card);
        mLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ViewGroup top = (ViewGroup) mLayout.findViewById(R.id.top);
        top.addView(inflateView(mContext));

        // Return our view
        if(mSide.equals("Right")) {
            UIUtil.reverseLayout(frame);
            UIUtil.flipBackground(mArrow);
        }
        return frame;
    }

    protected abstract View inflateView(Context context);

    @Override
    public void setButtonPos(int[] pos) {
        // Measure the view
        if(mLayout.getHeight() == 0) {
            mLayout.measure(MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST));
            layoutAllViews(mLayout, 0, 0, mLayout.getMeasuredWidth(), mLayout.getMeasuredHeight());
        }

        // Adjust arrow position
        int distance = pos[1];
        distance -= getArrowSize() / 2;
        mArrowParams = (LinearLayout.LayoutParams) mArrow.getLayoutParams();
        mArrowParams.topMargin = distance;

        // Adjust the card to align with the arrow
        int layoutHeight = mLayout.getHeight();
        int windowHeight = UIUtil.getWindowHeight(mContext);
        mLayoutParams = ((LinearLayout.LayoutParams) mLayout.getLayoutParams());
        if(distance + layoutHeight > windowHeight) {
            mLayoutParams.topMargin = windowHeight - layoutHeight;
        }
        else {
            mLayoutParams.topMargin = distance;
        }
    }

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    private View createArrow() {
        mArrow = new ThemedView(mContext);
        mArrow.setLayoutParams(new LinearLayout.LayoutParams(getArrowSize(), getArrowSize()));
        mArrow.setBackground(Theme.get(R.drawable.triangle_off));
        return mArrow;
    }

    private int getDimensionPixelSize(int res) {
        return mContext.getResources().getDimensionPixelSize(res);
    }

    private int getArrowSize() {
        return getDimensionPixelSize(R.dimen.arrow_size);
    }

    private void layoutAllViews(View v, int l, int t, int r, int b) {
        v.layout(l, t, r, b);
        if(ViewGroup.class.isAssignableFrom(v.getClass())) {
            ViewGroup fl = (ViewGroup) v;
            for(int i = 0; i < fl.getChildCount(); i++) {
                View child = fl.getChildAt(i);
                layoutAllViews(child, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            }
        }
    }
}
