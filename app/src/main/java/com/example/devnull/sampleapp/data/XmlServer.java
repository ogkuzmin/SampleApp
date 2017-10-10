package com.example.devnull.sampleapp.data;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface XmlServer {
    @GET("testXmlFeed.xml")
    Single<ResultXml> resultXmlList();
}
