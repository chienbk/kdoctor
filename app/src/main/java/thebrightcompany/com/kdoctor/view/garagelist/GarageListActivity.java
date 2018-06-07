package thebrightcompany.com.kdoctor.view.garagelist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.tengarage.ItemSubmitGarageOnClickListener;
import thebrightcompany.com.kdoctor.adapter.tengarage.ItemTenGarageOnClickListener;
import thebrightcompany.com.kdoctor.adapter.tengarage.TenGarageAdapter;
import thebrightcompany.com.kdoctor.model.garage.Garage;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.model.garage.LatLongMessage;
import thebrightcompany.com.kdoctor.presenter.tengarage.GetTenGaragePresenter;
import thebrightcompany.com.kdoctor.presenter.tengarage.GetTenGaragePresenterImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;

/**
 * Created by CongVC on 5/25/2018.
 */

public class GarageListActivity extends AppCompatActivity implements GetTenGaragesView, ItemTenGarageOnClickListener, ItemSubmitGarageOnClickListener{

    public static final String TAG = GarageListActivity.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL = 101;

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rvGarageList)
    RecyclerView rvGarageList;
    @BindView(R.id.txt_no_garage)
    TextView txt_no_garage;

    private TenGarageAdapter adapterGarageList;
    private ArrayList<GarageOnMap> mList = new ArrayList();

    private GetTenGaragePresenter presenter;
    private double mLat, mLng;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String mPhone = "";
    private boolean isFirstOpen = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_list);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(2);

        setTitle("10 Garage gần nhất");
        presenter = new GetTenGaragePresenterImpl(this);
    }

    @Override
    public void onGetTenGaragesSuccess(List<GarageOnMap> garageOnMaps) {
        if (garageOnMaps.size() > 0){
            adapterGarageList.notifyDataSetChanged(garageOnMaps);
        }else {
            rvGarageList.setVisibility(View.GONE);
            txt_no_garage.setVisibility(View.VISIBLE);
        }
    }

    //Handle the data receive from bluetooth
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LatLongMessage event){
        //todo something
        mLat = event.getLat();
        mLng = event.getLng();
        if (isFirstOpen){
            isFirstOpen = false;
            presenter.processGetTenGarage(Utils.APP_TOKEN, mLat, mLng, 10, "", "");
            adapterGarageList = new TenGarageAdapter(mList, new LatLng(mLat, mLng), this, this);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            rvGarageList.setLayoutManager(mLayoutManager);
            rvGarageList.setItemAnimator(new SlideInDownAnimator());
            rvGarageList.setAdapter(new SlideInLeftAnimationAdapter(adapterGarageList));
            rvGarageList.addItemDecoration(new VerticalSpaceItemDecoration(35));
        }
        Log.d(TAG, "Lat: " + mLat);
        Log.d(TAG, "Lng: " + mLng);
    }

    @Override
    public void onGetTenGaragesError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("10 Garage gần nhất");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * The method use to call garage
     *
     * @param phone
     */
    private void processCallGarage(String phone) {
        if (phone != null && phone.length() > 0){
            phone = phone.replaceAll("\\s+","");
            this.mPhone = phone;

            if (ContextCompat.checkSelfPermission(GarageListActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GarageListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + mPhone));
                startActivity(callSupport);
            }
        }else {
            showMessage("Garage này chưa cập nhật số điện thoại!");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHONE_CALL){
            if (ContextCompat.checkSelfPermission(GarageListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GarageListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + mPhone));
                startActivity(callSupport);
            }
        }
    }

    @Override
    public void onItemClickListener(int position, GarageOnMap garageOnMap) {
        //todo something
        //todo redirect to garage detail
        showMessage("You choice: " + garageOnMap.getName());
    }

    @Override
    public void onItemClickListener(boolean position, GarageOnMap garageOnMap) {
        if (position){
            showMessage("chức năng này đang được hoàn thiện");
        }else {
            processCallGarage(garageOnMap.getPhone());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
