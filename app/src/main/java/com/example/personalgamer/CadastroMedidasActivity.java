package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Path;

public class CadastroMedidasActivity extends AppCompatActivity {

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medidas);
        manager = new NetworkManager();
        manager.setNetworkObserver(getMedidasrObserver());
        manager.get(Path.urlCadastroUsuarios);
    }

    private NetworkObserver getMedidasrObserver() {
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
}
