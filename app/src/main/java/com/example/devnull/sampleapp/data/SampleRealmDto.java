package com.example.devnull.sampleapp.data;

import com.example.devnull.sampleapp.domain.SampleEntity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class SampleRealmDto extends RealmObject {

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
