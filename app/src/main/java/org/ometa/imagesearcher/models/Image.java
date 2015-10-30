package org.ometa.imagesearcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

    /*
            "width": "1152",
        "height": "864",
        "imageId": "ANd9GcQQigy-U6KTXke82n5hma5qvFM2UyVnkGtJme6pkZgl_1GYM--Yb90oqnOJ",
        "tbWidth": "150",
        "tbHeight": "113",
        "unescapedUrl": "http://www.blirk.net/wallpapers/1152x864/fuzzy-monkey-1.jpg",
        "url": "http://www.blirk.net/wallpapers/1152x864/fuzzy-monkey-1.jpg",
        "visibleUrl": "stackoverflow.com",
        "title": "json - rails best way to extract values from response hash - Stack <b>...</b>",
        "titleNoFormatting": "json - rails best way to extract values from response hash - Stack ...",
        "originalContextUrl": "http://stackoverflow.com/questions/17773949/rails-best-way-to-extract-values-from-response-hash",
        "content": "json - rails best way to",
        "contentNoFormatting": "json - rails best way to",
        "tbUrl": "http://t1.gstatic.com/images?q=tbn:ANd9GcQQigy-U6KTXke82n5hma5qvFM2UyVnkGtJme6pkZgl_1GYM--Yb90oqnOJ"
     */


/**
 * Created by devin on 10/30/15.
 */
public class Image {

    private int width;
    private int height;
    private String imageId;
    private int tbWidth;
    private int tbHeight;
    private String unescapedUrl;
    private String url;
    private String visibleUrl;
    private String title;
    private String titleNoFormatting;
    private String originalContextUrl;
    private String content;
    private String contentNoFormatting;
    private String tbUrl;

    public Image() {}

    // Decodes array of image json results into business model objects
    public static ArrayList<Image> fromJson(JSONArray jsonArray) {
        ArrayList<Image> images = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject imageJson = null;
            try {
                imageJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Image image = Image.fromJson(imageJson);

            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }

    // Returns a Image given the expected JSON
    public static Image fromJson(JSONObject jsonObject) {
        Image image = new Image();
        try {
            image.width = findOrZero("width", jsonObject);
            image.height = findOrZero("height", jsonObject);
            image.tbWidth = findOrZero("tbWidth", jsonObject);
            image.tbHeight = findOrZero("tbHeight", jsonObject);
            image.imageId = findOrBlank("imageId", jsonObject);
            image.title = findOrBlank("title", jsonObject);
            image.titleNoFormatting = findOrBlank("titleNoFormatting", jsonObject);
            image.content = findOrBlank("content", jsonObject);
            image.contentNoFormatting = findOrBlank("contentNoFormatting", jsonObject);
            image.url = findOrBlank("url", jsonObject);
            image.tbUrl = findOrBlank("tbUrl", jsonObject);
            image.visibleUrl = findOrBlank("visibleUrl", jsonObject);
            image.unescapedUrl = findOrBlank("unescapedUrl", jsonObject);
            image.originalContextUrl = findOrBlank("originalContextUrl", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    private static int findOrZero(String field, JSONObject obj) throws JSONException {
        return obj.has(field) ? Integer.parseInt(obj.getString(field)) : 0;
    }


    private static String findOrBlank(String field, JSONObject obj) throws JSONException {
        return obj.has(field) ? obj.getString(field) : "";
    }

    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImageId() {
        return imageId;
    }

    public int getTbWidth() {
        return tbWidth;
    }

    public int getTbHeight() {
        return tbHeight;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }

    public String getContent() {
        return content;
    }

    public String getContentNoFormatting() {
        return contentNoFormatting;
    }

    public String getTbUrl() {
        return tbUrl;
    }

}
