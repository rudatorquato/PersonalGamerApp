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

    private User personal;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getUserObserver());
        manager.get(Path.urlGetPersonal);
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
                        personal = UsersController.getUser(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (personal != null) {
                        setPersonal();
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

    public void setPersonal() {
        tv_name.setText(personal.getName());
        tv_email.setText(personal.getEmail());
        tv_telephone.setText(personal.getPhone());
    }
}
