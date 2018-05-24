package thebrightcompany.com.kdoctor.view.loginmain;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenView{

    public static final String TAG = LoginScreenActivity.class.getSimpleName();
    private static final int REQUEST_CODE_LOC = 22;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 101;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.frameContainer)
    FrameLayout frameLayout;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ButterKnife.bind(this);
        initView();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.Login_Fragment).commit();
        }
    }

    /**
     * The method use to init Views
     */
    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accessLocationPermission();
            checkPermissionCall();
        }
    }

    private void checkPermissionCall(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation   = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        int accessWriteToExternal = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listRequestPermission = new ArrayList<String>();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (accessWriteToExternal != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, REQUEST_CODE_LOC);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOC:
                if (grantResults.length > 0) {
                    for (int gr : grantResults) {
                        // Check if request is granted or not
                        if (gr != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }

                    //TODO - Add your code here to start Discovery

                }
                break;
            case PERMISSIONS_REQUEST_PHONE_CALL:
                if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission is granted
                        //call();
                    } else {
                        showMessage("Sorry!!! Permission Denied");
                    }
                }
                break;
            default:
                return;
        }
    }

    /**
     * The method used to hide the keyboard
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
