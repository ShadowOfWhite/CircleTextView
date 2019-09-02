package com.shadow.circletextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 写着玩的，正常应该继承TextView
 */
public class CircleTextView1 extends View {


    private int mTextColor = Color.RED;
    private int mCircleClor = Color.BLUE;
    private String mTextValue = "";
    private float mTextSize = 12;

    private final Paint mTextPaint;
    private final Paint mCirclePaint;

    private int mRadius;
    private static final String TAG = "CircleTextView";

    public CircleTextView1(Context context) {


        super(context);
        Log.i(TAG, "CircleTextView: 构造方法1");
        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleClor);
    }

    public CircleTextView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "CircleTextView: 构造方法2");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView1);

        mTextColor = typedArray.getColor(R.styleable.CircleTextView1_text_color, Color.GREEN);
        mTextValue = typedArray.getString(R.styleable.CircleTextView1_text);
        mTextSize = typedArray.getDimension(R.styleable.CircleTextView1_text_size,12);
        mCircleClor = typedArray.getColor(R.styleable.CircleTextView1_background_Color,Color.YELLOW);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleClor);

        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST){
            float textWidth = mTextPaint.measureText(mTextValue);//另一种方式获取宽度
            widthSize = (int) textWidth;
            widthSize+=60;//加上一点padding
        }

        int max = Math.max(widthSize, heightSize);
        mRadius = max /2;
        setMeasuredDimension(max,max);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.drawCircle(mRadius,mRadius,mRadius,mCirclePaint);
        canvas.drawLine(0,mRadius,mRadius*2,mRadius,mTextPaint);

        if (mTextValue != null && !mTextValue.isEmpty()){
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(mTextValue,0,mTextValue.length(),rect);


            int width = rect.width();
            int height = rect.height();
            Log.i(TAG, "onDraw: ****"+width+","+height);
            float x = (mRadius*2 - width) /2;

            canvas.save();
            canvas.translate(x,mRadius);
            mTextPaint.setColor(Color.RED);
            canvas.drawRect(rect,mTextPaint);
            canvas.restore();
            mTextPaint.setColor(Color.WHITE);

            canvas.drawText(mTextValue,mRadius,mRadius,mTextPaint);


            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float ascent = fontMetrics.ascent;

            mTextPaint.setColor(Color.BLACK);
            canvas.drawLine(0,mRadius+ascent,mRadius*2,mRadius+ascent,mTextPaint);

            float descent = fontMetrics.descent;
            mTextPaint.setColor(Color.GRAY);
            canvas.drawLine(0,mRadius+descent,mRadius*2,mRadius+descent,mTextPaint);
            float top = fontMetrics.top;
            mTextPaint.setColor(Color.RED);
            canvas.drawLine(0,mRadius+top,mRadius*2,mRadius+top,mTextPaint);
            float bottom = fontMetrics.bottom;
            mTextPaint.setColor(Color.GREEN);
            canvas.drawLine(0,mRadius+bottom,mRadius*2,mRadius+bottom,mTextPaint);
            Log.i(TAG, "onDraw: "+ascent+","+descent+","+top+","+bottom);



        }





    }
}
