package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.NetworkObserver;
import modelo.Measures;
import network.NetworkManager;
import util.Path;

public class CadastroMedidasActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;

    private TextInputEditText edt_weight, edt_stature, edt_shoulder, edt_inspiredChest, edt_leftRelaxedArm,
            edt_rightRelaxedArm, edt_leftThigh, edt_rightThigh, edt_leftForearm, edt_rightForearm,
            edt_leftContractedArm, edt_rightContractedArm, edt_waist, edt_abdomen, edt_hip, edt_leftLeg, edt_rightLeg;

    private String weight, stature, shoulder, inspiredChest, leftRelaxedArm, rightRelaxedArm, leftThigh, rightThigh, leftForearm,
            rightForearm, leftContractedArm, rightContractedArm, waist, abdomen, hip, leftLeg, rightLeg;

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

        coordinatorLayout = findViewById(R.id.activity_cadastro_medidas);
    }

    private NetworkObserver getMedidasrObserver() {
        if (networkObserver == null) {
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

            if (attemptRegister()) {
                JSONObject measures = new JSONObject();
                JSONObject object = new JSONObject();

                try {
                    object.put("weight", Float.parseFloat(weight));
                    object.put("stature", Float.parseFloat(stature));
                    object.put("shoulder", Float.parseFloat(shoulder));
                    object.put("inspired_chest", Float.parseFloat(inspiredChest));
                    object.put("left_relaxed_arm", Float.parseFloat(leftRelaxedArm));
                    object.put("right_relaxed_arm", Float.parseFloat(rightRelaxedArm));
                    object.put("left_thigh", Float.parseFloat(leftThigh));
                    object.put("right_thigh", Float.parseFloat(rightThigh));
                    object.put("left_forearm", Float.parseFloat(leftForearm));
                    object.put("right_forearm", Float.parseFloat(rightForearm));
                    object.put("left_contracted_arm", Float.parseFloat(leftContractedArm));
                    object.put("right_contracted_arm", Float.parseFloat(leftContractedArm));
                    object.put("waist", Float.parseFloat(waist));
                    object.put("abdomen", Float.parseFloat(abdomen));
                    object.put("hip", Float.parseFloat(hip));
                    object.put("left_leg", Float.parseFloat(leftLeg));
                    object.put("right_leg", Float.parseFloat(rightLeg));

                    measures.put("measures", object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("JSON", measures.toString());
                manager.putJson(measures, Path.urlUpdateMeasures);
            }
        }
    }

    private boolean attemptRegister() {
        weight = edt_weight.getText().toString();
        stature = edt_stature.getText().toString();
        shoulder = edt_shoulder.getText().toString();
        inspiredChest = edt_inspiredChest.getText().toString();
        leftRelaxedArm = edt_leftRelaxedArm.getText().toString();
        rightRelaxedArm = edt_rightRelaxedArm.getText().toString();
        leftThigh = edt_leftThigh.getText().toString();
        rightThigh = edt_rightThigh.getText().toString();
        leftForearm = edt_leftForearm.getText().toString();
        rightForearm = edt_rightForearm.getText().toString();
        leftContractedArm =  edt_leftContractedArm.getText().toString();
        rightContractedArm = edt_rightContractedArm.getText().toString();
        waist = edt_waist.getText().toString();
        abdomen = edt_abdomen.getText().toString();
        hip = edt_hip.getText().toString();
        leftLeg = edt_leftLeg.getText().toString();
        rightLeg = edt_rightLeg.getText().toString();

        if (weight.isEmpty() || stature.isEmpty() || shoulder.isEmpty() || inspiredChest.isEmpty() || leftRelaxedArm.isEmpty() ||
                rightRelaxedArm.isEmpty() || leftThigh.isEmpty() || rightThigh.isEmpty() || leftForearm.isEmpty() ||
                rightForearm.isEmpty() || leftContractedArm.isEmpty() || rightContractedArm.isEmpty() || waist.isEmpty() ||
                abdomen.isEmpty() || hip.isEmpty()|| leftLeg.isEmpty() || rightLeg.isEmpty()) {

            Snackbar.make(coordinatorLayout, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show();

            return false;
        }

        return true;
    }
}