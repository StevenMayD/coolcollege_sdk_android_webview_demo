package application;

import android.app.Application;

import com.coolcollege.aar.application.Options;


public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Options.init(this);
    }

    public static MyApplication get() {
        return instance;
    }
}
