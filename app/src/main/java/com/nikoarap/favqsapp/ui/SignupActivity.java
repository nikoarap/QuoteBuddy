package com.nikoarap.favqsapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoarap.favqsapp.R;
import com.nikoarap.favqsapp.api.FetchJSONDataAPI;
import com.nikoarap.favqsapp.api.RetrofitRequestClass;
import com.nikoarap.favqsapp.models.signup.SignUpAuthorization;
import com.nikoarap.favqsapp.models.signup.User;
import com.nikoarap.favqsapp.models.signup.UserSignupSessionRequest;
import com.nikoarap.favqsapp.utils.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_layout);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(v -> signup());

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    //user sign up implementation
    public void signup() {
        if (!validate()) {
            return;
        }

        signupButton.setEnabled(false);

        //starts a progressDialog that stops when a Sign up fails or is successful
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.creating_acc));
        progressDialog.show();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //sending user credentials to the server db and gets the authentication back
        FetchJSONDataAPI service = RetrofitRequestClass.fetchApi();
        service.signUpAccount(new UserSignupSessionRequest(new User(name, email, password))).enqueue(new Callback<SignUpAuthorization>() {
            @Override
            public void onResponse(@NonNull Call<SignUpAuthorization> call, @NonNull Response<SignUpAuthorization> response) {
                assert response.body() != null;
                if(response.body().getError_code() !=null){
                    onSignupFailed();
                    progressDialog.dismiss();
                }
                else {
                    onSignupSuccess();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpAuthorization> call, @NonNull Throwable t) {
                t.printStackTrace();
                onSignupFailed();
            }
        });
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Toast.makeText(SignupActivity.this, R.string.sign_up_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(SignupActivity.this, R.string.already_exists, Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    //checks if the user inputs are valid for logging in
    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() > 20) {
            nameText.setError(getString(R.string.must_be_between_2));
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.enter_valid));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 120) {
            passwordText.setError(getString(R.string.must_be_between_2));
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
            Toast.makeText(SignupActivity.this,
                    R.string.press_back, Toast.LENGTH_SHORT).show();
            Constants.backButtonCount++;
        }
    }
}
