package com.nikoarap.favqsapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.login.LoginAuthorization;
import com.nikoarap.favqsapp.models.login.UserLoginSessionRequest;
import com.nikoarap.favqsapp.models.login.User;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private int backButtonCount=0;
    private static String tokenResponse;
    private static String loginResponse;
    private static String emailResponse;

    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.link_signup) TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(v -> login());

        signupLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        service.loginAccount(new UserLoginSessionRequest(new User(email, password))).enqueue(new Callback<LoginAuthorization>() {
            @Override
            public void onResponse(@NonNull Call<LoginAuthorization> call, @NonNull Response<LoginAuthorization> response) {
                assert response.body() != null;
                if(response.body().getError_code() !=null){
                    onLoginFailed();
                    progressDialog.dismiss();
                }
                else {
                    Log.i(TAG, "onResponse: " + response.body().getToken());
                    Log.i(TAG, "onResponse: " + response.body().getLogin());
                    Log.i(TAG, "onResponse: " + response.body().getEmail());
                    Log.i(TAG, "onResponse: " + response.body().getError_code());
                    Log.i(TAG, "onResponse: " + response.body().getMessage());
                    tokenResponse = response.body().getToken();
                    loginResponse = response.body().getLogin();
                    emailResponse = response.body().getEmail();
                    SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                    prefEditor.putString("token", tokenResponse);
                    prefEditor.putString("login", loginResponse);
                    prefEditor.putString("email", emailResponse);
                    prefEditor.apply();

                    onLoginSuccess();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginAuthorization> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Response: " + t.getMessage());
                onLoginFailed();
            }
        });
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this,UserMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() > 20) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 120) {
            passwordText.setError("Must be between 5 and 120 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this,
                    "Press the back button again for exit", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
