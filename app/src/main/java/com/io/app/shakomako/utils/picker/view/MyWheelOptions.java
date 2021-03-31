package com.io.app.shakomako.utils.picker.view;

import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;

import com.io.app.shakomako.R;
import com.io.app.shakomako.utils.picker.adapter.ArrayWheelAdapter;
import com.io.app.shakomako.utils.picker.lib.WheelView;
import com.io.app.shakomako.utils.picker.listener.OnItemSelectedListener;

import java.util.ArrayList;

public class MyWheelOptions<T> {
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;

    private ArrayList<T> mOptions1Items;
    private ArrayList<T> mOptions2Items;
    private ArrayList<T> mOptions3Items;

    private boolean linkage = false;
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;

    private OnItemSelectedListener option1SelectedListener;
    private OnItemSelectedListener option2SelectedListener;
    private OnItemSelectedListener option3SelectedListener;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public MyWheelOptions(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setPicker(ArrayList<T> optionsItems) {
        setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items, boolean linkage) {
        setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<T> options1Items,
                          ArrayList<T> options2Items,
                          ArrayList<T> options3Items,
                          boolean linkage) {
        this.linkage = linkage;
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        int len = ArrayWheelAdapter.DEFAULT_LENGTH;

        wv_option1 = (WheelView) view.findViewById(R.id.options1);
        wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items, mOptions1Items.size()));
        wv_option1.setCurrentItem(0);

        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        if (mOptions2Items != null)
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items, mOptions2Items.size()));
        wv_option2.setCurrentItem(wv_option1.getCurrentItem());

        wv_option3 = (WheelView) view.findViewById(R.id.options3);
        if (mOptions3Items != null)
            wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items, mOptions3Items.size()));
        wv_option3.setCurrentItem(wv_option3.getCurrentItem());

        int textSize = 25;

        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
        wv_option3.setTextSize(textSize);

        if (this.mOptions2Items == null)
            wv_option2.setVisibility(View.GONE);
        if (this.mOptions3Items == null)
            wv_option3.setVisibility(View.GONE);

        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt2Select = 0;
                if (mOptions2Items != null) {
                    opt2Select = wv_option2.getCurrentItem();
                    opt2Select = opt2Select >= mOptions2Items.size() - 1 ? mOptions2Items.size() - 1 : opt2Select;

                    wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items));
                    wv_option2.setCurrentItem(opt2Select);
                }
                if (mOptions3Items != null) {
                    wheelListener_option2.onItemSelected(opt2Select);
                }
                if(option1SelectedListener != null){
                    option1SelectedListener.onItemSelected(index);
                }
            }
        };
        wheelListener_option2 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if (mOptions3Items != null) {
                    int opt1Select = wv_option1.getCurrentItem();
                   /* opt1Select = opt1Select >= mOptions3Items.size() - 1 ? mOptions3Items.size() - 1 : opt1Select;
                    index = index >= mOptions2Items.get(opt1Select).size() - 1 ? mOptions2Items.get(opt1Select).size() - 1 : index;
                    int opt3 = wv_option3.getCurrentItem();

                    opt3 = opt3 >= mOptions3Items.get(opt1Select).get(index).size() - 1 ? mOptions3Items.get(opt1Select).get(index).size() - 1 : opt3;
*/
                    wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items));
                    wv_option3.setCurrentItem(index);

                    if(option2SelectedListener != null){
                        option2SelectedListener.onItemSelected(index);
                    }
                    if(option3SelectedListener != null){
                        option3SelectedListener.onItemSelected(index);
                    }
                }
            }
        };


        if (options2Items != null && linkage)
            wv_option1.setOnItemSelectedListener(wheelListener_option1);
        else {
            wv_option1.setOnItemSelectedListener(option1SelectedListener);
            if(options2Items != null)
                wv_option2.setOnItemSelectedListener(option2SelectedListener);
        }
        if (options3Items != null && linkage)
            wv_option2.setOnItemSelectedListener(wheelListener_option2);
        else if(options3Items != null)
            wv_option3.setOnItemSelectedListener(option3SelectedListener);
    }

    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null)
            wv_option1.setLabel(label1);
        if (label2 != null)
            wv_option2.setLabel(label2);
        if (label3 != null)
            wv_option3.setLabel(label3);
    }

    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
        wv_option3.setCyclic(cyclic3);
    }

    public void setOption2Cyclic(boolean cyclic) {
        wv_option2.setCyclic(cyclic);
    }


    public void setOption3Cyclic(boolean cyclic) {
        wv_option3.setCyclic(cyclic);
    }


    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wv_option1.getCurrentItem();
        currentItems[1] = wv_option2.getCurrentItem();
        currentItems[2] = wv_option3.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        }
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
    }

    public void setOption1SelectedListener(OnItemSelectedListener option1SelectedListener) {
        this.option1SelectedListener = option1SelectedListener;
    }

    public void setOption2SelectedListener(OnItemSelectedListener option2SelectedListener) {
        this.option2SelectedListener = option2SelectedListener;
    }

    public void setOption3SelectedListener(OnItemSelectedListener option3SelectedListener) {
        this.option3SelectedListener = option3SelectedListener;
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items));
            wv_option2.setCurrentItem(opt2Select);
        }
        if (mOptions3Items != null) {
            wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items));
            wv_option3.setCurrentItem(opt3Select);
        }
    }

    public void setCustomTypeface(@NonNull Typeface typeface) {
        wv_option1.setFont(typeface);
        wv_option2.setFont(typeface);
        wv_option3.setFont(typeface);
    }


}

