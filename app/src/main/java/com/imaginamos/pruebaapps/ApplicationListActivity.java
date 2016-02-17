package com.imaginamos.pruebaapps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alirezaafkar.json.requester.Requester;
import com.alirezaafkar.json.requester.interfaces.ContentType;
import com.alirezaafkar.json.requester.interfaces.Methods;
import com.alirezaafkar.json.requester.requesters.JsonObjectRequester;
import com.alirezaafkar.json.requester.requesters.RequestBuilder;
import com.android.volley.Request;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.imaginamos.pruebaapps.adapter.GridAdapter;
import com.imaginamos.pruebaapps.mapper.JsonObjectListener;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;
import com.imaginamos.pruebaapps.util.Preferences;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ApplicationListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Preferences preferences;
    private ApplicationBussines applicationBussines;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private LinearLayout linearContainer;
    private Boolean tablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        linearContainer = (LinearLayout) findViewById(R.id.linearContainer);
        preferences = new Preferences(ApplicationListActivity.this);
        applicationBussines = new ApplicationBussines(getApplicationContext());
        gridView = (GridView) findViewById(R.id.gridApps);
        gridView.setOnItemClickListener(this);
        gridAdapter = new GridAdapter(getApplicationContext());
        verifyPageOrientation();
        Boolean internet = isOnline();

        if(internet) {
            if (preferences.readFirtsRunPreference()) {

                applicationBussines.deleteAll();
                Map<String, String> header = new HashMap<>();
                header.put("charset", "utf-8");
                Requester.Config config = new Requester.Config(getApplicationContext());
                config.setHeader(header);
                Requester.init(config);
                JsonObjectListener listener = new JsonObjectListener(getApplicationContext(),gridView);
                JsonObjectRequester mRequester;
                mRequester = new RequestBuilder(this)
                        .requestCode(0)
                        .contentType(ContentType.TYPE_JSON) //or ContentType.TYPE_FORM
                        .showError(true) //Show error with toast on Network or Server error
                        .shouldCache(true)
                        .priority(Request.Priority.NORMAL)
                        .allowNullResponse(true)
                        .buildObjectRequester(listener); //or .buildArrayRequester(listener);
                mRequester.request(Methods.GET, Constants.URL);
                gridView.setAdapter(gridAdapter);
                preferences.writeFirtsRunPreference();
            }else{
                gridView.setAdapter(gridAdapter);
            }
        }else{
            if (applicationBussines.getApplicationListSize() > 0) {
                gridView.setAdapter(gridAdapter);
            } else {
                showSnackBar(linearContainer, "verifique su conexion");
            }
        }
        //TODO para la siguiente actividad por favor borrame
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        int c = getDominantColor(largeIcon);
//
////        int color = ContextCompat.getColorStateList(getApplicationContext(), c).getDefaultColor();
//        String strColor = String.format("#%06X", 0xFFFFFF & c);
//        Toast.makeText(getApplicationContext(), "" + strColor, Toast.LENGTH_LONG).show();
//
//        android.support.v7.app.ActionBar bar = getSupportActionBar();
//
//        if ( bar != null)
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));
    }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        int color = bitmap1.getPixel(0, 0);

        return color;
    }

    private void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    private void verifyPageOrientation(){

        tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            gridView.setNumColumns(2);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            gridView.setNumColumns(1);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Application app = (Application) adapterView.getItemAtPosition(i);

        if(tablet){

            new MaterialStyledDialog(this)
                    .setTitle(app.getTitle()+"\n"+app.getCategory())
                    .setDescription(app.getSummary())
                    .setScrollable(true)
                    .show();
        }else {
            Intent applicationIntent = new Intent(ApplicationListActivity.this, ApplicationDetailActivity.class);
            applicationIntent.putExtra(Constants.APPLICATION_ID, app.getId());
            startActivity(applicationIntent);
        }
    }
}
