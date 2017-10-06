package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemView.SAMPLE_ITEM_ID_KEY;

public class EditOrAddItemPresenter extends MvpBasePresenter<EditOrAddItemView> {

    private static final int DEFAULT_ID_VALUE = -1;

    private int mId;
    private SampleEntity mData;

    @Inject
    SampleRepo mRepo;

    public EditOrAddItemPresenter() {
        DaggerSampleRepoComponent.builder().build().inject(this);
    }

    @UiThread
    public void loadData(Bundle bundle) {

        getView().showLoading();

        mId = DEFAULT_ID_VALUE;

        if (bundle != null)
            bundle.getInt(SAMPLE_ITEM_ID_KEY, DEFAULT_ID_VALUE);

        if (mId == DEFAULT_ID_VALUE) {
            setDataToViewAndShowContent(null);
        } else {
            Observable.just(mRepo.getById(mId)).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(data -> setDataToViewAndShowContent(data));
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

        final Function<Integer, Boolean> insertToDatabase = new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer max) throws Exception {
                SampleEntity entity = new SampleEntity(max + 1);
                entity.setName(enteredText);
                return mRepo.insert(entity);
            }
        };

        Single.just(mRepo.getMaxId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .map(max -> insertToDatabase.apply(max))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Boolean v) -> getView().closeView());
    }

    public void performRevertButton() {
        getView().closeView();
    }

    public void performBackButton() {
        String entered = getView().getEnteredText();
        if (mId == DEFAULT_ID_VALUE) {
            if (entered != null) {
                getView().showSaveDialog();
            }
        } else {
            if (!mData.getName().equals(entered)) {
                getView().showSaveDialog();
            }
        }
    }
}
