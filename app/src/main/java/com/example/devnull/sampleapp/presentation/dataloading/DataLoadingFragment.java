package com.example.devnull.sampleapp.presentation.dataloading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.QuoteEntity;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

public class DataLoadingFragment extends MvpFragment<DataLoadingView, DataLoadingPresenter> implements
        DataLoadingView, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = DataLoadingFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private QuoteRecyclerViewAdapter mRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.data_loading_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_content_frame);
        mRecyclerViewAdapter = new QuoteRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void showContent() {
        mRecyclerViewAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void setData(List<QuoteEntity> data) {
        mRecyclerViewAdapter.setData(data);
    }

    @Override
    public DataLoadingPresenter createPresenter() {
        return new DataLoadingPresenter();
    }
}
