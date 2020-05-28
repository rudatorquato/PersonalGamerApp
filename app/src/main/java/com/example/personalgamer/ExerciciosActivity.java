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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ExerciciosAdapter;
import interfaces.OnItemClicked;
import modelo.Training;

public class ExerciciosActivity extends AppCompatActivity implements View.OnClickListener, OnItemClicked {
    private Context context = this;
    private Button btn_cadastrar;
    private ExerciciosAdapter exerciciosAdapter;

    private TextView tv_training, tv_sequence, tv_place, tv_exercise, tv_series, tv_repetitions, tv_charge;

    private Training training;

    

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

        tv_training = findViewById(R.id.tv_training);
        tv_sequence = findViewById(R.id.tv_sequence);
        tv_place = findViewById(R.id.tv_place);
        tv_exercise = findViewById(R.id.tv_exercise);
        tv_series = findViewById(R.id.tv_series);
        tv_repetitions = findViewById(R.id.tv_repetition);
        tv_charge = findViewById(R.id.tv_charge);


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

    public void setTraining() {
        tv_training.setText(training.getTraining());
        tv_sequence.setText(String.valueOf(training.getSequence()));
        tv_place.setText(training.getPlace());
        tv_exercise.setText(training.getExercise());
        tv_series.setText(String.valueOf(training.getSeries()));
        tv_repetitions.setText(String.valueOf(training.getRepetitions()));
        tv_charge.setText(String.valueOf(training.getCharge()));
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
