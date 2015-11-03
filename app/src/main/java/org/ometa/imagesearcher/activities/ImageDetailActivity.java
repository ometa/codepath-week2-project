package org.ometa.imagesearcher.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.ometa.imagesearcher.R;
import org.ometa.imagesearcher.models.Image;

public class ImageDetailActivity extends AppCompatActivity {

    private static class ViewHolder {
        private ImageView ivImageBig;
    }

    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch views
        viewHolder = new ViewHolder();
        viewHolder.ivImageBig = (ImageView) findViewById(R.id.ivImageBig);

        // Extract book object from intent extras
        Intent intent = getIntent();
        Image image = intent.getParcelableExtra("image");

        // Populate data into views
        Picasso.with(this).load(Uri.parse(image.getUrl()))

                .placeholder(R.drawable.blurry_background)
                .into(viewHolder.ivImageBig, new Callback() {
                    @Override
                    public void onSuccess() {
                        //setupShareIntent();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
