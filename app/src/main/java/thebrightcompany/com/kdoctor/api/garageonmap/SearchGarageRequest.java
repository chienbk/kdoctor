package thebrightcompany.com.kdoctor.api.garageonmap;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class SearchGarageRequest extends BasePostRequest<GarageOnMapResponse> {

    public SearchGarageRequest(OnResponseListener<GarageOnMapResponse> listener) {
        super(Utils.URL_SEARCH_GARAGE_ON_MAP, new TypeToken<GarageOnMapResponse>() {
        }.getType(), listener);
    }

    public void setToken(String token){
        setParam("token", token);
    }

    public void setKey(String key){
        setParam("text", key);
    }

    public void setLimit(String limit){
        setParam("limit", limit);
    }

    public void setStart(String start){
        setParam("start", start);
    }
}
