package thebrightcompany.com.kdoctor.view.home.fragment.connection;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.connection.DeviceConnect;
import thebrightcompany.com.kdoctor.adapter.connection.ConnectionAdapter;
import thebrightcompany.com.kdoctor.adapter.connection.EditDeviceListener;
import thebrightcompany.com.kdoctor.adapter.connection.ExtensionDateListener;
import thebrightcompany.com.kdoctor.adapter.connection.ItemOnClickListener;
import thebrightcompany.com.kdoctor.model.connection.BluetoothConnection;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
import thebrightcompany.com.kdoctor.view.extensiondate.ExtensionDateActivity;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

import org.greenrobot.eventbus.ThreadMode;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment implements ConnectionView, ItemOnClickListener, EditDeviceListener,
        ExtensionDateListener{

    public static final String TAG = ConnectionFragment.class.getSimpleName();

    @BindView(R.id.listViewConnection) RecyclerView mLisView;
    @BindView(R.id.btn_scan) Button btn_scan;

    private HomeActivity homeActivity;

    private List<BluetoothConnection> mLists = new ArrayList<>();
    private ConnectionAdapter adapter;

    private BluetoothAdapter mBluetoothAdapter;
    //Map<String, Integer> devRssiValues;
    private static final long SCAN_PERIOD = 6000; //scanning for 6 seconds
    private boolean mScanning;
    private BluetoothConnection mDevice;
    private Handler mHandler;
    private String lastDeviceConnected = "";
    private int position;
    //private Bundle mBundle;

    private SharedPreferencesUtils sharedPreferencesUtils;
    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initBluetooth();
        getData();
        return view;
    }

    /**
     * The method use to init bluetooth
     */
    private void initBluetooth() {
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!homeActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showMessage(getString(R.string.ble_not_supported));
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) homeActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            showMessage(getString(R.string.ble_not_supported));
            return;
        }
    }

    /**
     * The method use to get bluetooth device
     *
     */
    private void getData() {
        scanLeDevice(true);
    }

    /**
     * The method use to initView
     * @param view
     */
    private void initView(View view) {

        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        if (sharedPreferencesUtils != null){
            lastDeviceConnected = sharedPreferencesUtils.readStringPreference(Contains.PREF_MAC_ADDRESS, "");
        }

        homeActivity.setTitle("Kết nối thiết bị");

        android.view.WindowManager.LayoutParams layoutParams = homeActivity.getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y = 200;

        mHandler = new Handler();
        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        adapter = new ConnectionAdapter(homeActivity, mLists, this, this, this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLisView.setLayoutManager(mLayoutManager);
        mLisView.setItemAnimator(new SlideInDownAnimator());
        mLisView.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        mLisView.addItemDecoration(new VerticalSpaceItemDecoration(35));
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
    }

    @Override
    public void onRefreshList() {
        mLists.clear();
        scanLeDevice(true);
        adapter.notifyDataSetChanged(mLists);
    }

    @Override
    public void showProgress() {
        homeActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        homeActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        homeActivity.showMessage(message);
    }

    @OnClick(R.id.btn_scan)
    public void processScan(){
        //todo something
        homeActivity.disconnectBluetooth();
        mLists.clear();
        adapter.notifyDataSetChanged(mLists);
        scanLeDevice(true);
    }

    @Override
    public void onItemClickListener(int position, BluetoothConnection bluetoothConnection) {
        //todo something
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        mDevice = mLists.get(position);
        this.position = position;

        if (!bluetoothConnection.isConnected()){
            showProgress();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            }, SCAN_PERIOD);
            homeActivity.connectBluetooth(bluetoothConnection.getMacAddress());
        }else {

            homeActivity.disconnectBluetooth();
            mDevice.setConnected(false);
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            homeActivity.showProgress();
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    hideProgress();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            hideProgress();
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    homeActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (device != null){
                                addDevice(device);
                                Log.d(TAG, device.getAddress());
                            }
                        }
                    });
                }
            };

    private void addDevice(BluetoothDevice device) {

        boolean deviceFound = false;

        for (BluetoothConnection listDev : mLists) {
            if (listDev.getMacAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }

        if (!deviceFound) {
            BluetoothConnection connection;
            if (device.getName() != null){
                connection = new BluetoothConnection(device.getName(),
                        device.getAddress(), "12/12/2018", "34:RF:6G:23:G6:HY",false, false);
            }else {
                connection = new BluetoothConnection("Unknown device",
                        device.getAddress(), "12/12/2018", "34:RF:6G:23:G6:HY",false, false);
            }

            mLists.add(connection);
                      adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (homeActivity.isConnected){
            Gson gson = new Gson();
            BluetoothConnection connection;
            String device = sharedPreferencesUtils.readStringPreference(Contains.PREF_OBJECT_CONNECTION, "");
            if (!TextUtils.isEmpty(device)){
                connection = gson.fromJson(device, BluetoothConnection.class);
                mLists.add(connection);
                adapter.notifyDataSetChanged(mLists);
            }
        }

        if (sharedPreferencesUtils != null){
            lastDeviceConnected = sharedPreferencesUtils.readStringPreference(Contains.PREF_MAC_ADDRESS, "");
        }
       /* if (homeActivity.isConnected){
           //todo something
        }*/

        homeActivity.setTitle("Kết nối thiết bị");
    }

    @Override
    public void onEditItemListener(int position, BluetoothConnection bluetoothConnection) {
        adapter.notifyItemChange(position, bluetoothConnection);
    }

    @Override
    public void onExtensionListener() {
        startActivity(new Intent(homeActivity, ExtensionDateActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeviceConnect device){
        //todo something
        hideProgress();
        Log.d(TAG, "isConnected: " + device.isConnected());
        if(device.isConnected()){
            mDevice.setConnected(true);
            adapter.notifyItemChange(position, mDevice);
            lastDeviceConnected = mDevice.getMacAddress();

            Gson gson = new Gson();
            String dv = gson.toJson(mDevice);
            if (sharedPreferencesUtils != null){
                sharedPreferencesUtils.writeStringPreference(Contains.PREF_OBJECT_CONNECTION, dv);
                sharedPreferencesUtils.writeStringPreference(Contains.PREF_MAC_ADDRESS, lastDeviceConnected);
            }

        }else {
            mDevice.setConnected(false);
            try {
                adapter.notifyItemChange(position, mDevice);
            }catch (Exception extension){
                Log.d(TAG, extension.toString());
            }
            showMessage(device.getMsg());
        }
    }
}
