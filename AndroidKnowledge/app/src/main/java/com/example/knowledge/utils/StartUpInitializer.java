package com.example.knowledge.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Collections;
import java.util.List;

public class StartUpInitializer implements Initializer<String> {


    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public String create(@NonNull Context context) {

        this.context = context;
        return "StartUpInitializer";
    }

    @Override
    @NonNull
    public List<Class<? extends Initializer<?>>> dependencies() {
        // No dependencies on other libraries.
        return Collections.emptyList();
    }

}