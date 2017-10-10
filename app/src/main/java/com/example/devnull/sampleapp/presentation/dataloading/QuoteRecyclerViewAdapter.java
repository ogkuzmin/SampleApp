/*
 *  Copyright (c) Ascom (Sweden) AB. All rights reserved.
 */
package com.example.devnull.sampleapp.presentation.dataloading;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.QuoteEntity;

import java.util.List;

import static com.example.devnull.sampleapp.presentation.dataloading.QuoteRecyclerViewAdapter.CardViewViewHolder;

public class QuoteRecyclerViewAdapter extends RecyclerView.Adapter<CardViewViewHolder> {

    private List<QuoteEntity> mEntities;

    public void setData(List<QuoteEntity> entities) {
        mEntities = entities;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_item_layout, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        QuoteEntity entity = mEntities.get(position);
        TextView idTextView = (TextView) holder.mCardView.findViewById(R.id.id_text_view);
        if (idTextView == null || !isEqualIdValues(idTextView, entity)) {
            holder.updateByQuoteEntity(entity);
        }
    }

    @Override
    public int getItemCount() {
        if (mEntities == null)
            return 0;

        return mEntities.size();
    }

    private static boolean isEqualIdValues(TextView textView, QuoteEntity entity) {
        long anotherId = Long.parseLong(textView.getText().toString());
        return anotherId == entity.getId();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView mIdTextView;
        TextView mDateTextView;
        TextView mTextTextView;

        public CardViewViewHolder(View view) {
            super(view);
            mCardView = (CardView) view;
            mIdTextView = (TextView) mCardView.findViewById(R.id.id_text_view);
            mDateTextView = (TextView) mCardView.findViewById(R.id.date_text_view);
            mTextTextView = (TextView) mCardView.findViewById(R.id.text_text_view);
        }

        public void updateByQuoteEntity(QuoteEntity entity) {
            mIdTextView.setText(String.valueOf(entity.getId()));
            mDateTextView.setText(entity.getDate());
            mTextTextView.setText(entity.getText());
        }
    }
}
