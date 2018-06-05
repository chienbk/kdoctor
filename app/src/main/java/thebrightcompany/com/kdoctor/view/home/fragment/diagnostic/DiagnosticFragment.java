package thebrightcompany.com.kdoctor.view.home.fragment.diagnostic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import thebrightcompany.com.kdoctor.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.adapter.diagnostic.DiagnosticsAdapter;
import thebrightcompany.com.kdoctor.adapter.diagnostic.ItemDiagnosticOnClickListener;
import thebrightcompany.com.kdoctor.model.connection.MessageEvent;
import thebrightcompany.com.kdoctor.model.diagnostic.Diagnostic;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
import thebrightcompany.com.kdoctor.view.customview.EndlessRecyclerViewScrollListener;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;
import thebrightcompany.com.kdoctor.view.home.fragment.troublecode.TroubleCodeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosticFragment extends Fragment implements DiagnosticView, ItemDiagnosticOnClickListener, OnGetDataFinish{

    public static final String TAG = DiagnosticFragment.class.getSimpleName();

    private HomeActivity homeActivity;

    @BindView(R.id.rcListDiagnostic) RecyclerView listViewDiagnostic;
    @BindView(R.id.layout_find_trouble_code) LinearLayout layout_find_trouble_code;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;

    private List<Diagnostic> mList = new ArrayList<>();
    private DiagnosticsAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int position;
    private Diagnostic mDiagnostic;

    private GetDiagnosticAsynTask getDiagnosticAsynTask;

    public DiagnosticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagnostic, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        getControls();
        return view;
    }

    /**
     * The method use to init controls
     */
    private void getControls() {

        getData();

        adapter = new DiagnosticsAdapter(homeActivity, mList, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listViewDiagnostic.setLayoutManager(mLayoutManager);
        listViewDiagnostic.setItemAnimator(new SlideInDownAnimator());
        listViewDiagnostic.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        listViewDiagnostic.addItemDecoration(new VerticalSpaceItemDecoration(35));

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //todo load more
                //adapter.notifyDataSetChanged(mList);

            }
        };

        listViewDiagnostic.addOnScrollListener(scrollListener);

        listViewDiagnostic.addOnScrollListener(scrollListener);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getDiagnosticAsynTask != null){
                    getDiagnosticAsynTask.cancel(true);
                }
                mList.clear();
                adapter.clearDataSetChanged();
                getData();
                adapter.notifyDataSetChanged(mList);
                adapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });
    }

    /**
     * The method use to get list Data
     */
    private void getData() {
        mList.clear();
        mList = Utils.getListDiagnostic();

        if (homeActivity.isConnected){
            try {
                getDiagnosticAsynTask = new GetDiagnosticAsynTask(mList, homeActivity, this);
                getDiagnosticAsynTask.execute();
            }catch (Exception e){
                Log.d(TAG, e.toString());
                getDiagnosticAsynTask.cancel(true);
            }
        }else {
            showMessage("Please connect to device!");
        }
    }

    private void initView(View view) {
        homeActivity.setTitle("Chuẩn đoán");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
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

    @Override
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Chuẩn đoán");
    }

    @OnClick(R.id.layout_find_trouble_code)
    public void processRedirectTroubleCodeScreen(){
        //todo redirect trouble code screen
        homeActivity.replaceFragment(new TroubleCodeFragment());
    }

    @OnClick(R.id.swipeLayout)
    public void processRefreshDiagnostic(){
        //todo something
        getData();
        adapter.notifyDataSetChanged(mList);
    }

    @Override
    public void onItemClickListener(int position, Diagnostic diagnostic) {
        //todo something
        this.mDiagnostic = diagnostic;
        this.position = position;
        senDataToDevice(mDiagnostic.getCode());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        //todo something
        showMessage(event.getMessage());
        String data = event.getMessage();
        if (data.contains(Contains.CONS_VERHICLE_SPEED)){
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToVehicleSpeed(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_ENGINE_RPM)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToEngineRPM(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_ENGINE_COOLANT_TEMPERATURE)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToCoolantTemperature(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_CALCULATED_ENGINE_LOAD)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToEngineLoad(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_FUEL_TANK_LEVEL_INPUT)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToFuelTank(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_FUEL_PRESSURE)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToFuelPressure(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_INTAKE_AIR_TEMPERATURE)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToIntakeAirTemperature(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_ETHANOL_FUEL)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToEthanolFuel(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_ENGINE_OIL_TEMPERATURE)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToEngineOilTemperature(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_SHORT_TERM_FUEL_TRIM_BANK_1)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToShortTermFuelTrim_Bank_1(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_LONG_TERM_FUEL_TRIM_BANK_1)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToLongTermFuelTrim_Bank_1(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_CATALYST_TEMPERATURE_BANK_1_SENSOR_1)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToCatalystTemperature_Bank1_Sensor1(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_CATALYST_TEMPERATURE_BANK_2_SENSOR_1)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToCatalystTemperature_Bank2_Sensor1(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_AMBIENT_AIR_TEMPERATURE)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToAmbientAirTemperature(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
        else if(data.contains(Contains.CONS_ABSOLUTE_THROTTLE_POSITION_B)) {
            data = Utils.convertDataReceiveToString(data);
            //Set value to diagnostic
            mDiagnostic.setValue(Utils.convertIntegerToAbsoluteThrottle_Position_B(data) + "");
            //Reload
            adapter.notifyItemChange(position, mDiagnostic);
        }
    }

    /**
     * The method use to send data to device
     * @param msg
     */
    private void senDataToDevice(String msg){
        if (homeActivity.isConnected){
            homeActivity.sendDataToBLE(msg);
        }else {
            showMessage("Please connect to device!");
        }
    }

    @Override
    public void onGetDataFinish(Boolean aBoolean) {
        //todo something
        //Process send data to ble
        if (homeActivity.isConnected){
            try {
                getDiagnosticAsynTask = new GetDiagnosticAsynTask(mList, homeActivity, this);
                getDiagnosticAsynTask.execute();
            }catch (Exception e){
                Log.d(TAG, e.toString());
                getDiagnosticAsynTask.cancel(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getDiagnosticAsynTask != null){
            getDiagnosticAsynTask.cancel(true);
        }
    }
}
