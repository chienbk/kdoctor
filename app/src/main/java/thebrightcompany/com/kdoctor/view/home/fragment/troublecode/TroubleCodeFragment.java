package thebrightcompany.com.kdoctor.view.home.fragment.troublecode;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.troublecode.ItemTroubleCodeOnClickListener;
import thebrightcompany.com.kdoctor.adapter.troublecode.TroubleCodeAdapter;
import thebrightcompany.com.kdoctor.model.connection.MessageEvent;
import thebrightcompany.com.kdoctor.model.troublecode.TroubleCode;
import thebrightcompany.com.kdoctor.presenter.troublecode.TroubleCodePresentor;
import thebrightcompany.com.kdoctor.presenter.troublecode.TroubleCodePresentorImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;
import thebrightcompany.com.kdoctor.view.home.fragment.garageonmap.FindGarageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TroubleCodeFragment extends Fragment implements TroubleCodeView, ItemTroubleCodeOnClickListener{

    public static final String TAG = TroubleCodeFragment.class.getSimpleName();
    private HomeActivity homeActivity;

    @BindView(R.id.listTroubleCode) RecyclerView listTroubleCode;
    @BindView(R.id.layout_find_garage) LinearLayout layout_find_garage;
    @BindView(R.id.txt_totalsOfErrors) TextView txt_totalsOfErrors;


    private static AlertDialog.Builder dlgBuilder;
    private GetTroubleCodeAsynTask troubleCodeAsynTask;

    private boolean isReceiveTotalError = false;
    private boolean isReceiveDetailError = false;

    private List<TroubleCode> mListTroubleCodes = new ArrayList<>();
    private TroubleCodeAdapter adapter;
    private TroubleCodePresentor presentor;
    public TroubleCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trouble_code, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        initView(view);
        getData();
        return view;
    }

    /**
     *
     */
    private void getData() {

        List<String> mList = new ArrayList<>();
        mList.add("01 01");
        mList.add("03");

        if (homeActivity.isConnected){
            showProgress();
            troubleCodeAsynTask = new GetTroubleCodeAsynTask(homeActivity, mList);
            troubleCodeAsynTask.execute();
        }else {
            showMessage(getString(R.string.msg_alert_connection));
        }
        createTimeOut(5000);
    }

    private void initView(View view) {
        homeActivity.setTitle("Mã lỗi");
        adapter = new TroubleCodeAdapter(mListTroubleCodes, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listTroubleCode.setLayoutManager(mLayoutManager);
        listTroubleCode.setItemAnimator(new SlideInDownAnimator());
        listTroubleCode.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        listTroubleCode.addItemDecoration(new VerticalSpaceItemDecoration(35));

        presentor = new TroubleCodePresentorImpl(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
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
        homeActivity.setTitle("Mã lỗi");
    }

    @OnClick(R.id.txt_lb_errors)
    public void processGetTotalsErrors(){
        if (homeActivity.isConnected){
            showProgress();
            homeActivity.sendDataToBLE("01 01");
        }else {
            showMessage(getString(R.string.msg_alert_connection));
        }
        createTimeOut(5000);
    }

    @OnClick(R.id.layout_find_garage)
    public void processRedirectToGarageOnMapScreen(){
        //todo redirect to garage screen
        homeActivity.replaceFragment(new FindGarageFragment());
    }

    //Handle the data receive from bluetooth
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        //todo something
        String data = event.getMessage();
        Log.d(TAG, "data: " + data);
        //showMessage(data);

        if (data.contains(Contains.TROUBLE_CODE)){
            isReceiveTotalError = true;
            data = Utils.convertDataReceiveToString(data);
            Log.d(TAG, "convertDataReceiveToString: " + data);
            txt_totalsOfErrors.setText(Utils.convertToTotalCErrorCode(data) + "");
            //hideProgress();
        }else if (data.contains(Contains.DETAIL_ERROR_CODE)){
            Log.d(TAG, "getTroubleCodeDetail");
            isReceiveDetailError = true;
            List<Integer> listOfErrorCode = new ArrayList<>();
            listOfErrorCode = Utils.getIntergerOfError(data);
            List<String> pCode = new ArrayList<>();
            for (int i = 0; i < listOfErrorCode.size(); i++){
                pCode.add(Utils.getErrorPCode(listOfErrorCode.get(i)));
                Utils.ERROR_CODE += Utils.getErrorPCode(listOfErrorCode.get(i)) + ", ";
                Log.d(TAG, "pCode:" + Utils.getErrorPCode(listOfErrorCode.get(i)));

            }
            //process get error code detail
            Gson gson = new Gson();
            String json = gson.toJson(pCode);
            Log.d(TAG, "json: " + json);
            presentor.processGetTroubleCodeDetail(Utils.APP_TOKEN, json);

        }

        if (isReceiveTotalError && isReceiveDetailError){
            hideProgress();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.trouble_codes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_codes:
                clearObdFaultCodes();
                break;
        }
        return true;

    }

    /**
     * clear OBD fault codes after a warning
     * confirmation dialog is shown and the operation is confirmed
     */
    protected void clearObdFaultCodes() {
        dlgBuilder
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.obd_clearcodes)
                .setMessage(R.string.obd_clear_info)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // set service CLEAR_CODES to clear the codes
                                //todo something
                                if (homeActivity.isConnected){
                                    homeActivity.sendDataToBLE("04");
                                }else {
                                    showMessage(getString(R.string.msg_alert_connection));
                                }
                            }
                        })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void createTimeOut(long time){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                hideProgress();
            }
        }, time );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (troubleCodeAsynTask != null)
            troubleCodeAsynTask.cancel(true);
    }

    @Override
    public void onGetTroubleCodeSuccess(String token, List<TroubleCode> troubleCodes) {
        this.mListTroubleCodes.addAll(troubleCodes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetTroubleCodeError(int status_code, String msg) {
        showMessage(msg);
        if (status_code == Contains.TOKEN_EXPIRED){
            homeActivity.logout();
        }
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onItemClickListener(int position, TroubleCode troubleCode) {
        //todo something
        showMessage("Chức năng đang được hoàn thiện!");
    }
}
