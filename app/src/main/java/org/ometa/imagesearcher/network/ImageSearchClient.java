package org.ometa.imagesearcher.network;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by devin on 10/30/15.
 */
public class ImageSearchClient {
    private static final String TAG = ImageSearchClient.class.getSimpleName();

    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private static final String BASE_QUERY = "?v=1.0&rsz=8";
    private AsyncHttpClient client;

    public ImageSearchClient() {
        client = new AsyncHttpClient();
    }

    private String getApiUrl() {
        return getApiUrl("");
    }
    private String getApiUrl(String path) {
        return API_BASE_URL + path;
    }


    private String getQuery(String query) {
        String str = "";
        try {
            str = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void getImages(final String query, final Map<String, String> opts, JsonHttpResponseHandler handler) {

        String fullQuery = "";
        StringBuffer b = new StringBuffer();
        try {
            for (String key : opts.keySet()) {
                String encKey = URLEncoder.encode(key, "utf-8");
                String encVal = URLEncoder.encode(opts.get(key), "utf-8");
                b.append("&").append(encKey).append("=").append(encVal);
            }
            fullQuery = BASE_QUERY + "&q=" + URLEncoder.encode(query, "utf-8") + b.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Query URL: " + fullQuery);

        client.get(getApiUrl() + fullQuery, handler);
    }

//    public static Map<String, String> buildOpts

//    ?v=1.0&q=fuzzy%20monkey


//    as_sitesearch=photobucket.com
//
//
//  imgc=gray / color

// Size (small, medium, large, extra-large)
// Color filter (black, blue, brown, gray, green, etc...)
// Type (faces, photo, clip art, line art)
// Site (espn.com)
/*




    imgsz
            imgsz=icon
    Restricts the search to images of the specified size, where size can be one of:

    imgsz=icon restricts results to small images
    imgsz=small|medium|large|xlarge restricts results to medium-sized images
    imgsz=xxlarge restricts results to large images
    imgsz=huge restricts resykts to extra-large images


    imgtype (experimental)
    imgtype=face
    Restricts the search to one of the following image types:

    imgtype=face restricts results to images of faces.
            imgtype=photo restricts results to photographic images.
    imgtype=clipart restricts results to clipart images.
    imgtype=lineart restricts results to line drawing images.

// imgcolor (experimental)

    imgcolor=black
    Restricts results to images that contain a specified color predominantly:

    imgcolor=black
            imgcolor=blue
    imgcolor=brown
            imgcolor=gray
    imgcolor=green
            imgcolor=orange
    imgcolor=pink
            imgcolor=purple
    imgcolor=red
            imgcolor=teal
    imgcolor=white
            imgcolor=yellow


start
start=4
This argument supplies the start index of the first search result. Each successful response contains a cursor object (see below) which includes an array of pages. The start property for a page may be used as a valid value for this argument. For reference, a sample cursor object is shown below:

"cursor": {
  "pages": [[
    { "start": "0", "label": 1 },
    { "start": "4", "label": 2 },
    { "start": "8", "label": 3 },
    { "start": "12","label": 4 } ]],
  "estimatedResultCount": "48758",
  "currentPageIndex": 0,
  "moreResultsUrl": "http://www.google.com/search..."
}




*/
}
