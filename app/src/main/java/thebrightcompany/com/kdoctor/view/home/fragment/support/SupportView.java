package thebrightcompany.com.kdoctor.view.home.fragment.support;

import thebrightcompany.com.kdoctor.model.support.Company;
import thebrightcompany.com.kdoctor.view.BaseView;

public interface SupportView extends BaseView{

    void onInformationOfCompanySuccess(Company companyInfomation);
    void onInformationError(String msg);
    void onCommonError(String msg);
}
