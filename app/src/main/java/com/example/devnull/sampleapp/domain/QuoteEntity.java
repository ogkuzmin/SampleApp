package com.example.devnull.sampleapp.domain;

public class QuoteEntity {
    private long id;
    private String mDate;
    private String mText;

    public QuoteEntity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }
}
