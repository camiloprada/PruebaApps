package com.imaginamos.pruebaapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.jrummyapps.android.widget.AnimatedSvgView;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AppCompatActivity {

    private AnimatedSvgView svgView;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);

        svgView.postDelayed(new Runnable() {

            @Override
            public void run() {
                svgView.start();
            }
        }, 500);

        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {

            @Override
            public void onStateChange(int state) {
                if (state == AnimatedSvgView.STATE_TRACE_STARTED) {

                } else if (state == AnimatedSvgView.STATE_FINISHED) {
                    finish();
                    startActivity(new Intent(SplashActivity.this, ApplicationListActivity.class));
                    if (index == -1) index = 0; // first time
                }
            }
        });

    }
    /**
     * Splash from the library AwesomeSplash with custom configuration
     * @param configurationSplash
     */
//    @Override
//    public void initSplash(ConfigSplash configurationSplash) {
//
//        boolean tablet = getResources().getBoolean(R.bool.tablet);
//        if (tablet) {
//            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//        configurationSplash.setBackgroundColor(R.color.colorPrimary);
//        configurationSplash.setAnimCircularRevealDuration(2000);
//        configurationSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
//        configurationSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);
//        if(tablet){
//        configurationSplash.setLogoSplash(R.drawable.ic_splash_tablet);}
//        else{
//        configurationSplash.setLogoSplash(R.drawable.ic_splash_phone);}
//        configurationSplash.setAnimLogoSplashDuration(2000);
//        configurationSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);
//
//        configurationSplash.setOriginalHeight(400);
//        configurationSplash.setOriginalWidth(400);
//        configurationSplash.setAnimPathStrokeDrawingDuration(3000);
//        configurationSplash.setPathSplashStrokeSize(3);
//        configurationSplash.setPathSplashStrokeColor(R.color.colorPrimary);
//        configurationSplash.setAnimPathFillingDuration(3000);
//        configurationSplash.setPathSplashFillColor(R.color.colorPrimaryDark);
//
//        configurationSplash.setTitleSplash(getString(R.string.splash_text));
//        configurationSplash.setTitleTextColor(R.color.colorAccent);
//        configurationSplash.setTitleTextSize(45f);
//        configurationSplash.setAnimTitleDuration(2000);
//        configurationSplash.setAnimTitleTechnique(Techniques.FlipInX);
//
//    }
//
//    @Override
//    public void animationsFinished() {
//        this.finish();
//        startActivity(new Intent(SplashActivity.this, ApplicationListActivity.class));
//    }

}
