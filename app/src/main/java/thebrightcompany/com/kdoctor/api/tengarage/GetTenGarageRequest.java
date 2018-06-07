package thebrightcompany.com.kdoctor.api.tengarage;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class GetTenGarageRequest extends BasePostRequest<GarageOnMapResponse> {

    public GetTenGarageRequest(OnResponseListener<GarageOnMapResponse> listener) {
        super(Utils.URL_GET_TEN_GARAGE, new TypeToken<GarageOnMapResponse>() {
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

    public void setLimit(String limit){
        setParam("limit", limit);
    }

    public void setSortType(String type){
        setParam("sortBy", type);
    }

    public void setDistance(String distance){
        setParam("distance", distance);
    }
}
