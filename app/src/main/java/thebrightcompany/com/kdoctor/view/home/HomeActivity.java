package thebrightcompany.com.kdoctor.view.home;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.connection.MessageEvent;
import thebrightcompany.com.kdoctor.pushnotification.app.Config;
import thebrightcompany.com.kdoctor.pushnotification.utils.NotificationUtils;
import thebrightcompany.com.kdoctor.service.BluetoothService;
import thebrightcompany.com.kdoctor.view.home.fragment.connection.ConnectionFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.diagnostic.DiagnosticFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.history.HistoryFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.setting.SettingFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.support.SupportFragment;
import thebrightcompany.com.kdoctor.view.home.fragment.troublecode.TroubleCodeFragment;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeView {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final int REQUEST_ENABLE_BT = 2;
    public static final int UART_PROFILE_CONNECTED = 20;
    public static final int UART_PROFILE_DISCONNECTED = 21;

    private static final int REQUEST_CODE_LOC = 22;

    public static int mState = UART_PROFILE_DISCONNECTED;

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    public static BluetoothService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private Fragment lastFragment;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initView();
        initGoogleFirebase();
        displayFirebaseRegId();

        initBLE();
        initService();
    }

    /**
     * The method use to initView
     */
    private void initView() {
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        lastFragment = new ConnectionFragment();
        replaceFragment(lastFragment);
    }

    /**
     * The method use to init service
     */
    private void initService() {
        Intent bindIntent = new Intent(this, BluetoothService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    /**
     * The method use to make Gatt
     * @return
     */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    /**
     * UART service connected/disconnected
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mService = ((BluetoothService.LocalBinder) iBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    };

    /**
     * The method use to listener service
     */
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(BluetoothService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d(TAG, "UART_CONNECT_MSG");
                        mState = UART_PROFILE_CONNECTED;
                    }
                });
            }

            //*********************//
            if (action.equals(BluetoothService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d(TAG, "UART_DISCONNECT_MSG");
                        mState = UART_PROFILE_DISCONNECTED;
                        mService.close();
                        //setUiState();
                        //todo something
                    }
                });
            }

            //*********************//
            if (action.equals(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
            //*********************//
            if (action.equals(BluetoothService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(BluetoothService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            String text = new String(txValue, "UTF-8").replaceAll("\\s+","");

                            Log.d(TAG, "dataReceive: " + txValue.toString());
                            Log.d(TAG, "data After format: " + text);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }
            //*********************//
            if (action.equals(BluetoothService.DEVICE_DOES_NOT_SUPPORT_UART)){
                showMessage(getString(R.string.msg_ble_do_not_support));
                mService.disconnect();
            }

        }
    };

    /**
     * The method use to send data to BLE
     * @param msg
     */
    public void sendDataToBLE(String msg){
        byte[] value;
        try {
            //send data to service
            value = msg.getBytes("UTF-8");
            mService.writeRXCharacteristic(value);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * The method use to connect to BLE device
     * @param address
     */
    public void connectBluetooth(String address){
        mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        mService.connect(mDevice.getAddress());
    }

    /**
     * The method use to process receive data
     * @param data
     */
    private void processReceiveData(String data) {
        EventBus.getDefault().post(new MessageEvent(data));
    }

    /**
     * The method use to disconnect bluetooth
     */
    public void disconnectBluetooth(){
        mService.disconnect();
    }


    /**
     * Init ble
     */
    private void initBLE() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            showMessage(getString(R.string.msg_ble_is_not_available));
            return;
        }
    }

    /**
     * Display firebase register
     */
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.d("Firebase Reg Id: ", " - " + regId);
        else
            Log.d(TAG, "Firebase Reg Id is not received yet!");
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
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            //showMessage("nRFUART's running in background.\n             Disconnect to exit");
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.popup_title)
                    .setMessage(R.string.popup_message)
                    .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.popup_no, null)
                    .show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_connection) {

            fragment = new ConnectionFragment();
        } else if (id == R.id.nav_diagnostic) {

            fragment = new DiagnosticFragment();
        } else if (id == R.id.nav_trouble_code) {

            fragment = new TroubleCodeFragment();
        } else if (id == R.id.nav_add_of_garage) {

            fragment = new FindGarageFragment();
        } else if (id == R.id.nav_history) {

            fragment = new HistoryFragment();
        } else if (id == R.id.nav_setting) {

            fragment = new SettingFragment();
        } else if (id == R.id.nav_support) {

            fragment = new SupportFragment();
        } else if (id == R.id.nav_exit) {

            processLogout();
        }

        replaceFragment(fragment);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            lastFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.framelayout_home, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
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

        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        super.onPause();
    }

    @Override
    public void processLogout() {
        showLogoutDialog();
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

    /**
     * The method use to show dialog
     */
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        TextView title = new TextView(HomeActivity.this);
        title.setTextColor(ContextCompat.getColor(HomeActivity.this, android.R.color.black));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0, 30, 0, 0);
        title.setLayoutParams(lp);
        title.setText("Thoát");
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(getString(R.string.msg_logout));
        builder.setPositiveButton(getString(R.string.msg_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton(getString(R.string.msg_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /**
     * The method use to logout
     */
    private void logout() {
        startActivity(new Intent(this, LoginScreenActivity.class));
        finish();
    }
}
