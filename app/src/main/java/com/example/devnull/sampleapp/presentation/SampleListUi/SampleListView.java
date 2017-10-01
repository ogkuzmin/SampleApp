package com.example.devnull.sampleapp.presentation.SampleListUi;

import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * An interface for list activity with recycler view and list of item in it.
 */
public interface SampleListView extends MvpLceView<List<SampleEntity>> {
}
