package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.GalleryFile;

import java.util.List;

public class GalleryGridViewAdapter extends ArrayAdapter<GalleryFile> {

    private List<GalleryFile> mData;
    private int mLayoutResourse;
    private Context mContext;


    public GalleryGridViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        mContext = context;
        mLayoutResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutResourse, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GalleryFile galleryFile = mData.get(position);

        if (galleryFile == null)
            return null;

        holder.image.setImageDrawable(new BitmapDrawable(mContext.getResources(), galleryFile.getThumbnail()));
        return convertView;
    }

    public void setData(List<GalleryFile> data) {
        mData = data;
    }

    static class ViewHolder {
        ImageView image;
    }
}
