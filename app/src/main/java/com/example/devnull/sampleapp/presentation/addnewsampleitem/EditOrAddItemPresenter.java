package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemView.SAMPLE_ITEM_ID_KEY;

public class EditOrAddItemPresenter extends MvpBasePresenter<EditOrAddItemView> {

    private static final String LOG_TAG = EditOrAddItemPresenter.class.getSimpleName();

    private static final int DEFAULT_ID_VALUE = -1;

    private int mId;
    private SampleEntity mData;

    @Inject
    SampleRepo mRepo;

    private final Scheduler.Worker mIoWorker;

    public EditOrAddItemPresenter() {
        DaggerSampleRepoComponent.builder().build().inject(this);
        mIoWorker = Schedulers.io().createWorker();
        Log.d(LOG_TAG, "::constructor");
    }

    @UiThread
    public void loadData(Intent intent) {

        getView().showLoading();

        mId = DEFAULT_ID_VALUE;

        if (intent != null)
            mId = intent.getIntExtra(SAMPLE_ITEM_ID_KEY, DEFAULT_ID_VALUE);

        Log.d(LOG_TAG, "::loadData() with mId " + mId);

        if (mId == DEFAULT_ID_VALUE) {
            setDataToViewAndShowContent(null);
        } else {
            mIoWorker.schedule(() -> {
                Observable.just(mRepo.getById(mId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> setDataToViewAndShowContent(data));
            });
        }
    }

    @UiThread
    public void setDataToViewAndShowContent(@Nullable SampleEntity data) {
        mData = data;
        getView().setData(data);
        getView().showContent();
    }

    public void performDoneButton() {
        final String enteredText = getView().getEnteredText();

        if (mData != null && enteredText.equals(mData.getName())) {
            getView().closeView();
            return;
        }

        if (mData != null) {
            updateDataObject(enteredText);
        } else {
            insertNewObject(enteredText);
        }
    }

    private void insertNewObject(final String enteredText) {

        mIoWorker.schedule(() -> {
            int max = mRepo.getMaxId();
            SampleEntity entity = new SampleEntity(max + 1);
            entity.setName(enteredText);
            mRepo.insert(entity);
            AndroidSchedulers.mainThread().createWorker().schedule(() -> getView().closeView());
        });
    }

    private void updateDataObject(final String enteredText) {
        mData.setName(enteredText);

        Single.just(mRepo.update(mData))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Boolean v) -> getView().closeView());
    }

    public void performRevertButton() {
        getView().closeView();
    }

    public void performBackButton() {
        String entered = getView().getEnteredText();
        if (mData == null) {
            if (entered == null || entered.isEmpty()) {
                getView().closeView();
            } else {
                getView().showSaveDialog();
            }
        } else {
            if (!mData.getName().equals(entered)) {
                getView().showSaveDialog();
            } else {
                getView().closeView();
            }
        }
    }
}
