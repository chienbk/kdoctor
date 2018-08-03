package thebrightcompany.com.kdoctor.api.logout;


import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.login.LoginResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LogoutCallAPI {

    public void processLogout(String token, String fcm_token, OnResponseListener<LoginResponse> listener){
        LogoutRequest request = new LogoutRequest(listener);
        request.setDeviceToken(token);
        request.setFcmToken(fcm_token);
        App.addRequest(request, "Logout");
    }
}
