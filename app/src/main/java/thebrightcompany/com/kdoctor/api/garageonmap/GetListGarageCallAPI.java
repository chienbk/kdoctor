package thebrightcompany.com.kdoctor.api.garageonmap;


import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMapResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class GetListGarageCallAPI {

    public void processGetListGarage(String token, String lat, String lng, String distance, OnResponseListener<GarageOnMapResponse> listener){
        GetListGarageRequest request = new GetListGarageRequest(listener);
        request.setToken(token);
        request.setLat(lat);
        request.setLng(lng);
        request.setDistance(distance);
        App.addRequest(request, "Get List Garage");
    }
}
