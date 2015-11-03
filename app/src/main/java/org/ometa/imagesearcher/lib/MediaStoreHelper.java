package org.ometa.imagesearcher.lib;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by dbreen on 10/29/15.
 */
public class MediaStoreHelper {

    public static Uri getImage(ImageView image, ContentResolver contentResolver) {
        MediaStoreHelper.initMediaDir();

        Drawable mDrawable = image.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(contentResolver,
                mBitmap, "Image Description", null);

        Uri uri = Uri.parse(path);
        return uri;
    }

    // There's an emulator bug, see:
    // https://code.google.com/p/android/issues/detail?id=75447
    public static void initMediaDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        if (sdcard == null) { return; }
        File dcim = new File(sdcard, "DCIM");
        if (dcim == null) { return; }
        File camera = new File(dcim, "Camera");
        if (camera.exists()) { return; }
        camera.mkdir();
    }
}
