package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Path;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private TextInputEditText edt_user, edt_email, edt_user_type;
    private Button sign_in_button, sign_up_button;
    private CoordinatorLayout coordinatorLayout;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getCadastroObserver());
    }

    private void loadViews(){
        edt_user = findViewById(R.id.edt_user);
        edt_email = findViewById(R.id.edt_email);
        edt_user_type = findViewById(R.id.edt_user_type);

        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(this);
        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(this);

        coordinatorLayout = findViewById(R.id.activity_cadastro);
    }

    private NetworkObserver getCadastroObserver() {
        if (networkObserver == null){
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                startActivity(new Intent(context, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.sign_up_button:
                Toast.makeText(context, "Cadastrado efetuado", Toast.LENGTH_SHORT).show();


                startActivity(new Intent(context, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            default:
                break;
        }
    }
}
