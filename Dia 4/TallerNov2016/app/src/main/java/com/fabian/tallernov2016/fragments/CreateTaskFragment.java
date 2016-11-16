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
import android.support.v4.app.FragmentTransaction;
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
import com.fabian.tallernov2016.activities.QRScannerActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fabian on 11/6/16.
 */

public class CreateTaskFragment extends Fragment implements TitleSelectDialogFragment.onTitleSelectedListener {

    //Request codes
    static final int REQUEST_CODE_CAM_PERMISSION = 1;
    static final int REQUEST_CODE_IMG_CAPTURE = 2;

    //region Atributos

    File mCurrentPhotoFile;
    Bitmap mCamImage;
    ImageView mCamPreview;

    EditText mEditTitle;
    EditText mEditDetail;

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
        mEditTitle = (EditText) view.findViewById(R.id.etTaskTitle);
        mEditDetail = (EditText) view.findViewById(R.id.etTaskDetail);

        //Asigna eventos a los botones
        view.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraScreen();
            }
        });

        view.findViewById(R.id.qrCodeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQRScannerScreen();
            }
        });

        view.findViewById(R.id.titlesDialogButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleSelectDialog();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMG_CAPTURE) {
                mCamImage = BitmapFactory.decodeFile(mCurrentPhotoFile.getAbsolutePath(), null);
                mCamPreview.setImageBitmap(mCamImage);
            }else if(requestCode == QRScannerActivity.REQUEST_CODE_QR) {

                //Rellena el edittext
                String code = data.getStringExtra(QRScannerActivity.QR_CODE_INTENT_KEY);
                mEditDetail.setText(code);
            }
        }
    }

    @Override
    public void onTitleSelected(String title) {
        mEditTitle.setText(title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_create_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save) {
            Toast.makeText(getContext(), "Guardar", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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

    private void openQRScannerScreen() {

        //Primero se revisan los permisos para acceder a la camara
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "No hay permisos para acceder a la camara", Toast.LENGTH_SHORT).show();
            return;
        }

        //Inicia el activity esperando un resultado. Esto va a generar una llamada a onActivityResult
        Intent intent = new Intent(getActivity(), QRScannerActivity.class);
        startActivityForResult(intent, QRScannerActivity.REQUEST_CODE_QR);
    }

    private void openTitleSelectDialog() {

        //Se crea el FragmentDialog
        TitleSelectDialogFragment dialogFragment = new TitleSelectDialogFragment();

        //Se guarda el target fragment a quien notificar cuando se selecciona un titulo
        dialogFragment.setTargetFragment(this, 1);

        Bundle args = new Bundle();
        args.putInt("position", R.id.rbCarne);
        dialogFragment.setArguments(args);

        //Se muestra el dialogo
        dialogFragment.show(getFragmentManager(), null);
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
