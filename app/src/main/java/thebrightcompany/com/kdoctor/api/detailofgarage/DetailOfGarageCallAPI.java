package thebrightcompany.com.kdoctor.api.detailofgarage;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetailResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class DetailOfGarageCallAPI {

    public void processForgotPassword(String token, String idOfGarage, OnResponseListener<GarageDetailResponse> listener){
        DetailOfGarageRequest request = new DetailOfGarageRequest(listener, idOfGarage);
        request.setToken(token);
        App.addRequest(request, "Detail Garage");
    }
}
