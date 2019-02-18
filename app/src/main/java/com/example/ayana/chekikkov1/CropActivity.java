package com.example.ayana.chekikkov1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.isseiaoki.simplecropview.CropImageView;

import java.io.ByteArrayOutputStream;

public class CropActivity extends AppCompatActivity {
    Bitmap croppedBitmap;
    CropImageView mCropView;
    public static final String EXTRA_CROPPED_IMAGE = "com.example.ayana.chekikkov1.extra.CROPPED.IMAGE";
    String extraUri;
    private static final String TAG = CropActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        Bundle extras = getIntent().getExtras();
        extraUri = extras.getString(MainActivity.EXTRA_URI);
        Uri uriData = Uri.parse(extraUri);

        mCropView = findViewById(R.id.cropImageView);
        mCropView.setCropMode(CropImageView.CropMode.SQUARE);
        mCropView.setInitialFrameScale(0.75f);
        mCropView.setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH);

        Glide.with(this)
                .load(uriData)
                .apply(new RequestOptions().override(650, 650))
                .into(mCropView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_next) {
            croppedBitmap = mCropView.getCroppedBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();
            Intent intent = new Intent(this, PhotoFilterActivity.class);
            intent.putExtra(EXTRA_CROPPED_IMAGE, byteArray);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
