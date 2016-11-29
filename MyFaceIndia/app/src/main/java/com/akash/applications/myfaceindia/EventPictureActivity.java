package com.akash.applications.myfaceindia;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EventPictureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_event_picture);
        ImageView iv = (ImageView)findViewById(R.id.EventImageView);

        Glide.with(getBaseContext()).load(getIntent().getStringExtra("imgurl"))
                .centerCrop()
                .placeholder(R.drawable.loading_image_imageview)
                .into(iv);
    }
}
