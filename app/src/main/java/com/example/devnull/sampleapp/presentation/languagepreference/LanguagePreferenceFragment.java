package com.example.devnull.sampleapp.presentation.languagepreference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.devnull.sampleapp.R;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

public class LanguagePreferenceFragment extends MvpFragment<LanguagePreferenceView, LanguagePreferencePresenter>
        implements LanguagePreferenceView {

    private final String[] mTitles = new String[] { getContext().getResources().getString(Languages.DEFAULT_RES_CODE),
            getContext().getResources().getString(Languages.ENGLISH_RES_CODE),
            getContext().getResources().getString(Languages.RUSSIAN_RES_CODE)};
    private Spinner mPreferenceSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.language_preference_layout,
                container, false);
        return layout;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        mPreferenceSpinner = (Spinner) rootView.findViewById(R.id.preference_values_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                mTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPreferenceSpinner.setAdapter(adapter);
        mPreferenceSpinner.setOnItemClickListener( (adapterView, view, i, l) ->
                presenter.performItemClick(rootView.getContext(), i));
    }

    @Override
    public LanguagePreferencePresenter createPresenter() {
        return new LanguagePreferencePresenter();
    }
}
