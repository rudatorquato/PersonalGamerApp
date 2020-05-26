package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class InfoTreinoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;
    private ImageView img_gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_treino);

        loadViews();
        showGif();
    }

    public void loadViews() {
        img_gif = findViewById(R.id.img_gif);

        coordinatorLayout = findViewById(R.id.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void showGif() {
        Glide.with(context).load(R.drawable.gif_13).into(img_gif);
    }
}
