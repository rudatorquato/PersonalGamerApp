package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.NetworkObserver;
import modelo.Training;
import network.NetworkManager;
import util.Path;

public class CadastraExercicioActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;

    private EditText edt_training, edt_place, edt_exercise, edt_image, edt_sequence,
            edt_series, edt_repetitions, edt_charge;

    private String training, place, exercise, image, sequence, series,repetitions, charge;

    private Button btn_cadastrar;

    private Training mtraining;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_exercicio);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getExercicioObserver());


    }

    private void loadViews() {
        edt_training = findViewById(R.id.edt_training);
        edt_place = findViewById(R.id.edt_place);
        edt_exercise = findViewById(R.id.edt_exercise);
        //edt_image = findViewById(R.id.edt_image);
        edt_sequence = findViewById(R.id.edt_sequence);
        edt_series = findViewById(R.id.edt_series);
        edt_repetitions = findViewById(R.id.edt_repetitions);
        edt_charge = findViewById(R.id.edt_charge);

        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(this);

        coordinatorLayout = findViewById(R.id.activity_cadastra_exercicio);
    }

    private NetworkObserver getExercicioObserver() {
        if (networkObserver == null){
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {
                    Log.d("RESPONSE", response);
                }

                @Override
                public void doOnPut(String response) {
                    startActivity(new Intent(context, DashboardActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }

                @Override
                public void doOnError(String response) {
                    Log.d("ERRO", response);
                }
            };
        }
        return networkObserver;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_cadastrar) {

            if (attemptRegister()) {
                JSONObject mtraining = new JSONObject();
                JSONObject object = new JSONObject();

                try {
                    object.put("training", training);
                    object.put("place", place);
                    object.put("exercise", exercise);
                    //object.put("image", image);
                    object.put("sequence", Integer.parseInt(sequence));
                    object.put("series", Integer.parseInt(series));
                    object.put("repetitions", Integer.parseInt(repetitions));
                    object.put("charge", Integer.parseInt(charge));

                    mtraining.put("training", object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                Log.d("JSON", mtraining.toString());
                manager.putJson(mtraining, Path.urlUpdateTraning.concat(preferences.getString("id", "none")));

            }
        }
    }

    private boolean attemptRegister() {

        training = edt_training.getText().toString();
        place = edt_place.getText().toString();
        exercise = edt_exercise.getText().toString();
        //image = edt_image.getText().toString();
        sequence = edt_sequence.getText().toString();
        series = edt_series.getText().toString();
        repetitions = edt_repetitions.getText().toString();
        charge = edt_charge.getText().toString();

        if (training.isEmpty() || place.isEmpty() || exercise.isEmpty() || sequence.isEmpty() || series.isEmpty() ||
                repetitions.isEmpty() || charge.isEmpty()) {

            Snackbar.make(coordinatorLayout, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show();

            return false;
        }

        return true;
    }
}
