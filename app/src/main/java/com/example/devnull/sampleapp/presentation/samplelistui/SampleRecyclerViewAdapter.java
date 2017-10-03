package com.example.devnull.sampleapp.presentation.samplelistui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.devnull.sampleapp.domain.SampleEntity;

import java.util.List;

import static com.example.devnull.sampleapp.presentation.samplelistui.SampleRecyclerViewAdapter.SampleViewHolder;


public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolder> {

    private List<SampleEntity> mEntities;

    public void setEntitiesList(List<SampleEntity> entitiesList) {
        mEntities = entitiesList;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mEntities == null)
            return 0;
        else
            return mEntities.size();
    }

    protected static final class SampleViewHolder extends RecyclerView.ViewHolder {
        public SampleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
