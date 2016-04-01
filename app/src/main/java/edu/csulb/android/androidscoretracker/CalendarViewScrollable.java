package edu.csulb.android.androidscoretracker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.CalendarView;

public class CalendarViewScrollable extends CalendarView{

    public int day = 1;
    public int month = 1;
    public int year = 1980;
    public boolean updated = false;

    public CalendarViewScrollable(Context context) {
        super(context);
        this.setOnDateChangeListener(this.initOnDateChangeListener());
    }

    public CalendarViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnDateChangeListener(this.initOnDateChangeListener());
    }

    public CalendarViewScrollable(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        this.setOnDateChangeListener(this.initOnDateChangeListener());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            ViewParent p = getParent();
            if (p != null)
                p.requestDisallowInterceptTouchEvent(true);
        }

        return false;
    }

    private OnDateChangeListener initOnDateChangeListener() {
        return new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int newYear, int newMonth,
                                            int dayOfMonth) {
                day = dayOfMonth;
                year = newYear;
                month = newMonth;
                updated = true;

                Log.d("debug", "CHANGE LISTENER: " + day + "/" + month + "/" + year);

            }
        };
    }



}
