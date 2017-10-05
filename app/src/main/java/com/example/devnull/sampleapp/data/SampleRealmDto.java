package com.example.devnull.sampleapp.data;

import android.support.annotation.NonNull;

import com.example.devnull.sampleapp.domain.SampleEntity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class SampleRealmDto extends RealmObject implements Comparable<SampleRealmDto> {

    public static final String ID_FIELD_NAME = "mId";
    public static final String NAME_FIELD_NAME = "mName";
    public static final String IS_CHECKED_FIELD_NAME = "isChecked";

    @PrimaryKey
    private int mId;
    @Required
    private String mName;
    private boolean isChecked;

    public SampleRealmDto() {

    }

    public int getId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public int compareTo(@NonNull SampleRealmDto sampleRealmDto) {
        return (mId - sampleRealmDto.mId);
    }

    static SampleEntity createEntity(SampleRealmDto dto) {
        SampleEntity entity = new SampleEntity(dto.mId);
        entity.setName(dto.mName);
        entity.setChecked(dto.isChecked);

        return entity;
    }

    static SampleRealmDto createFromEntity(SampleEntity entity) {
        SampleRealmDto dto = new SampleRealmDto();
        dto.mId = entity.getId();
        dto.mName = entity.getName();
        dto.isChecked = entity.isChecked();

        return dto;
    }
}
