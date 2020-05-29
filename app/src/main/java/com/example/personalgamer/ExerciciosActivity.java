package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import adapter.ExerciciosAdapter;
import controller.TraningController;
import interfaces.NetworkObserver;
import interfaces.OnItemClicked;
import modelo.Training;
import network.NetworkManager;
import util.Path;

public class ExerciciosActivity extends AppCompatActivity implements View.OnClickListener, OnItemClicked {
    private Context context = this;
    private Button btn_cadastrar;
    private ExerciciosAdapter exerciciosAdapter;

    private Training training;
    private ArrayList<Training> trainings = new ArrayList<>();

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();

        updateRecycleView(trainings);

        manager = new NetworkManager();
        manager.setNetworkObserver(getExerciciosObserver());

        Log.d("ID_LOGADO", preferences.getString("id", "none"));
        manager.get(Path.urlGetUsuario.concat(preferences.getString("id", "none")));
    }

    private NetworkObserver getExerciciosObserver() {
        if (networkObserver == null) {
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {
                    Log.d("RESPONSE_EXERCICIOS", response);

                    try {
                        training = TraningController.getTranings(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    trainings.add(training);

                    updateRecycleView(trainings);
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

    private void loadViews() {
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(this);
    }

    private void updateRecycleView(List<Training> trainings) {
        exerciciosAdapter = new ExerciciosAdapter(trainings);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(exerciciosAdapter);
        registerForContextMenu(recyclerView);
        exerciciosAdapter.setOnClick(ExerciciosActivity.this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cadastrar) {
            startActivity(new Intent(context, CadastraExercicioActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(context, InfoTreinoActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
