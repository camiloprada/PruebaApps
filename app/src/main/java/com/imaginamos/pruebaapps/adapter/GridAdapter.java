package com.imaginamos.pruebaapps.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.imaginamos.pruebaapps.R;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmilopr on 14/02/2016.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private ApplicationBussines applicationBussines;
    private List<Application> applicationList;

    public GridAdapter(Context context){
        this.context = context;
        applicationBussines = new ApplicationBussines(context);
        applicationList = applicationBussines.getApplicationList();
    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int i) {
        return applicationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return applicationList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }
        //Sets all the application data for the main view, it uses the db information (greendao) and image cache (UniversalImageLoader)
        TextView labelTitle = (TextView) view.findViewById(R.id.labelTitle);
        TextView labelArtist = (TextView) view.findViewById(R.id.labelArtist);
        TextView labelPrice = (TextView) view.findViewById(R.id.labelPrice);
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.imageApplication);
//        DisplayImageOptions options;
//        options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();

        labelTitle.setText(applicationList.get(i).getTitle());
        labelArtist.setText(applicationList.get(i).getArtist());
        Double price = applicationList.get(i).getPrice();
        if (price == 0.0){
            labelPrice.setText(R.string.free);
        }else{
            labelPrice.setText(applicationList.get(i).getPrice().toString());
        }
        Uri uri = Uri.parse(applicationList.get(i).getUrlImageLarge());
        imageView.setImageURI(uri);
//        ImageLoader.getInstance().displayImage(applicationList.get(i).getUrlImageLarge(), imageView);
        return view;
    }
}
