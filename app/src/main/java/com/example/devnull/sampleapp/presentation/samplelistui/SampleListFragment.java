package com.example.devnull.sampleapp.presentation.samplelistui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import java.util.List;

public class SampleListFragment extends
        MvpLceFragment<RelativeLayout, List<SampleEntity>, SampleListView, SampleListPresenter>
        implements SampleListView {

    final SampleRecyclerViewAdapter adapter = new SampleRecyclerViewAdapter();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout mLoadingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_list_fragment_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sample_list_recycler_view);
        mAdapter = new SampleRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.progress_dialog_container);
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public SampleListPresenter createPresenter() {
        return new SampleListPresenter();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        showProgressBar();
    }

    @Override
    public void showContent() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(List<SampleEntity> data) {
        adapter.setEntitiesList(data);

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    private void showProgressBar() {
        mRecyclerView.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBarAndShowRecyclerView() {
        mLoadingLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
