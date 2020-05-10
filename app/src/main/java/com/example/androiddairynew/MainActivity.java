package com.example.androiddairynew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androiddairynew.activity.DairyActivity;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;


public class MainActivity extends AppCompatActivity implements CalendarView.OnCalendarSelectListener{

    private TextView curretDayTextView;

    private TextView currentYearTextView;

    private TextView currentLunarTextView;

    private CalendarView calendarView;

    private ImageView currentDayImageView;

    private ImageView monthImageView;

    private ImageView mbackgroudLayout;

    private ImageView msetupImageview;

    private static final long DOUBLE_TIME = 300;
    private static long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curretDayTextView = findViewById(R.id.currentDayTextView);
        currentYearTextView = findViewById(R.id.currentYearTextView);
        currentLunarTextView = findViewById(R.id.currentLunarTextView);
        calendarView = findViewById(R.id.calendarView);

        // 返回“今天”
        currentDayImageView = findViewById(R.id.currentDayImageView);
        currentDayImageView.setOnClickListener((v)->{
            calendarView.scrollToCurrent();
        });

        // 展开年视图
        monthImageView = findViewById(R.id.monthImageView);
        monthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = calendarView.getCurYear();
                calendarView.showYearSelectLayout(currentYear);
            }
        });

        // 设置
        msetupImageview = findViewById(R.id.setup);
        msetupImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        calendarView.setOnCalendarSelectListener(this);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    /**
     * 点击日历事件
     *
     * @param calendar 日历
     * @param isClick 点击事件
     */
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            String currentDayStr = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
            Intent intent = new Intent(MainActivity.this, DairyActivity.class);
            intent.putExtra("currentDayStr", currentDayStr);
            startActivity(intent);
        } else{
            curretDayTextView.setText(String.valueOf(calendar.getMonth())+"月" + String.valueOf(calendar.getDay()) + "日");
            currentYearTextView.setText(String.valueOf(calendar.getYear())+"年");
            currentLunarTextView.setText(String.valueOf(calendar.getLunar()));
        }
        lastClickTime = currentTimeMillis;
    }
}
