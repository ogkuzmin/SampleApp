/*
 *  Copyright (c) Ascom (Sweden) AB. All rights reserved.
 */
package com.example.devnull.sampleapp.presentation.languagepreference;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.devnull.sampleapp.domain.QuoteEntity;

import java.util.List;

public class QuoteRecyclerViewAdapter extends RecyclerView.Adapter<CardView> {

    private List<QuoteEntity> mEntities;

    public void setData(List<QuoteEntity> entities) {
        mEntities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private static class CardView
}
