package com.example.devnull.sampleapp.presentation.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.GalleryFile;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

/**
 * Created by okuzmin on 06.10.17.
 */

public class GalleryFragment extends MvpFragment<GalleryView, GalleryPresenter> implements GalleryView,
        View.OnClickListener {

    private static final String LOG_TAG = GalleryFragment.class.getSimpleName();

    private GridView mGridView;
    private GalleryGridViewAdapter mAdapter;
    private FloatingActionMenu mFabMenu;
    private FloatingActionButton mFabTakePhoto;
    private FloatingActionButton mFabChooseFromGallery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView = (GridView) view.findViewById(R.id.grid_view_container);
        mAdapter = new GalleryGridViewAdapter(getContext(), R.layout.gallery_item_layout, this);
        mGridView.setAdapter(mAdapter);
        mFabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        mFabTakePhoto = (FloatingActionButton) view.findViewById(R.id.fab_take_photo);
        mFabChooseFromGallery = (FloatingActionButton) view.findViewById(R.id.fab_choose_from_gallery);
        mFabTakePhoto.setOnClickListener(clickView -> {
            mFabMenu.close(true);
            presenter.performTakePhotoButtonClick(clickView.getContext());
        });
        mFabChooseFromGallery.setOnClickListener(clickView -> {
            mFabMenu.close(true);
            presenter.performChooseFromGalleryButtonClick(getContext());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "::onResume " + Thread.currentThread());
        presenter.loadData(getContext());
    }

    @Override
    public GalleryPresenter createPresenter() {
        return new GalleryPresenter(getContext());
    }

    @Override
    public void startActivityByRequest(Intent intent, int request) {
        startActivityForResult(intent, request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data, getContext());
    }

    @Override
    public void showFailToast() {
        Toast.makeText(getContext(), R.string.take_photo_fail_text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(List<GalleryFile> data) {
        Log.d(LOG_TAG, "::setData");
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        Log.d(LOG_TAG, "::onClick");

        GalleryGridViewAdapter.ViewHolder holder = null;

        switch (view.getId()) {
            case R.id.image_delete_button:
                Log.d(LOG_TAG, "::onClick clicked delete button");
                holder = (GalleryGridViewAdapter.ViewHolder) ((View) view.getParent()).getTag();
                presenter.deleteGalleryFileById(holder.id);
                presenter.loadData(getContext());
                break;

            case R.id.image_container:
                Log.d(LOG_TAG, "::onClick clicked image container");
                holder = (GalleryGridViewAdapter.ViewHolder) ((View) view.getParent()).getTag();
                presenter.performShowImage(getContext(), holder.path);
                break;
        }
    }
}
