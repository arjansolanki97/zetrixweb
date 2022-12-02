package com.hummingtech.zetrixwebpractical;

import android.app.Application;

public class App extends Application {


    private static App myApp;

    @Override
    public void onCreate() {
        super.onCreate();

        myApp = this;

    }

    public static App getInstance() {
        return myApp;
    }

}
