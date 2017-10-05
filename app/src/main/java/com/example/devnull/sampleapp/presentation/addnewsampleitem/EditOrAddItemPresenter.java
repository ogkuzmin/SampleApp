package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemView.SAMPLE_ITEM_ID_KEY;

public class EditOrAddItemPresenter extends MvpBasePresenter<EditOrAddItemView> {

    private static final int DEFAULT_ID_VALUE = -1;

    @Inject
    SampleRepo mRepo;

    public EditOrAddItemPresenter() {
        DaggerSampleRepoComponent.builder().build().inject(this);
    }

    @UiThread
    public void loadData(Bundle bundle) {

        getView().showLoading();
        int id = bundle.getInt(SAMPLE_ITEM_ID_KEY, DEFAULT_ID_VALUE);

        if (id == DEFAULT_ID_VALUE) {
            setDataToViewAndShowContent(null);
        } else {
            Observable.just(mRepo.getById(id)).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(data -> setDataToViewAndShowContent(data));
        }
    }

    @UiThread
    public void setDataToViewAndShowContent(@Nullable SampleEntity data) {
        getView().setData(data);
        getView().showContent();
    }
}
