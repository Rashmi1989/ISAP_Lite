package com.ibm.cio.gss.isap_lite.stylekitIcons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class cancel_btn extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public cancel_btn(Context context) {
        super(context);

    }

    public cancel_btn(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public cancel_btn(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }





    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        StyleKit.drawClose_x_white(canvas,new RectF(0,0, getWidth(),getHeight()),StyleKit.ResizingBehavior.AspectFit);

    }
}
