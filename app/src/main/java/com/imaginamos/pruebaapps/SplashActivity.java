package com.imaginamos.pruebaapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configuracionSplash) {

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        configuracionSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configuracionSplash.setAnimCircularRevealDuration(2000); //int ms
        configuracionSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configuracionSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        configuracionSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
        configuracionSplash.setAnimLogoSplashDuration(2000); //int ms
        configuracionSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //configuracionSplash.setLogoSplash();
        // configuracionSplash.setPathSplash("fonts/imagen.png"); //set path String
        configuracionSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configuracionSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configuracionSplash.setAnimPathStrokeDrawingDuration(3000);
        configuracionSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configuracionSplash.setPathSplashStrokeColor(R.color.colorPrimary); //any color you want form colors.xml
        configuracionSplash.setAnimPathFillingDuration(3000);
        configuracionSplash.setPathSplashFillColor(R.color.colorPrimaryDark); //path object filling color

        configuracionSplash.setTitleSplash("My Awesome App");
        configuracionSplash.setTitleTextColor(R.color.colorAccent);
        configuracionSplash.setTitleTextSize(30f); //float value
        configuracionSplash.setAnimTitleDuration(3000);
        configuracionSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configuracionSplash.setTitleFont("fonts/diti_sweet.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {
        this.finish();
        startActivity(new Intent(SplashActivity.this, ApplicationListActivity.class));
    }

}
