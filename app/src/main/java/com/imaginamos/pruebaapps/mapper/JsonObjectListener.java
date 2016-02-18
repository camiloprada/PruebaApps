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
import com.nostra13.universalimageloader.core.DisplayImageOptions;

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
        //Ok


        try {
            //Log.d("Debugtext","Parsing JSON.");

            //parse json
            JSONArray entries = jsonObject.getJSONObject("feed").getJSONArray("entry");
            //Log.d("Debugtext","entries number= "+entries.length());
            for(int i=0;i<entries.length();i++){
                //the Entry
                JSONObject entry = entries.getJSONObject(i);
                ////////////////////////////////////////////////////
                //App name
                JSONObject imName = entry.getJSONObject("im:name");
                String name = imName.getString("label"); //im:name/label
                //////////////////////////
                //App image URLs
                JSONArray images = entry.getJSONArray("im:image");
                String urlImSmall = images.getJSONObject(0).getString("label"); //im:image/label[0]
                String urlImMed = images.getJSONObject(1).getString("label"); //im:image/label[1]
                String urlImLarge = images.getJSONObject(2).getString("label"); //im:image/label[2]
                //////////////////////////
                //App summary
                JSONObject sum = entry.getJSONObject("summary");
                String summary = sum.getString("label"); //summary/label
                //////////////////////////
                //App price and currency
                JSONObject imPrice = entry.getJSONObject("im:price").getJSONObject("attributes");
                double price = imPrice.getDouble("amount"); //im:price/attributes/amount
                String currency = imPrice.getString("currency"); //im:price/attributes/currency
                //////////////////////////
                //App contentType
                JSONObject imContentType = entry.getJSONObject("im:contentType");
                String type = imContentType.getJSONObject("attributes").getString("label"); //im:contentType/attributes/label
                //////////////////////////
                //App rights
                JSONObject right = entry.getJSONObject("rights");
                String rights = right.getString("label"); //rights/label
                //////////////////////////
                //App title
                JSONObject titl = entry.getJSONObject("title");
                String title = titl.getString("label"); //title/label
                //////////////////////////
                //App link
                JSONObject lin = entry.getJSONObject("link").getJSONObject("attributes");
                String link = lin.getString("href"); //link/attributes/href
                //////////////////////////
                //App ID and attributes
                JSONObject id = entry.getJSONObject("id");
                String idLabel = id.getString("label"); //id/label
                String idNumber = id.getJSONObject("attributes").getString("im:id"); //id/attributes/im:id
                String bundleId = id.getJSONObject("attributes").getString("im:bundleId"); //id/attributes/im:bundleId
                //////////////////////////
                //App artist and artist link
                JSONObject art = entry.getJSONObject("im:artist");
                String artist = art.getString("label"); //im:artist/label
                String artistLink= art.getJSONObject("attributes").getString("href"); //im:artist/attributes/href
                //////////////////////////
                //App category
                JSONObject categ = entry.getJSONObject("category").getJSONObject("attributes");
                String category = categ.getString("label"); //category/attributes/label
                String categoryId = categ.getString("im:id"); //category/attributes/im:id
                String scheme = categ.getString("scheme"); //category/attributes/scheme
                //////////////////////////
                //App release date
                JSONObject imdate = entry.getJSONObject("im:releaseDate").getJSONObject("attributes");
                String releaseDate  = imdate.getString("label");

                applicationBussines.createApplication(name, urlImSmall, urlImMed, urlImLarge, summary, currency, type, rights, title, link, idLabel, idNumber, bundleId, artist, artistLink, category, categoryId, scheme, price, releaseDate);

//                final App newApp = new App(name,urlImSmall,urlImMed,urlImLarge,summary,price,currency,type,rights,title,link,idLabel,idNumber,bundleId,artist,artistLink,
//                        category,categoryId,scheme,releaseDate);
//                apps.add(newApp);
            }

            //Updates the adapter
//            rvadapter.notifyDataSetChanged();
            //Stops the refreshing
//            swipeContainer.setRefreshing(false);
            //Toast.makeText(context,"Lista actualizada.",Toast.LENGTH_SHORT).show();
            //Log.d("Debugtext","se notifico al adapter, numelementos= "+rvadapter.getItemCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  Toast.makeText(getApplicationContext(), jsonObject. +"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(int requestCode, VolleyError volleyError, @Nullable JSONObject errorObject) {
        //Error (Not server or network error)
    }

    @Override
    public void onFinishResponse(int requestCode, VolleyError volleyError, String message) {
        //Network or Server error
    }

    @Override
    public void onRequestStart(int requestCode) {
        //Show loading or disable button
    }

    @Override
    public void onRequestFinish(int requestCode) {
        gridAdapter = new GridAdapter(context);
        gridAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridAdapter);
    }
}