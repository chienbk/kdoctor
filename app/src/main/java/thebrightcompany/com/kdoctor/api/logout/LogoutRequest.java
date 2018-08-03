package thebrightcompany.com.kdoctor.api.logout;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.login.LoginResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LogoutRequest extends BasePostRequest<LoginResponse> {

    public LogoutRequest(OnResponseListener<LoginResponse> listener) {
        super(Utils.URL_LOGOUT, new TypeToken<LoginResponse>() {
        }.getType(), listener);
    }

    public void setFcmToken(String fcm_token){
        setParam("device_token", fcm_token);
    }
    public void setDeviceToken(String token){
        setParam("token", token);
    }
}
