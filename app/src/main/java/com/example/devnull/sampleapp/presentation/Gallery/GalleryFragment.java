package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class GalleryFragment extends MvpFragment<GalleryView, GalleryPresenter> implements GalleryView {

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
        mAdapter = new GalleryGridViewAdapter(getContext(), R.layout.gallery_item_layout);
        mFabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        mFabTakePhoto = (FloatingActionButton) view.findViewById(R.id.fab_take_photo);
        mFabChooseFromGallery = (FloatingActionButton) view.findViewById(R.id.fab_choose_from_gallery);
        mFabTakePhoto.setOnClickListener(clickView -> presenter.performTakePhotoButtonClick(clickView.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(getContext());
    }

    @Override
    public GalleryPresenter createPresenter() {
        return new GalleryPresenter();
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
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }
}
