package com.fabian.tallernov2016;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by fabian on 11/6/16.
 */

public class Utils {

    /**
     * Revisa si un EditText contiene un texto no vacio.
     * Si el edit text es vacio le establece un error y le da foco.
     *
     * @param editText
     * @return El texto que contiene. {null} en caso de que sea vacio.
     */
    public static String checkEditTextForEmpty(Context context, EditText editText) {
        String value = editText.getText().toString();
        if (TextUtils.isEmpty(value)) {
            editText.setError(context.getString(R.string.error_field_required));
            editText.requestFocus();
            return null;
        }
        return value;
    }
}
