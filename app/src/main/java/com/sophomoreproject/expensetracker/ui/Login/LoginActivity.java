package com.sophomoreproject.expensetracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.sophomoreproject.expensetracker.ExpenseTrackerApp;
import com.sophomoreproject.expensetracker.R;
import com.sophomoreproject.expensetracker.ui.BaseActivity;
import com.sophomoreproject.expensetracker.ui.MainActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean logged = PreferenceManager.getDefaultSharedPreferences(ExpenseTrackerApp.getContext()).getBoolean(getString(R.string.already_accepted_user_key), false);
        if (logged) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

}
