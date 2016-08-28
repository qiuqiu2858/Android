package com.example.adam.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.adam.myapplication.R;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by adam on 8/28/16.
 */
public class MyAdapter extends ArrayAdapter<SoftReference<Bitmap>> {
    int resource;
    LayoutInflater layoutInflater;
    public MyAdapter(Context context, int resource, List<SoftReference<Bitmap>> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(getContext());
        this.resource = resource;
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
        if(getItem(position).get() != null)
            holder.imageView.setImageBitmap(getItem(position).get());
        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
    }

}
