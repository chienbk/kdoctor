package thebrightcompany.com.kdoctor.api.register;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.register.RegisterResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class RegisterRequest extends BasePostRequest<RegisterResponse> {

    public RegisterRequest(OnResponseListener<RegisterResponse> listener) {
        super(Utils.URL_REGISTER, new TypeToken<RegisterResponse>() {
        }.getType(), listener);
    }

    public void setFullName(String fullName){
        setParam("full_name", fullName);
    }
    public void setEmail(String email) {
        setParam("email", email);
    }
    public void setPhone(String phone){
        setParam("phone", phone);
    }
    public void setPassword(String password){
        setParam("password", password);
    }

}
