package thebrightcompany.com.kdoctor.api.support;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.support.SupportResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class SupportRequest extends BasePostRequest<SupportResponse> {

    public SupportRequest(OnResponseListener<SupportResponse> listener) {
        super(Utils.URL_SUPPORT, new TypeToken<SupportResponse>() {
        }.getType(), listener);
    }

}
