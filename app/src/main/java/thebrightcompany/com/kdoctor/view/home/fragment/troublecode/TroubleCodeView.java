package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;

import thebrightcompany.com.kdoctor.view.BaseView;

public interface TroubleCodeView extends BaseView{
    void onGetTroubleCodeSuccess(String msg);
    void onGetTroubleCodeError(int status_code, String msg);
    void onCommonError(String msg);
}
