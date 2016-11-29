package com.akash.applications.myfaceindia;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ZoomInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_in);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZoomInActivity.this.finish();
            }
        });

        Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image-uri")))
                .centerCrop()
                .placeholder(R.drawable.loadingimg)
                .into((ImageView) findViewById(R.id.zoom_imview));
    }
}
