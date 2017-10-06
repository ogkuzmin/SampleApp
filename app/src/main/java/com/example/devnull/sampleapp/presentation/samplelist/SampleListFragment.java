package com.example.devnull.sampleapp.presentation.samplelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import java.util.List;

public class SampleListFragment extends
        MvpLceFragment<RecyclerView, List<SampleEntity>, SampleListView, SampleListPresenter>
        implements SampleListView, View.OnClickListener {

    private static final String LOG_TAG = SampleListFragment.class.getSimpleName();

    private SampleRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(LOG_TAG, "::onCreateView()");
        return inflater.inflate(R.layout.sample_list_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SampleRecyclerViewAdapter(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        contentView.setLayoutManager(mLayoutManager);
        contentView.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        Log.v(LOG_TAG, "::onViewCreated()");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestDataAndSetToView();
        Log.v(LOG_TAG, "::onResume()");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample_list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.add_action_menu:
                presenter.startAddingActivity(getContext(), null);
                return true;

            default:
        }

        return false;
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
        mAdapter.notifyDataSetChanged();
        dismissProgressBarAndShowRecyclerView();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(List<SampleEntity> data) {
        mAdapter.setEntitiesList(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    private void showProgressBar() {
        Log.d(LOG_TAG, "::showProgressBar");
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBarAndShowRecyclerView() {
        Log.d(LOG_TAG, "::dismissProgressBarAndShowRecyclerView");
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        presenter.performClickOnItemView(view);
    }
}
