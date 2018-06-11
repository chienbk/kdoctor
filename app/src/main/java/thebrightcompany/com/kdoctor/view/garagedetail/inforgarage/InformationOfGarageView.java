package thebrightcompany.com.kdoctor.view.garagedetail.inforgarage;

import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetail;
import thebrightcompany.com.kdoctor.view.garagedetail.GarageDetailView;

public interface InformationOfGarageView extends GarageDetailView{

    void getGaraDetailSuccess(GarageDetail garageDetail);
    void getGaraDetailError(String msg);
}
