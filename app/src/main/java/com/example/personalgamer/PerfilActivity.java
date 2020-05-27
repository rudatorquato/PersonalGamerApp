package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Path;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button btn_medidas;

    private NetworkManager manager;
    private NetworkObserver networkObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        loadViews();
        manager = new NetworkManager();
        manager.setNetworkObserver(getUserObserver());
        manager.get(Path.urlGetUsuario);
    }

    private void loadViews() {
        btn_medidas = findViewById(R.id.btn_medidas);
        btn_medidas.setOnClickListener(this);
    }

    private NetworkObserver getUserObserver() {
        if (networkObserver == null){
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {
                    Log.d("RESPONSE", response);
                }

                @Override
                public void doOnError(String response) {
                    Log.d("ERRO", response);
                }
            };
        }
        return networkObserver;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_medidas) {
            startActivity(new Intent(context, CadastroMedidasActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
