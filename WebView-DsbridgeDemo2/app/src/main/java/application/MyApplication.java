package application;

import android.app.Application;

import com.coolcollege.aar.application.Options;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Options.init(this);
    }
}
