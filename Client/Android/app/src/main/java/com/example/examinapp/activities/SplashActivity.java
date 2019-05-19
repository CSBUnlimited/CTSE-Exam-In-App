package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.viewmodels.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout _progressSplashLinearLayout;

    private SplashViewModel _splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_splash);

        _progressSplashLinearLayout = findViewById(R.id.progressSplashLinearLayout);

        _splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        _splashViewModel.init();

        _splashViewModel.getIsShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isShowLoading) {
                if (isShowLoading) {
                    _progressSplashLinearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    _progressSplashLinearLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        _splashViewModel.getNextScreenEnum().observe(this, new Observer<NextScreenEnum>() {
            @Override
            public void onChanged(@Nullable NextScreenEnum nextScreenEnum) {
                if (nextScreenEnum == NextScreenEnum.Login) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if (nextScreenEnum == NextScreenEnum.MainStudent) {

                }
                else if (nextScreenEnum == NextScreenEnum.MainLecturer) {
                    showErrorMessageDialog();
                }
                else if (nextScreenEnum == NextScreenEnum.SomeThingWentWrong) {
                    showErrorMessageDialog();
                }
            }
        });
    }

    private void showErrorMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR !!!");
        builder.setMessage("Something went wrong.\nPlease try again!");

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                _splashViewModel.getLoggedInUserData(SplashActivity.this.getApplicationContext());
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        _splashViewModel.getLoggedInUserData(this.getApplicationContext());
    }
}
