package com.softsandr.sortvisual.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.softsandr.sortvisual.R;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;
import com.softsandr.sortvisual.di.activity.BaseActivity;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity implements HomeActivityPresenter.View {

    @Inject
    HomeActivityPresenter presenter;

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

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.act_home__toolbar));
        presenter.startSort();
    }

    @Override
    public void onSortFinished() {
        Toast.makeText(this, "onSortFinished", Toast.LENGTH_SHORT).show();
    }
}
