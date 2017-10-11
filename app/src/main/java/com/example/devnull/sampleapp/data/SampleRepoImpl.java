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
        Log.d(LOG_TAG, "::getAll returns the list with size " + list.size());
        return list;
    }

    @Override
    public SampleEntity getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = realm.where(SampleRealmDto.class).equalTo(SampleRealmDto.ID_FIELD_NAME, id).findFirst();
        SampleEntity result = SampleRealmDto.createEntity(dto);
        realm.close();
        return result;
    }

    @Override
    public boolean insert(SampleEntity entity) {
        Log.d(LOG_TAG, "::insert() " + entity);

        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.insert(dto);
        realm.commitTransaction();
        realm.close();
        return true;
    }

    @Override
    public boolean update(SampleEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.insertOrUpdate(dto);
        realm.commitTransaction();
        realm.close();
        return true;
    }

    @Override
    public boolean delete(SampleEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        SampleRealmDto dto = SampleRealmDto.createFromEntity(entity);
        realm.beginTransaction();
        realm.where(SampleRealmDto.class).equalTo(SampleRealmDto.ID_FIELD_NAME, dto.getId()).
                findAll().deleteFirstFromRealm();
        realm.commitTransaction();
        realm.close();
        return true;
    }

    @Override
    public int getMaxId() {
        int id;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SampleRealmDto> results = realm.where(SampleRealmDto.class).findAll();
        Number number = results.max(SampleRealmDto.ID_FIELD_NAME);
        if (number == null)
            id = 0;
        else
            id = number.intValue();
        realm.close();
        Log.d(LOG_TAG, "::getMaxId() returns " + id);
        return id;
    }
}
