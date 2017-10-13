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
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemView.SAMPLE_ITEM_ID_KEY;


public class SampleListPresenter extends MvpBasePresenter<SampleListView> {

    private static final String LOG_TAG = SampleListPresenter.class.getSimpleName();

    @Inject
    SampleRepo mRepo;

    private final Scheduler.Worker mIoWorker;

    private SampleItemView mLongClickedItemView;

    public SampleListPresenter() {
        SampleRepoComponent component = DaggerSampleRepoComponent.builder().build();
        component.inject(this);
        mIoWorker = Schedulers.io().createWorker();
        Log.d(LOG_TAG, "Constructor");
    }

    @UiThread
    public void requestDataAndSetToView() {
        getView().showLoading(false);
        mIoWorker.schedule(() -> asyncRequestDataAndSetToView());
    }

    private void asyncRequestDataAndSetToView() {

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

        SampleItemView itemView = (SampleItemView) view.getParent().getParent();

        switch (view.getId()){
            case R.id.clickable_content_frame:
                Log.d(LOG_TAG, "::performClickOnItemView() click on content frame");
                startAddingActivity(view.getContext(), itemView);
                break;

            case R.id.checkbox:
                Log.d(LOG_TAG, "::performClickOnItemView() click on checkbox");
                performUpdateEntityWithCheckBoxEvent(itemView);
                break;

        }
    }

    @UiThread
    private void performUpdateEntityWithCheckBoxEvent(final SampleItemView itemView) {
        final SampleEntity entity = itemView.getEntity();
        entity.swapChecked();
        itemView.updateView();

       mIoWorker.schedule(() -> mRepo.update(entity));
    }

    @UiThread
    public void performLongClick(View view) {
        mLongClickedItemView = (SampleItemView) view.getParent().getParent();
        getView().showPopupMenu(mLongClickedItemView);
    }

    @UiThread
    public void performEditAction() {
        startAddingActivity(mLongClickedItemView.getContext(), mLongClickedItemView);
    }

    @UiThread
    public void performDeleteAction() {
       mIoWorker.schedule(() -> { mRepo.delete(mLongClickedItemView.getEntity());
           AndroidSchedulers.mainThread().createWorker().schedule(() -> requestDataAndSetToView());
       });
    }
}
