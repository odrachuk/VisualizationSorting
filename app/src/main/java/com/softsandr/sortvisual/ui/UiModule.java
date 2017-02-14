package com.softsandr.sortvisual.ui;

import com.softsandr.sortvisual.di.activity.ActivityComponentBuilder;
import com.softsandr.sortvisual.di.activity.ActivityKey;
import com.softsandr.sortvisual.ui.home.HomeActivity;
import com.softsandr.sortvisual.ui.home.HomeActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(
        subcomponents = {
                HomeActivityComponent.class
        }
)
public abstract class UiModule {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity.class)
    public abstract ActivityComponentBuilder homeActivityComponentBuilder(HomeActivityComponent.Builder impl);
}