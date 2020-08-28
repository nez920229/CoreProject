package com.boco.app.core.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeTextView extends AppCompatTextView {
    public MarqueeTextView(Context context) {
        this(context,null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setClickable(false);
    }

    @Override
    public boolean isFocused() {
        return true;
    }


}