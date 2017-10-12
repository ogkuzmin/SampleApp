package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.domain.GalleryFile;

import java.util.List;

public class GalleryGridViewAdapter extends ArrayAdapter<GalleryFile> {

    private static final String LOG_TAG = GalleryGridViewAdapter.class.getSimpleName();

    private List<GalleryFile> mData;
    private int mLayoutResourse;
    private Context mContext;


    public GalleryGridViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        mContext = context;
        mLayoutResourse = resource;
    }

    @Nullable
    @Override
    public GalleryFile getItem(int position) {
        if (mData == null)
            return null;
        else
           return mData.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder holder;
       Log.d(LOG_TAG, "::getView");

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutResourse, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_container);
            holder.image.setScaleType(ImageView.ScaleType.MATRIX);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GalleryFile galleryFile = mData.get(position);

        if (galleryFile == null) {
            Log.d(LOG_TAG, "::getView gallery file is null!!! Returns null view");
            return null;
        }

        holder.image.setImageDrawable(new BitmapDrawable(mContext.getResources(), galleryFile.getThumbnail()));
        return convertView;
    }

    public void setData(List<GalleryFile> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        else
            return mData.size();
    }

    static class ViewHolder {
        ImageView image;
    }
}
