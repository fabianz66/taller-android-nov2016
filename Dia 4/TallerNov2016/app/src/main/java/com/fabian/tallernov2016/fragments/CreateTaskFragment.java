package com.fabian.tallernov2016.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.activities.QRCodeScannerActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fabian on 11/6/16.
 */

public class CreateTaskFragment extends Fragment {

    //Camera permission code
    static final int CAMERA_PHOTOS_PERMISSION_REQUEST_CODE = 2;

    //Codigo para pedir que se tome una foto
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //region Atributos

    EditText mEditTitle;
    EditText mEditDetail;
    String mCurrentPhotoPath;
    Bitmap mCamImage;
    ImageView mCamPreview;

    //endregion

    //region Ciclo de vida

    @Override
    public void onResume() {
        super.onResume();

        //Cambia el titulo de la ventana
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_create_task_screen);
        }

        //Pedimos permiso para la camara
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PHOTOS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Crea el View que se va a mostrar en este fragment.
        // Esto se hace a partir de un layout (XML).
        View view = inflater.inflate(R.layout.fragment_create_task, null);

        //Guarda los views que se van a utilizar luego
        mEditTitle = (EditText) view.findViewById(R.id.editTitle);
        mEditDetail = (EditText) view.findViewById(R.id.editDetail);
        mCamPreview = (ImageView) view.findViewById(R.id.ivCamPhoto);

        //Asigna eventos a los botones
        view.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraScreen();
            }
        });

        view.findViewById(R.id.qrButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQRScannerScreen();
            }
        });

        //Avisamos que este fragment crea sus propios elementos en el toolbar
        //Esto va a llamar onCreateOptionsMenu
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_create_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == QRCodeScannerActivity.QR_CODE_REQUEST_CODE) {

                //Obtiene el codigo leido
                String qrCodeContent = data.getStringExtra(QRCodeScannerActivity.QR_CODE_INTENT_KEY);

                //Rellena el campo de detalle
                mEditDetail.setText(qrCodeContent);

            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {

                try {

                    //Obtiene la imagen del lugar donde se guardó y la muestra
                    mCamImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                    mCamPreview.setImageBitmap(mCamImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_save:
                attemptSave();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Eventos de botones

    private void openCameraScreen() {

        //Primero se revisan los permisos para acceder a la camara
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "No hay permisos para acceder a la camara", Toast.LENGTH_SHORT).show();
            return;
        }

        //Crea un intent para abrir el app de Camara y tomar una foto
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            //Crea el archivo donde se va a guardar la imagen
            File imageFile = createImageFile();
            if (imageFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.fabian.tallernov2016.fileprovider", imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(getContext(), "Error creando archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openQRScannerScreen() {

        //Primero se revisan los permisos para acceder a la camara
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "No hay permisos para acceder a la camara", Toast.LENGTH_SHORT).show();
            return;
        }

        //Muestra la activity para leer de un QR
        Intent scannerIntent = new Intent(getActivity(), QRCodeScannerActivity.class);
        startActivityForResult(scannerIntent, QRCodeScannerActivity.QR_CODE_REQUEST_CODE);
    }

    private void attemptSave() {
        Toast.makeText(getContext(), "Guardar", Toast.LENGTH_SHORT).show();


    }

    //endregion

    //region Utils

    private File createImageFile() {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                    storageDir      // directory
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion
}
