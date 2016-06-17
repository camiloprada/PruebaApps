package com.imaginamos.pruebaapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.github.clans.fab.FloatingActionButton;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static String pathPhoto;
    private SimpleDraweeView imageViewAppDetail;
    private TextView textViewArtistDetail;
    private TextView textViewReleaseDateDetail;
    private TextView textViewRightsDetail;
    private TextView textViewTypeDetail;
    private TextView textViewCategoryDetail;
    private TextView textViewPriceDetail;
    private TextView textViewSummaryDetail;
    private Long id;
    private FloatingActionButton mFab;
    private ApplicationBussines applicationBussines;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_detail);
        verifyPageOrientation();
        initView();
        getExtras();
        applicationBussines = new ApplicationBussines(getApplicationContext());
        Application app = applicationBussines.getApplication(id);
        setApplicationInfo(app);
    }

    /**
     * Sets the information of the selected app in main view
     *
     * @param app
     */
    private void setApplicationInfo(Application app) {

        Uri uri = Uri.parse(app.getUrlImageLarge());
        setImage(uri, imageViewAppDetail);
        textViewArtistDetail.setText(app.getArtist());
        textViewReleaseDateDetail.setText(app.getReleaseDate());
        textViewRightsDetail.setText(app.getRights());
        textViewTypeDetail.setText(app.getType());
        textViewCategoryDetail.setText(app.getCategory());
        if (app.getPrice() == 0.0) {
            textViewPriceDetail.setText(R.string.free);
        } else {
            textViewPriceDetail.setText(app.getPrice().toString());
        }
        textViewSummaryDetail.setText(app.getSummary());
    }

    /**
     * get the views
     */
    private void initView() {
        imageViewAppDetail = (SimpleDraweeView) findViewById(R.id.imageViewAppDetail);
        textViewArtistDetail = (TextView) findViewById(R.id.textViewArtistDetail);
        textViewReleaseDateDetail = (TextView) findViewById(R.id.textViewReleaseDateDetail);
        textViewRightsDetail = (TextView) findViewById(R.id.textViewRightsDetail);
        textViewTypeDetail = (TextView) findViewById(R.id.textViewTypeDetail);
        textViewCategoryDetail = (TextView) findViewById(R.id.textViewCategoryDetail);
        textViewPriceDetail = (TextView) findViewById(R.id.textViewPriceDetail);
        textViewSummaryDetail = (TextView) findViewById(R.id.textViewSummaryDetail);
        textViewSummaryDetail.setMovementMethod(new ScrollingMovementMethod());
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

    }

    /**
     * Get extras from previous activity, basically the application ID
     */
    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        id = extras.getLong(Constants.APPLICATION_ID);
    }

    /**
     * verify the device orientation
     */
    private void verifyPageOrientation() {

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                takePicture();
                break;
        }
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
                Uri uri = Uri.parse("file://" + pathPhoto);
                setImage(uri, imageViewAppDetail);
            }
        }
    }

    private void setImage(Uri uri, SimpleDraweeView view) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(150, 150))
                .setAutoRotateEnabled(true)
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setAutoPlayAnimations(true)
                .build();
        view.setController(draweeController);
    }
}