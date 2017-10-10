package com.example.devnull.sampleapp.data;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Root data object of XML parsing.
 */
@Root(name = "result")
public class ResultXml {

    @Element(name = "totalPages")
    int mTotalPages;

    @ElementList(name = "quotes")
    List<QuoteXmlDto> mQuotes;

    public ResultXml() { }

    public int getTotalPages() {
        return mTotalPages;
    }

    public List<QuoteXmlDto> getQuotes() {
        return mQuotes;
    }
}
