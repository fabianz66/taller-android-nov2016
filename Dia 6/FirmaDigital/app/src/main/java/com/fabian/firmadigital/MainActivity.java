package com.fabian.firmadigital;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText mEditCertName; //Nombre del certificado
    EditText mEditCertPass; //Clave del certificado
    EditText mEditCertAlias; //Alias contenido en el certificado
    EditText mEditCertAliasPass; //Clave del alias
    EditText mEditDocToSignFilename; //Nombre del archivo que se va a firmar
    EditText mEditSignatureFilename; //Nombre del archivo que va a contener la firma resultante
    EditText mEditPublicCertFilename; //Nombre del archivo que va a contener la clave publica que se comparte para validar el archivo
    TextView mTvResult; //Donde se muestra el resultado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se pide permiso para escribir en la memoria externa del dispositivo
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        }

        //Obtiene los views
        mEditCertName = (EditText) findViewById(R.id.etCertName);
        mEditCertPass = (EditText) findViewById(R.id.etCertPass);
        mEditCertAlias = (EditText) findViewById(R.id.etAlias);
        mEditCertAliasPass = (EditText) findViewById(R.id.etAliasPass);
        mEditDocToSignFilename = (EditText) findViewById(R.id.etDocFilename);
        mEditSignatureFilename = (EditText) findViewById(R.id.etSignatureFilename);
        mEditPublicCertFilename = (EditText) findViewById(R.id.etPublicSigDoc);
        mTvResult = (TextView) findViewById(R.id.tvResultado);

        //Agrega eventos de botones
        findViewById(R.id.btnSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignDocument();
            }
        });

        findViewById(R.id.btnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptVerifySignature();
            }
        });
    }

    /**
     * Obtiene los valores ingresados por el usuario e intenta firmar el documento
     */
    public void attemptSignDocument() {

        //Obtiene los valores que ingreso el usuario
        String certFilename = mEditCertName.getText().toString();
        char[] cert_pass = mEditCertPass.getText().toString().toCharArray();
        String alias = mEditCertAlias.getText().toString();
        char[] alias_pass = mEditCertAliasPass.getText().toString().toCharArray();
        String docToSignFilename = mEditDocToSignFilename.getText().toString();
        String signatureFilename = mEditSignatureFilename.getText().toString();
        String publicCertFilename = mEditPublicCertFilename.getText().toString();

        //Verifica que los archivos existan en la carpeta de downloads
        File downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //Verifica que el certificado exista
        File certFile = new File(downloadsPath, certFilename);
        if(!certFile.exists()) {
            mTvResult.setText("Certificado no encontrado");
            return;
        }

        //Verifica que exista el documento que se va a firmar
        File docToSignFile = new File(downloadsPath, docToSignFilename);
        if(!docToSignFile.exists()) {
            mTvResult.setText("Documento no encontrado");
            return;
        }

        //Estos se van a generar asi que no se revisa que existan
        File signatureFile = new File(downloadsPath, signatureFilename);
        File publicCertFile = new File(downloadsPath, publicCertFilename);

        //Intenta firmar el documento
        String error = FirmaDigital.sign(certFile.getAbsolutePath(),
                cert_pass,
                alias,
                alias_pass,
                docToSignFile.getAbsolutePath(),
                signatureFile.getAbsolutePath(),
                publicCertFile.getAbsolutePath());

        //Muestra el resultado
        if(error != null) {
            mTvResult.setText(error);
        }else {
            mTvResult.setText("Documento firmado");
        }
    }

    /**
     * Obtiene los valores ingresados por el usuario e intenta verificar el documento
     */
    public void attemptVerifySignature() {

//        //Obtiene los valores
//        String keystore = mEditCertName.getText().toString(); //Nombre del certificado
//        char[] keystore_pass = mEditCertPass.getText().toString().toCharArray();
//        String alias = mEditCertAlias.getText().toString();
//        char[] alias_pass = mEditCertAliasPass.getText().toString().toCharArray();
//        String docToSign = mEditDocToSignFilename.getText().toString();
//        String signatureFilename = mEditSignatureFilename.getText().toString();
//        String publicCertFilename = mEditPublicCertFilename.getText().toString();
//
//        //Intenta firmar el documento
//        String error = FirmaDigital.sign(keystore, keystore_pass, alias, alias_pass, docToSign, signatureFilename, publicCertFilename);
//        if(error != null) {
//            mTvResult.setText(error);
//        }else {
//            mTvResult.setText("Documento firmado");
//        }
    }
}
