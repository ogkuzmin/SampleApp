package com.example.devnull.sampleapp.data;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class SampleXmlServer {

    private static final String URL = "http://storage.space-o.ru";

    private static SampleXmlServer INSTANCE;
    private final XmlServer mServer;
    private final Retrofit api;

    public static SampleXmlServer getInstance() {
        if (INSTANCE == null) {
            synchronized (SampleXmlServer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SampleXmlServer();
                }
            }
        }
        return INSTANCE;
    }

    private SampleXmlServer() {
        api = new Retrofit.Builder()
                .baseUrl(URL)
                .client(new OkHttpClient())
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                            new Persister(new AnnotationStrategy())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        mServer = api.create(XmlServer.class);
    }

    public Observable<List<QuoteXmlDto>> getQuoteList() {
        return mServer.quoteList();
    }
}
