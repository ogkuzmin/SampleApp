package com.example.devnull.sampleapp.domain;

import java.io.File;

public class GalleryFile {

    private File mFile;

    public GalleryFile(String path) {
        mFile = new File(path);
        validateFileField();
    }

    public GalleryFile(File file) {
        mFile = file;
        validateFileField();
    }

    private void validateFileField() {
        if (mFile == null || !mFile.exists() || !mFile.canRead() || mFile.isDirectory())
            throw new IllegalArgumentException("Illegal file. Is should exist, be readable and be a file");
    }
}
