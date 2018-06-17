package thebrightcompany.com.kdoctor.view.loginmain;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.pushnotification.app.Config;
import thebrightcompany.com.kdoctor.pushnotification.utils.NotificationUtils;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenView{

    public static final String TAG = LoginScreenActivity.class.getSimpleName();
    private static final int REQUEST_CODE_LOC = 22;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 101;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.frameContainer)
    FrameLayout frameLayout;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ButterKnife.bind(this);
        initGoogleFirebase();
        initView();
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        String deviceToken = sharedPreferencesUtils.readStringPreference(Contains.PREF_DEVICE_TOKEN, "");
        if (!TextUtils.isEmpty(deviceToken)){
            Utils.APP_TOKEN = deviceToken;
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.Login_Fragment).commit();
        }
    }

    /**
     * Init google firebase
     */
    private void initGoogleFirebase() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Log.d(TAG, "message: " + message);
                }
            }
        };
        displayFirebaseRegId();
    }

    /**
     * Display firebase register
     */
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            Utils.FCM_TOKEN = regId;
            Log.d("Firebase Reg Id: ", " - " + regId);
        } else
            Log.d(TAG, "Firebase Reg Id is not received yet!");
    }

    /**
     * The method use to init Views
     */
    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accessLocationPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation   = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        int accessWriteToExternal = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int accessCall = checkSelfPermission(Manifest.permission.CALL_PHONE);
        int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);

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

        if (accessCall != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.CALL_PHONE);
        }

        if (accessCamera != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.CAMERA);
        }

        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, REQUEST_CODE_LOC);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
