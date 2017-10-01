package com.example.devnull.sampleapp.data;


import com.example.devnull.sampleapp.domain.SampleEntity;

import java.util.List;

public interface SampleRepo {

    List<SampleEntity> getAll();
    SampleEntity getById(int id);
    void insert(SampleEntity entity);
    void update(SampleEntity entity);
    void delete(SampleEntity entity);
}
