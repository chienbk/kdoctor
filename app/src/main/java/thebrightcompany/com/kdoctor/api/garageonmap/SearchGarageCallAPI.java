package thebrightcompany.com.kdoctor.api.garageonmap;


import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class SearchGarageCallAPI {

    public void processGetListGarage(String token, String key, String limit, String start, OnResponseListener<GarageOnMapResponse> listener){
        SearchGarageRequest request = new SearchGarageRequest(listener);
        request.setToken(token);
        request.setKey(key);
        request.setLimit(limit);
        request.setStart(start);
        App.addRequest(request, "Search Garage");
    }
}
