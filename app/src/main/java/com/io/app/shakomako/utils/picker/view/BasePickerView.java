package com.io.app.shakomako.utils.picker.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.io.app.shakomako.R;
import com.io.app.shakomako.utils.picker.listener.OnDismissListener;
import com.io.app.shakomako.utils.picker.utils.PickerViewAnimateUtil;


public abstract class BasePickerView {
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );

    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup decorView;
    private ViewGroup rootView;
    private FrameLayout overlayFrameLayout;

    private OnDismissListener onDismissListener;
    private boolean isDismissing;

    Button btnSubmit, btnCancel;

    public View getBtnSubmit() {
        return btnSubmit;
    }

    public void setBtnSubmit(Button btnSubmit) {
        this.btnSubmit = btnSubmit;
    }

    public View getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public void hideCancelButton() {
        if (this.btnCancel != null)
            this.btnCancel.setVisibility(View.GONE);
    }

    public void hideSubmitButton() {
        if (this.btnSubmit != null)
            this.btnSubmit.setVisibility(View.GONE);
    }

    public void showCancelButton() {
        if (this.btnCancel != null)
            this.btnCancel.setVisibility(View.VISIBLE);
    }

    public void showSubmitButton() {
        if (this.btnSubmit != null)
            this.btnSubmit.setVisibility(View.VISIBLE);
    }

    public void setCancelButtonText(String newText) {
        if (this.btnCancel != null)
            this.btnCancel.setText(newText);
    }

    public void setSubmitButtonText(String newText) {
        if (this.btnSubmit != null)
            this.btnSubmit.setText(newText);
    }

    public void setCancelButtonText(int resId) {
        if (this.btnCancel != null)
            this.btnCancel.setText(resId);
    }

    public void setSubmitButtonText(int resId) {
        if (this.btnSubmit != null)
            this.btnSubmit.setText(resId);
    }

    public void setSubmitButtonTextSize(float textSize) {
        if (this.btnSubmit != null)
            this.btnSubmit.setTextSize(textSize);
    }
    public void setSubmitButtonTextColor(int color) {
        if (this.btnSubmit != null)
            this.btnSubmit.setTextColor(color);
    }

    public void setCancelButtonTextSize(float textSize) {
        if (this.btnCancel != null)
            this.btnCancel.setTextSize(textSize);
    }
    public void setCancelButtonTextColor(int color) {
        if (this.btnCancel != null)
            this.btnCancel.setTextColor(color);
    }

    public abstract void setCustomFont(@NonNull Typeface typeface);

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.BOTTOM;

    public BasePickerView(Context context){
        this.context = context;

        initViews();
        init();
        initEvents();
    }

    protected void initViews(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        overlayFrameLayout = (FrameLayout) rootView.findViewById(R.id.outmost_container);
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
        contentContainer.setLayoutParams(params);
    }

    public void hideOverlay() {
        overlayFrameLayout.setBackgroundResource(0);
    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }
    protected void initEvents() {
    }

    private void onAttached(View view) {
        decorView.addView(view);
        contentContainer.startAnimation(inAnim);
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }

    public boolean isShowing() {
        View view = decorView.findViewById(R.id.outmost_container);
        return view != null;
    }
    public void dismiss() {
        if (isDismissing) {
            return;
        }

        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                decorView.post(new Runnable() {
                    @Override
                    public void run() {
                        decorView.removeView(rootView);
                        isDismissing = false;
                        if (onDismissListener != null) {
                            onDismissListener.onDismiss(BasePickerView.this);
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contentContainer.startAnimation(outAnim);
        isDismissing = true;
    }
    public Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public BasePickerView setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        }
        else{
            view.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id){
        return contentContainer.findViewById(id);
    }
}
