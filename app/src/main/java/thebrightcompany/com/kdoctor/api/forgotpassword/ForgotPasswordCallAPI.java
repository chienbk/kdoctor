package com.portalbeanz.ink4udev.networkutils.forgotpassword;

import com.portalbeanz.ink4udev.App;
import com.portalbeanz.ink4udev.model.forgotpassword.ForgotPasswordResponse;
import com.portalbeanz.ink4udev.networkutils.OnResponseListener;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ForgotPasswordCallAPI {

    public void processForgotPassword(String email, OnResponseListener<ForgotPasswordResponse> listener){
        ForgotPasswordRequest request = new ForgotPasswordRequest(listener);
        request.setEmail(email);
        App.addRequest(request, "Forgot Password");
    }
}
