package org.ometa.imagesearcher.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.activities.ImageDetailActivity;
import org.ometa.imagesearcher.models.Image;

import java.util.ArrayList;

/**
 * Created by devin on 10/30/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Image> images;

    public ImageAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
    }

    public void clear() {
        images = new ArrayList<>();
    }

    public void add(Image image) {
        images.add(image);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (convertView == null) {
            view = new ImageView(context);
        }

        String url = getItem(position).getTbUrl();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.blurry_background)
                .into(view);
        view.setAdjustViewBounds(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("image", getItem(position));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
