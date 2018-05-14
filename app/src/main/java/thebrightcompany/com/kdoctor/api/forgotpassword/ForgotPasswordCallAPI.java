package thebrightcompany.com.kdoctor.api.forgotpassword;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.forgotpassword.ForgotPasswordResponse;

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
