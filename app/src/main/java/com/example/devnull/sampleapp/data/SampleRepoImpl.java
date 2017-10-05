package com.example.devnull.sampleapp.data;

import android.util.Log;

import com.example.devnull.sampleapp.domain.SampleEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class SampleRepoImpl implements SampleRepo {

    private static final String LOG_TAG = SampleRepoImpl.class.getSimpleName();

    @Inject
    public SampleRepoImpl() {
        Log.d(LOG_TAG, "Inside constructor");
    }

    @Override
    public List<SampleEntity> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SampleRealmDto> results = realm.where(SampleRealmDto.class).findAll();
        List<SampleEntity> list = new ArrayList<SampleEntity>();
        for (SampleRealmDto realmDto: results) {
            list.add(SampleRealmDto.createEntity(realmDto));
        }
        realm.close();
        return list;
    }

    @Override
    public SampleEntity getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = realm.where(SampleRealmDto.class).equalTo(SampleRealmDto.ID_FIELD_NAME, id).findFirst();
        realm.close();
        return SampleRealmDto.createEntity(dto);
    }

    @Override
    public void insert(SampleEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.insert(dto);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void update(SampleEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.insertOrUpdate(dto);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void delete(SampleEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.where(SampleRealmDto.class).equalTo(SampleRealmDto.ID_FIELD_NAME, dto.getId()).
                findAll().deleteFirstFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public int getMaxId() {
        int id;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SampleRealmDto> results = realm.where(SampleRealmDto.class).findAll();
        id = results.max(SampleRealmDto.ID_FIELD_NAME).intValue();
        realm.close();
        return id;
    }

    @Override
    public void addListener(Listener listener) {

    }
}
