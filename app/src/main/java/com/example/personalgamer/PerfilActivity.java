package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import controller.MeasuresController;
import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.Measures;
import modelo.User;
import network.NetworkManager;
import util.Path;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private TextView tv_name, tv_email, tv_phone, tv_info, tv_type_user, tv_username;
    private TextView tv_weight, tv_stature, tv_shoulder, tv_inspiredChest, tv_leftRelaxedArm,
            tv_rightRelaxedArm, tv_leftThigh, tv_rightThigh, tv_leftForearm, tv_rightForearm,
            tv_leftContractedArm, tv_rightContractedArm, tv_waist, tv_abdomen, tv_hip, tv_leftLeg, tv_rightLeg;
    private Button btn_medidas;

    private User user;
    private Measures measures;
    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getUserObserver());
        //manager.get(Path.urlGetUsuario); //+ preferences.getString("id", "none"));
        manager.get(Path.urlGetUsuario.concat(preferences.getString("id", "none")));
    }

    private void loadViews() {
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        tv_info = findViewById(R.id.tv_info);
        tv_type_user = findViewById(R.id.tv_type_user);
        tv_username = findViewById(R.id.tv_username);

        tv_weight = findViewById(R.id.tv_weight);
        tv_stature = findViewById(R.id.tv_stature);
        tv_shoulder = findViewById(R.id.tv_shoulder);
        tv_inspiredChest = findViewById(R.id.tv_inspiredChest);
        tv_leftRelaxedArm = findViewById(R.id.tv_leftRelaxedArm);
        tv_rightRelaxedArm = findViewById(R.id.tv_rightRelaxedArm);
        tv_leftThigh = findViewById(R.id.tv_leftThigh);
        tv_rightThigh = findViewById(R.id.tv_rightThigh);
        tv_leftForearm = findViewById(R.id.tv_leftForearm);
        tv_rightForearm = findViewById(R.id.tv_rightForearm);
        tv_leftContractedArm = findViewById(R.id.tv_leftContractedArm);
        tv_rightContractedArm = findViewById(R.id.tv_rightContractedArm);
        tv_waist = findViewById(R.id.tv_waist);
        tv_abdomen = findViewById(R.id.tv_abdomen);
        tv_hip = findViewById(R.id.tv_hip);
        tv_leftLeg = findViewById(R.id.tv_leftLeg);
        tv_rightLeg = findViewById(R.id.tv_rightLeg);

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
                    Log.d("RESPONSE_PERFIL", response);
                    try {
                        user = UsersController.getUser(response);
                        measures = MeasuresController.getMeasures(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (user != null) {
                        setUser();
                    }

                    if (measures != null) {
                        setMeasures();
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
        tv_phone.setText(user.getPhone());
        tv_info.setText(user.getInfo());
        tv_type_user.setText(user.getTypeUser());
        tv_username.setText(user.getUsername());
    }

    public void setMeasures() {
        tv_weight.setText(String.valueOf(measures.getWeight()));
        tv_stature.setText(String.valueOf(measures.getStature()));
        tv_shoulder.setText(String.valueOf(measures.getShoulder()));
        tv_inspiredChest.setText(String.valueOf(measures.getInspiredChest()));
        tv_leftRelaxedArm.append(String.valueOf(measures.getLeftRelaxedArm()));
        tv_rightRelaxedArm.append(String.valueOf(measures.getRightRelaxedArm()));
        tv_leftThigh.append(String.valueOf(measures.getLeftThigh()));
        tv_rightThigh.append(String.valueOf(measures.getRightThigh()));
        tv_leftForearm.append(String.valueOf(measures.getLeftForearm()));
        tv_rightForearm.append(String.valueOf(measures.getRightForearm()));
        tv_leftContractedArm.append(String.valueOf(measures.getLeftContractedArm()));
        tv_rightContractedArm.append(String.valueOf(measures.getRightContractedArm()));
        tv_waist.setText(String.valueOf(measures.getWaist()));
        tv_abdomen.setText(String.valueOf(measures.getAbdomen()));
        tv_hip.setText(String.valueOf(measures.getHip()));
        tv_leftLeg.append(String.valueOf(measures.getLeftLeg()));
        tv_rightLeg.append(String.valueOf(measures.getRightLeg()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_medidas) {
            startActivity(new Intent(context, CadastroMedidasActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
