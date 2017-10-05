package com.example.devnull.sampleapp.domain;

public class SampleEntity {

    private final int mId;
    private String mName;
    private boolean isChecked = false;

    public SampleEntity(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
