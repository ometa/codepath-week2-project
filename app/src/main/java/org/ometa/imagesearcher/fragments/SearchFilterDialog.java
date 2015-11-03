package org.ometa.imagesearcher.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
        SearchFilterDialog frag = new SearchFilterDialog();
        Bundle args = new Bundle();
        // todo: add parcelable to opts, pass that instead

        
        args.putString("image_size", opts.getImageSize());
        args.putString("image_type", opts.getImageType());
        args.putString("as_site_search", opts.getAsSiteSearch());
        args.putString("colorize", opts.getImgColoration());
        args.putString("color_filter", opts.getColorFilter());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewHolder = new ViewHolder();
        return inflater.inflate(R.layout.fragment_search_filter, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
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

        if (getArguments().containsKey("image_size"))
        // Fetch arguments from bundle and set variables
        if (argHas("image_size")) {
            viewHolder.etImageSize.setText(getArguments().getString("image_size", "default size"));
        }
        if (argHas("image_type")) {
            viewHolder.etImageType.setText(getArguments().getString("image_type", "default type"));
        }

        if (argHas("as_site_search")) {
            viewHolder.etAsSiteSearch.setText(getArguments().getString("as_site_search", "default search"));
        }

        if (argHas("colorize")) {
            viewHolder.etColorize.setText(getArguments().getString("colorize", "default colorize"));
        }

        if (argHas("color_filter")) {
            viewHolder.etColorFilter.setText(getArguments().getString("color_filter", "default color filter"));
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
