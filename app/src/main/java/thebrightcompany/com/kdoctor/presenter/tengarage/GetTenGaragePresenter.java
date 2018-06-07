package thebrightcompany.com.kdoctor.presenter.tengarage;

/**
 * Created by ChienNV on 4/17/17.
 */

public interface GetTenGaragePresenter {

    void processGetTenGarage(String token, double lat, double lng, int limit, String sortBy, String distance);
}
