package com.softsandr.sortvisual.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softsandr.sortvisual.R;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;
import com.softsandr.sortvisual.di.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeActivityPresenter.View {

    @Inject
    HomeActivityPresenter presenter;

    private LinearLayout chart;
    private int chartPadding, chartBarMrg, chartBarWidth;

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
        chart = (LinearLayout) findViewById(R.id.act_home__chart);
        chartPadding = getResources().getDimensionPixelSize(R.dimen.act_home__frame_padding);
        chartBarMrg = getResources().getDimensionPixelSize(R.dimen.act_home__bar_margin_right);
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
    public void onSortStarted(int size) {
        hideSoftKeyboard(findViewById(R.id.act_home__array_et));
        initChart(size);
    }

    private void initChart(int size) {
        chart.removeAllViews();
        chartBarWidth = (chart.getMeasuredWidth() - chartPadding * 2 - chartBarMrg * (size - 1)) / size;
        LinearLayout.LayoutParams barInitialLp = new LinearLayout.LayoutParams(chartBarWidth, FrameLayout.LayoutParams.MATCH_PARENT);
        barInitialLp.gravity = Gravity.BOTTOM;
        for (int i = 0; i < size; i++) {
            View bar = new View(this);
            bar.setBackground(ContextCompat.getDrawable(this, R.drawable.initial_bar));
            if (i != size - 1) {
                barInitialLp.rightMargin = chartBarMrg;
            }
            bar.setLayoutParams(barInitialLp);
            chart.addView(bar);
        }
    }

    @Override
    public void onSortFinished(@NotNull int[] digits) {
        Timber.d(Arrays.toString(digits));
        fillChart(digits);
    }

    private void fillChart(int[] digits) {
        chart.removeAllViews();
        int maxValue = digits[digits.length - 1];
        int chartHeight = chart.getMeasuredHeight();
        double heightMultiplier = 1f;
        if (maxValue > chartHeight) {
            heightMultiplier = new BigDecimal((double) maxValue / chartHeight).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        } else if (maxValue < chartHeight) {
            heightMultiplier = new BigDecimal((double) chartHeight / maxValue).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        }
        for (int i = 0; i < digits.length; i++) {
            View bar = new View(this);
            bar.setBackground(ContextCompat.getDrawable(this, R.drawable.initial_bar));
            LinearLayout.LayoutParams barInitialLp = new LinearLayout.LayoutParams(chartBarWidth, (int) (digits[i] * heightMultiplier));
            barInitialLp.gravity = Gravity.BOTTOM;
            if (i != digits.length - 1) {
                barInitialLp.rightMargin = chartBarMrg;
            }
            bar.setLayoutParams(barInitialLp);
            chart.addView(bar);
        }
    }

    private void hideSoftKeyboard(View target) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }
}
