package com.io.app.shakomako.utils.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.io.app.shakomako.R;
import com.io.app.shakomako.utils.picker.view.BasePickerView;
import com.io.app.shakomako.utils.picker.view.MyWheelOptions;

import java.util.ArrayList;

public class MyOptionsPickerView<T> extends BasePickerView implements View.OnClickListener {
    MyWheelOptions wheelOptions;
    private TextView tvTitle;
    private OnOptionsSelectListener optionsSelectListener;
    private OnCancelClickListener cancelClickListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public MyOptionsPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pickerview_options, contentContainer);

        setBtnSubmit((Button) findViewById(R.id.btnSubmit));
        getBtnSubmit().setTag(TAG_SUBMIT);
        setBtnCancel((Button) findViewById(R.id.btnCancel));
        getBtnCancel().setTag(TAG_CANCEL);
        getBtnSubmit().setOnClickListener(this);
        getBtnCancel().setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        final View optionspicker = findViewById(R.id.optionspicker);
        wheelOptions = new MyWheelOptions(optionspicker);
    }

    public void setPicker(ArrayList<T> optionsItems) {
        wheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items,
                          ArrayList<T> options3Items,
                          boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    @Override
    public void setCustomFont(@NonNull Typeface typeface) {
        wheelOptions.setCustomTypeface(typeface);
    }


    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0);
    }


    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0);
    }


    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3);
    }


    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null);
    }


    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null);
    }


    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3);
    }


    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            cancelClickListener.onCancel();
            dismiss();
            return;
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            dismiss();
            return;
        }
    }

    public interface OnOptionsSelectListener {
        public void onOptionsSelect(int options1, int option2, int options3);
    }

    public interface OnCancelClickListener {
        void onCancel();
    }

    public void setOnoptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTitleTextSize(float textSize) {
        tvTitle.setTextSize(textSize);
    }

    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }


    public MyWheelOptions getWheelOptions() {
        return wheelOptions;
    }
}

