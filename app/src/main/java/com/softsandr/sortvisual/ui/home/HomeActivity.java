package com.softsandr.sortvisual.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softsandr.sortvisual.R;
import com.softsandr.sortvisual.di.activity.ActivityComponentBuilderHolder;
import com.softsandr.sortvisual.di.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeActivity extends BaseActivity implements HomeActivityPresenter.View {

    public static final int DEF_MAXIMUM_VAL = 1000;

    @Inject
    HomeActivityPresenter presenter;

    private LinearLayout chart;
    private TextView errorTv;
    private Button stopBtn;

    private int chartPadding, chartBarMrg, chartBarWidth;
    private Integer userMaxVal;
    private Long userAnimFrequency;

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
        errorTv = (TextView) findViewById(R.id.act_home__error_tv);
        final TextView inputTv = (TextView) findViewById(R.id.act_home__input);
        findViewById(R.id.act_home__start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userMaxVal = Integer.parseInt(((TextView) findViewById(R.id.act_home__max)).getText().toString());
                    userAnimFrequency = Long.parseLong(((TextView) findViewById(R.id.act_home__frequency)).getText().toString());
                } catch (NumberFormatException ignored) {
                }
                presenter.startSort(inputTv.getText().toString().trim().split("\\s+"), userAnimFrequency);
                stopBtn.setEnabled(true);
            }
        });
        stopBtn = (Button) findViewById(R.id.act_home__stop_btn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.stopSort();
                v.setEnabled(false);
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
        Timber.d("onSortStarted");
        hideSoftKeyboard(findViewById(R.id.act_home__input));
        initChart(size);
    }

    @Override
    public void onSortStep(@NotNull int[] digits) {
        Timber.d("onSortStep");
        Timber.d(Arrays.toString(digits));
        fillChart(digits);
    }

    @Override
    public void onSortFinished(@NotNull int[] digits) {
        Timber.d("onSortFinished");
        Timber.d(Arrays.toString(digits));
        stopBtn.setEnabled(false);
        fillChart(digits);
    }

    @Override
    public void onSortError(@Nullable Throwable th) {
        Timber.d("onSortError: %s", th);
        chart.removeAllViews();
        chart.setVisibility(GONE);
        errorTv.setVisibility(VISIBLE);
        stopBtn.setEnabled(false);
        if (th != null) {
            errorTv.setText(getString(R.string.error_details, th.getMessage()));
        }
    }

    private void initChart(int size) {
        errorTv.setVisibility(GONE);
        chart.removeAllViews();
        chart.setVisibility(VISIBLE);
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

    private void fillChart(int[] digits) {
        chart.removeAllViews();
        int chartHeight = chart.getMeasuredHeight();
        double heightMultiplier = 1f;
        int maxValue = userMaxVal != null ? userMaxVal * 10 : DEF_MAXIMUM_VAL;
        if (maxValue > chartHeight) {
            heightMultiplier = new BigDecimal((double) maxValue / chartHeight).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        } else if (maxValue < chartHeight) {
            heightMultiplier = new BigDecimal((double) chartHeight / maxValue).setScale(0, BigDecimal.ROUND_CEILING).intValue();
        }
        for (int i = 0; i < digits.length; i++) {
            View bar = new View(this);
            bar.setBackground(ContextCompat.getDrawable(this, R.drawable.initial_bar));
            int chartBarHeight = new BigDecimal((maxValue > chartHeight) ? (double) digits[i] / heightMultiplier : (double) digits[i] * heightMultiplier)
                    .setScale(0, BigDecimal.ROUND_CEILING).intValue();
            LinearLayout.LayoutParams barInitialLp = new LinearLayout.LayoutParams(chartBarWidth, chartBarHeight);
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
