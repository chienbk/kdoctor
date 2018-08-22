package thebrightcompany.com.kdoctor.api.garageonmap;


import com.google.gson.reflect.TypeToken;

import thebrightcompany.com.kdoctor.api.OnResponseListener;
import thebrightcompany.com.kdoctor.api.base.BasePostRequest;
import thebrightcompany.com.kdoctor.model.register.RegisterResponse;
import thebrightcompany.com.kdoctor.utils.Utils;

/**
 * Created by ChienNV on 10/25/16.
 */

public class CreateOrderRequest extends BasePostRequest<RegisterResponse> {

    public CreateOrderRequest(OnResponseListener<RegisterResponse> listener) {
        super(Utils.URL_CREATE_ORDER, new TypeToken<RegisterResponse>() {
        }.getType(), listener);
    }

   public void setGaraId(String garage_id){
       setParam("garage_id", garage_id);
   }

   public void setName(String name) {
       setParam("name", name);
   }

   public void setEmail(String email) {
       setParam("email", email);
   }

   public void setPhone(String phone) {
       setParam("phone", phone);
   }

   public void setTypeOfCar(String typeOfCar){
       setParam("typeOfCar", typeOfCar);
   }

   public void setLisenceOfCar(String lisenceOfCar) {
       setParam("lisenceOfCar", lisenceOfCar);
   }

   public void setNote(String note) {
       setParam("note", note);
   }

   public void setTroubleCode(String troublecode){
       setParam("troublecode", troublecode);
   }

   public void setToken(String token) {
       setParam("token", token);
   }

   public void setLat(String lat){
       setParam("lat", lat);
   }

   public void setLng(String lng){
       setParam("lng", lng);
   }

   public void setAddress(String address){
        setParam("address", address);
   }
}

