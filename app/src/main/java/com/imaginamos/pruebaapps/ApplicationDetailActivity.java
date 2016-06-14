package com.imaginamos.pruebaapps;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class ApplicationDetailActivity extends AppCompatActivity {

    private SimpleDraweeView imageViewAppDetail;
    private TextView textViewArtistDetail;
    private TextView textViewReleaseDateDetail;
    private TextView textViewRightsDetail;
    private TextView textViewTypeDetail;
    private TextView textViewCategoryDetail;
    private TextView textViewPriceDetail;
    private TextView textViewSummaryDetail;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_detail);
        verifyPageOrientation();
        initView();
        getExtras();
        ApplicationBussines applicationBussines  = new ApplicationBussines(getApplicationContext());
        Application app = applicationBussines.getApplication(id);
        setApplicationInfo(app);
    }

    /**
     * Sets the information of the selected app in main view
     * @param app
     */
    private void setApplicationInfo(Application app){
//        ImageLoader.getInstance().displayImage(app.getUrlImageLarge(), imageViewAppDetail);
//        Bitmap image = ImageLoader.getInstance().loadImageSync(app.getUrlImageLarge());


        Uri uri = Uri.parse(app.getUrlImageLarge());
        imageViewAppDetail.setImageURI(uri);
//        Bitmap image = imageViewAppDetail.getDrawingCache();
//        changeActionBarColorAndTitle(image, app.getTitle());
        textViewArtistDetail.setText(app.getArtist());
        textViewReleaseDateDetail.setText(app.getReleaseDate());
        textViewRightsDetail.setText(app.getRights());
        textViewTypeDetail.setText(app.getType());
        textViewCategoryDetail.setText(app.getCategory());
        if (app.getPrice()==0.0){
        textViewPriceDetail.setText(R.string.free);
        }else {
            textViewPriceDetail.setText(app.getPrice().toString());
        }
        textViewSummaryDetail.setText(app.getSummary());
    }

    /**
     * get the views
     */
    private void initView(){
        imageViewAppDetail = (SimpleDraweeView) findViewById(R.id.imageViewAppDetail);
        textViewArtistDetail= (TextView) findViewById(R.id.textViewArtistDetail);
        textViewReleaseDateDetail= (TextView) findViewById(R.id.textViewReleaseDateDetail);
        textViewRightsDetail= (TextView) findViewById(R.id.textViewRightsDetail);
        textViewTypeDetail= (TextView) findViewById(R.id.textViewTypeDetail);
        textViewCategoryDetail= (TextView) findViewById(R.id.textViewCategoryDetail);
        textViewPriceDetail= (TextView) findViewById(R.id.textViewPriceDetail);
        textViewSummaryDetail= (TextView) findViewById(R.id.textViewSummaryDetail);
        textViewSummaryDetail.setMovementMethod(new ScrollingMovementMethod());
        
    }

    /**
     * Change the action bar color by the dominant color from app image
     * @param image
     * @param title
     */
    private void changeActionBarColorAndTitle(Bitmap image, String title) {

        int dominantColor = getDominantColor(image);
        String strColor = String.format("#%06X", 0xFFFFFF & dominantColor);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if ( bar != null) {

            bar.setTitle(title);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));
        }
    }

    /**
     * get the dominant color from bitmap - (GOOGLE RESEARCH)
     * @param bitmap
     * @return
     */
    public static int getDominantColor(Bitmap bitmap) {
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 2, 2, true);
        int color = bitmap1.getPixel(0, 1);

        return color;
    }

    /**
     * Get extras from previous activity, basically the application ID
     */
    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        id = extras.getLong(Constants.APPLICATION_ID);
    }

    /**
     * verify the device orientation
     */
    private void verifyPageOrientation(){

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
