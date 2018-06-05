package thebrightcompany.com.kdoctor.presenter.register;

import java.io.File;

/**
 * Created by ChienNV on 4/18/17.
 */

public interface RegisterPresenter {

    void processRegister(String full_name, String email, String phone, String password, String rePassword, File avatar);
}
