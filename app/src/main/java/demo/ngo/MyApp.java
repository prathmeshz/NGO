package demo.ngo;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by prathmesh zade on 7/12/2017.
 */

public class MyApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AppFont.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
