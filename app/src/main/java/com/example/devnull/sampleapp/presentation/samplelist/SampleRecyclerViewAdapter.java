package com.example.devnull.sampleapp.presentation.samplelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;

import org.w3c.dom.Entity;

import java.util.List;
import java.util.zip.Inflater;

import static com.example.devnull.sampleapp.presentation.samplelist.SampleRecyclerViewAdapter.SampleViewHolder;


public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolder> {

    private List<SampleEntity> mEntities;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongListener;

    public SampleRecyclerViewAdapter(View.OnClickListener clickListener,
                                     View.OnLongClickListener longClickListener) {
        mListener = clickListener;
        mLongListener = longClickListener;
    }

    public void setEntitiesList(List<SampleEntity> entitiesList) {
        mEntities = entitiesList;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SampleItemView itemView = (SampleItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_item_layout, parent, false);
        itemView.setClickListener(mListener);
        itemView.setOnLongClickListener(mLongListener);
        return new SampleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        SampleEntity entity = mEntities.get(position);

        if (!entity.equals(holder.mItemView.getEntity()))
            holder.mItemView.setEntity(entity);
    }

    @Override
    public int getItemCount() {
        if (mEntities == null)
            return 0;
        else
            return mEntities.size();
    }

    protected static final class SampleViewHolder extends RecyclerView.ViewHolder {

        private SampleItemView mItemView;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mItemView = (SampleItemView) itemView;
        }
    }
}
