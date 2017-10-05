package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class EditOrAddItemActivity extends MvpActivity<EditOrAddItemView, EditOrAddItemPresenter>
        implements EditOrAddItemView {

    private EditText mNameEditText;
    private Button mDoneButton;
    private Button mRevertButton;
    private Bundle mBundle;
    private SampleEntity mEntity;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mBundle = bundle;

        setContentView(R.layout.add_new_item_layout);
        mNameEditText = (EditText) findViewById(R.id.title_edit_text);
        mDoneButton = (Button) findViewById(R.id.button_done);
        mRevertButton = (Button) findViewById(R.id.button_revert);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(mBundle);
    }

    @Override
    public void setData(SampleEntity entity) {
        mEntity = entity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {
        String actionBarTitle;

        if (mEntity == null) {
            actionBarTitle = getResources().getString(R.string.add_new_item_activity_title);
        } else {
            actionBarTitle = getResources().getString(R.string.edit_item_activity_title);
        }

        getActionBar().setTitle(actionBarTitle);
    }

    @NonNull
    @Override
    public EditOrAddItemPresenter createPresenter() {
        return new EditOrAddItemPresenter();
    }
}
