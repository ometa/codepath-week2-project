package org.ometa.imagesearcher.models;

/**
 * Created by devin on 11/1/15.
 * This feels so verbose. There must be an easier way.
 */
public class SearchFilterOptions {

    // size

    public enum Size {
        ICON, MEDIUM, LARGE, XLARGE
    }

    public final String ICON = "Icon";
    public final String MEDIUM= "Medium";
    public final String LARGE = "Large";
    public final String XLARGE ="X-Large";

    private Size imageSize;

    public String getImageSizeQuery() {
        switch (imageSize) {
            case ICON:
                return "icon";
            case MEDIUM:
                return "medium";    // small|medium|large|xlarge
            case LARGE:
                return "xxlarge";
            case XLARGE:
                return "huge";
            default:
                return "medium";
        }
    }

    public void setImageSize(Size imageSize) {
        this.imageSize = imageSize;
    }

    // as_sitesearch

    private String asSiteSearch; // "photobucket.com"

    public String getAsSiteSearch() {
        return asSiteSearch;
    }

    public void setAsSiteSearch(String asSiteSearch) {
        this.asSiteSearch = asSiteSearch;
    }


    // imgType

    public enum ImgType {
        FACE, PHOTO, CLIPART, LINEART
    }

    public final String FACE = "Faces";
    public final String PHOTO ="Photo";
    public final String CLIPART= "Clip Art";
    public final String LINEART = "Line Drawings";

    private ImgType imgType;

    public String getImageTypeQuery() {
        switch (imgType) {
            case FACE:
                return "face";
            case PHOTO:
                return "photo";
            case CLIPART:
                return "clipart";
            case LINEART:
                return "lineart";
            default:
                return "photo";
        }
    }


    // imgc


    public enum ImgColoration {
        IMGC_GRAY, IMGC_COLOR
    }
    public final String IMGC_GRAY = "Grayscale";
    public final String IMGC_COLOR = "Color";
    private ImgColoration imgColoration;

    public String getImgColorationQuery() {
        switch (imgColoration) {
            case IMGC_COLOR:
                return "color";
            case IMGC_GRAY:
                return "gray";
            default:
                return "color";
        }
    }


    public enum ImgColor {
        IMGCOLOR_, IMGCOLOR_, IMGCOLOR_, IMGCOLOR_, IMGCOLOR_,
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

    private ImgColor imgColor;

    public String getImgColor() {
        switch (imgColor) {
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
                return "oeange";
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


    public String buildQuery() {
//        return "as_sitesearch=" + asSiteSearch;
//        return "imgsz=" + getImageSizeQuery();
//        return "imgtype=" + getImageTypeQuery();
        return "imgc=" + getImgColorationQuery();
    }






}

// Size (small, medium, large, extra-large)
// Color filter (black, blue, brown, gray, green, etc...)
// Type (faces, photo, clip art, line art)
// Site (espn.com)
/*



}
