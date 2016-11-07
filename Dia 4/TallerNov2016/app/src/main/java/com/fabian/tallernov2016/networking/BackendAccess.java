package com.fabian.tallernov2016.networking;

import android.content.Context;
import android.net.Uri;

import com.fabian.tallernov2016.AppContext;

/**
 * Created by fabian on 11/6/16.
 */

public class BackendAccess {

    //region Constants

    private static final String HTTP_SCHEME = "http";
    private static final String AUTHORITY = "45.55.130.134:3000";
    final static String URL_EXTENSION = ".json";

    //endregion

    //region Attributes

    AppContext mContext;

    //endregion

    //region Constructor

    BackendAccess(AppContext context) {
        mContext = context;
    }

    //endregion

    //region Static methods

    static Uri.Builder getBuilder() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP_SCHEME).encodedAuthority(AUTHORITY);
        return builder;
    }

    //endregion
}
