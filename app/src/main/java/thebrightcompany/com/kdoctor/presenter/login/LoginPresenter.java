package thebrightcompany.com.kdoctor.presenter.login;

/**
 * Created by ChienNV on 4/17/17.
 */

public interface LoginPresenter {

    void processLogin(String email, String password, int type, String device_token, String third_token, String full_name);
}
