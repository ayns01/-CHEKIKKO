package com.example.ayana.chekikkov1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.ayana.chekikkov1.Adapter.TabPageAdapter;
import com.example.ayana.chekikkov1.Fragment.ColorsFragment;
import com.example.ayana.chekikkov1.Fragment.DoodleFragment;
import com.example.ayana.chekikkov1.Fragment.FramesFragment;
import com.example.ayana.chekikkov1.Material.MaterialsList;
import com.example.ayana.chekikkov1.Paint.PaintView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoFilterActivity extends AppCompatActivity implements
                                                    ColorsFragment.OnFragmentInteractionListener,
                                                    FramesFragment.OnFragmentInteractionListener,
                                                    DoodleFragment.OnFragmentInteractionListener,
                                                    DoodleFragment.OnFragmentUndoListener,
                                                    DoodleFragment.OnFragmentDefaultPenListener,
                                                    DoodleFragment.OnFragmentPoscaPenListener{
    private Bitmap mSentBitmap;
    private Bitmap mFrameBitmap;
    private Bitmap mPhotoBitmap;
    private Bitmap mDoodleBitmap;
    private ImageView mPreviewImageView;
    private ImageView mPreviewFrameView;
    private PaintView mPaintView;
    private Bitmap mResultBitmap;

    private int frameDrawableId = R.drawable.frame_white;

    private static final int REQUEST_SAVE_IMAGE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_filter);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray(CropActivity.EXTRA_CROPPED_IMAGE);
        mSentBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mPreviewImageView = findViewById(R.id.previewImageView);
        mPreviewFrameView = findViewById(R.id.previewFrameView);
        mPaintView = findViewById(R.id.paintView);

        mPhotoBitmap = Bitmap.createBitmap(mSentBitmap.getWidth(),
                mSentBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mPhotoBitmap);

        mFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_white);

        mDoodleBitmap = Bitmap.createBitmap(mFrameBitmap.getWidth() - 35,
                mFrameBitmap.getHeight() - 35,
                Bitmap.Config.ARGB_8888);

        // image for previewing
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(1.1f);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
        mPreviewImageView.setColorFilter(filter);
        mPreviewImageView.setImageBitmap(mPhotoBitmap);
        mPreviewFrameView.setImageBitmap(mFrameBitmap);

        // image for saving
        ColorMatrix cm2 = new ColorMatrix();
        cm2.setSaturation(1.75f);
        ColorMatrixColorFilter filter2 = new ColorMatrixColorFilter(cm2);
        Paint paint = new Paint();
        paint.setColorFilter(filter2);
        canvas.drawBitmap(mSentBitmap, 0, 0, paint);


        final TabLayout tabLayout = findViewById(R.id.tablayout);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                } else if (tab.getPosition() == 1) {
                } else {
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    // FilterFragment
    public void onFragmentInteraction(int pos) {

        MaterialsList materialsList = new MaterialsList();

        mPhotoBitmap = Bitmap.createBitmap(mSentBitmap.getWidth(),
                    mSentBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mPhotoBitmap);
        Paint paint = new Paint();
        ColorMatrix matrix = materialsList.getColorFilter(pos);
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(matrix)));
        mPreviewImageView.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(matrix)));
        canvas.drawBitmap(mSentBitmap, 0, 0, paint);
    }

    @Override
    // FrameFragment
    public void onFramesFragmentInteraction(int pos) {

        MaterialsList materialsList = new MaterialsList();

        mPreviewFrameView.setImageResource(materialsList.getFrame(pos));
        frameDrawableId = materialsList.getFrame(pos);
    }

    @Override
    // Doodle Fragment
    public void onDoodleFragmentInteraction(int pos) {

        MaterialsList materialsList = new MaterialsList();

        int r = materialsList.getR(pos);
        int g = materialsList.getG(pos);
        int b = materialsList.getB(pos);

        mPaintView.chooseColor(r, g, b);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Bitmap backWallBmp = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.backwall);
            mResultBitmap = Bitmap.createBitmap(backWallBmp.getWidth(), backWallBmp.getHeight(),
                    backWallBmp.getConfig());
            Canvas canvas = new Canvas(mResultBitmap);
            Bitmap frameResBmp = BitmapFactory.decodeResource(getResources(), frameDrawableId);
            Bitmap photoSize = BitmapFactory.decodeResource(this.getResources(),
                            R.drawable.photo_size_criteria);
            Bitmap photoResBmp = Bitmap.createScaledBitmap(mPhotoBitmap,
                    photoSize.getWidth(),
                    photoSize.getHeight(),
                    false);
            Bitmap doodleBmp = mPaintView.getBitmap();
            Bitmap paintResBmp = Bitmap.createScaledBitmap(doodleBmp, mDoodleBitmap.getWidth(),
                    mDoodleBitmap.getHeight(), false);
            int leftOfFrame = (backWallBmp.getWidth() - mFrameBitmap.getWidth()) / 2;
            int topOfFrame = (backWallBmp.getHeight() - mFrameBitmap.getHeight()) / 2;
            int topOfPhoto = (int)(((backWallBmp.getHeight() - photoResBmp.getHeight()) / 2) - (topOfFrame * 1.8));
            int leftOfPhoto = (backWallBmp.getWidth() - photoResBmp.getWidth()) / 2;
            canvas.drawBitmap(backWallBmp, 0, 0, null);
            canvas.drawBitmap(photoResBmp, leftOfPhoto, topOfPhoto, null);
            canvas.drawBitmap(frameResBmp, leftOfFrame, topOfFrame, null);
            canvas.drawBitmap(paintResBmp, leftOfFrame + 15, topOfFrame + 5, null);

            try {
                saveBitmap(mResultBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/jpeg");
//            intent.putExtra(Intent.EXTRA_TITLE, System.currentTimeMillis() + "-emo.jpeg");
//            startActivityForResult(intent, REQUEST_SAVE_IMAGE);

        }
        return super.onOptionsItemSelected(item);

    }

    public void saveBitmap(Bitmap saveImage) throws IOException {

        // storage/emulated/0/MyPhoto
        final String SAVE_DIR = "/MyPhoto/";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        try{
            if(!file.exists()){
                file.mkdir();
            }
        }catch(SecurityException e){
            e.printStackTrace();
            throw e;
        }

        Date mDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = fileNameDate.format(mDate) + ".jpg";
        String AttachName = file.getAbsolutePath() + "/" + fileName;

        try {
            FileOutputStream out = new FileOutputStream(AttachName);
            saveImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw e;
        }

//        // save index
//        ContentValues values = new ContentValues();
//        ContentResolver contentResolver = getContentResolver();
//        values.put(Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(Images.Media.TITLE, fileName);
//        values.put("_data", AttachName);
//        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == REQUEST_SAVE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null) {

                Uri uri = resultData.getData();

                try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {

                    mResultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                    Intent i = new Intent(this, MainActivity.class);
                    // delete all stack of Activity
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // save file in this app
    private void saveToInternalStorage(Bitmap bitmapImage) {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        // path to /data/data/yourapp/app_data/imageDir
        File imageDirectory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File dateDirectory = cw.getDir("dateDir", Context.MODE_PRIVATE);

        // Create imageDir
        File myImagePath = new File(imageDirectory, System.currentTimeMillis() + "emmmmmo.jpg");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());
        File myDatePath = new File(dateDirectory, currentDateandTime);

        FileOutputStream imageFos = null;
        FileOutputStream dateFos = null;
        try {
            imageFos = new FileOutputStream(myImagePath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, imageFos);

            dateFos = new FileOutputStream(myDatePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                imageFos.close();
                dateFos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDoodleFragmentUndoInteraction() {
        mPaintView.undoPath();
    }

    @Override
    public void onDoodleFragmentDefaultPenInteraction() { mPaintView.setPen(1); }

    @Override
    public void onDoodleFragmentPoscaPenInteraction() {
        mPaintView.setPen(2);
    }
}
