package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;

import java.util.List;

import thebrightcompany.com.kdoctor.model.troublecode.TroubleCode;
import thebrightcompany.com.kdoctor.view.BaseView;

public interface TroubleCodeView extends BaseView{
    void onGetTroubleCodeSuccess(String token, List<TroubleCode> troubleCodes);
    void onGetTroubleCodeError(int status_code, String msg);
    void onCommonError(String msg);
}
