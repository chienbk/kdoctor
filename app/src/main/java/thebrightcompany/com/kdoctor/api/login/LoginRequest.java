package thebrightcompany.com.kdoctor.api.login;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.login.LoginResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LoginRequest extends BasePostRequest<LoginResponse> {

    public LoginRequest(OnResponseListener<LoginResponse> listener) {
        super(Utils.URL_LOGIN, new TypeToken<LoginResponse>() {
        }.getType(), listener);
    }

    public void setEmail(String email) {
        setParam("email", email);
    }
    public void setPassword(String password){
        setParam("password", password);
    }
    public void setType(String type){
        setParam("type", type);
    }
    public void setDeviceToken(String token){
        setParam("device_token", token);
    }
    public void setThirdToken(String token){
        setParam("third_token", token);
    }
    public void setFullName(String fullName){
        setParam("full_name", fullName);
    }
    public void setPlatform(String platform){
        setParam("platform", platform);
    }

}
