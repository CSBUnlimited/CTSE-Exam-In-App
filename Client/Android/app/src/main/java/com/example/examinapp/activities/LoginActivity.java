package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.examinapp.R;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText _usernameTextInputEditText;
    private TextInputEditText _passwordTextInputEditText;
    private Button _loginButton;

    private LinearLayout _progressLoginLinearLayout;
    private LinearLayout _invalidLoginLinearLayout;
    private TextView _invalidLoginTextView;

    private LoginViewModel _loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _usernameTextInputEditText = findViewById(R.id.usernameTextInputEditText);
        _passwordTextInputEditText = findViewById(R.id.passwordTextInputEditText);
        _loginButton = findViewById(R.id.loginButton);

        _progressLoginLinearLayout = findViewById(R.id.progressLoginLinearLayout);
        _invalidLoginLinearLayout = findViewById(R.id.invalidLoginLinearLayout);
        _invalidLoginTextView = findViewById(R.id.invalidLoginTextView);

        _loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        _loginViewModel.init();

        _loginViewModel.getLoadingInformationModelData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable final LoadingInformationModel loadingInformationModel) {

                if (loadingInformationModel.getIsPending()) {
                    _progressLoginLinearLayout.setVisibility(View.VISIBLE);
                    _invalidLoginLinearLayout.setVisibility(View.GONE);
                }
                else if (loadingInformationModel.getIsSucess()) {
                    _progressLoginLinearLayout.setVisibility(View.GONE);
                    _invalidLoginLinearLayout.setVisibility(View.GONE);

                    finish();
                }
                else if (loadingInformationModel.getIsError()) {
                    _invalidLoginTextView.setText(loadingInformationModel.getMessage());
                    _progressLoginLinearLayout.setVisibility(View.GONE);
                    _invalidLoginLinearLayout.setVisibility(View.VISIBLE);
                }

                if (loadingInformationModel.getIsPending()) {
                    _usernameTextInputEditText.setEnabled(false);
                    _passwordTextInputEditText.setEnabled(false);
                    _loginButton.setEnabled(false);
                }
                else {
                    _usernameTextInputEditText.setEnabled(true);
                    _passwordTextInputEditText.setEnabled(true);
                    _loginButton.setEnabled(true);
                }
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _loginViewModel.login(LoginActivity.this.getApplicationContext(), _usernameTextInputEditText.getText().toString(), _passwordTextInputEditText.getText().toString());
            }
        });
    }
}
