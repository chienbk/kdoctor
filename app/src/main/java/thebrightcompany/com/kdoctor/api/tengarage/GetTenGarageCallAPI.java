package thebrightcompany.com.kdoctor.api.tengarage;


import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class GetTenGarageCallAPI {

    public void processGetListGarage(String token, String lat, String lng, String limit, String sortBy, String distance, OnResponseListener<GarageOnMapResponse> listener){
        GetTenGarageRequest request = new GetTenGarageRequest(listener);
        request.setToken(token);
        request.setLat(lat);
        request.setLng(lng);
        request.setLimit(limit);
        request.setSortType(sortBy);
        request.setDistance(distance);
        App.addRequest(request, "Get Ten Garage");
    }
}
