package com.softsandr.sortvisual.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.softsandr.sortvisual.R;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;
import com.softsandr.sortvisual.di.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import javax.inject.Inject;

import timber.log.Timber;

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
        final TextView inputTv = (TextView) findViewById(R.id.act_home__array_et);
        findViewById(R.id.act_home__start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startSort(inputTv.getText().toString().trim().split("\\s+"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSortStarted() {
        hideSoftKeyboard(findViewById(R.id.act_home__array_et));
    }

    @Override
    public void onSortFinished(@NotNull int[] digits) {
        Timber.d(Arrays.toString(digits));
    }

    private void hideSoftKeyboard(View target) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }
}
