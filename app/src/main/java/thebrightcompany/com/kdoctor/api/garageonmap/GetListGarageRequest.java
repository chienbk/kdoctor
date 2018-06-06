package thebrightcompany.com.kdoctor.api.garageonmap;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class GetListGarageRequest extends BasePostRequest<GarageOnMapResponse> {

    public GetListGarageRequest(OnResponseListener<GarageOnMapResponse> listener) {
        super(Utils.URL_GET_LIST_GARAGE, new TypeToken<GarageOnMapResponse>() {
        }.getType(), listener);
    }

    public void setToken(String token){
        setParam("token", token);
    }

    public void setLat(String lat){
        setParam("lat", lat);
    }

    public void setLng(String lng){
        setParam("lng", lng);
    }

    public void setDistance(String distance){
        setParam("distance", distance);
    }
}
