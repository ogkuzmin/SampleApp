package com.example.devnull.sampleapp.data;

import android.util.Log;

import com.example.devnull.sampleapp.domain.GalleryFile;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class GalleryRepoImpl implements GalleryRepo {

    private static final String LOG_TAG = GalleryRepoImpl.class.getSimpleName();

    @Inject
    public GalleryRepoImpl() {

    }

    @Override
    public boolean insert(GalleryFile file) {
        Log.d(LOG_TAG, "::insert() " + file);

        Realm realm = Realm.getDefaultInstance();
        GalleryRealmDto dto = GalleryRealmDto.createFromGalleryFile(file);
        realm.beginTransaction();
        realm.insert(dto);
        realm.commitTransaction();
        realm.close();
        return true;
    }

    @Override
    public List<GalleryFile> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GalleryRealmDto> results = realm.where(GalleryRealmDto.class).findAll();
        List<GalleryFile> list = new ArrayList<GalleryFile>();
        for (GalleryRealmDto realmDto: results) {
            list.add(GalleryRealmDto.createGalleryFile(realmDto));
        }
        realm.close();
        Log.d(LOG_TAG, "::getAll returns the list with size " + list.size());
        return list;
    }



    @Override
    public boolean delete(GalleryFile file) {
        Realm realm = Realm.getDefaultInstance();
        GalleryRealmDto dto = GalleryRealmDto.createFromGalleryFile(file);
        realm.beginTransaction();
        realm.where(GalleryRealmDto.class).equalTo(GalleryRealmDto.PATH_FIELD_NAME, dto.getPath()).
                findAll().deleteFirstFromRealm();
        realm.commitTransaction();
        realm.close();
        return true;
    }

    @Override
    public int getMaxId() {
        int id;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GalleryRealmDto> results = realm.where(GalleryRealmDto.class).findAll();
        Number number = results.max(GalleryRealmDto.ID_FIELD_NAME);
        if (number == null)
            id = 0;
        else
            id = number.intValue();
        realm.close();
        Log.d(LOG_TAG, "::getMaxId() returns " + id);
        return id;
    }

    @Override
    public boolean deleteById(long id) {
        Log.d(LOG_TAG, "::deleteById() trying to delete by id " + id);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        boolean result = realm.where(GalleryRealmDto.class).equalTo(GalleryRealmDto.ID_FIELD_NAME, (int)id).
                findAll().deleteFirstFromRealm();
        realm.commitTransaction();
        realm.close();
        Log.d(LOG_TAG, "::deleteById() result is " + result);
        return true;
    }
}
