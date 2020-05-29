package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.User;
import network.NetworkManager;
import util.Path;

public class GameActivity extends AppCompatActivity {
    private Context context = this;
    private TextView tv_exp;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getGameObserver());

        manager.get(Path.urlGetUsuario.concat(preferences.getString("id", "none")));
    }

    private void loadViews() {
        tv_exp = findViewById(R.id.tv_exp);
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
                        user = UsersController.getUser(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (user != null) {
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
        tv_exp.setText(user.getExp());
    }
}
