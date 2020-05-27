package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Mask;
import util.Path;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button sign_in_button, sign_in_switch, sign_up_switch, aluno_switch, personal_switch;
    private View v_user, v_email, v_phone;
    private EditText edt_phone;
    private CardView crd_switch_type;
    private boolean change;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getLoginObserver());
        manager.get(Path.urlCadastroUsuarios);
    }

    private NetworkObserver getLoginObserver() {
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
                public void doOnError(String response) {
                    Log.d("ERRO", response);
                }
            };
        }
        return networkObserver;
    }

    private void loadViews(){
        v_user = findViewById(R.id.v_user);
        v_email = findViewById(R.id.v_email);
        v_phone = findViewById(R.id.v_phone);

        crd_switch_type = findViewById(R.id.crd_switch_type);

        edt_phone = findViewById(R.id.edt_phone);
        edt_phone.addTextChangedListener(Mask.insert(edt_phone, "CPFMask"));

        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(this);
        sign_in_switch = findViewById(R.id.sign_in_switch);
        sign_in_switch.setOnClickListener(this);
        sign_up_switch = findViewById(R.id.sign_up_switch);
        sign_up_switch.setOnClickListener(this);

        aluno_switch = findViewById(R.id.aluno_switch);
        aluno_switch.setOnClickListener(this);
        personal_switch = findViewById(R.id.personal_switch);
        personal_switch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_switch:
                sign_up_switch.setClickable(true);
                sign_up_switch.setTextColor(Color.parseColor("#DCDCDC"));
                sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                sign_in_switch.setClickable(false);
                sign_in_switch.setTextColor(Color.parseColor("#808080"));
                sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));

                v_email.setVisibility(View.GONE);
                v_phone.setVisibility(View.GONE);
                crd_switch_type.setVisibility(View.GONE);

                sign_in_button.setText("Entrar");
                break;
            case R.id.sign_up_switch:
                sign_in_switch.setClickable(true);
                sign_in_switch.setTextColor(Color.parseColor("#DCDCDC"));
                sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                sign_up_switch.setClickable(false);
                sign_up_switch.setTextColor(Color.parseColor("#808080"));
                sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));

                v_email.setVisibility(View.VISIBLE);
                v_phone.setVisibility(View.VISIBLE);
                crd_switch_type.setVisibility(View.VISIBLE);

                sign_in_button.setText("Cadastrar");
                break;
            case R.id.aluno_switch:
                personal_switch.setClickable(true);
                personal_switch.setTextColor(Color.parseColor("#DCDCDC"));
                personal_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                aluno_switch.setClickable(false);
                aluno_switch.setTextColor(Color.parseColor("#808080"));
                aluno_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));
                break;
            case R.id.personal_switch:
                aluno_switch.setClickable(true);
                aluno_switch.setTextColor(Color.parseColor("#DCDCDC"));
                aluno_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                personal_switch.setClickable(false);
                personal_switch.setTextColor(Color.parseColor("#808080"));
                personal_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));
                break;
            case R.id.sign_in_button:
                startActivity(new Intent(context, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            default:
                break;
        }
    }
}
