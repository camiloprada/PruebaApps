package com.imaginamos.pruebaapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jrummyapps.android.widget.AnimatedSvgView;
import com.localytics.android.Localytics;


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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Localytics.onNewIntent(this, intent);
    }


}
