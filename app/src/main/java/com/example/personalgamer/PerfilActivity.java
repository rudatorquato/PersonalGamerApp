package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button btn_medidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        loadViews();
    }

    private void loadViews() {
        btn_medidas = findViewById(R.id.btn_medidas);
        btn_medidas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_medidas) {
            startActivity(new Intent(context, CadastroMedidasActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
