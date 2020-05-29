package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import interfaces.NetworkObserver;
import network.NetworkManager;

public class GameActivity extends AppCompatActivity {
    private Context context = this;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getGameObserver());
    }

    private void loadViews() {

    }

    private NetworkObserver getGameObserver() {
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
                public void doOnPut(String response) {
                    startActivity(new Intent(context, DashboardActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
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
