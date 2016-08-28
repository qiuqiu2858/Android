package com.example.adam.myapplication;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adam.myapplication.Adapter.MyAdapter;

import java.lang.ref.SoftReference;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    Button addPicBtn;
    ListView listView;
    MyAdapter myAdapter;
    LruCache<Integer, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        addPicBtn = (Button) findViewById(R.id.addPicBtn);
        listView = (ListView) findViewById(R.id.listView);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
        myAdapter = new MyAdapter(this, R.layout.listitem_image, mMemoryCache);
        listView.setAdapter(myAdapter);
        addPicBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPicBtn:
                Uri imageUri = MainActivity.imageUri;
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
                for(int i = 0; i < 5; i++) {
                    Bitmap oriBm = BitmapFactory.decodeFile(imagePath, options);
                    mMemoryCache.put(mMemoryCache.size(), oriBm);
                    myAdapter.notifyDataSetChanged();
                }
                Toast.makeText(this, "size of cache is " + mMemoryCache.size(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
