package com.portalbeanz.ink4udev.networkutils.forgotpassword;

import com.google.gson.reflect.TypeToken;
import com.portalbeanz.ink4udev.AppContrains;
import com.portalbeanz.ink4udev.model.forgotpassword.ForgotPasswordResponse;
import com.portalbeanz.ink4udev.networkutils.OnResponseListener;
import com.portalbeanz.ink4udev.networkutils.base.BasePostRequest;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ForgotPasswordRequest extends BasePostRequest<ForgotPasswordResponse> {

    public ForgotPasswordRequest(OnResponseListener<ForgotPasswordResponse> listener) {
        super(AppContrains.URL_FORGOT_PASSWORD, new TypeToken<ForgotPasswordResponse>() {
        }.getType(), listener);
    }

    public void setEmail(String email) {
        setParam("email", email);
    }

}
