package com.nikoarap.quotebuddy.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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


import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.api.APIHandlingService;
import com.nikoarap.quotebuddy.api.RetrofitRequestClass;
import com.nikoarap.quotebuddy.models.login.LoginAuthorization;
import com.nikoarap.quotebuddy.models.login.UserLoginSessionRequest;
import com.nikoarap.quotebuddy.models.login.User;
import com.nikoarap.quotebuddy.preferences.PrefsHelper;
import com.nikoarap.quotebuddy.utils.Constants;


public class LoginActivity extends AppCompatActivity {

    private PrefsHelper prefsHelper = new PrefsHelper();


    //bind all the viewObjects in ButterKnife
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

    //login authentication implementation
    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        //starts a progressDialog that stops when a Login fails or is successful
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.auth));
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //sending user credentials to the server db and gets the authentication back
        APIHandlingService service = RetrofitRequestClass.fetchApi();
        service.loginAccount(new UserLoginSessionRequest(new User(email, password))).enqueue(new Callback<LoginAuthorization>() {
            @Override
            public void onResponse(@NonNull Call<LoginAuthorization> call, @NonNull Response<LoginAuthorization> response) {
                assert response.body() != null;
                if(response.body().getError_code() !=null){
                    onLoginFailed();
                    progressDialog.dismiss();
                }
                else {
                    //save all user login data to Shared preferences
                    prefsHelper.saveLoginData(response,LoginActivity.this);
                    onLoginSuccess();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginAuthorization> call, @NonNull Throwable t) {
                t.printStackTrace();
                onLoginFailed();
            }
        });
    }


    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this,UserMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, R.string.invalid_mail_pass, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    //checks if the user inputs are valid for logging in
    public boolean validate() {
        boolean valid = true;

        String name = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() > 20) {
            emailText.setError(getString(R.string.enter_valid));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 120) {
            passwordText.setError(getString(R.string.must_be_between));
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    //pressing the backButton twice exits the application
    @Override
    public void onBackPressed() {
        if(Constants.backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this,
                    getString(R.string.press_back), Toast.LENGTH_SHORT).show();
            Constants.backButtonCount++;
        }
    }
}
