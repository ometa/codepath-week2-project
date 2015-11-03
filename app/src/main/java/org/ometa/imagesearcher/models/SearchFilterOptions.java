package org.ometa.imagesearcher.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by devin on 11/1/15.
 * This feels so verbose. There must be an easier way.
 * See: public final LinkedHashMap<String, String>
 *      http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
 */
public class SearchFilterOptions implements Parcelable {

    public SearchFilterOptions() {
        imageSize = null;
        asSiteSearch = null;
        imageType = null;
        imageColorization = null;
        colorFilter = null;
    }

    // todo: I am sure there are more efficient ways of doing this.
    public String toQuery() {
        StringBuilder sb = new StringBuilder();
        if (asSiteSearch != null) {
            sb.append("&as_sitesearch=").append(asSiteSearch);
        }
        if (imageSize != null) {
            sb.append("&imgsz=").append(getImageSizeKey());
        }
        if (imageType != null) {
            sb.append("&imgtype=").append(getImageTypeKey());
        }
        if (imageColorization != null) {
            sb.append("&imgc=").append(getImageColorizationKey());
        }
        if (colorFilter != null) {
            sb.append("&imgcolor=").append(getColorFilterKey());
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------
    // size

    public final static String SIZE_ICON = "Icon";
    public final static String SIZE_MEDIUM = "Medium";
    public final static String SIZE_LARGE = "Large";
    public final static String SIZE_XLARGE = "X-Large";
    public final static String[] imageSizes = {SIZE_ICON, SIZE_MEDIUM, SIZE_LARGE, SIZE_XLARGE};

    private final static LinkedHashMap<String, String> imageSizeKeys;
    static {
        imageSizeKeys = new LinkedHashMap<>();
        imageSizeKeys.put(SIZE_ICON, "icon");
        imageSizeKeys.put(SIZE_MEDIUM, "medium");
        imageSizeKeys.put(SIZE_LARGE, "xxlarge");
        imageSizeKeys.put(SIZE_XLARGE, "huge");
    }

    private String imageSize;

    public String getImageSize() {
        return imageSize;
    }

    public String getImageSizeKey() {

        // todo: This is gross.
        if (imageSize == null) {
            return null;
        }
        if (! imageSizeKeys.containsValue(imageSize)) {
           return null;
        }

        return imageSizeKeys.get(imageSize);
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    // ---------------------------------------------------------------
    // as_sitesearch

    private String asSiteSearch; // "photobucket.com"

    public String getAsSiteSearch() {
        return asSiteSearch;
    }

    public void setAsSiteSearch(String asSiteSearch) {
        this.asSiteSearch = asSiteSearch;
    }

    // ---------------------------------------------------------------
    // imgType

    public static final String TYPE_FACE = "Faces";
    public static final String TYPE_PHOTO = "Photo";
    public static final String TYPE_CLIPART = "Clip Art";
    public static final String TYPE_LINEART = "Line Drawings";
    public static final String[] imageTypes = {TYPE_FACE, TYPE_PHOTO, TYPE_CLIPART, TYPE_LINEART};
    
    private static final HashMap<String, String> imageTypeKeys;
    static {
        imageTypeKeys = new HashMap<>();
        imageTypeKeys.put(TYPE_FACE, "face");
        imageTypeKeys.put(TYPE_PHOTO, "photo");
        imageTypeKeys.put(TYPE_CLIPART, "clipart");
        imageTypeKeys.put(TYPE_LINEART, "lineart");
    }

    private String imageType;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageTypeKey() {

        // todo: This is gross.
        if (imageType == null) {
            return null;
        }
        if (! imageTypeKeys.containsValue(imageType)) {
            return null;
        }

        return imageTypeKeys.get(imageType);
    }

    // ---------------------------------------------------------------
    // imgc (color or greyscale?

    public static final String IMGC_GRAY = "Grayscale";
    public static final String IMGC_COLOR = "Color";
    public static String[] imageColorizations = {IMGC_GRAY, IMGC_COLOR};
            
    private static final HashMap<String, String> imageColorizationKeys;
    static {
        imageColorizationKeys = new HashMap<>();
        imageColorizationKeys.put(IMGC_GRAY, "gray");
        imageColorizationKeys.put(IMGC_COLOR, "color");
    }

    private String imageColorization;

    public String getImageColorization() {
        return imageColorization;
    }

    public void setImageColorization(String imageColorization) {
        this.imageColorization = imageColorization;
    }

    public String getImageColorizationKey() {

        // todo: This is gross.
        if (imageType == null) {
            return null;
        }
        if (! imageColorizationKeys.containsValue(imageColorization)) {
            return null;
        }

        return imageColorizationKeys.get(imageColorization);
    }

    // ---------------------------------------------------------------
    // imgcolor

    public static final String IMGCOLOR_BLACK = "Grayscale";
    public static final String IMGCOLOR_BLUE = "Blue";
    public static final String IMGCOLOR_BROWN = "Brown";
    public static final String IMGCOLOR_GRAY = "Gray";
    public static final String IMGCOLOR_GREEN = "Green";
    public static final String IMGCOLOR_ORANGE = "Orange";
    public static final String IMGCOLOR_PINK = "Pink";
    public static final String IMGCOLOR_PURPLE = "Purple";
    public static final String IMGCOLOR_RED = "Red";
    public static final String IMGCOLOR_TEAL = "Teal";
    public static final String IMGCOLOR_WHITE = "White";
    public static final String IMGCOLOR_YELLOW = "Yellow";
    public static final String[] colorFilters = {
        IMGCOLOR_BLACK, IMGCOLOR_BLUE, IMGCOLOR_BROWN, IMGCOLOR_GRAY, IMGCOLOR_GREEN,
        IMGCOLOR_ORANGE, IMGCOLOR_PINK, IMGCOLOR_PURPLE, IMGCOLOR_RED, IMGCOLOR_TEAL,
        IMGCOLOR_WHITE, IMGCOLOR_YELLOW
    };

    private static final HashMap<String, String> colorFilterKeys;
    static {
        colorFilterKeys = new HashMap<>();
        colorFilterKeys.put(IMGCOLOR_BLACK, "black");
        colorFilterKeys.put(IMGCOLOR_BLUE, "blue");
        colorFilterKeys.put(IMGCOLOR_BROWN, "brown");
        colorFilterKeys.put(IMGCOLOR_GRAY, "gray");
        colorFilterKeys.put(IMGCOLOR_GREEN, "green");
        colorFilterKeys.put(IMGCOLOR_ORANGE, "orange");
        colorFilterKeys.put(IMGCOLOR_PINK, "pink");
        colorFilterKeys.put(IMGCOLOR_PURPLE, "purple");
        colorFilterKeys.put(IMGCOLOR_RED, "red");
        colorFilterKeys.put(IMGCOLOR_TEAL, "teal");
        colorFilterKeys.put(IMGCOLOR_WHITE, "white");
        colorFilterKeys.put(IMGCOLOR_YELLOW, "yellow");
    }

    private String colorFilter;

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getColorFilterKey() {
        // todo: This is gross.
        if (colorFilter == null) {
            return null;
        }
        if (! colorFilterKeys.containsValue(colorFilter)) {
            return null;
        }

        return colorFilterKeys.get(colorFilter);
    }

    // ---------------------------------------------------------------
    // parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.asSiteSearch);
        dest.writeString(this.imageType);
        dest.writeString(this.imageColorization);
        dest.writeString(this.colorFilter);
    }

    protected SearchFilterOptions(Parcel in) {
        this.imageSize = in.readString();
        this.asSiteSearch = in.readString();
        this.imageType = in.readString();
        this.imageColorization = in.readString();
        this.colorFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchFilterOptions> CREATOR = new Parcelable.Creator<SearchFilterOptions>() {
        public SearchFilterOptions createFromParcel(Parcel source) {
            return new SearchFilterOptions(source);
        }

        public SearchFilterOptions[] newArray(int size) {
            return new SearchFilterOptions[size];
        }
    };
}