package com.example.examenordinario;

import android.app.Application;
import android.os.SystemClock;

public class splashscreen extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1000);
    }
}


