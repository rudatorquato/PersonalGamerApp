package com.example.personalgamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private Context context = this;
    private static final int CAMERA_REQUEST_CODE = 100;
    private Button btn_comecar;

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        loadViews();

        ScannerView = new ZXingScannerView(this);
    }

    private void loadViews() {
        btn_comecar = findViewById(R.id.btn_comecar);
        btn_comecar.setOnClickListener(this);
    }

    @Override
    public void handleResult(Result result) {
        startActivity(new Intent(context, InfoTreinoActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("qrcode", result.toString()));
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

//        ScannerView.setResultHandler(this);
//        ScannerView.startCamera();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_comecar) {
            checkCameraPermission();
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

                setContentView(ScannerView);
                ScannerView.setResultHandler(this);
                ScannerView.startCamera();

            } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
