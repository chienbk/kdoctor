package thebrightcompany.com.kdoctor.presenter.garageonmap;

/**
 * Created by ChienNV on 4/17/17.
 */

public interface GarageOnMapPresenter {

    void processGetGarageOnMap(String token, long lat, long lng, int distance);
    void processSearchGarageOnMap(String token, String key, int limit, int start);
}
