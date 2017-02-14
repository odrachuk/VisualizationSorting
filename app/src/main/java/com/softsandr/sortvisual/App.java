package com.softsandr.sortvisual;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.softsandr.sortvisual.di.activity.ActivityComponentBuilder;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import timber.log.Timber;

public class App extends Application implements ActivityComponentBuilderHolder {

    @Inject
    Timber.Tree logTree;

    @Inject
    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> activityComponentBuilders;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().appModule(new AppComponent.AppModule(this)).build().inject(this);
        Timber.plant(logTree);
    }

    @Override
    public ActivityComponentBuilder getActivityComponentBuilder(@NonNull Class<? extends Activity> activityClass) {
        return activityComponentBuilders.get(activityClass).get();
    }

    public static ActivityComponentBuilderHolder getActivityComponentBuilderHolder(Context context) {
        return ((ActivityComponentBuilderHolder) context.getApplicationContext());
    }
}