package com.fabian.tallernov2016;


import android.app.Application;

import com.fabian.tallernov2016.models.Task;
import com.fabian.tallernov2016.models.User;

import java.util.List;

/**
 * Created by fabian on 11/6/16.
 */

public class AppContext extends Application {

    //region Atributos

    private User mUser;

    //endregion

    public void setUser(User pUser)
    {
        mUser = pUser;
    }

    public User getUser() {
        return mUser;
    }
}
