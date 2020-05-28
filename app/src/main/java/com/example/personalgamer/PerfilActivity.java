package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import controller.MeasuresController;
import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.Measures;
import modelo.Users;
import network.NetworkManager;
import util.Path;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private TextView tv_name, tv_email, tv_telephone, tv_info, tv_type_user, tv_username;
    private TextView tv_weight, tv_stature, tv_shoulder, tv_inspiredChest, tv_leftRelaxedArm,
            tv_rightRelaxedArm, tv_leftThigh, tv_rightThigh, tv_leftForearm, tv_rightForearm,
            tv_leftContractedArm, tv_rightContractedArm, tv_waist, tv_abdomen, tv_hip, tv_leftLeg, tv_rightLeg;
    private Button btn_medidas;

    private Users user;
    private Measures measures;
    private NetworkManager manager;
    private NetworkObserver networkObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getUserObserver());
        manager.get("http://med-apps.herokuapp.com/usuario/");
    }

    private void loadViews() {
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_telephone = findViewById(R.id.tv_telephone);
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
                public void doOnError(String response) {
                }
            };
        }
        return networkObserver;
    }

    public void setUser() {
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_telephone.setText(user.getTelephone());
        tv_info.setText(user.getInfo());
        tv_type_user.setText(user.getTypeUser());
        tv_username.setText(user.getUsername());
    }

    public void setMeasures() {
        tv_weight.setText(String.valueOf(measures.getWeight()));
        tv_stature.setText(String.valueOf(measures.getStature()));
        tv_shoulder.setText(String.valueOf(measures.getShoulder()));
        tv_inspiredChest.setText(String.valueOf(measures.getInspiredChest()));
        tv_leftRelaxedArm.setText(String.valueOf(measures.getLeftRelaxedArm()));
        tv_rightRelaxedArm.setText(String.valueOf(measures.getRightRelaxedArm()));
        tv_leftThigh.setText(String.valueOf(measures.getLeftThigh()));
        tv_rightThigh.setText(String.valueOf(measures.getRightThigh()));
        tv_leftForearm.setText(String.valueOf(measures.getLeftForearm()));
        tv_rightForearm.setText(String.valueOf(measures.getRightForearm()));
        tv_leftContractedArm.setText(String.valueOf(measures.getLeftContractedArm()));
        tv_rightContractedArm.setText(String.valueOf(measures.getRightContractedArm()));
        tv_waist.setText(String.valueOf(measures.getWaist()));
        tv_abdomen.setText(String.valueOf(measures.getAbdomen()));
        tv_hip.setText(String.valueOf(measures.getHip()));
        tv_leftLeg.setText(String.valueOf(measures.getLeftLeg()));
        tv_rightLeg.setText(String.valueOf(measures.getRightLeg()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_medidas) {
            startActivity(new Intent(context, CadastroMedidasActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
