package com.fabian.tallernov2016.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fabian.tallernov2016.R;

/**
 * Created by fabian on 11/15/16.
 */

public class TitleSelectDialogFragment extends DialogFragment {

    //Interfaz que debe implementar la activity que llame este fragment
    interface onTitleSelectedListener {
        void onTitleSelected(String title);
    }

    //RadioGroup que muestra las opciones
    RadioGroup mRadioGroup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Se verifica que la activity que llamo el Dialogo implemente la interfaz necesaria
        if (!(getTargetFragment() instanceof onTitleSelectedListener)) {
            throw new RuntimeException("Activity must implement onTitleSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Crea el view
        View root = inflater.inflate(R.layout.fragment_dialog_title_select, container, false);

        //Guarda el RadioGroup
        mRadioGroup = (RadioGroup) root.findViewById(R.id.titlesGroup);

        root.findViewById(R.id.saveTitleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se le notifica a la activity
                RadioButton radioButton = (RadioButton) mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());
                String selectedText = radioButton.getText().toString();
                ((onTitleSelectedListener) getTargetFragment()).onTitleSelected(selectedText);

                //Se cierra el dialogo
                dismiss();
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog);
    }
}
