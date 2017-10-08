package com.example.devnull.sampleapp.presentation.samplelist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.di.SampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemActivity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemView.SAMPLE_ITEM_ID_KEY;


public class SampleListPresenter extends MvpBasePresenter<SampleListView> implements SampleRepo.Listener{

    private static final String LOG_TAG = SampleListPresenter.class.getSimpleName();

    @Inject
    SampleRepo mRepo;

    private SampleItemView mLongClickedItemView;

    public SampleListPresenter() {
        SampleRepoComponent component = DaggerSampleRepoComponent.builder().build();
        component.inject(this);
        Log.d(LOG_TAG, "Constructor");
    }

    @Override
    public void onRepoChanged() {
        requestDataAndSetToView();
    }

    @UiThread
    public void requestDataAndSetToView() {
        getView().showLoading(false);
        Single.just(mRepo.getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postDataToView(data));
    }

    @UiThread
    private void postDataToView(List<SampleEntity> data) {

        Log.d(LOG_TAG, "::postDataToView() posting list data with size " + data.size());

        SampleListView view = getView();

        if (view == null)
            return;

        view.setData(data);
        view.showContent();
    }

    @UiThread
    public void startAddingActivity(Context context, SampleItemView itemView) {
        Intent intent = new Intent(context, EditOrAddItemActivity.class);

        if (itemView != null) {
            intent.putExtra(SAMPLE_ITEM_ID_KEY, itemView.getEntity().getId());
            Log.d(LOG_TAG, "::startAddingActivity() put int extra " + itemView.getEntity().getId());
        }

        context.startActivity(intent);
        Log.d(LOG_TAG, "::startAddingActivity()");
    }

    public void performClickOnItemView(View view) {
        SampleItemView itemView = (SampleItemView) view.getParent();

        switch (view.getId()){
            case R.id.clickable_content_frame:
                startAddingActivity(view.getContext(), itemView);
                break;

            case R.id.checkbox:
                performUpdateEntityWithCheckBoxEvent(itemView);
                break;

        }
    }

    private void performUpdateEntityWithCheckBoxEvent(final SampleItemView itemView) {
        final SampleEntity entity = itemView.getEntity();
        entity.swapChecked();
        itemView.updateView();

        Completable.fromAction(() -> mRepo.update(entity))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void performLongClick(View view) {
        mLongClickedItemView = (SampleItemView) view.getParent();
        getView().showPopupMenu(mLongClickedItemView);
    }

    public void performEditAction() {
        startAddingActivity(mLongClickedItemView.getContext(), mLongClickedItemView);
    }

    public void performDeleteAction() {
        Completable.fromAction(() -> mRepo.delete(mLongClickedItemView.getEntity()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> requestDataAndSetToView());
    }
}
