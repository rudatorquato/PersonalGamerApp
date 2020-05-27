package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Path;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button sign_in_button, sign_up_button;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getCadastroObserver());
        manager.get(Path.urlCadastroUsuarios);
    }

    private NetworkObserver getCadastroObserver() {
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

    private void loadViews(){
        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(this);
        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                startActivity(new Intent(context, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.sign_up_button:
                startActivity(new Intent(context, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            default:
                break;
        }
    }
}
