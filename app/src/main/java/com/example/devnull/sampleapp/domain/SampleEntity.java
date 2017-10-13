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

    @Override
    public String toString() {
        return "Entity with id " + mId + ", name " + mName + ", isChecked? " + isChecked;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        if (((SampleEntity) obj).mId != mId)
            return false;
        if (!((SampleEntity) obj).mName.equals(mName))
            return false;

        return true;
    }

    public void swapChecked() {
        if (isChecked) {
            isChecked = false;
        } else {
            isChecked = true;
        }
    }
}
