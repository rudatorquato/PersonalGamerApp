package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;
    private CardView crd_perfil, crd_qrcode, crd_treino, crd_personal, crd_game;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();
    }

    private void loadViews() {
        crd_perfil = findViewById(R.id.crd_perfil);
        crd_perfil.setOnClickListener(this);
        crd_qrcode = findViewById(R.id.crd_qrcode);
        crd_qrcode.setOnClickListener(this);
        crd_treino = findViewById(R.id.crd_treino);
        crd_treino.setOnClickListener(this);
        crd_personal = findViewById(R.id.crd_personal);
        crd_personal.setOnClickListener(this);
        crd_game = findViewById(R.id.crd_game);
        crd_game.setOnClickListener(this);

        coordinatorLayout = findViewById(R.id.activity_dashboard);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crd_perfil:
                startActivity(new Intent(context, PerfilActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.crd_qrcode:
                startActivity(new Intent(context, QrCodeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.crd_treino:
                startActivity(new Intent(context, ExerciciosActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.crd_personal:
                startActivity(new Intent(context, PersonalActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.crd_game:
                startActivity(new Intent(context, GameActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            preferences.edit()
                    .remove("is_logged")
                    .remove("id")
                    .apply();

            startActivity(new Intent(context, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
