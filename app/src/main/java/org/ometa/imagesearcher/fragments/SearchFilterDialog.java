package org.ometa.imagesearcher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.models.SearchFilterOptions;

/**
 * Created by devin on 11/1/15.
 */
public class SearchFilterDialog extends DialogFragment {

    private static class ViewHolder {
        private EditText etImageSize;
        private EditText etImageType;
        private EditText etAsSiteSearch;
        private EditText etColorize;
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
        viewHolder.etImageSize = (EditText) view.findViewById(R.id.etImageSize);
        viewHolder.etImageType = (EditText) view.findViewById(R.id.etImageType);
        viewHolder.etAsSiteSearch = (EditText) view.findViewById(R.id.etAsSiteSearch);
        viewHolder.etColorize = (EditText) view.findViewById(R.id.etColorize);
        viewHolder.etColorFilter = (EditText) view.findViewById(R.id.etColorFilter);

        // set dialog title
        getDialog().setTitle(getString(R.string.search_filter));


        SearchFilterOptions opts = getArguments().getParcelable("opts");
        if (opts.getImageSize() != null) {
            viewHolder.etImageSize.setText(opts.getImageSize());
        }
        if (opts.getImageType() != null) {
            viewHolder.etImageType.setText(opts.getImageType());
        }
        if (opts.getAsSiteSearch() != null) {
            viewHolder.etAsSiteSearch.setText(opts.getAsSiteSearch());
        }
        if (opts.getImgColoration() != null) {
            viewHolder.etColorize.setText(opts.getImgColoration());
        }
        if (opts.getColorFilter() != null) {
            viewHolder.etColorFilter.setText(opts.getColorFilter());
        }

        // Show soft keyboard automatically and request focus to field
        viewHolder.etImageSize.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    private boolean argHas(String str) {
        if (getArguments().containsKey(str)) {
            return true;
        }
        return false;
    }
}
