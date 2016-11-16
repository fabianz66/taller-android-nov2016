package com.fabian.tallernov2016.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by fabian on 9/5/16.
 * REFERENCE:
 * https://github.com/dm77/barcodescanner
 * https://www.numetriclabz.com/android-qr-code-scanner-using-zxingscanner-library-tutorial/
 */
public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //region Constants

    //Request code to use with startActivityForResult
    public static final int REQUEST_CODE_QR = 1000;

    //Key to fetch the content of the qr code
    public static final String QR_CODE_INTENT_KEY = "QRContent";

    //endregion

    //region Attributes

    private ZXingScannerView mScannerView;

    //endregion

    //region Lifecycle


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    //endregion

    @Override
    public void handleResult(Result result) {

        //Set result
        Intent data = new Intent();
        data.putExtra(QR_CODE_INTENT_KEY, result.getText());
        setResult(Activity.RESULT_OK, data);

        //End activity
        finish();
    }
}