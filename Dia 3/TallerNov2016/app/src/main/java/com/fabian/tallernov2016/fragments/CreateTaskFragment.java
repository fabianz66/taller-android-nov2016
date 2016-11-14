package com.fabian.tallernov2016.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fabian.tallernov2016.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fabian on 11/6/16.
 */

public class CreateTaskFragment extends Fragment {

    //Request codes
    static final int REQUEST_CODE_CAM_PERMISSION = 1;
    static final int REQUEST_CODE_IMG_CAPTURE = 2;

    //region Atributos

    File mCurrentPhotoFile;
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

        //Si no se tienen permisos se piden
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAM_PERMISSION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Crea el View que se va a mostrar en este fragment.
        // Esto se hace a partir de un layout (XML).
        View view = inflater.inflate(R.layout.fragment_create_task, null);

        //Guarda los views que se van a utilizar luego
        mCamPreview = (ImageView) view.findViewById(R.id.ivCamPhoto);

        //Asigna eventos a los botones
        view.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraScreen();
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMG_CAPTURE) {
                mCamImage = BitmapFactory.decodeFile(mCurrentPhotoFile.getAbsolutePath(), null);
                mCamPreview.setImageBitmap(mCamImage);
            }
        }
    }

    //endregion

    //region Eventos de botones

    private void openCameraScreen() {

        //Primero se revisan los permisos para acceder a la camara
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "No hay permisos para acceder a la camara", Toast.LENGTH_SHORT).show();
            return;
        }

        //Crea un intent para abrir el app de Camara y tomar una foto
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            //Crea el archivo donde se va a guardar la imagen
            File imageFile = createImageFile();
            if (imageFile != null) {
                mCurrentPhotoFile = imageFile;
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.fabian.tallernov2016.fileprovider", mCurrentPhotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMG_CAPTURE);
            } else {
                Toast.makeText(getContext(), "Error creando archivo", Toast.LENGTH_SHORT).show();
            }
        }
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
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion
}
