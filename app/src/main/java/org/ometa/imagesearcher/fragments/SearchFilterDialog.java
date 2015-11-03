package org.ometa.imagesearcher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.models.SearchFilterOptions;

import java.util.Arrays;

/**
 * Created by devin on 11/1/15.
 */
public class SearchFilterDialog extends DialogFragment {

    private static class ViewHolder {
        private Spinner spinnerImageSize;
        private Spinner spinnerImageType;
        private Spinner spinnerImageColorization;
        private EditText etAsSiteSearch;
        private EditText etColorFilter;
    }
    private ViewHolder viewHolder;

    public SearchFilterDialog() {}

    public static SearchFilterDialog newInstance(SearchFilterOptions opts) {
        Bundle args = new Bundle();
        args.putParcelable("opts", opts);
        SearchFilterDialog frag = new SearchFilterDialog();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewHolder = new ViewHolder();
        return inflater.inflate(R.layout.fragment_search_filter, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHolder.spinnerImageSize = (Spinner) view.findViewById(R.id.spinnerImageSize);
        viewHolder.spinnerImageType = (Spinner) view.findViewById(R.id.spinnerImageType);
        viewHolder.etAsSiteSearch = (EditText) view.findViewById(R.id.etAsSiteSearch);
        viewHolder.spinnerImageColorization = (Spinner) view.findViewById(R.id.spinnerImageColorization);
        viewHolder.etColorFilter = (EditText) view.findViewById(R.id.etColorFilter);

        SearchFilterOptions opts = getArguments().getParcelable("opts");

        // set dialog title
        getDialog().setTitle(getString(R.string.search_filter));

        // initialize image size spinner
        ArrayAdapter<String> aaImageSize = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageSizes);
        aaImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageSize.setAdapter(aaImageSize);

        // initialize image type spinner
        ArrayAdapter<String> aaImageType = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageTypes);
        aaImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageType.setAdapter(aaImageType);

        // initialize image colorization spinner
        ArrayAdapter<String> aaImageColorization = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageColorizations);
        aaImageColorization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageColorization.setAdapter(aaImageColorization);

        if (opts.getImageSize() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageSizes).indexOf(opts.getImageSize());
            viewHolder.spinnerImageSize.setSelection(pos);
        }
        if (opts.getImageType() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageTypes).indexOf(opts.getImageType());
            viewHolder.spinnerImageType.setSelection(pos);
        }
        if (opts.getAsSiteSearch() != null) {
            viewHolder.etAsSiteSearch.setText(opts.getAsSiteSearch());
        }
        if (opts.getImageColorization() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageColorizations).indexOf(opts.getImageColorization());
            viewHolder.spinnerImageColorization.setSelection(pos);
        }

        if (opts.getColorFilter() != null) {
            viewHolder.etColorFilter.setText(opts.getColorFilter());
        }

        // Show soft keyboard automatically and request focus to field
        viewHolder.spinnerImageSize.requestFocus();
//        viewHolder.etImageSize.requestFocus();

//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    private boolean argHas(String str) {
        if (getArguments().containsKey(str)) {
            return true;
        }
        return false;
    }
}
