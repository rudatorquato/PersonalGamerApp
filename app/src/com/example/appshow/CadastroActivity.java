package com.example.appshow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import interfaces.NetworkObserver;
import network.NetworkManager;
import util.Mask;
import util.MaskType;
import util.Path;
import util.VerifyConnection;

public class CadastroActivity extends AppCompatActivity {
    private Context context = this;
    private NetworkManager manager;
    private NetworkObserver networkObserver;

    //private EditText edt_nome, edt_cpf, edt_credencial, edt_senha, edt_conf_senha;
    private EditText edt_TypeUser, edt_Email;
    //private RadioButton rd_medico, rd_enfermagem, rd_farmacia;

    private View progressView, registerFormView;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        loadViews();

        edt_senha.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptRegistration();
                return true;
            }
            return false;
        });

        Button signUpButton = findViewById(R.id.btn_cadastar);
        signUpButton.setOnClickListener(view -> attemptRegistration());

    }

    public Context getContext() {
        return context;
    }

    private void loadViews() {

        edt_TypeUser = findViewById(R.id.edt_TypeUser);
        edt_Email = findViewById(R.id.edt_Email);

        //edt_nome = findViewById(R.id.edt_nome);
        //edt_cpf = findViewById(R.id.edt_cpf);
        //edt_cpf.addTextChangedListener(Mask.insert(edt_cpf, MaskType.CPF));
        //edt_credencial = findViewById(R.id.edt_credencial);
        //edt_senha = findViewById(R.id.edt_senha);
        //edt_conf_senha = findViewById(R.id.edt_conf_senha);

        //rd_medico = findViewById(R.id.rd_medico);
        //rd_enfermagem = findViewById(R.id.rd_enfermagem);
        //rd_farmacia = findViewById(R.id.rd_farmacia);

        registerFormView = findViewById(R.id.register_form);
        progressView = findViewById(R.id.register_progress);

        coordinatorLayout = findViewById(R.id.activity_cadastro);
    }

    private void attemptRegistration() {

        // Reset errors.
        edt_TypeUser.setError(null);
        edt_Email.setError(null);

        //edt_nome.setError(null);
        //edt_cpf.setError(null);
        //edt_credencial.setError(null);
        //edt_senha.setError(null);
        //edt_conf_senha.setError(null);

        // Store values at the time of the login attempt.
        String TypeUser = edt_TypeUser.getText().toString();
        String Email = edt_Email.getText().toString();

        //String nome = edt_nome.getText().toString();
        //String cpf = Mask.unmask(edt_cpf.getText().toString());
        //String credencial = edt_credencial.getText().toString();
        //String senha = edt_senha.getText().toString();
        //String senha_conf = edt_conf_senha.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password confirmation, if the user entered one.
        if (TextUtils.isEmpty(senha_conf) && TextUtils.isEmpty(senha)) {
            edt_conf_senha.setError(getString(R.string.error_field_required));
            focusView = edt_conf_senha;
            cancel = true;
        } else if (!senha.equals(senha_conf)){
            edt_conf_senha.setError(getString(R.string.error_not_equal_password));
            focusView = edt_conf_senha;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(senha) || !isPasswordValid(senha)) {
            edt_senha.setError(getString(R.string.error_invalid_password));
            focusView = edt_senha;
            cancel = true;
        }

        // Check for a valid credential.
        if (TextUtils.isEmpty(credencial)) {
            edt_credencial.setError(getString(R.string.error_field_required));
            focusView = edt_credencial;
            cancel = true;
        }

        if (TextUtils.isEmpty(cpf)) {
            edt_cpf.setError(getString(R.string.error_field_required));
            focusView = edt_cpf;
            cancel = true;
        } else if (!isCPFValid(cpf)) {
            edt_cpf.setError(getString(R.string.error_invalid_cpf));
            focusView = edt_cpf;
            cancel = true;
        }

        if (TextUtils.isEmpty(nome)) {
            edt_nome.setError(getString(R.string.error_field_required));
            focusView = edt_nome;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            if (VerifyConnection.verifyConnection(this)) {

                Map<String, String> params = new HashMap<>();
                params.put("usuario", cpf);
                params.put("nome", nome);
                params.put("credencial", credencial);
                params.put("password", senha);

                if (rd_medico.isChecked()) params.put("roles", "is_medico");
                if (rd_enfermagem.isChecked()) params.put("roles", "is_enfermagem");
                if (rd_farmacia.isChecked()) params.put("roles", "is_farmacia");

                manager = new NetworkManager();
                manager.setNetworkObserver(getCadastroObserver());
                manager.post(params, Path.urlCadastro);

                showProgress(true);
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

    private boolean isCPFValid(String cpf) {
        //TODO: Replace this with your own logic
        return cpf.length() > 10;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    private NetworkObserver getCadastroObserver() {
        if (networkObserver == null) {
            networkObserver = new NetworkObserver() {
                @Override
                public void doOnPost(String response) {
                    startActivity(new Intent(context, UsuariosActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    showProgress(false);
                    finish();
                }

                @Override
                public void doOnGet(String response) {
                    System.out.println("GET: " + response);
                }

                @Override
                public void doOnError(String response) {

                    System.out.println("Cadastro ERRO: " + response);
                    showProgress(false);
                }
            };
        }
        return networkObserver;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        registerFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.search);
        menu.removeItem(R.id.ic_data);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            startActivity(new Intent(context, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
