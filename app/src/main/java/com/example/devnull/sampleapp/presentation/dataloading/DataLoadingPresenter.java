/*
 *  Copyright (c) Ascom (Sweden) AB. All rights reserved.
 */
package com.example.devnull.sampleapp.presentation.dataloading;

import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.devnull.sampleapp.data.QuoteXmlDto;
import com.example.devnull.sampleapp.data.SampleXmlServer;
import com.example.devnull.sampleapp.domain.QuoteEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DataLoadingPresenter extends MvpBasePresenter<DataLoadingView> {

    private static final String LOG_TAG = DataLoadingPresenter.class.getSimpleName();

    private SampleXmlServer mServer;

    private final Observer<List<QuoteXmlDto>> mObserver = new Observer<List<QuoteXmlDto>>() {

        private final List<QuoteXmlDto> results = new ArrayList<QuoteXmlDto>();

        @Override
        public void onSubscribe(Disposable subscription) {
        }

        @Override
        public void onNext(List<QuoteXmlDto> quoteXmlDtos) {
            results.addAll(quoteXmlDtos);
        }

        @Override
        public void onError(Throwable throwable) {
            getView().showError(throwable);
        }

        @Override
        public void onComplete() {
            postDataToView(results);
        }
    };

    public DataLoadingPresenter() {
        mServer = SampleXmlServer.getInstance();
    }

    public void loadData() {
        getView().showLoading(false);

        mServer.getQuoteList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(mObserver);
    }

    @WorkerThread
    private void postDataToView(List<QuoteXmlDto> results) {
        Log.d(LOG_TAG, "::postDataToView list size " + results.size());
        List<QuoteEntity> viewResult = new ArrayList<QuoteEntity>();
        for (QuoteXmlDto dto: results) {
            viewResult.add(QuoteXmlDto.createQuoteEntityFromDto(dto));
        }

        Single.just(viewResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> { getView().setData(list); getView().showContent();});
    }
}

