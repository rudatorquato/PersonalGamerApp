package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import controller.TrainingController;
import interfaces.NetworkObserver;
import modelo.Training;
import network.NetworkManager;
import util.Path;

public class InfoTreinoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private TextView tv_training, tv_sequence, tv_place, tv_exercise, tv_series, tv_repetitions, tv_charge;
    private CoordinatorLayout coordinatorLayout;
    private ImageView img_gif;
    private String qrcode;
    private Training training;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_treino);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getExerciciosObserver());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            qrcode = bundle.getString("qrcode");

            try {
                training = TrainingController.getTraining(qrcode);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (training != null) {
                setTraining();
            }
        } else {
            manager.get(Path.urlGetUsuario.concat(preferences.getString("id", "none")));
        }
    }

    private NetworkObserver getExerciciosObserver() {
        if (networkObserver == null) {
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {

                }

                @Override
                public void doOnGet(String response) {
                    try {
                        training = TrainingController.getTrainings(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (training != null) {
                        setTraining();
                    }
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
        img_gif = findViewById(R.id.img_gif);

        tv_training = findViewById(R.id.tv_training);
        tv_sequence = findViewById(R.id.tv_sequence);
        tv_place = findViewById(R.id.tv_place);
        tv_exercise = findViewById(R.id.tv_exercise);
        tv_series = findViewById(R.id.tv_series);
        tv_repetitions = findViewById(R.id.tv_repetition);
        tv_charge = findViewById(R.id.tv_charge);

        coordinatorLayout = findViewById(R.id.activity_main);
    }

    public void setTraining() {
        showGif(training.getImage());
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

    }

    public void showGif(String gif) {
        Glide.with(context).load(context.getResources().getIdentifier(gif, "drawable", context.getPackageName())).into(img_gif);
    }
}
