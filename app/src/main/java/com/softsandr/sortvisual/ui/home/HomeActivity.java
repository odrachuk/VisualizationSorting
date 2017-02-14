package com.softsandr.sortvisual.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.softsandr.sortvisual.R;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;
import com.softsandr.sortvisual.di.activity.BaseActivity;
import com.softsandr.sortvisual.di.fragment.FragmentComponentBuilder;
import com.softsandr.sortvisual.di.fragment.FragmentComponentBuilderHolder;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class HomeActivity extends BaseActivity implements FragmentComponentBuilderHolder {

    @Inject
    HomeActivityPresenter activityPresenter;

    @Inject
    Map<Class<? extends Fragment>, Provider<FragmentComponentBuilder>> fragmentComponentBuilders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        initView();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void injectMembers(@NonNull ActivityComponentBuilderHolder activityComponentBuilderHolder) {
        ((HomeActivityComponent.Builder) activityComponentBuilderHolder.getActivityComponentBuilder(HomeActivity.class))
                .activityModule(new HomeActivityComponent.HomeActivityModule(this))
                .build()
                .injectMembers(this);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public FragmentComponentBuilder getFragmentComponentBuilder(@NonNull Class<? extends Fragment> fragmentClass) {
        return fragmentComponentBuilders.get(fragmentClass).get();
    }

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.act_home__toolbar));
    }
}
