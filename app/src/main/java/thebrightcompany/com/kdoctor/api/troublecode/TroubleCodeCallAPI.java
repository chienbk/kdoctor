package thebrightcompany.com.kdoctor.api.troublecode;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.troublecode.TroubleCodeResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class TroubleCodeCallAPI {

    public void processGetTroubleCode(String token, String pCode, OnResponseListener<TroubleCodeResponse> listener){
        TroubleCodeRequest request = new TroubleCodeRequest(listener);
        request.setToken(token);
        request.setCodes(pCode);
        App.addRequest(request, "Trouble code");
    }
}
