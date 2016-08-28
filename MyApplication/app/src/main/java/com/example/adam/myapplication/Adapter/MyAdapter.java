package com.example.adam.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.adam.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by adam on 8/28/16.
 */
public class MyAdapter extends BaseAdapter {
    int resource;
    LayoutInflater layoutInflater;
    LruCache<Integer, Bitmap> bitmapLruCache;
    public MyAdapter(Context context, int resource, LruCache<Integer, Bitmap> bitmapMap) {
        layoutInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.bitmapLruCache = bitmapMap;

    }

    @Override
    public int getCount() {
        return bitmapLruCache.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmapLruCache.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(resource,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listitem_imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.imageView.setImageBitmap(bitmapLruCache.get(position));
        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
    }

}
