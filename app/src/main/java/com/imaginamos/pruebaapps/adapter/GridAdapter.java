package com.imaginamos.pruebaapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imaginamos.pruebaapps.R;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;

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

        TextView labelTitle = (TextView) view.findViewById(R.id.labelTitle);
        TextView labelArtist = (TextView) view.findViewById(R.id.labelArtist);
        TextView labelPrice = (TextView) view.findViewById(R.id.labelPrice);

        labelTitle.setText(applicationList.get(i).getTitle());
        labelArtist.setText(applicationList.get(i).getArtist());
        Double price = applicationList.get(i).getPrice();
        if (price == 0.0){
            labelPrice.setText("gratis");
        }else{
            labelPrice.setText(applicationList.get(i).getPrice().toString());
        }


        return view;
    }
}
