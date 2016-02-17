package com.imaginamos.pruebaapps;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.imaginamos.pruebaapps.model.Application;
import com.imaginamos.pruebaapps.model.ApplicationBussines;
import com.imaginamos.pruebaapps.util.Constants;

public class ApplicationDetailActivity extends AppCompatActivity {

    
    private TextView textViewTitleDetail;
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

        textViewTitleDetail.setText(app.getTitle());
        textViewArtistDetail.setText(app.getArtist());
        textViewReleaseDateDetail.setText(app.getReleaseDate());
        textViewRightsDetail.setText(app.getRights());
        textViewTypeDetail.setText(app.getType());
        textViewCategoryDetail.setText(app.getCategory());
        textViewPriceDetail.setText(app.getPrice().toString());
        textViewSummaryDetail.setText(app.getSummary());
        
    }

    private void initView(){
        textViewTitleDetail= (TextView) findViewById(R.id.textViewTitleDetail);
        textViewArtistDetail= (TextView) findViewById(R.id.textViewArtistDetail);
        textViewReleaseDateDetail= (TextView) findViewById(R.id.textViewReleaseDateDetail);
        textViewRightsDetail= (TextView) findViewById(R.id.textViewRightsDetail);
        textViewTypeDetail= (TextView) findViewById(R.id.textViewTypeDetail);
        textViewCategoryDetail= (TextView) findViewById(R.id.textViewCategoryDetail);
        textViewPriceDetail= (TextView) findViewById(R.id.textViewPriceDetail);
        textViewSummaryDetail= (TextView) findViewById(R.id.textViewSummaryDetail);
        textViewSummaryDetail.setMovementMethod(new ScrollingMovementMethod());
        
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
