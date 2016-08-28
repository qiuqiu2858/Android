package com.example.adam.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.adam.myapplication.Database.DBHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String TAG = MainActivity.class.getSimpleName();
    private Button btn,dbBtn, photoBtn, albumPhotoBtn, deletePicBtn, listPicBtn;
    private ImageView imageView;

    DBHelper dbHelper;
    SQLiteDatabase db;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    List<String> list;

    ImageLoader imageLoader;

    Bitmap bm;

    public static Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        dbBtn = (Button) findViewById(R.id.dbBtn);
        photoBtn = (Button) findViewById(R.id.photoBtn);
        albumPhotoBtn = (Button) findViewById(R.id.albumBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        deletePicBtn = (Button) findViewById(R.id.deletePicBtn);
        listPicBtn = (Button) findViewById(R.id.listPicBtn);
        btn.setOnClickListener(this);
        dbBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        albumPhotoBtn.setOnClickListener(this);
        deletePicBtn.setOnClickListener(this);
        listPicBtn.setOnClickListener(this);
        if(!checkPermission(permissions))
            ActivityCompat.requestPermissions(this, list.toArray(new String[list.size()]), 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if(checkPermission(permissions)) {
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
                break;
            case R.id.dbBtn:
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
                break;
            case R.id.photoBtn:
                if(checkPermission(permissions)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.albumBtn:
                if(checkPermission(permissions)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.deletePicBtn:
                if(bm != null) {
                    Log.w(TAG, "pic is going to be deleted");
                    bm.recycle();
                    bm = null;
//                    System.gc();
                }
                break;
            case R.id.listPicBtn:
                startActivity(new Intent(this, ListActivity.class));
                break;

        }
    }

    public boolean checkPermission(String[] array) {
        list = new ArrayList<>();

        for(int i = 0; i < array.length; i++) {
            if(ContextCompat.checkSelfPermission(this, array[i]) != PackageManager.PERMISSION_GRANTED) {
                list.add(array[i]);
            }
        }

        if(list.size() == 0)
            return true;
        else {
            return false;
        }
    }

    @Override
    public void  onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                for(int i = 0; i < permissions.length; i++) {
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (permissions[i].equals(Manifest.permission.CAMERA)) {
                            Log.d(TAG, "request camera failed");
                        } else {
                            Log.d(TAG, "request disk failed");
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    Bundle bundle = data.getExtras();
                    bm = (Bitmap)bundle.get("data");
                    imageView.setImageBitmap(bm);
                    break;
                case 1:
                    imageUri = data.getData();
                    Log.d(TAG, "uri: " + imageUri.toString());
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(imageUri, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    c.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imagePath, options);
                    int height = options.outHeight;
                    int width= options.outWidth;
                    int inSampleSize = 2;
                    int minLen = Math.min(height, width);
                    if(minLen > 100) {
                        float ratio = (float)minLen / 100.0f;
                        inSampleSize = (int)ratio;
                    }
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = inSampleSize;
                    bm = BitmapFactory.decodeFile(imagePath, options);
                    Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight());
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setImageBitmap(bm);
                    bm = null;

//                    ImageSize size = new ImageSize(80,80);
//                    imageLoader.loadImage(imageUri.toString(), size, new ImageLoadingListener() {
//                        @Override
//                        public void onLoadingStarted(String imageUri, View view) {
//
//                        }
//
//                        @Override
//                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//                        }
//
//                        @Override
//                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                            imageView.setImageBitmap(loadedImage);
//                            Log.w(TAG, "bit map size is " + loadedImage.getByteCount()/1024);
//                        }
//
//                        @Override
//                        public void onLoadingCancelled(String imageUri, View view) {
//
//                        }
//                    });
//
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 4;
//                    Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
//                    Log.w(TAG, "bm1 size :" + bm.getByteCount()/1024);
//
//                    Bitmap bm2 = ThumbnailUtils.extractThumbnail(bm, 300, 300);
//                    Log.w(TAG, "bm2 size :" + bm2.getByteCount()/1024);
//                    bm.recycle();
//
//                    ByteArrayOutputStream out = new ByteArrayOutputStream();
//                    bm2.compress(Bitmap.CompressFormat.JPEG, 80, out);
//                    bm2.recycle();
//                    byte[] array = out.toByteArray();
//
//                    Bitmap bm3 = BitmapFactory.decodeByteArray(array,0, array.length);
//                    Log.w(TAG, "bm3 size :" + bm3.getByteCount()/1024);
//
//
//                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                    imageView.setImageBitmap(bm3);

            }
        }
    }
}
