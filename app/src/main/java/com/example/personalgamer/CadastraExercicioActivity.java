package com.example.personalgamer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.NetworkObserver;
import modelo.Training;
import network.NetworkManager;
import util.Path;

public class CadastraExercicioActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;

    private TextInputEditText edt_training, edt_place, edt_exercise, edt_image, edt_sequence,
            edt_series, edt_repetitions, edt_charge;
    private ImageView dialog_image, img_gif;
    private GridView gridview;

    private String training, place, exercise, image, sequence, series,repetitions, charge;

    private Button btn_cadastrar;

    private Training mtraining;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    private SharedPreferences preferences;

    private Integer[] imageIDs = {
            R.drawable.gif_01, R.drawable.gif_02, R.drawable.gif_03, R.drawable.gif_04,
            R.drawable.gif_05, R.drawable.gif_06, R.drawable.gif_07, R.drawable.gif_08,
            R.drawable.gif_09, R.drawable.gif_10, R.drawable.gif_11, R.drawable.gif_12,
            R.drawable.gif_13, R.drawable.gif_14, R.drawable.gif_15, R.drawable.gif_16,
            R.drawable.gif_17, R.drawable.gif_18, R.drawable.gif_19, R.drawable.gif_20,
            R.drawable.gif_21, R.drawable.gif_22, R.drawable.gif_23, R.drawable.gif_24,
            R.drawable.gif_25, R.drawable.gif_26, R.drawable.gif_27, R.drawable.gif_28,
            R.drawable.gif_29, R.drawable.gif_30, R.drawable.gif_31, R.drawable.gif_32,
            R.drawable.gif_33, R.drawable.gif_34, R.drawable.gif_35, R.drawable.gif_36,
            R.drawable.gif_37, R.drawable.gif_38, R.drawable.gif_39, R.drawable.gif_40,
            R.drawable.gif_41
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_exercicio);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        
        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getExercicioObserver());
    }

    private void loadViews() {
        img_gif = findViewById(R.id.img_gif);
        dialog_image = findViewById(R.id.dialog_image);
        dialog_image.setOnClickListener(this);

        edt_training = findViewById(R.id.edt_training);
        edt_place = findViewById(R.id.edt_place);
        edt_exercise = findViewById(R.id.edt_exercise);
        edt_image = findViewById(R.id.edt_image);
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
        if (v.getId() == R.id.dialog_image) {
            dialogChooseImages();
        }
        if (v.getId() == R.id.btn_cadastrar) {

            if (attemptRegister()) {
                JSONObject mtraining = new JSONObject();
                JSONObject object = new JSONObject();

                try {
                    object.put("training", training);
                    object.put("place", place);
                    object.put("exercise", exercise);
                    object.put("image", image);
                    object.put("sequence", Integer.parseInt(sequence));
                    object.put("series", Integer.parseInt(series));
                    object.put("repetitions", Integer.parseInt(repetitions));
                    object.put("charge", Integer.parseInt(charge));

                    mtraining.put("training", object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("JSON", mtraining.toString());
                manager.putJson(mtraining, Path.urlUpdateTraning.concat(preferences.getString("id", "none")));

            }
        }
    }

    private boolean attemptRegister() {

        training = edt_training.getText().toString();
        place = edt_place.getText().toString();
        exercise = edt_exercise.getText().toString();
        image = edt_image.getText().toString();
        sequence = edt_sequence.getText().toString();
        series = edt_series.getText().toString();
        repetitions = edt_repetitions.getText().toString();
        charge = edt_charge.getText().toString();

        if (training.isEmpty() || place.isEmpty() || exercise.isEmpty() || image.isEmpty() || sequence.isEmpty() || series.isEmpty() ||
                repetitions.isEmpty() || charge.isEmpty()) {

            Snackbar.make(coordinatorLayout, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(230, 230));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            Glide.with(context).load(context.getResources().getIdentifier(getResources().getResourceEntryName(imageIDs[position]), "drawable", context.getPackageName())).into(mImageView);
//            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }

    private void dialogChooseImages() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View information = inflater.inflate(R.layout.dialog_choose_image, null);

        gridview = (GridView) information.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapterGridView(getApplicationContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Toast.makeText(context, getResources().getResourceEntryName(imageIDs[position]), Toast.LENGTH_LONG).show();
                edt_image.setText(getResources().getResourceEntryName(imageIDs[position]));
                showGif(getResources().getResourceEntryName(imageIDs[position]));
            }
        });

        builder.setView(information)
                .setPositiveButton("Ok", (dialog, id1) -> {

                })
                .setNegativeButton("Cancelar", (dialog, id2) -> {
                });
        builder.create().show();
    }

    public void showGif(String gif) {
        Glide.with(context).load(context.getResources().getIdentifier(gif, "drawable", context.getPackageName())).into(img_gif);
    }
}
