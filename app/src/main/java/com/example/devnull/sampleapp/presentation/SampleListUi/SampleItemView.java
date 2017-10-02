package com.example.devnull.sampleapp.presentation.SampleListUi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;

public class SampleItemView extends RelativeLayout {

    private ImageView mImageView;
    private TextView mTextView;
    private CheckBox mCheckbox;

    private SampleEntity mEntity;

    public SampleItemView(Context context) {
        super(context);
        initUiControls(context);
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUiControls(context);
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUiControls(context);
    }

    public SampleItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setEntity(SampleEntity entity) {
        mEntity = entity;
        mImageView.setImageDrawable(entity);
    }

    private void initUiControls(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sample_item_layout, this);
        mImageView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
    }
}
