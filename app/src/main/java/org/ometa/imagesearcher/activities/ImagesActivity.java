package org.ometa.imagesearcher.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.adapters.ImageAdapter;
import org.ometa.imagesearcher.models.Image;
import org.ometa.imagesearcher.network.ImageSearchClient;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ImagesActivity extends AppCompatActivity {
    private static final String TAG = ImagesActivity.class.getSimpleName();

    private GridView gvImages;
    private ImageAdapter adapter;
    private ImageSearchClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gvImages = (GridView) findViewById(R.id.gvImages);
        final ArrayList<Image> images = new ArrayList<Image>();
        adapter = new ImageAdapter(this, images);
        gvImages.setAdapter(adapter);

        client = new ImageSearchClient();
        fetchAllImages("cats");
    }

    private int totalPages;
    private int currentPageIndex;

    private void fetchAllImages(String query) {
//        showProgressBar();
        HashMap<String, String> opts = new HashMap<>();  // todo: customize this with a dialogue
        totalPages = 0;
        currentPageIndex = -1;
        fetchImages(query, opts);
//        hideProgressBar();
    }

    private void fetchImages(final String query, final HashMap<String, String> opts) {
        client.getImages(query, opts, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject rd = null;
                    if (response != null) {
                        rd = response.getJSONObject("responseData");
                        JSONArray results = rd.getJSONArray("results");
                        JSONObject cursor = rd.getJSONObject("cursor");

                        final ArrayList<Image> images = Image.fromJson(results);
                        for (Image image : images) {
                            adapter.add(image);
                        }
                        adapter.notifyDataSetChanged();

                        currentPageIndex = cursor.has("currentPageIndex") ? cursor.getInt("currentPageIndex") : 0;
                        totalPages = cursor.has("pages") ? cursor.getJSONArray("pages").length() : 0;

                        if (morePagesToGo()) {
                            HashMap<String, String> newOpts = new HashMap<>(opts);
                            int start = (currentPageIndex * 8) + 8;
                            newOpts.put("start", String.valueOf(start));

                            // todo: here is the recursion. try to polish this.
                            fetchImages(query, newOpts);
                        }
                    }
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

    private boolean morePagesToGo() {
        if (currentPageIndex < totalPages - 1) {
            return true;
        }
        return false;
    }

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

    MenuItem miSearchSpinner;

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

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clear();
                fetchAllImages(query);
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

        if (id == R.id.actionSettings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
