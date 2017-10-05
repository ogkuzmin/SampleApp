package com.example.devnull.sampleapp.presentation.addnewsampleitem;

import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by okuzmin on 05.10.17.
 */

public interface EditOrAddItemView extends MvpView {

    String SAMPLE_ITEM_ID_KEY = "SAMPLE_ITEM_ID_KEY";

    void setData(SampleEntity entity);
    void showLoading();
    void showContent();
    String getEnteredText();
    void showSaveDialog();
    void closeView();
}
