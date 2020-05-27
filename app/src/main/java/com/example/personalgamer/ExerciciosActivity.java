package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import adapter.ExerciciosAdapter;
import modelo.Training;

public class ExerciciosActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private ExerciciosAdapter exerciciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        ArrayList<Training> trainings = new ArrayList<>();
        trainings.add(new Training("dasd", "dasda","dasdas","dasda",0303, 9494,49949,4004));
        trainings.add(new Training("dasd", "dasda","dasdas","dasda",0303, 9494,49949,4004));
        trainings.add(new Training("dasd", "dasda","dasdas","dasda",0303, 9494,49949,4004));
        updateRecycleView(trainings);
    }

    private void updateRecycleView(List<Training> trainings) {
        exerciciosAdapter = new ExerciciosAdapter(trainings);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(exerciciosAdapter);
        registerForContextMenu(recyclerView);
//        exerciciosAdapter.setOnClick(ExerciciosActivity.this);
    }

    @Override
    public void onClick(View v) {

    }
}
