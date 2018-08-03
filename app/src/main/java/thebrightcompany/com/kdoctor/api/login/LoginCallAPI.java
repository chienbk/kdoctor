package thebrightcompany.com.kdoctor.api.login;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.login.LoginResponse;
import thebrightcompany.com.kdoctor.utils.Contains;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LoginCallAPI {

    public void processForgotPassword(String email, String password, String type, String token, String thirdToken, String fullName, OnResponseListener<LoginResponse> listener){
        LoginRequest request = new LoginRequest(listener);
        request.setEmail(email);
        request.setPassword(password);
        request.setType(type);
        request.setDeviceToken(token);
        request.setThirdToken(thirdToken);
        request.setFullName(fullName);
        request.setPlatform(Contains.PLATFORM_ANDROID);
        App.addRequest(request, "Login");
    }
}
