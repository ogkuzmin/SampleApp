package com.example.devnull.sampleapp.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface XmlServer {
    @GET("/testXmlFeed.xml")
    Observable<List<QuoteXmlDto>> quoteList();
}
