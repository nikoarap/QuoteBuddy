package com.nikoarap.quotebuddy.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nikoarap.quotebuddy.R;
import com.nikoarap.quotebuddy.preferences.PrefsHelper;
import com.nikoarap.quotebuddy.utils.Constants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserMenuActivity extends AppCompatActivity {

    private PrefsHelper prefsHelper = new PrefsHelper();
    public static String loginResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //gets the saved user credentials(login, user-token) from SharedPreferences
        loginResponse = prefsHelper.getStringfromPrefs(getString(R.string.loginResponse),UserMenuActivity.this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favourites, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(UserMenuActivity.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(UserMenuActivity.this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


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
            Toast.makeText(UserMenuActivity.this,
                    R.string.press_back, Toast.LENGTH_SHORT).show();
            Constants.backButtonCount++;
        }
    }

    //menu button at action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            LogOutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("InflateParams")
    public void LogOutDialog() {
        this.setTheme(R.style.AlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.logout_dialog_layout, null);
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            //deletes the user-token from the Shared Preferences, so that he stays logged out
            prefsHelper.deleteStringfromPrefs(getString(R.string.tokenResponse),UserMenuActivity.this);
            Intent intent = new Intent(UserMenuActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
        });
        builder.show();
    }

}