package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
    private AlertDialog mAlertDialog;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mBundle = bundle;

        setContentView(R.layout.add_new_item_layout);
        mNameEditText = (EditText) findViewById(R.id.title_edit_text);
        mDoneButton = (Button) findViewById(R.id.button_done);
        mDoneButton.setOnClickListener(v -> presenter.performDoneButton());
        mRevertButton = (Button) findViewById(R.id.button_revert);
        mRevertButton.setOnClickListener(v -> presenter.performRevertButton());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(mBundle);
    }

    @Override
    public void onBackPressed() {
        presenter.performBackButton();
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
            mNameEditText.setText(mEntity.getName());
        }

        getSupportActionBar().setTitle(actionBarTitle);
    }

    @Override
    public String getEnteredText() {
        return mNameEditText.getText().toString();
    }

    @Override
    public void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save_dialog_title);
        builder.setMessage(R.string.save_dialog_msg);
        builder.setPositiveButton(R.string.save_button_dialog_title, (di, i) -> presenter.performDoneButton());
        builder.setNegativeButton(R.string.cancel_button_dialog_title, (di, i) -> super.onBackPressed());
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    @Override
    public void closeView() {
        super.onBackPressed();
    }

    @NonNull
    @Override
    public EditOrAddItemPresenter createPresenter() {
        return new EditOrAddItemPresenter();
    }
}
