package com.example.devnull.sampleapp.presentation.samplelist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;

public class SampleItemView extends LinearLayout {

    private static final String LOG_TAG = SampleItemView.class.getSimpleName();

    private LinearLayout mClickableContent;
    private ImageView mImageView;
    private TextView mTextView;
    private CheckBox mCheckbox;

    private SampleEntity mEntity;

    public SampleItemView(Context context) {
        super(context);
        Log.d(LOG_TAG, "::constructor(Context context)");
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(LOG_TAG, "::constructor(Context context, @Nullable AttributeSet attrs)");
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(LOG_TAG, "::constructor(Context context, @Nullable AttributeSet attrs, int defStyleAttr)");
    }

    public SampleItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(LOG_TAG, "::constructor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initUiControls();
    }

    public void setEntity(SampleEntity entity) {
        Log.d(LOG_TAG, "setEntity " + entity);

        mEntity = entity;
        mImageView.setImageDrawable(getResourceDrawable());
        mTextView.setText(mEntity.getName());
        mCheckbox.setChecked(mEntity.isChecked());
    }

    public void setClickListener(OnClickListener listener) {
        mClickableContent.setOnClickListener(listener);
        mCheckbox.setOnClickListener(listener);
    }

    public SampleEntity getEntity() {
        return mEntity;
    }

    private void initUiControls() {
        mClickableContent = (LinearLayout) findViewById(R.id.clickable_content_frame);
        mImageView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
    }

    private Drawable getResourceDrawable() {
        Drawable drawable;

        if (mEntity.isChecked())
            drawable = getContext().getDrawable(R.drawable.ic_cardiogram);
        else
            drawable = getContext().getDrawable(R.drawable.ic_heart);

        return drawable;
    }

    public void updateView() {
        mCheckbox.setChecked(mEntity.isChecked());
        mImageView.setImageDrawable(getResourceDrawable());
    }
}
