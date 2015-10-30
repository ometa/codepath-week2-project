package org.ometa.imagesearcher.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.ometa.imagesearcher.models.Image;

import java.util.ArrayList;

/**
 * Created by devin on 10/30/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Image> images;

    public ImageAdapter(Context c, ArrayList<Image> i) {
        context = c;
        images = i;
    }

    public int getCount() {
        return images.size();
    }

    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (convertView == null) {
            view = new ImageView(context);
        }

        String url = getItem(position).getTbUrl();
        Picasso.with(context).load(url).into(view);  // .placeholder(R.drawable.ic_nocover)
        view.setLayoutParams(new GridView.LayoutParams(85, 85));
        view.setPadding(8, 8, 8, 8);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    public void clear() {
        images = new ArrayList<>();
    }

    public void add(Image image) {
        images.add(image);
    }
}
