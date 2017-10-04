package com.example.devnull.sampleapp.presentation.samplelistui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.di.SampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import java.util.List;

import javax.inject.Inject;

public class SampleListFragment extends
        MvpLceFragment<RecyclerView, List<SampleEntity>, SampleListView, SampleListPresenter>
        implements SampleListView {

    private static final String LOG_TAG = SampleListFragment.class.getSimpleName();

    final SampleRecyclerViewAdapter adapter = new SampleRecyclerViewAdapter();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout mLoadingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(LOG_TAG, "::onCreateView()");
        return inflater.inflate(R.layout.sample_list_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SampleRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        contentView.setLayoutManager(mLayoutManager);
        contentView.setAdapter(mAdapter);

        Log.v(LOG_TAG, "::onViewCreated()");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
        presenter.requestDataAndSetToView();
        Log.v(LOG_TAG, "::onResume()");
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
        dismissProgressBarAndShowRecyclerView();
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
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBarAndShowRecyclerView() {
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

}
