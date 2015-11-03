package org.ometa.imagesearcher.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.models.SearchFilterOptions;

import java.util.Arrays;

/**
 * Created by devin on 11/1/15.
 */
public class SearchFilterDialog extends DialogFragment {

    // fragment listener
    private OnFilterButtonPressedListener listener;

    public interface OnFilterButtonPressedListener {
        void onFilterButtonPressed(SearchFilterOptions opts);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFilterButtonPressedListener) {
            listener = (OnFilterButtonPressedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFilterButtonPressedListener");
        }
    }

    // viewholder pattern
    private static class ViewHolder {
        private Spinner spinnerImageSize;
        private Spinner spinnerImageType;
        private Spinner spinnerImageColorization;
        private Spinner spinnerColorFilter;
        private EditText etAsSiteSearch;
        private Button btnFilter;
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

        // setup the view holder
        viewHolder.spinnerImageSize = (Spinner) view.findViewById(R.id.spinnerImageSize);
        viewHolder.spinnerImageType = (Spinner) view.findViewById(R.id.spinnerImageType);
        viewHolder.spinnerImageColorization = (Spinner) view.findViewById(R.id.spinnerImageColorization);
        viewHolder.spinnerColorFilter = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        viewHolder.etAsSiteSearch = (EditText) view.findViewById(R.id.etAsSiteSearch);
        viewHolder.btnFilter = (Button) view.findViewById(R.id.btnFilter);

        // get options from the activity
        SearchFilterOptions opts = getArguments().getParcelable("opts");

        // set dialog title
        getDialog().setTitle(getString(R.string.search_filter));

        // initialize our spinners
        initSpinners(opts);

        // init sitesearch field
        if (opts.getAsSiteSearch() != null) {
            viewHolder.etAsSiteSearch.setText(opts.getAsSiteSearch());
        }

        // Show soft keyboard automatically and request focus to field
        viewHolder.etAsSiteSearch.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // hook in the filter button callback
        viewHolder.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFilterOptions opts = getUpdatedOptions();
                listener.onFilterButtonPressed(opts);
                dismiss();
            }
        });
    }

    private SearchFilterOptions getUpdatedOptions() {
        SearchFilterOptions opts = getArguments().getParcelable("opts");

        int iImageSize = viewHolder.spinnerImageSize.getSelectedItemPosition();
        int iImageType = viewHolder.spinnerImageType.getSelectedItemPosition();
        int iImageColorization = viewHolder.spinnerImageColorization.getSelectedItemPosition();
        int iColorFilter = viewHolder.spinnerColorFilter.getSelectedItemPosition();
        String newAsSiteSearch = viewHolder.etAsSiteSearch.getText().toString();

        if (iImageSize != AdapterView.INVALID_POSITION) {
            String newImageSize = SearchFilterOptions.imageSizes[iImageSize];
            if (!newImageSize.equals(opts.getImageSize())) {
                opts.setImageSize(newImageSize);
            }
        }

        if (iImageType != AdapterView.INVALID_POSITION) {
            String newImageType = SearchFilterOptions.imageTypes[iImageType];
            if (!newImageType.equals(opts.getImageType())) {
                opts.setImageType(newImageType);
            }
        }

        if (iImageColorization != AdapterView.INVALID_POSITION) {
            String newImageColorization = SearchFilterOptions.imageColorizations[iImageColorization];
            if (!newImageColorization.equals(opts.getImageColorization())) {
                opts.setImageColorization(newImageColorization);
            }
        }

        if (iColorFilter != AdapterView.INVALID_POSITION) {
            String newColorFilter = SearchFilterOptions.colorFilters[iColorFilter];
            if (!newColorFilter.equals(opts.getColorFilter())) {
                opts.setColorFilter(newColorFilter);
            }
        }

        if (newAsSiteSearch != null && ! newAsSiteSearch.equals(opts.getAsSiteSearch())) {
            opts.setAsSiteSearch(newAsSiteSearch);
        }

        return opts;
    }

    private void initSpinners(SearchFilterOptions opts) {
        // image size spinner
        ArrayAdapter<String> aaImageSize = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageSizes);
        aaImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageSize.setAdapter(aaImageSize);

        viewHolder.spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHolder.spinnerImageSize.setSelection(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // image type spinner
        ArrayAdapter<String> aaImageType = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageTypes);
        aaImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageType.setAdapter(aaImageType);

        viewHolder.spinnerImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHolder.spinnerImageType.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // image colorization spinner
        ArrayAdapter<String> aaImageColorization = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.imageColorizations);
        aaImageColorization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerImageColorization.setAdapter(aaImageColorization);

        viewHolder.spinnerImageColorization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHolder.spinnerImageColorization.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // color filter spinner
        ArrayAdapter<String> aaColorFilter= new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                SearchFilterOptions.colorFilters);
        aaImageColorization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinnerColorFilter.setAdapter(aaColorFilter);

        viewHolder.spinnerColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewHolder.spinnerColorFilter.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // set default values
        if (opts.getImageSize() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageSizes).indexOf(opts.getImageSize());
            viewHolder.spinnerImageSize.setSelection(pos);
        }
        if (opts.getImageType() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageTypes).indexOf(opts.getImageType());
            viewHolder.spinnerImageType.setSelection(pos);
        }
        if (opts.getImageColorization() != null) {
            int pos = Arrays.asList(SearchFilterOptions.imageColorizations).indexOf(opts.getImageColorization());
            viewHolder.spinnerImageColorization.setSelection(pos);
        }

        if (opts.getColorFilter() != null) {
            int pos = Arrays.asList(SearchFilterOptions.colorFilters).indexOf(opts.getColorFilter());
            viewHolder.spinnerColorFilter.setSelection(pos);
        }
    }
}
