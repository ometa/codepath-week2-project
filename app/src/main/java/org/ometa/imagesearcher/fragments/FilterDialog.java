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
import android.widget.TextView;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.models.SearchOptions;

import java.util.Arrays;

/**
 * Created by devin on 11/1/15.
 */
public class FilterDialog extends DialogFragment {

    public FilterDialog() {}

    // fragment listener
    private OnFilterButtonPressedListener listener;

    public interface OnFilterButtonPressedListener {
        void onFilterButtonPressed(SearchOptions opts);
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

    // view holder pattern
    private static class ViewHolder {
        private Spinner spinnerImageSize;
        private Spinner spinnerImageType;
        private Spinner spinnerImageColorization;
        private Spinner spinnerColorFilter;
        private EditText etAsSiteSearch;
        private Button btnFilter;
        private Button btnReset;
    }
    private ViewHolder viewHolder;

    public static FilterDialog newInstance(SearchOptions opts) {
        Bundle args = new Bundle();
        args.putParcelable("opts", opts);
        FilterDialog frag = new FilterDialog();
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

        // set dialog title
        getDialog().setTitle(getString(R.string.search_filter));

        // get options from the activity
        SearchOptions opts = getArguments().getParcelable("opts");

        // setup view holder
        viewHolder.spinnerImageSize = (Spinner) view.findViewById(R.id.spinnerImageSize);
        viewHolder.spinnerImageType = (Spinner) view.findViewById(R.id.spinnerImageType);
        viewHolder.spinnerImageColorization = (Spinner) view.findViewById(R.id.spinnerImageColorization);
        viewHolder.spinnerColorFilter = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        viewHolder.etAsSiteSearch = (EditText) view.findViewById(R.id.etAsSiteSearch);
        viewHolder.btnFilter = (Button) view.findViewById(R.id.btnFilter);
        viewHolder.btnReset = (Button) view.findViewById(R.id.btnReset);

        // initialize our spinners
        initSpinner(viewHolder.spinnerImageSize, SearchOptions.imageSizes, opts.getImageSize());
        initSpinner(viewHolder.spinnerImageType, SearchOptions.imageTypes, opts.getImageType());
        initSpinner(viewHolder.spinnerImageColorization, SearchOptions.imageColorizations, opts.getImageColorization());
        initSpinner(viewHolder.spinnerColorFilter, SearchOptions.colorFilters, opts.getColorFilter());

        // initialize the as_sitesearch field
        if (opts.getAsSiteSearch() != null) {
            viewHolder.etAsSiteSearch.setText(opts.getAsSiteSearch());
        }
        viewHolder.etAsSiteSearch.requestFocus();

        // hook in the filter button
        viewHolder.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchOptions opts = getUpdatedOptions();
                listener.onFilterButtonPressed(opts);
                dismiss();
            }
        });

        // hook in the reset button
        viewHolder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFilterButtonPressed(new SearchOptions());
                dismiss();
            }
        });
    }

    // This checks for differences between the options object and the various views,
    // and updates and returns the options object with any new values.
    private SearchOptions getUpdatedOptions() {
        SearchOptions opts = getArguments().getParcelable("opts");

        int iImageSize = viewHolder.spinnerImageSize.getSelectedItemPosition();
        int iImageType = viewHolder.spinnerImageType.getSelectedItemPosition();
        int iImageColorization = viewHolder.spinnerImageColorization.getSelectedItemPosition();
        int iColorFilter = viewHolder.spinnerColorFilter.getSelectedItemPosition();
        String newAsSiteSearch = viewHolder.etAsSiteSearch.getText().toString();

        if (iImageSize != AdapterView.INVALID_POSITION) {
            String newImageSize = SearchOptions.imageSizes[iImageSize];
            if (!newImageSize.equals(opts.getImageSize())) {
                opts.setImageSize(newImageSize);
            }
        }

        if (iImageType != AdapterView.INVALID_POSITION) {
            String newImageType = SearchOptions.imageTypes[iImageType];
            if (!newImageType.equals(opts.getImageType())) {
                opts.setImageType(newImageType);
            }
        }

        if (iImageColorization != AdapterView.INVALID_POSITION) {
            String newImageColorization = SearchOptions.imageColorizations[iImageColorization];
            if (!newImageColorization.equals(opts.getImageColorization())) {
                opts.setImageColorization(newImageColorization);
            }
        }

        if (iColorFilter != AdapterView.INVALID_POSITION) {
            String newColorFilter = SearchOptions.colorFilters[iColorFilter];
            if (!newColorFilter.equals(opts.getColorFilter())) {
                opts.setColorFilter(newColorFilter);
            }
        }

        if (newAsSiteSearch != null && ! newAsSiteSearch.equals(opts.getAsSiteSearch())) {
            opts.setAsSiteSearch(newAsSiteSearch);
        }

        return opts;
    }

    private void initSpinner(final Spinner spinner, String[] optionsArray, String startingValue) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                optionsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    // add the hint text to the very end
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // do not display last item. It is used as hint.
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (startingValue == null) {
            // set the hint the default selection so it appears on launch
            spinner.setSelection(adapter.getCount());
        } else {
            // set the spinner to the correct entry
            int pos = Arrays.asList(optionsArray).indexOf(startingValue);
            spinner.setSelection(pos);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}