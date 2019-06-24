package com.example.ayana.chekikkov1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    public static final String EXTRA_URI = "com.example.ayana.chekikkov1.extra.URI";

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    ImageView mInitView;

//    private RecyclerView cardRecyclerView;
//    private SavedPhotoAdapter adapter;
//    private List<SavedPhoto> mSavedPhotoList;
//
//    Bitmap[] fisBitmap;
//    String[] fisString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int[] initImageList = {R.drawable.emo1, R.drawable.emo2, R.drawable.emo3, R.drawable.emo4,
                R.drawable.emo5, R.drawable.emo6, R.drawable.emo7, R.drawable.emo8};
        mInitView = findViewById(R.id.init_image);
        Random random = new Random();
        int i = random.nextInt(initImageList.length);
        mInitView.setImageResource(initImageList[i]);
//        initCollapsingToolbar();

//        cardRecyclerView = findViewById(R.id.card_recycler_view);
//        mSavedPhotoList = new ArrayList<>();
//        adapter = new SavedPhotoAdapter(this, mSavedPhotoList);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        cardRecyclerView.setLayoutManager(mLayoutManager);
//        cardRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        cardRecyclerView.setAdapter(adapter);


//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String savedDate = extras.getString(PhotoFilterActivity.EXTRA_SAVED_DATE);
//        }

//        ContextWrapper cw = new ContextWrapper(getApplicationContext());
//        // path to /data/data/yourapp/app_data/imageDir
//        if (cw.getDir("imageDir", Context.MODE_PRIVATE) != null){
//            File imageDirectory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//            String imageDirectoryPath = imageDirectory.getAbsolutePath();
//            try {
//                loadImageFromStorage(imageDirectoryPath);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        // path to /data/data/yourapp/app_data/dateDir
//        if (cw.getDir("dateDir", Context.MODE_PRIVATE) != null){
//            File directory = cw.getDir("dateDir", Context.MODE_PRIVATE);
//            String dateDirectoryPath = directory.getAbsolutePath();
//            try {
//                loadDateFromStorage(dateDirectoryPath);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

//        for (int i=0; i < fisBitmap.length; i++) {
//            SavedPhoto savedPhoto = new SavedPhoto(fisString[i], fisBitmap[i]);
//            mSavedPhotoList.add(savedPhoto);
//            adapter.notifyDataSetChanged();
//        }

//        prepareDefaultPhotos();


        // Floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readStorage();
            }
        });
    }

    public void readStorage() {
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d("saveBitmap: パーミッション確認", "書き込み権限取得済み");
        } else {
            // ユーザーはパーミッションを許可していない
            Log.d("saveBitmap: パーミッション確認", "書き込み権限未取得");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            );
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionsResult", "パーミッションもらえた");
                    // TODO: パーミッションもらえたときの実装
                } else {
                    Log.d("PermissionsResult", "パーミッションもらえなかった");
                    // TODO: パーミッションもらえなかったときの実装
                }
            }
        }
    }

//    private void loadImageFromStorage(String path) throws FileNotFoundException {
//        File directory = new File(path);
//        File[] files = directory.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            Log.d("Files", "FileName:" + files[i].getName()); //these are the different filenames in the directory
//            String name = files[i].getName();
//            File f = new File(path, name);
//            fisBitmap[i] = BitmapFactory.decodeStream(new FileInputStream(f));
////            SavedPhoto a = new SavedPhoto("Text", b);
////            mSavedPhotoList.add(a);
////            adapter.notifyDataSetChanged();
//        }
//    }

//    private void loadDateFromStorage(String path) throws FileNotFoundException {
//        File directory = new File(path);
//        File[] files = directory.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            Log.d("Files", "FileName:" + files[i].getName()); //these are the different filenames in the directory
//            fisString[i] = files[i].getName();
//        }
//    }

//    private void prepareDefaultPhotos() {
//        Bitmap[] covers = new Bitmap[]{
//                BitmapFactory.decodeResource(this.getResources(),
//                        R.drawable.fish)};
//        SavedPhoto a = new SavedPhoto("Sample", covers[0]);
//        mSavedPhotoList.add(a);
//        adapter.notifyDataSetChanged();
//    }

//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }


//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null) {

                Uri selectedImage = resultData.getData();

                Intent intent = new Intent(this, CropActivity.class);
                intent.putExtra(EXTRA_URI, selectedImage.toString());
                startActivity(intent);
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
