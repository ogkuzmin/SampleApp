package com.example.devnull.sampleapp.presentation.SampleListUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import java.util.List;

public class SampleListFragment extends
        MvpLceFragment<RelativeLayout, List<SampleEntity>, SampleListView, SampleListPresenter>
        implements SampleListView {

    final SampleRecyclerViewAdapter adapter = new SampleRecyclerViewAdapter();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(List<SampleEntity> data) {
        adapter.setEntitiesList(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }


}
