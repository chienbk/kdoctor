package thebrightcompany.com.kdoctor.api.garageonmap;

import thebrightcompany.com.kdoctor.App;
import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.model.register.RegisterResponse;

/**
 * Created by ChienNV on 10/25/16.
 */

public class CreateOrderCallAPI {

    public void processCreateOrder(String garage_id, String name, String phone, String email,
                                   String typeOfCar, String licenseOfCar, String note, String troublecode,
                                   String token, String lat, String lng,
                                   OnResponseListener<RegisterResponse> listener){
        CreateOrderRequest request = new CreateOrderRequest(listener);
        request.setGaraId(garage_id);
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setTypeOfCar(typeOfCar);
        request.setLisenceOfCar(licenseOfCar);
        request.setNote(note);
        request.setTroubleCode(troublecode);
        request.setToken(token);
        request.setLat(lat);
        request.setLng(lng);

        App.addRequest(request, "Create Order");
    }

    public void processCreateOrderWithLocation(String garage_id, String name, String phone, String email,
                                   String typeOfCar, String licenseOfCar, String note, String troublecode,
                                   String token, String location,
                                   OnResponseListener<RegisterResponse> listener){
        CreateOrderRequest request = new CreateOrderRequest(listener);
        request.setGaraId(garage_id);
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setTypeOfCar(typeOfCar);
        request.setLisenceOfCar(licenseOfCar);
        request.setNote(note);
        request.setTroubleCode(troublecode);
        request.setToken(token);
        request.setAddress(location);

        App.addRequest(request, "Create Order");
    }
}
