package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.NetworkObserver;
import modelo.Measures;
import network.NetworkManager;
import util.Path;

public class CadastroMedidasActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private EditText edt_weight, edt_stature, edt_shoulder, edt_inspiredChest, edt_leftRelaxedArm,
            edt_rightRelaxedArm, edt_leftThigh, edt_rightThigh, edt_leftForearm, edt_rightForearm,
            edt_leftContractedArm, edt_rightContractedArm, edt_waist, edt_abdomen, edt_hip, edt_leftLeg, edt_rightLeg;

    private Button btn_cadastrar;

    private Measures measures;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medidas);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getMedidasrObserver());
    }

    private void loadViews() {
        edt_weight = findViewById(R.id.edt_weight);
        edt_stature = findViewById(R.id.edt_stature);
        edt_shoulder = findViewById(R.id.edt_shouder);
        edt_inspiredChest = findViewById(R.id.edt_inspiredChest);
        edt_leftRelaxedArm = findViewById(R.id.edt_leftRelaxedArm);
        edt_rightRelaxedArm = findViewById(R.id.edt_rightRelaxedArm);
        edt_leftThigh = findViewById(R.id.edt_leftThigh);
        edt_rightThigh = findViewById(R.id.edt_rightThigh);
        edt_leftForearm = findViewById(R.id.edt_leftForearm);
        edt_rightForearm = findViewById(R.id.edt_rightForearm);
        edt_leftContractedArm = findViewById(R.id.edt_leftContractedArm);
        edt_rightContractedArm = findViewById(R.id.edt_rightContractedArm);
        edt_waist = findViewById(R.id.edt_waist);
        edt_abdomen = findViewById(R.id.edt_abdomen);
        edt_hip = findViewById(R.id.edt_hip);
        edt_leftLeg = findViewById(R.id.edt_leftLeg);
        edt_rightLeg = findViewById(R.id.edt_rightLeg);

        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(this);
    }

    private NetworkObserver getMedidasrObserver() {
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
                    startActivity(new Intent(context, PerfilActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
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
        
        if (v.getId() == R.id.btn_cadastrar) {

            JSONObject measures = new JSONObject();
            JSONObject object = new JSONObject();

            try {
                object.put("weight", Float.parseFloat(edt_weight.getText().toString()));
                object.put("stature", Float.parseFloat(edt_stature.getText().toString()));
                object.put("shoulder", Float.parseFloat(edt_shoulder.getText().toString()));
                object.put("inspired_chest", Float.parseFloat(edt_inspiredChest.getText().toString()));
                object.put("left_relaxed_arm", Float.parseFloat(edt_leftRelaxedArm.getText().toString()));
                object.put("right_relaxed_arm", Float.parseFloat(edt_rightRelaxedArm.getText().toString()));
                object.put("left_thigh", Float.parseFloat(edt_leftThigh.getText().toString()));
                object.put("right_thigh", Float.parseFloat(edt_rightThigh.getText().toString()));
                object.put("left_forearm", Float.parseFloat(edt_leftForearm.getText().toString()));
                object.put("right_forearm", Float.parseFloat(edt_rightForearm.getText().toString()));
                object.put("left_contracted_arm", Float.parseFloat(edt_leftContractedArm.getText().toString()));
                object.put("right_contracted_arm", Float.parseFloat(edt_rightContractedArm.getText().toString()));
                object.put("waist", Float.parseFloat(edt_waist.getText().toString()));
                object.put("abdomen", Float.parseFloat(edt_abdomen.getText().toString()));
                object.put("hip", Float.parseFloat(edt_hip.getText().toString()));
                object.put("left_leg", Float.parseFloat(edt_leftLeg.getText().toString()));
                object.put("right_leg", Float.parseFloat(edt_rightLeg.getText().toString()));

                measures.put("measures", object);

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                object.put("weight", 1);
//                object.put("stature", 1);
//                object.put("shoulder", 1);
//                object.put("inspired_chest", 1);
//                object.put("left_relaxed_arm", 1);
//                object.put("right_relaxed_arm", 1);
//                object.put("left_thigh", 1);
//                object.put("right_thigh", 1);
//                object.put("left_forearm", 1);
//                object.put("right_forearm", 1);
//                object.put("left_contracted_arm", 1);
//                object.put("right_contracted_arm", 1);
//                object.put("waist", 1);
//                object.put("abdomen", 1);
//                object.put("hip", 1);
//                object.put("left_leg", 1);
//                object.put("right_leg", 1);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            Log.d("JSON", measures.toString());
            manager.putJson(measures, Path.urlUpdateMeasures);
        }
    }
}
