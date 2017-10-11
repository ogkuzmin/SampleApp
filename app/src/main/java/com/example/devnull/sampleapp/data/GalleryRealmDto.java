package com.example.devnull.sampleapp.data;

import android.support.annotation.NonNull;

import com.example.devnull.sampleapp.domain.GalleryFile;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class GalleryRealmDto extends RealmObject implements Comparable<GalleryRealmDto> {

    public static final String ID_FIELD_NAME = "mId";
    public static final String PATH_FIELD_NAME = "mPath";

    @PrimaryKey
    private int mId;
    @Required

    private String mPath;

    public GalleryRealmDto() {

    }

    public int getId() {
        return mId;
    }

    public String getPath() {
        return mPath;
    }

    @Override
    public int compareTo(@NonNull GalleryRealmDto galleryRealmDto) {
        return (mId - galleryRealmDto.mId);
    }

    public static GalleryFile createGalleryFile(GalleryRealmDto realmDto) {
        GalleryFile file = new GalleryFile(realmDto.getId(), realmDto.getPath());
        return file;
    }

    public static GalleryRealmDto createFromGalleryFile(GalleryFile galleryFile) {
        GalleryRealmDto realmDto = new GalleryRealmDto();
        realmDto.mId = galleryFile.getId();
        realmDto.mPath = galleryFile.getFile().getAbsolutePath();

        return realmDto;
    }
}
