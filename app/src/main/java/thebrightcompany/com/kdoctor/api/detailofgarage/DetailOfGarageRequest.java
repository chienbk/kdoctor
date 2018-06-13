package thebrightcompany.com.kdoctor.api.detailofgarage;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetailResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class DetailOfGarageRequest extends BasePostRequest<GarageDetailResponse> {


    public DetailOfGarageRequest(OnResponseListener<GarageDetailResponse> listener, String idOfGarage) {

        super(String.format(Utils.URL_GARAGE_DETAIL, idOfGarage), new TypeToken<GarageDetailResponse>() {
        }.getType(), listener);
    }

    public void setToken(String token) {
        setParam("token", token);
    }
}
