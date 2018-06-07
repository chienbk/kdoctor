package thebrightcompany.com.kdoctor.view.home.fragment.garageonmap;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.diagnostic.DiagnosticsAdapter;
import thebrightcompany.com.kdoctor.adapter.garaonmap.ItemSearchGarageOnClickListener;
import thebrightcompany.com.kdoctor.adapter.garaonmap.SearchGarageAdapter;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.model.garage.LatLongMessage;
import thebrightcompany.com.kdoctor.presenter.garageonmap.GarageOnMapPresenter;
import thebrightcompany.com.kdoctor.presenter.garageonmap.GarageOnMapPresenterImpl;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
import thebrightcompany.com.kdoctor.view.garagelist.GarageListActivity;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGarageFragment extends Fragment implements FindGarageView, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, ItemSearchGarageOnClickListener{

    public static final String TAG = FindGarageFragment.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL = 101;

    @BindView(R.id.layout_detail)
    LinearLayout layout_detail;
    @BindView(R.id.img_exit)
    ImageView img_exit;
    @BindView(R.id.txt_nameOfGarage)
    TextView txt_nameOfGarage;
    @BindView(R.id.txt_distance)
    TextView txt_distance;
    @BindView(R.id.rate_garage)
    RatingBar rate_garage;
    @BindView(R.id.txt_addressOfGarage)
    TextView txt_addressOfGarage;

    @BindView(R.id.rc_search_garage)
    RecyclerView rc_search_garage;
    @BindView(R.id.img_search)
    ImageView img_search;

    @BindView(R.id.edit_search)
    EditText edit_search;



    private HomeActivity homeActivity;
    private GoogleMap mGoogleMap;
    private List<GarageOnMap> mListGarages = new ArrayList<>();

    //Use to get my location
    private double mLon = -34;
    private double mLat = 151;
    private Marker currentMarker;
    private boolean isFirstOpen = true;
    private boolean isFirstCallAPI = true;

    private GarageOnMapPresenter presenter;
    private GarageOnMap garageOnMap;
    private String phone = "";
    private String key = "";

    private List<GarageOnMap> searchGaras = new ArrayList<>();
    private SearchGarageAdapter adapter;

    public FindGarageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_garage, container, false);
        ButterKnife.bind(this, view);
        initMaps();
        initView(view);
        return view;
    }

    /**
     * The method use to init views
     * @param view
     */
    private void initView(View view) {

        presenter = new GarageOnMapPresenterImpl(this);
        homeActivity.setTitle("Tìm Garage");

        if (searchGaras.size() == 0){
            rc_search_garage.setVisibility(View.GONE);
        }

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString();
                if (TextUtils.isEmpty(data)){
                    key = "";
                    rc_search_garage.setVisibility(View.GONE);
                    img_search.setBackground(homeActivity.getDrawable(R.drawable.ic_search_gara));
                }else {
                    layout_detail.setVisibility(View.GONE);
                    img_search.setBackground(homeActivity.getDrawable(R.drawable.ic_search_close));
                    key = data;
                    presenter.processSearchGarageOnMap(Utils.APP_TOKEN, key, 10, 0);
                }
            }
        });
    }

    private void initRecycleView(){
        adapter = new SearchGarageAdapter(searchGaras, new LatLng(mLat, mLon), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rc_search_garage.setLayoutManager(mLayoutManager);
        rc_search_garage.setItemAnimator(new SlideInDownAnimator());
        rc_search_garage.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        rc_search_garage.addItemDecoration(new VerticalSpaceItemDecoration(35));
    }

    /**
     * The method use to init google maps
     */
    private void initMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (HomeActivity) context;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.clear();
        if (mGoogleMap != null){
            mGoogleMap.setOnMarkerClickListener(this);
        }
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
    public void onResume() {
        super.onResume();
        homeActivity.setTitle("Địa chỉ garage");
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onSearchGarageSuccess(List<GarageOnMap> garageOnMaps) {
        if (garageOnMaps != null){
            this.searchGaras.clear();
            this.searchGaras = garageOnMaps;
            rc_search_garage.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged(searchGaras);
        }
    }

    @Override
    public void onSearchGarageError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onGetListGaragesSuccess(List<GarageOnMap> garageOnMaps) {
        if (mGoogleMap != null){
            this.mListGarages = garageOnMaps;
            addGarageToMap(mListGarages);
        }
    }

    /**
     * The method use to get list data
     * @param garageOnMaps
     */
    private void addGarageToMap(List<GarageOnMap> garageOnMaps) {
        if (garageOnMaps.size() > 0 && garageOnMaps != null){
            for (GarageOnMap gara : garageOnMaps){
                Marker marker =
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(gara.getLat(), gara.getLng()))
                                //.title(title)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_garage_location))
                                .alpha(0.7f));
                marker.setTag(gara.getId());
            }
        }
    }

    @Override
    public void onGetListGaragesError(String msg) {
        showMessage(msg);
    }

    //Handle the data receive from bluetooth
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LatLongMessage event){
        //todo something
        mLat = event.getLat();
        mLon = event.getLng();
        if (mGoogleMap != null && isFirstCallAPI){
            presenter.processGetGarageOnMap(Utils.APP_TOKEN, mLat, mLon, 5);
            isFirstCallAPI = false;
        }
        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLon);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            if (isFirstOpen){
                initRecycleView();
                moveCamera(mLat, mLon);
                isFirstOpen = false;
            }
        }

    }

    @OnClick(R.id.fab_add)
    public void focusMyLocation(){
        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLon);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            moveCamera(mLat, mLon);
        }
    }

    /**
     *
     * @param cr_lat
     * @param cr_lng
     */
    private void moveCamera(double cr_lat, double cr_lng) {

        if (mGoogleMap != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cr_lat, cr_lng), 15));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int id = (int) marker.getTag();
        if (id != -1){
            GarageOnMap garageOnMap = new GarageOnMap();
            for (int i = 0; i < mListGarages.size(); i ++){
                if (id == mListGarages.get(i).getId()){
                    garageOnMap = mListGarages.get(i);
                    processDisplayInformationOfGarage(garageOnMap);
                    break;
                }

            }
        }
        return false;
    }

    /**
     * The method use to display information of garage
     * @param garageOnMap
     */
    private void processDisplayInformationOfGarage(GarageOnMap garageOnMap) {
        layout_detail.setVisibility(View.VISIBLE);
        try {
            phone = garageOnMap.getPhone();
            txt_nameOfGarage.setText(garageOnMap.getName());
            txt_distance.setText(Utils.calculationByDistance(new LatLng(mLat, mLon),
                    new LatLng(garageOnMap.getLat(), garageOnMap.getLng())) + " km");
            rate_garage.setRating(garageOnMap.getRate());
            txt_addressOfGarage.setText(garageOnMap.getAddress());
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }
    }

    @OnClick(R.id.img_exit)
    public void processExit(){
        layout_detail.setVisibility(View.GONE);
    }

    @OnClick(R.id.layout_get_ten_garage)
    public void getTenGarage(){
        //todo something
        homeActivity.redirectToListTenGarageScreen();
    }

    @OnClick(R.id.btn_direct)
    public void processRedirect(){
        //todo something
        showMessage("Chúng tôi đang hoàn thiện!");
    }

    @OnClick(R.id.btn_contact)
    public void processContactGarage(){
        //todo something
        processCallGarage(phone);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_search)
    public void processSearch(){
        //todo something
        if (!TextUtils.isEmpty(key)){
            edit_search.setText("");
            homeActivity.hideKeyboard();
            img_search.setBackground(homeActivity.getDrawable(R.drawable.ic_search_gara));
        }

    }

    /**
     * The method use to call garage
     *
     * @param phone
     */
    private void processCallGarage(String phone) {
        if (phone != null && phone.length() > 0){
            phone = phone.replaceAll("\\s+","");

            if (ContextCompat.checkSelfPermission(homeActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + phone));
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
            if (ContextCompat.checkSelfPermission(homeActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:" + phone));
                startActivity(callSupport);
            }
        }
    }

    @Override
    public void onItemClickListener(int position, GarageOnMap garageOnMap) {
        //todo something
        searchGaras.clear();
        rc_search_garage.setVisibility(View.GONE);
        homeActivity.hideKeyboard();
        moveCamera(garageOnMap.getLat(), garageOnMap.getLng());
        processDisplayInformationOfGarage(garageOnMap);
    }
}
