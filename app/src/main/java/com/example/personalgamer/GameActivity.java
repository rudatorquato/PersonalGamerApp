package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.User;
import network.NetworkManager;
import util.Path;

public class GameActivity extends AppCompatActivity {
    private Context context = this;

    private NetworkManager manager;
    private NetworkObserver networkObserver;


    private User user;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getGameObserver());

        manager.get(Path.urlGetUsuario.concat(preferences.getString("id", "none")));
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
