package thebrightcompany.com.kdoctor.view.register;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @Override
    public void onRegisterUser(String name, String msg, String phone, String pass, File avatar) {

    }

    @Override
    public void onRegisterUserSuccess(String msg) {

    }

    @Override
    public void onRegisterError(String msg) {

    }

    @Override
    public void onBackToLoginScreen() {

    }

    @Override
    public void onFullNameError(String msg) {

    }

    @Override
    public void onEmailError(String msg) {

    }

    @Override
    public void onPhoneError(String msg) {

    }

    @Override
    public void onPasswordError(String msg) {

    }

    @Override
    public void onAvatarError(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
