package com.imaginamos.pruebaapps.mapper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.Toast;

import com.alirezaafkar.json.requester.interfaces.Response;
import com.android.volley.VolleyError;
import com.imaginamos.pruebaapps.adapter.GridAdapter;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.model.DaoMaster;
import com.imaginamos.pruebaapps.model.DaoSession;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kmilopr on 14/02/2016.
 */

public class JsonObjectListener extends Response.SimpleObjectResponse {

    private ApplicationBussines applicationBussines;
    private GridAdapter gridAdapter;
    private GridView gridView;
    private Context context;


    public JsonObjectListener(Context context, GridView gridView) {
        applicationBussines = new ApplicationBussines(context);
        this.gridView = gridView;
        this.context = context;
    }

    @Override
    public void onResponse(int requestCode, @Nullable JSONObject jsonObject) {
        try {
            applicationBussines.deleteAll();
            //get the information data from json and save it in the greendao db
            JSONArray entries = jsonObject.getJSONObject("feed").getJSONArray("entry");
            for(int i=0;i<entries.length();i++){
                JSONObject entry = entries.getJSONObject(i);
                JSONObject imName = entry.getJSONObject("im:name");
                String name = imName.getString("label");
                JSONArray images = entry.getJSONArray("im:image");
                String urlImSmall = images.getJSONObject(0).getString("label");
                String urlImMed = images.getJSONObject(1).getString("label");
                String urlImLarge = images.getJSONObject(2).getString("label");
                JSONObject sum = entry.getJSONObject("summary");
                String summary = sum.getString("label");
                JSONObject imPrice = entry.getJSONObject("im:price").getJSONObject("attributes");
                double price = imPrice.getDouble("amount");
                String currency = imPrice.getString("currency");
                JSONObject imContentType = entry.getJSONObject("im:contentType");
                String type = imContentType.getJSONObject("attributes").getString("label");
                JSONObject right = entry.getJSONObject("rights");
                String rights = right.getString("label");
                JSONObject title1 = entry.getJSONObject("title");
                String title = title1.getString("label");
                JSONObject lin = entry.getJSONObject("link").getJSONObject("attributes");
                String link = lin.getString("href");
                JSONObject id = entry.getJSONObject("id");
                String idLabel = id.getString("label");
                String idNumber = id.getJSONObject("attributes").getString("im:id");
                String bundleId = id.getJSONObject("attributes").getString("im:bundleId");
                JSONObject art = entry.getJSONObject("im:artist");
                String artist = art.getString("label");
                String artistLink= art.getJSONObject("attributes").getString("href");
                JSONObject categ = entry.getJSONObject("category").getJSONObject("attributes");
                String category = categ.getString("label");
                String categoryId = categ.getString("im:id");
                String scheme = categ.getString("scheme");
                JSONObject imdate = entry.getJSONObject("im:releaseDate").getJSONObject("attributes");
                String releaseDate  = imdate.getString("label");
                applicationBussines.createApplication(name, urlImSmall, urlImMed, urlImLarge, summary, currency, type, rights, title, link, idLabel, idNumber, bundleId, artist, artistLink, category, categoryId, scheme, price, releaseDate);
            }
        } catch (JSONException e) {}
    }

    @Override
    public void onErrorResponse(int requestCode, VolleyError volleyError, @Nullable JSONObject errorObject) {
    }

    @Override
    public void onFinishResponse(int requestCode, VolleyError volleyError, String message) {
    }

    @Override
    public void onRequestStart(int requestCode) {
    }

    @Override
    public void onRequestFinish(int requestCode) {
        gridAdapter = new GridAdapter(context);
        gridAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridAdapter);
    }
}