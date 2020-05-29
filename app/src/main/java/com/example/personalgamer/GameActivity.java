package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.User;
import network.NetworkManager;

public class GameActivity extends AppCompatActivity {
    private Context context = this;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private User exp;

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
                    try {
                        exp = UsersController.getUser(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (exp != null) {
                        setXp();
                    }
                }

                @Override
                public void doOnPut(String response) {

                }

                @Override
                public void doOnError(String response) {
                    Log.d("ERRO", response);
                }
            };
        }
        return networkObserver;
    }

    public void setXp() {

    }
}
