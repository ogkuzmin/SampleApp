package com.example.devnull.sampleapp.presentation.Gallery;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

/**
 * Created by okuzmin on 06.10.17.
 */

public class GalleryFragment extends MvpFragment<GalleryView, GalleryPresenter> implements GalleryView {

    @Override
    public GalleryPresenter createPresenter() {
        return new GalleryPresenter();
    }
}
