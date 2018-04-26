package com.portalbeanz.ink4udev.view;

import android.content.Context;

/**
 * Created by ChienNV on 11/23/16.
 */

public interface BaseView {

    Context getContext();

    void showProgress();

    void hideProgress();

    void onCommonError(String msg);

    void onApiMessage(String msg);

    void showMessage(String message);
}
