package com.example.personalgamer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Context context = this;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        startActivity(new Intent(context, CadastraExercicioActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    private void loadViews() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferences.contains("is_logged")) {
            startActivity(new Intent(context, DashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }
}

