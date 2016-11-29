package com.akash.applications.myfaceindia;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomImage extends Activity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zoom_image);

        iv = (ImageView)findViewById(R.id.zoomImageView);
        Glide.with(ZoomImage.this)
                .load(getIntent().getStringExtra("url"))
                .placeholder(R.drawable.loadingimg)
                .into(iv);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(iv);
        photoViewAttacher.update();


    }
}
