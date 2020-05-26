package com.example.personalgamer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//Responsavel pelo scan
public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        //setContentView(R.layout.activity_qr_code);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {

        TreinandoActivity.resultado.setText(result.getText());
        onBackPressed();

    }

    @Override
    public void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
