package com.example.androiddairynew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class DairyMonthView extends MonthView {

    // 文字paint
//    private Paint mTextPaint = new Paint();
//
//    // 选中日期时的Paint
//    private Paint mSelectPaint = new Paint();
//
//    // 当前日期的cycle paint
//    private Paint mCurrentDayCyclePaint = new Paint();
//
//    // 当前日期的文字Paint
//    private Paint mCurrentDayTextPaint = new Paint();
//
//    private Paint selectedDayPaint = new Paint();
//
//    private Paint lunarDayPaint = new Paint();

    // 所有正常的文字样式
    private Paint mTextPaint = new Paint();

    // 所有正常农历的文字样式
    private Paint mLunarTextPaint = new Paint();

    // 当前日期的文字样式
    private Paint mCurrentTextPaint = new Paint();

    // 当前农历的文字样式
    private Paint mCurrentLunarTextPaint = new Paint();

    // 选中日期的文字样式
    private Paint mSelectTextPaint = new Paint();

    // 选中日期农历的文字样式
    private Paint mSelectLunarPaint = new Paint();

    // 选中日期的圆样式
    private Paint mSelectCyclePaint = new Paint();

    private int mTextSize = 14;

    private int mLunarTextSize = 8;

    public DairyMonthView(Context context) {
        super(context);

        // 文字样式
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(dipToPx(context, mTextSize));

        // 农历文字样式
        mLunarTextPaint.setAntiAlias(true);
        mLunarTextPaint.setStyle(Paint.Style.FILL);
        mLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        mLunarTextPaint.setColor(0xff000000);
        mLunarTextPaint.setTextSize(dipToPx(context, mLunarTextSize));

        // 当前文字样式
        mCurrentTextPaint.setAntiAlias(true);
        mCurrentTextPaint.setStyle(Paint.Style.FILL);
        mCurrentTextPaint.setTextAlign(Paint.Align.CENTER);
        mCurrentTextPaint.setColor(0xff66CDAA);
        mCurrentTextPaint.setTextSize(dipToPx(context, mTextSize));

        // 当前农历文字样式
        mCurrentLunarTextPaint.setAntiAlias(true);
        mCurrentLunarTextPaint.setStyle(Paint.Style.FILL);
        mCurrentLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        mCurrentLunarTextPaint.setTextSize(0xff6CDAA);
        mCurrentLunarTextPaint.setTextSize(dipToPx(context, mLunarTextSize));

        // 选中日期的文字样式
        mSelectTextPaint.setAntiAlias(true);
        mSelectTextPaint.setStyle(Paint.Style.FILL);
        mSelectTextPaint.setTextAlign(Paint.Align.CENTER);
        mSelectTextPaint.setColor(0xff66CDAA);
        mSelectTextPaint.setTextSize(dipToPx(context, mTextSize));

        // 选中日期的农历样式
        mSelectLunarPaint.setAntiAlias(true);
        mSelectLunarPaint.setStyle(Paint.Style.FILL);
        mSelectLunarPaint.setTextAlign(Paint.Align.CENTER);
        mSelectLunarPaint.setColor(0xff66CDAA);
        mSelectLunarPaint.setTextSize(dipToPx(context, mLunarTextSize));

        // 选中日期的cycle样式
        mSelectCyclePaint.setColor(0xffF5FFFA);
    }

    /**
     * 当前日期被选中的样式
     * @param canvas 画布
     * @param calendar 日历对象
     * @param x x坐标
     * @param y y坐标
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        canvas.drawCircle(x+mItemWidth/2, y+mItemHeight/2,mItemWidth>mItemHeight? mItemHeight/2 : mItemWidth/2, mSelectCyclePaint);
        canvas.drawText(String.valueOf(calendar.getDay()), x+mItemWidth/2, y+mItemHeight/2, mSelectTextPaint);
        canvas.drawText(String.valueOf(calendar.getLunar()), x + mItemWidth/2, y+3*mItemHeight/4, mSelectLunarPaint);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    /**
     * 绘制文本
     * @param canvas 画布
     * @param calendar 日历对象
     * @param x x坐标
     * @param y y坐标
     * @param hasScheme 判断事件标记服务
     * @param isSelected 是否被选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        // 当前日期的样式
        if(calendar.isCurrentDay()){
//            canvas.drawCircle(x+mItemWidth/2, y+mItemHeight/2, mItemWidth>mItemHeight? mItemHeight/2 : mItemWidth/2, mCurrentDayCyclePaint);
            canvas.drawText(String.valueOf(calendar.getDay()), x+mItemWidth/2, y+mItemHeight/2, mCurrentTextPaint);
            canvas.drawText(String.valueOf(calendar.getLunar()), x+mItemWidth/2, y+3*mItemHeight/4, mCurrentLunarTextPaint);
        } else if(!isSelected){
            canvas.drawText(String.valueOf(calendar.getDay()), x+mItemWidth/2, y+mItemHeight/2, mTextPaint);
            canvas.drawText(String.valueOf(calendar.getLunar()), x+mItemWidth/2, y+3*mItemHeight/4, mLunarTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
