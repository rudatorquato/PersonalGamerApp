package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import adapter.ExerciciosAdapter;
import interfaces.OnItemClicked;
import modelo.Training;

public class ExerciciosActivity extends AppCompatActivity implements View.OnClickListener, OnItemClicked {
    private Context context = this;
    private Button btn_cadastrar;
    private ExerciciosAdapter exerciciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        loadViews();

        ArrayList<Training> trainings = new ArrayList<>();
        trainings.add(new Training("Img 01", "Training 01","Place 01","Exerc 01",10, 11,12,13));
        trainings.add(new Training("Img 02", "Training 02","Place 02","Exerc 02",20, 21,22,23));
        trainings.add(new Training("Img 03", "Training 03","Place 03","Exerc 03",30, 31,32,33));
        updateRecycleView(trainings);
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
