package com.imaginamos.pruebaapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alirezaafkar.json.requester.Requester;
import com.alirezaafkar.json.requester.interfaces.ContentType;
import com.alirezaafkar.json.requester.interfaces.Methods;
import com.alirezaafkar.json.requester.requesters.JsonObjectRequester;
import com.alirezaafkar.json.requester.requesters.RequestBuilder;
import com.android.volley.Request;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.imaginamos.pruebaapps.adapter.GridAdapter;
import com.imaginamos.pruebaapps.mapper.JsonObjectListener;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;
import com.imaginamos.pruebaapps.util.Preferences;
import com.localytics.android.Localytics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static String pathPhoto;
    private ApplicationBussines applicationBussines;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private LinearLayout linearContainer;
    private Boolean tablet;
    private Uri file;
    private Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        linearContainer = (LinearLayout) findViewById(R.id.linearContainer);
        applicationBussines = new ApplicationBussines(getApplicationContext());
        gridView = (GridView) findViewById(R.id.gridApps);
        gridView.setOnItemClickListener(this);
        gridAdapter = new GridAdapter(getApplicationContext());
        verifyPageOrientation();
        Boolean internet = isOnline();

        if (internet) {
            //Delete all the data in greendao's db for the new information

            Map<String, String> header = new HashMap<>();
            header.put("charset", "utf-8");
            Requester.Config config = new Requester.Config(getApplicationContext());
            config.setHeader(header);
            Requester.init(config);
            JsonObjectListener listener = new JsonObjectListener(getApplicationContext(), gridView);
            JsonObjectRequester mRequester;
            mRequester = new RequestBuilder(this)
                    .requestCode(0)
                    .contentType(ContentType.TYPE_JSON)
                    .showError(true)
                    .shouldCache(true)
                    .priority(Request.Priority.NORMAL)
                    .allowNullResponse(true)
                    .buildObjectRequester(listener);
            mRequester.request(Methods.GET, Constants.URL);
            gridView.setAdapter(gridAdapter);

        } else {
            if (applicationBussines.getApplicationListSize() > 0) {
                gridView.setAdapter(gridAdapter);
            } else {
                showSnackBar(linearContainer, getString(R.string.verify_your_internet));
            }
        }

    }

    /**
     * Shows a simple snack bar with message for internet troubles
     *
     * @param view
     * @param message for internet torubles
     */
    private void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * Check internet connection by doing a ping to google
     *
     * @return true if there are internet false if not
     */
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * The name says all
     * for the exercise ,landscape phone and portrait for tablet
     * sets the number of columns for the gridview list - grid
     */
    private void verifyPageOrientation() {

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
        final Application app = (Application) adapterView.getItemAtPosition(i);
        Map<String, String> extraAttributes = new HashMap<>();
        extraAttributes.put(Constants.APP_NAME, app.getName());
        extraAttributes.put(Constants.APP_CATEGORY, app.getCategory());
        extraAttributes.put(Constants.ARTIST_NAME, app.getArtist());
        extraAttributes.put(Constants.ARTIST_LINK, app.getArtistLink());
        Localytics.tagEvent(Constants.APP_INFO, extraAttributes);
        Localytics.tagContentViewed(app.getName(), app.getId().toString(), app.getType(), extraAttributes);

        if (tablet) {
            id = app.getId();
            //shows a custom dialog from library (only for tablet)
            MaterialStyledDialog dialog = new MaterialStyledDialog(this);
            dialog.setTitle(app.getTitle() + "\n" + app.getCategory());
            dialog.setDescription(app.getSummary());
            dialog.setScrollable(true);
            dialog.setHeaderColor(R.color.colorPrimaryDark);
            //verify the apps' category and sets the icon
            switch (app.getCategory()) {
                case "Application":
                    dialog.setIcon(R.mipmap.ic_application);
                    break;
                case "Music":
                    dialog.setIcon(R.mipmap.ic_music);
                    break;
                case "Social Networking":
                    dialog.setIcon(R.mipmap.ic_social);
                    break;
                case "Games":
                    dialog.setIcon(R.mipmap.ic_game);
                    break;
                case "Photo & Video":
                    dialog.setIcon(R.mipmap.ic_photo_video);
                    break;
                case "Productivity":
                    dialog.setIcon(R.mipmap.ic_productivity);
                    break;
                case "Education":
                    dialog.setIcon(R.mipmap.ic_education);
                    break;
                case "Navigation":
                    dialog.setIcon(R.mipmap.ic_navigation);
                    break;
                case "Travel":
                    dialog.setIcon(R.mipmap.ic_travel);
                    break;
                case "Entertainment":
                    dialog.setIcon(R.mipmap.ic_entertaiment);
                    break;
                case "Shopping":
                    dialog.setIcon(R.mipmap.ic_shopping);
                    break;
            }
            dialog.setPositive(getResources().getString(R.string.download_app), new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    //Starts the browser with the link of the app
                    String url = app.getLink();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
            dialog.setNeutral("Cambiar imagen", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    takePicture();
                }
            });
            dialog.show();
        } else {
            //starts the detail activity (only for phone)
            Intent applicationIntent = new Intent(ApplicationListActivity.this, ApplicationDetailActivity.class);
            applicationIntent.putExtra(Constants.APPLICATION_ID, app.getId());
            startActivity(applicationIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gridAdapter = new GridAdapter(getApplicationContext());
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }


    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PruebaApps");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        pathPhoto = mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg";
        return new File(pathPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                applicationBussines.updateImagePhoto("file://" + pathPhoto, id);
                gridAdapter = new GridAdapter(getApplicationContext());
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
            }
        }
    }

}
