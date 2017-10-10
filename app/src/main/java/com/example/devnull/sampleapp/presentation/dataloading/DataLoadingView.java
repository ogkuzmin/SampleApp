package com.example.devnull.sampleapp.presentation.dataloading;

import android.support.annotation.UiThread;

import com.example.devnull.sampleapp.domain.QuoteEntity;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface DataLoadingView extends MvpView {

    @UiThread
    void showContent();

    @UiThread
    void showError(Throwable throwable);

    @UiThread
    void setData(List<QuoteEntity> data);
}
