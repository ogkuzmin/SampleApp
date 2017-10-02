package com.example.devnull.sampleapp.presentation.SampleListUi;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.devnull.sampleapp.domain.SampleEntity;

import java.util.List;


public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleEntity> {

    private List<SampleEntity> mEntities;

    public void setEntitiesList(List<SampleEntity> entitiesList) {
        mEntities = entitiesList;
    }

    @Override
    public SampleEntity onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleEntity holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mEntities == null)
            return 0;
        else
            return mEntities.size();
    }
}
