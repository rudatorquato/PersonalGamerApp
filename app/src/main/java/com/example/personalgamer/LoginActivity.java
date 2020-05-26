package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import util.Mask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button sign_in_button, sign_up_button, sign_in_switch, sign_up_switch;
    private View v_user, v_email, v_phone, v_user_type;
    private EditText edt_phone;
    private boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadViews();

    }

    public void loadViews(){
        v_user = findViewById(R.id.v_user);
        v_email = findViewById(R.id.v_email);
        v_phone = findViewById(R.id.v_phone);
        v_user_type = findViewById(R.id.v_user_type);

        edt_phone = findViewById(R.id.edt_phone);
        edt_phone.addTextChangedListener(Mask.insert(edt_phone, "CPFMask"));

        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(this);
//        sign_up_button = findViewById(R.id.sign_up_button);
//        sign_up_button.setOnClickListener(this);
        sign_in_switch = findViewById(R.id.sign_in_switch);
        sign_in_switch.setOnClickListener(this);
        sign_up_switch = findViewById(R.id.sign_up_switch);
        sign_up_switch.setOnClickListener(this);
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
                v_user_type.setVisibility(View.GONE);

                sign_in_button.setText("Entrar");
                break;
            case R.id.sign_up_switch:
                sign_in_switch.setClickable(true);
                sign_in_switch.setTextColor(Color.parseColor("#DCDCDC"));
                sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                sign_up_switch.setClickable(false);
                sign_up_switch.setTextColor(Color.parseColor("#808080"));
                sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));
                sign_in_button.setText("Cadastrar");

                v_email.setVisibility(View.VISIBLE);
                v_phone.setVisibility(View.VISIBLE);
                v_user_type.setVisibility(View.VISIBLE);
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
