package com.example.devnull.sampleapp.domain;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;

public class GalleryFile implements Comparable<GalleryFile> {

    private int mId;
    private File mFile;
    private Bitmap mThumbnail;

    public GalleryFile(int id, String path) {
        this(id, new File(path));
    }

    public GalleryFile(int id, File file) {
        mId = id;
        mFile = file;
        validateFileField();
    }

    private void validateFileField() {
        if (mFile == null || !mFile.exists() /*|| !mFile.canRead() */|| mFile.isDirectory())
            throw new IllegalArgumentException("Illegal file. Is should exist, be readable and be a file");
    }

    public int getId() {
        return mId;
    }

    public File getFile() {
        return mFile;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    @Override
    public int compareTo(@NonNull GalleryFile galleryFile) {
        return (mId - galleryFile.mId);
    }

    @Override
    public String toString() {
        return "GalleryFile with id " + mId + "\n Paths is \"" + mFile.getAbsolutePath() + "\"";
    }
}
