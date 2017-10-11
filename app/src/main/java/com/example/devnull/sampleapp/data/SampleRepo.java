package com.example.devnull.sampleapp.data;


import com.example.devnull.sampleapp.domain.SampleEntity;

import java.util.List;

public interface SampleRepo {

    List<SampleEntity> getAll();
    SampleEntity getById(int id);
    boolean insert(SampleEntity entity);
    boolean update(SampleEntity entity);
    boolean delete(SampleEntity entity);
    int getMaxId();
}
