package org.ometa.imagesearcher.models;

/**
 * Created by devin on 11/1/15.
 * This feels so verbose. There must be an easier way.
 * See: public final LinkedHashMap<String, String>
 *      http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
 */
public class SearchFilterOptions {

    public SearchFilterOptions() {
        imageSize = null;
        asSiteSearch = null;
        imageType = null;
        imgColoration = null;
        colorFilter = null;
    }

    // todo: I am sure there are more efficient ways of doing this.
    public String toQuery() {
        StringBuilder sb = new StringBuilder();
        if (asSiteSearch != null) {
            sb.append("&as_sitesearch=").append(asSiteSearch);
        }
        if (imageSize != null) {
            sb.append("&imgsz=").append(getImageSize());
        }
        if (imageType != null) {
            sb.append("&imgtype=").append(getImageType());
        }
        if (imgColoration != null) {
            sb.append("&imgc=").append(getImgColoration());
        }
        if (colorFilter != null) {
            sb.append("&imgcolor=").append(getColorFilter());
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------
    // size

    public enum Size {
        SIZE_ICON, SIZE_MEDIUM, SIZE_LARGE, SIZE_XLARGE;

        public static boolean contains(String str) {
            try {
                ImgType.valueOf(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public final String SIZE_ICON = "Icon";
    public final String SIZE_MEDIUM= "Medium";
    public final String SIZE_LARGE = "Large";
    public final String SIZE_XLARGE ="X-Large";

    private String imageSize;

    public String getImageSize() {

        // todo: This is gross.
        if(imageSize == null) {
            return null;
        }

        switch (imageSize) {
            case SIZE_ICON:
                return "icon";
            case SIZE_MEDIUM:
                return "medium";    // small|medium|large|xlarge
            case SIZE_LARGE:
                return "xxlarge";
            case SIZE_XLARGE:
                return "huge";
            default:
                return "medium";
        }
    }

    public void setImageSize(String imageSize) {
        if (Size.contains(imageSize)) {
            this.imageSize = imageSize;
        }
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

    public enum ImgType {
        TYPE_FACE, TYPE_PHOTO, TYPE_CLIPART, TYPE_LINEART;

        public static boolean contains(String str) {
            try {
                ImgType.valueOf(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public final String TYPE_FACE = "Faces";
    public final String TYPE_PHOTO ="Photo";
    public final String TYPE_CLIPART= "Clip Art";
    public final String TYPE_LINEART = "Line Drawings";

    private String imageType;

    public String getImageType() {

        // todo: This is gross.
        if(imageType == null) {
            return null;
        }

        switch (imageType) {
            case TYPE_FACE:
                return "face";
            case TYPE_PHOTO:
                return "photo";
            case TYPE_CLIPART:
                return "clipart";
            case TYPE_LINEART:
                return "lineart";
            default:
                return "photo";
        }
    }

    public void setImageType(String imageType) {
        if (ImgType.contains(imageType)) {
            this.imageType = imageType;
        }
    }

    // ---------------------------------------------------------------
    // imgc (color or greyscale?

    public enum ImgColoration {
        IMGC_GRAY, IMGC_COLOR;

        public static boolean contains(String str) {
            try {
                ImgColoration.valueOf(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
    public final String IMGC_GRAY = "Grayscale";
    public final String IMGC_COLOR = "Color";

    private String imgColoration;

    public String getImgColoration() {

        // todo: This is gross.
        if(imgColoration == null) {
            return null;
        }

        switch (imgColoration) {
            case IMGC_COLOR:
                return "color";
            case IMGC_GRAY:
                return "gray";
            default:
                return "color";
        }
    }

    public void setImgColoration(String imgColoration) {
        if (ImgColoration.contains(imgColoration)) {
            this.imgColoration = imgColoration;
        }
    }

    // ---------------------------------------------------------------
    // imgcolor

    public enum ColorFilter {
        IMGCOLOR_BLACK, IMGCOLOR_BLUE, IMGCOLOR_BROWN, IMGCOLOR_GRAY, IMGCOLOR_GREEN,
        IMGCOLOR_ORANGE, IMGCOLOR_PINK, IMGCOLOR_PURPLE, IMGCOLOR_RED, IMGCOLOR_TEAL,
        IMGCOLOR_WHITE, IMGCOLOR_YELLOW;

        public static boolean contains(String str) {
            try {
                ColorFilter.valueOf(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public final String IMGCOLOR_BLACK = "Grayscale";
    public final String IMGCOLOR_BLUE = "Blue";
    public final String IMGCOLOR_BROWN = "Brown";
    public final String IMGCOLOR_GRAY = "Gray";
    public final String IMGCOLOR_GREEN = "Green";
    public final String IMGCOLOR_ORANGE = "Orange";
    public final String IMGCOLOR_PINK = "Pink";
    public final String IMGCOLOR_PURPLE = "Purple";
    public final String IMGCOLOR_RED = "Red";
    public final String IMGCOLOR_TEAL = "Teal";
    public final String IMGCOLOR_WHITE = "White";
    public final String IMGCOLOR_YELLOW = "Yellow";

    private String colorFilter;

    public String getColorFilter() {

        // todo: This is gross.
        if(colorFilter == null) {
            return null;
        }

        switch (colorFilter) {
            case IMGCOLOR_BLACK:
                return "black";
            case IMGCOLOR_BLUE:
                return "blue";
            case IMGCOLOR_BROWN:
                return "brown";
            case IMGCOLOR_GRAY:
                return "gray";
            case IMGCOLOR_GREEN:
                return "green";
            case IMGCOLOR_ORANGE:
                return "orange";
            case IMGCOLOR_PINK:
                return "pink";
            case IMGCOLOR_PURPLE:
                return "purple";
            case IMGCOLOR_RED:
                return "red";
            case IMGCOLOR_TEAL:
                return "teal";
            case IMGCOLOR_WHITE:
                return "white";
            case IMGCOLOR_YELLOW:
                return "yellow";
            default:
                return "black";
        }
    }

    public void setColorFilter(String colorFilter) {
        if (ColorFilter.contains(colorFilter)) {
            this.colorFilter = colorFilter;
        }
    }
}
