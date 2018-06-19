package thebrightcompany.com.kdoctor.api.troublecode;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.troublecode.TroubleCodeResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class TroubleCodeRequest extends BasePostRequest<TroubleCodeResponse> {


    public TroubleCodeRequest(OnResponseListener<TroubleCodeResponse> listener) {

        super(Utils.URL_TROUBLE_CODE, new TypeToken<TroubleCodeResponse>() {
        }.getType(), listener);
    }

    public void setToken(String token) {
        setParam("token", token);
    }
    public void setCodes(String codes){
        setParam("codes", codes);
    }
}
