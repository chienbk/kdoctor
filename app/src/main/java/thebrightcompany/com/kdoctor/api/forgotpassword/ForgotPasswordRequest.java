package thebrightcompany.com.kdoctor.api.forgotpassword;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.forgotpassword.ForgotPasswordResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class ForgotPasswordRequest extends BasePostRequest<ForgotPasswordResponse> {

    public ForgotPasswordRequest(OnResponseListener<ForgotPasswordResponse> listener) {
        super(Utils.URL_FORGOT_PASSWORD, new TypeToken<ForgotPasswordResponse>() {
        }.getType(), listener);
    }

    public void setEmail(String email) {
        setParam("email", email);
    }

}
