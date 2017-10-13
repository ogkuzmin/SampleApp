/*
 *  Copyright (c) Ascom (Sweden) AB. All rights reserved.
 */
package com.example.devnull.sampleapp.presentation.dataloading;

import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.devnull.sampleapp.data.QuoteXmlDto;
import com.example.devnull.sampleapp.data.ResultXml;
import com.example.devnull.sampleapp.data.SampleXmlServer;
import com.example.devnull.sampleapp.domain.QuoteEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DataLoadingPresenter extends MvpBasePresenter<DataLoadingView> {

    private static final String LOG_TAG = DataLoadingPresenter.class.getSimpleName();

    private SampleXmlServer mServer;
    private final Scheduler.Worker mIoWorker;

    private final SingleObserver<ResultXml> mObserver = new SingleObserver<ResultXml>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.d(LOG_TAG, "SingleObserver::onSubscribe()");
        }

        @Override
        public void onSuccess(@NonNull ResultXml resultXml) {
            Log.d(LOG_TAG, "SingleObserver::onSuccess()");
            postDataToView(resultXml);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(LOG_TAG, "SingleObserver::onError() " + e.toString());
            AndroidSchedulers.mainThread().createWorker().schedule(() -> getView().showError(e));
        }
    };

    public DataLoadingPresenter() {
        mServer = SampleXmlServer.getInstance();
        mIoWorker = Schedulers.io().createWorker();
    }

    public void loadData() {
        mIoWorker.schedule(() -> asyncLoadData());
    }

    private void asyncLoadData() {
        mServer.getQuoteList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(mObserver);
    }

    @WorkerThread
    private void postDataToView(ResultXml results) {
        Log.d(LOG_TAG, "::postDataToView list size " + results.getQuotes().size() + ", totalPages "
                + results.getTotalPages());
        List<QuoteEntity> viewResult = new ArrayList<QuoteEntity>();
        for (QuoteXmlDto dto: results.getQuotes()) {
            viewResult.add(QuoteXmlDto.createQuoteEntityFromDto(dto));
        }

        Single.just(viewResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> { getView().setData(list); getView().showContent();});
    }
}

