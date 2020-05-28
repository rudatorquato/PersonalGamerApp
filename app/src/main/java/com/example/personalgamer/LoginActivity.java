package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import controller.UsersController;
import interfaces.NetworkObserver;
import modelo.Users;
import network.NetworkManager;
import util.Mask;
import util.Path;
import util.VerifyConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context = this;
    private Button sign_in_button, sign_in_switch, sign_up_switch, aluno_switch, personal_switch;
    private View v_user, v_username,  v_phone, v_email;
    private TextInputEditText edt_user, edt_username, edt_phone, edt_email;
    private CardView crd_switch_type;
    private CoordinatorLayout coordinatorLayout;

    private boolean change;
    private String usertype = "aluno";
    private List<Users> users;

    private NetworkManager manager;
    private NetworkObserver networkObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadViews();

        manager = new NetworkManager();
        manager.setNetworkObserver(getRegisterObserver());
    }

    private void loadViews(){
        v_user = findViewById(R.id.v_user);
        v_username = findViewById(R.id.v_username);
        v_email = findViewById(R.id.v_email);
        v_phone = findViewById(R.id.v_phone);

        edt_user = findViewById(R.id.edt_user);
        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_phone.addTextChangedListener(Mask.insert(edt_phone, "CPFMask"));

        crd_switch_type = findViewById(R.id.crd_switch_type);

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

    private NetworkObserver getRegisterObserver() {
        if (networkObserver == null){
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {
                    if (change) {
                        change = false;
                        edt_username.setError(null);

                        sign_up_switch.setClickable(true);
                        sign_up_switch.setTextColor(Color.parseColor("#DCDCDC"));
                        sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                        sign_in_switch.setClickable(false);
                        sign_in_switch.setTextColor(Color.parseColor("#808080"));
                        sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));

                        v_user.setVisibility(View.GONE);
                        v_phone.setVisibility(View.GONE);
                        v_email.setVisibility(View.GONE);
                        crd_switch_type.setVisibility(View.GONE);

                        sign_in_button.setText("Entrar");
                    } else {
                        startActivity(new Intent(context, DashboardActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }

                @Override
                public void doOnGet(String response) {
                    new ArrayList<>();

                    try {
                        users = UsersController.getUsers(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (users != null) {
                        attemptLogin();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_switch:
                change = false;
                edt_username.setError(null);

                sign_up_switch.setClickable(true);
                sign_up_switch.setTextColor(Color.parseColor("#DCDCDC"));
                sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                sign_in_switch.setClickable(false);
                sign_in_switch.setTextColor(Color.parseColor("#808080"));
                sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));

                v_user.setVisibility(View.GONE);
                v_phone.setVisibility(View.GONE);
                v_email.setVisibility(View.GONE);
                crd_switch_type.setVisibility(View.GONE);

                sign_in_button.setText("Entrar");
                break;
            case R.id.sign_up_switch:
                change = true;
                edt_username.setError(null);

                sign_in_switch.setClickable(true);
                sign_in_switch.setTextColor(Color.parseColor("#DCDCDC"));
                sign_in_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                sign_up_switch.setClickable(false);
                sign_up_switch.setTextColor(Color.parseColor("#808080"));
                sign_up_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));

                v_user.setVisibility(View.VISIBLE);
                v_phone.setVisibility(View.VISIBLE);
                v_email.setVisibility(View.VISIBLE);
                crd_switch_type.setVisibility(View.VISIBLE);

                sign_in_button.setText("Cadastrar");
                break;
            case R.id.aluno_switch:
                usertype = "aluno";

                personal_switch.setClickable(true);
                personal_switch.setTextColor(Color.parseColor("#DCDCDC"));
                personal_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                aluno_switch.setClickable(false);
                aluno_switch.setTextColor(Color.parseColor("#808080"));
                aluno_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));
                break;
            case R.id.personal_switch:
                usertype = "personal";

                aluno_switch.setClickable(true);
                aluno_switch.setTextColor(Color.parseColor("#DCDCDC"));
                aluno_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                personal_switch.setClickable(false);
                personal_switch.setTextColor(Color.parseColor("#808080"));
                personal_switch.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_white));
                break;
            case R.id.sign_in_button:
                if (!change) {
                    manager.get(Path.urlUsuarios);
                } else {
                    attemptRegistration();
                }
                break;
            default:
                break;
        }
    }

    private void attemptLogin() {

        edt_username.setError(null);

        String username = edt_username.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            edt_username.setError(getString(R.string.error_field_required));
            focusView = edt_username;
            cancel = true;
        } else {
            for (Users user: users) {
                if (user.getUsername().equals(username)) {
                    break;
                } else {
                    Toast.makeText(context, "O usuário informado não existe!", Toast.LENGTH_LONG).show();
                    cancel = true;
                }
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (VerifyConnection.verifyConnection(this)) {

                JSONObject params = new JSONObject();
                try {
                    params.put("username", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(context, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

//                manager.postJson(params, Path.urlUsuarios);

//                showProgress(true);
            } else {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Sem internet", Snackbar.LENGTH_LONG)
                        .setAction("Conectar", view -> {
                            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            assert wifi != null;
                            wifi.setWifiEnabled(true);
                        });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        }
    }

    private void attemptRegistration() {

        edt_username.setError(null);
        edt_user.setError(null);
        edt_phone.setError(null);
        edt_email.setError(null);

        String username = edt_username.getText().toString();
        String user = edt_user.getText().toString();
        String  phone = Mask.unmask(edt_phone.getText().toString());
        String email = edt_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (change) {
            if (TextUtils.isEmpty(email)) {
                edt_email.setError(getString(R.string.error_field_required));
                focusView = edt_email;
                cancel = true;
            } else if (!isEmailValid(email)) {
                edt_email.setError(getString(R.string.error_invalid_email));
                focusView = edt_email;
                cancel = true;
            }

            if (TextUtils.isEmpty(phone)) {
                edt_phone.setError(getString(R.string.error_field_required));
                focusView = edt_phone;
                cancel = true;
            } else if (!isPhoneValid(phone)) {
                edt_phone.setError(getString(R.string.error_invalid_phone));
                focusView = edt_phone;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(user)) {
            edt_user.setError(getString(R.string.error_field_required));
            focusView = edt_user;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            edt_username.setError(getString(R.string.error_field_required));
            focusView = edt_username;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (VerifyConnection.verifyConnection(this)) {

                JSONObject params = new JSONObject();
                try {
                    params.put("name", user);
                    params.put("username", username);
                    params.put("email", email);
                    params.put("telephone", phone);
                    params.put("typeuser", usertype);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                manager.postJson(params, Path.urlCadastroUsuarios);

//                showProgress(true);
            } else {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Sem internet", Snackbar.LENGTH_LONG)
                        .setAction("Conectar", view -> {
                            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            assert wifi != null;
                            wifi.setWifiEnabled(true);
                        });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        }
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() > 10;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

}
