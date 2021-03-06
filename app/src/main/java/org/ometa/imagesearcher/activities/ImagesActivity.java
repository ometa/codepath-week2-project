package org.ometa.imagesearcher.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.adapters.ImageAdapter;
import org.ometa.imagesearcher.fragments.FilterDialog;
import org.ometa.imagesearcher.listeners.EndlessScrollListener;
import org.ometa.imagesearcher.models.Image;
import org.ometa.imagesearcher.models.SearchOptions;
import org.ometa.imagesearcher.network.ImageSearchClient;
import org.ometa.imagesearcher.network.Network;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


// display search form in action bar
// accept user input, display spinner, search google api
// display returned api data

// User can enter a search query that will display a grid of image results from the Google Image API.
// User can click on "settings" which allows selection of advanced search options to filter results
// User can configure advanced search filters such as:
// Size (small, medium, large, extra-large)
// Color filter (black, blue, brown, gray, green, etc...)
// Type (faces, photo, clip art, line art)
// Site (espn.com)
// Subsequent searches will have any filters applied to the search results
// User can tap on any image in results to see the image full-screen
// User can scroll down “infinitely” to continue loading more image results (up to 8 pages)


public class ImagesActivity extends AppCompatActivity implements FilterDialog.OnFilterButtonPressedListener {
    private static final String TAG = ImagesActivity.class.getSimpleName();

    private MenuItem miSearchSpinner;
    private ImageAdapter adapter;
    private ImageSearchClient client;

    // hold state of our image query
    private SearchOptions filterOptions;
    private String currentQuery;

    // helper variables for the API calls
    private int totalPages;

    // view holder
    private static class ViewHolder {
        GridView gvImages;
    }
    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewHolder = new ViewHolder();
        viewHolder.gvImages = (GridView) findViewById(R.id.gvImages);

        client = new ImageSearchClient();
        filterOptions = new SearchOptions();

        // init the gridview w/ an empty array of images
        adapter = new ImageAdapter(this, new ArrayList<Image>());
        viewHolder.gvImages.setAdapter(adapter);

        // endless scrolling
        viewHolder.gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (pageExists(page)) {
                    basicApiLoad(page);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private boolean pageExists(int page) {
        if (page <= totalPages) return true;
        return false;
    }

    private void basicApiLoad() {
        basicApiLoad(0);
    }

    private void basicApiLoad(int starting_at_page) {
        int start_at = Math.abs(starting_at_page - 1) * 8;
        client.getImages(currentQuery, filterOptions, start_at, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject rd;
                    rd = response.getJSONObject("responseData");
                    JSONArray results = rd.getJSONArray("results");
                    JSONObject cursor = rd.getJSONObject("cursor");

                    final ArrayList<Image> images = Image.fromJson(results);
                    for (Image image : images) {
                        adapter.add(image);
                    }
                    adapter.notifyDataSetChanged();

                    totalPages = cursor.has("pages") ? cursor.getJSONArray("pages").length() : 0;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Response code: " + Integer.toString(statusCode));
                Log.d(TAG, "Response: " + responseString);
                Log.d(TAG, "Headers: " + headers.toString());
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    // ---------------------------------------------------
    // dialog

    private void showSearchFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialog sfd = FilterDialog.newInstance(filterOptions);
        sfd.show(fm, "fragment_search_filter");
    }

    // ---------------------------------------------------

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        miSearchSpinner = menu.findItem(R.id.miSearchSpinner);
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miSearchSpinner);
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        miSearchSpinner.setVisible(true);
    }

    public void hideProgressBar() {
        miSearchSpinner.setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_images, menu);

        // init search bar
        final MenuItem searchItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Network.isDown(getBaseContext())) {
                    Toast.makeText(getBaseContext(), "No internet connection detected. Please check settings and try again.", Toast.LENGTH_LONG).show();
                    return true;
                }
                // only update and query api if value changed
                if (currentQuery == null || !currentQuery.equals(query)) {
                    currentQuery = query;
                    adapter.clear();
                    basicApiLoad();
                }
                MenuItemCompat.collapseActionView(searchItem);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
                // todo: how to use this callback without having the api go nuts
                // and reset after we click the back arrow?
                // todo: how to not run this after every keystroke if the user is
                // still typing?
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnuFilter) {
            showSearchFilterDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFilterButtonPressed(SearchOptions opts) {
        filterOptions = opts;

        if (Network.isDown(getBaseContext())) {
            Toast.makeText(getParent(), "No internet connection detected. Please check settings and try again.", Toast.LENGTH_LONG).show();
        } else {
            // only update the results if we have a query string
            if (currentQuery != null) {
                adapter.clear();
                basicApiLoad();
            }
        }
    }
}
