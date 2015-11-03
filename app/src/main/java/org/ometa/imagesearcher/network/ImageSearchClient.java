package org.ometa.imagesearcher.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.ometa.imagesearcher.models.SearchOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by devin on 10/30/15.
 *
 * start
 * start=4
 * This argument supplies the start index of the first search result. Each successful response contains a cursor object (see below) which includes an array of pages. The start property for a page may be used as a valid value for this argument. For reference, a sample cursor object is shown below:
 *
 * "cursor": {
 * "pages": [[
 * { "start": "0", "label": 1 },
 * { "start": "4", "label": 2 },
 * { "start": "8", "label": 3 },
 * { "start": "12","label": 4 } ]],
 * "estimatedResultCount": "48758",
 * "currentPageIndex": 0,
 * "moreResultsUrl": "http://www.google.com/search..."
 *  }
 */
public class ImageSearchClient {
    private static final String TAG = ImageSearchClient.class.getSimpleName();

    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private static final String BASE_QUERY = "?v=1.0&rsz=8";
    private AsyncHttpClient client;

    public ImageSearchClient() {
        client = new AsyncHttpClient();
    }

    public void getImages(final String searchString, SearchOptions opts, int start, JsonHttpResponseHandler handler) {

        // setup the query string
        String fullQuery = null;
        try {
            fullQuery = BASE_QUERY + "&q=" + URLEncoder.encode(searchString, "utf-8")
                    + opts.toQuery() + "&start=" + start;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // call the api
        client.get(API_BASE_URL + fullQuery, handler);
    }
}
