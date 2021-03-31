package com.io.app.shakomako.utils.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.io.app.shakomako.R;
import com.io.app.shakomako.utils.picker.view.BasePickerView;
import com.io.app.shakomako.utils.picker.view.MyWheelTime;
import com.io.app.shakomako.utils.picker.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePickerView extends BasePickerView implements View.OnClickListener {

    @Override
    public void setCustomFont(@NonNull Typeface typeface) {
        wheelTime.setCustomTypeface(typeface);
    }

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, HOUR_MIN_SEC, HOUR_MIN_APPM
    }

    MyWheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    public TimePickerView(Context context, Type type) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);

        setBtnSubmit((Button)findViewById(R.id.btnSubmit));
        getBtnSubmit().setTag(TAG_SUBMIT);
        setBtnCancel((Button)findViewById(R.id.btnCancel));
        getBtnCancel().setTag(TAG_CANCEL);
        getBtnSubmit().setOnClickListener(this);
        getBtnCancel().setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new MyWheelTime(timepickerview, type);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int ampm = calendar.get(Calendar.AM_PM);
        wheelTime.setPicker(year, month, day, hours, minute, second, ampm);

    }

    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    public void setTime(Date date, Type type) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours;
        if (type == Type.HOUR_MIN_APPM) {
            hours = calendar.get(Calendar.HOUR);
        } else {
            hours = calendar.get(Calendar.HOUR_OF_DAY);
        }
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        Log.v("am_pm", "" + calendar.get(Calendar.AM_PM));
        int ampm = calendar.get(Calendar.AM_PM);
        wheelTime.setPicker(year, month, day, hours, minute, second, ampm);
    }

    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        public void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}