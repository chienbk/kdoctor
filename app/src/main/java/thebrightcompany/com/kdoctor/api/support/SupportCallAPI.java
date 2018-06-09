package thebrightcompany.com.kdoctor.api.support;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.support.SupportResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class SupportCallAPI {

    public void processForgotPassword(OnResponseListener<SupportResponse> listener){
        SupportRequest request = new SupportRequest(listener);
        App.addRequest(request, "Support");
    }
}
