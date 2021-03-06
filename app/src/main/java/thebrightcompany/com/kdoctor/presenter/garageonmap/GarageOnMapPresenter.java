package thebrightcompany.com.kdoctor.presenter.garageonmap;

/**
 * Created by ChienNV on 4/17/17.
 */

public interface GarageOnMapPresenter {

    void processGetGarageOnMap(String token, double lat, double lng, int distance);
    void processSearchGarageOnMap(String token, String key, int limit, int start);
    void processCreateOrder(String garage_id, String name, String phone, String email,
                            String typeOfCar, String licenseOfCar, String note, String troublecode,
                            String token, String lat, String lng);
    void processCreateOrderWithLocation(String garage_id, String name, String phone, String email,
                            String typeOfCar, String licenseOfCar, String note, String troublecode,
                            String token, String location);
}
