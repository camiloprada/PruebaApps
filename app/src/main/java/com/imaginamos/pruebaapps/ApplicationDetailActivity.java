package com.imaginamos.pruebaapps;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ApplicationDetailActivity extends AppCompatActivity {

    private ImageView imageViewAppDetail;
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
    
    private void setApplicationInfo(Application app){
        ImageLoader.getInstance().displayImage(app.getUrlImageLarge(), imageViewAppDetail);
        Bitmap image = ImageLoader.getInstance().loadImageSync(app.getUrlImageLarge());

        changeActionBarColorAndTitle(image, app.getTitle());
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

    private void initView(){
        imageViewAppDetail = (ImageView) findViewById(R.id.imageViewAppDetail);
        textViewArtistDetail= (TextView) findViewById(R.id.textViewArtistDetail);
        textViewReleaseDateDetail= (TextView) findViewById(R.id.textViewReleaseDateDetail);
        textViewRightsDetail= (TextView) findViewById(R.id.textViewRightsDetail);
        textViewTypeDetail= (TextView) findViewById(R.id.textViewTypeDetail);
        textViewCategoryDetail= (TextView) findViewById(R.id.textViewCategoryDetail);
        textViewPriceDetail= (TextView) findViewById(R.id.textViewPriceDetail);
        textViewSummaryDetail= (TextView) findViewById(R.id.textViewSummaryDetail);
        textViewSummaryDetail.setMovementMethod(new ScrollingMovementMethod());
        
    }
    private void changeActionBarColorAndTitle(Bitmap image, String title) {

        int dominantColor = getDominantColor(image);
        String strColor = String.format("#%06X", 0xFFFFFF & dominantColor);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if ( bar != null) {

            bar.setTitle(title);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));
        }
    }


    public static int getDominantColor(Bitmap bitmap) {
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 2, 2, true);
        int color = bitmap1.getPixel(0, 1);

        return color;
    }

    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        id = extras.getLong(Constants.APPLICATION_ID);
    }
    private void verifyPageOrientation(){

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
