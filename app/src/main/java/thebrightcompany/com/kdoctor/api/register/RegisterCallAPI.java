package thebrightcompany.com.kdoctor.api.register;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.register.RegisterResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class RegisterCallAPI {

    public void processForgotPassword(String full_name, String email, String phone, String password, OnResponseListener<RegisterResponse> listener){
        RegisterRequest request = new RegisterRequest(listener);
        request.setFullName(full_name);
        request.setEmail(email);
        request.setPhone(phone);
        request.setPassword(password);

        App.addRequest(request, "Register");
    }
}
