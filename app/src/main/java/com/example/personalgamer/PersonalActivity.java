package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.User;
import network.NetworkManager;
import util.Path;

public class PersonalActivity extends AppCompatActivity {
    private Context context = this;
    private TextView tv_name, tv_email, tv_telephone;

    private User user;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getUserObserver());
        manager.get(Path.urlGetUsuario);
    }

    private void loadViews() {
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_telephone = findViewById(R.id.tv_phone);

    }

    private NetworkObserver getUserObserver() {
        if (networkObserver == null){
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {

                    try {
                        user = UsersController.getUser(response);
                        //measures = MeasuresController.getMeasures(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (user != null) {
                        setUser();
                    }
                }

                @Override
                public void doOnPut(String response) {

                }

                @Override
                public void doOnError(String response) {
                }
            };
        }
        return networkObserver;
    }

    public void setUser() {
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_telephone.setText(user.getPhone());
    }
}
