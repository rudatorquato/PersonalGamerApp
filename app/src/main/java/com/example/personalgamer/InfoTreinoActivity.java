package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class InfoTreinoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_treino);

        loadViews();
    }

    public void loadViews() {
        coordinatorLayout = findViewById(R.id.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
