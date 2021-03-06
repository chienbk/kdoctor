package thebrightcompany.com.kdoctor.view.home.fragment.garageonmap;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
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
import thebrightcompany.com.kdoctor.adapter.garaonmap.ItemSearchGarageOnClickListener;
import thebrightcompany.com.kdoctor.adapter.garaonmap.SearchGarageAdapter;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.model.garage.LatLongMessage;
import thebrightcompany.com.kdoctor.presenter.garageonmap.GarageOnMapPresenter;
import thebrightcompany.com.kdoctor.presenter.garageonmap.GarageOnMapPresenterImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.utils.VerticalSpaceItemDecoration;
import thebrightcompany.com.kdoctor.view.garagedetail.ActivityGarageDetail;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGarageFragment extends Fragment implements FindGarageView, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, ItemSearchGarageOnClickListener, GoogleMap.OnMapClickListener{

    public static final String TAG = FindGarageFragment.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL = 101;

    @BindView(R.id.layout_detail)
    LinearLayout layout_detail;
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
    private double mLng = -34;
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

    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean isChoice = false;
    private int idOfGarage = -100;
    private String mLocation = "";

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
        homeActivity.showDialogAskEnableGPS();
        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        mLat = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Contains.PREF_LAT, "0"));
        mLng = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Contains.PREF_LNG, "0"));
        Utils.NAME_CUSTOMER = sharedPreferencesUtils.readStringPreference(Contains.PREF_NAME_CUSTOMER, "0");
        Utils.EMAIL_CUSTOMER = sharedPreferencesUtils.readStringPreference(Contains.PREF_EMAIL_CUSTOMER, "0");
        Utils.PHONE_CUSTOMER = sharedPreferencesUtils.readStringPreference(Contains.PREF_PHONE_CUSTOMER, "0");

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
                    img_search.setBackground(homeActivity.getDrawable(R.drawable.ic_close));
                    key = data;
                    presenter.processSearchGarageOnMap(Utils.APP_TOKEN, key, 10, 0);
                }
            }
        });
    }

    private void initRecycleView(){
        adapter = new SearchGarageAdapter(searchGaras, new LatLng(mLat, mLng), this);
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
            moveCamera(mLat, mLng);
            presenter.processGetGarageOnMap(Utils.APP_TOKEN, mLat, mLng, 5);
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
        homeActivity.setTitle("Tìm Garage");
    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onSearchGarageSuccess(String token, List<GarageOnMap> garageOnMaps) {
        homeActivity.updateToken(token);
        Utils.APP_TOKEN = token;
        if (garageOnMaps != null){
            this.searchGaras.clear();
            this.searchGaras = garageOnMaps;
            rc_search_garage.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged(searchGaras);
        }
    }

    @Override
    public void onSearchGarageError(int status_code, String msg) {
        showMessage(msg);
        if (status_code == Contains.TOKEN_EXPIRED){
            homeActivity.logout();
        }
    }

    @Override
    public void onGetListGaragesSuccess(String token, List<GarageOnMap> garageOnMaps) {
        homeActivity.updateToken(token);
        Utils.APP_TOKEN = token;
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
    public void onGetListGaragesError(int status_code, String msg) {
        showMessage(msg);
    }

    @Override
    public void onCreateOrderSuccess(String msg) {
        showMessage(msg);
    }

    @Override
    public void onCreateOrderError(String msg) {
        showMessage(msg);
    }

    //Handle the data receive from bluetooth
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LatLongMessage event){
        //todo something
        mLat = event.getLat();
        mLng = event.getLng();
        Utils.LAT = String.valueOf(mLat);
        Utils.LNG = String.valueOf(mLng);
        if (mGoogleMap != null && isFirstCallAPI){
            mGoogleMap.clear();
            presenter.processGetGarageOnMap(Utils.APP_TOKEN, mLat, mLng, 5);
            isFirstCallAPI = false;
        }
        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLng);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            if (isFirstOpen){
                initRecycleView();
                moveCamera(mLat, mLng);
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

            LatLng yourLocation = new LatLng(mLat, mLng);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            moveCamera(mLat, mLng);
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
                    //idOfGarage = id;
                    garageOnMap = mListGarages.get(i);

                    if (isChoice && idOfGarage == id){
                        layout_detail.setVisibility(View.GONE);
                        isChoice = false;
                    }else {
                        processDisplayInformationOfGarage(garageOnMap);
                    }

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
        this.garageOnMap = garageOnMap;
        isChoice = true;
        idOfGarage =  garageOnMap.getId();
        layout_detail.setVisibility(View.VISIBLE);
        try {
            phone = garageOnMap.getPhone();
            txt_nameOfGarage.setText(garageOnMap.getName());
            txt_distance.setText(Utils.distFrom(new LatLng(mLat, mLng), new LatLng(garageOnMap.getLat(), garageOnMap.getLng())) + " km");
            rate_garage.setRating(garageOnMap.getRate());
            txt_addressOfGarage.setText(garageOnMap.getAddress());
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }
    }

    @OnClick(R.id.imgAvatar)
    public void processExit(){
        //layout_detail.setVisibility(View.GONE);
        Intent intent = new Intent(homeActivity, ActivityGarageDetail.class);
        intent.putExtra(Contains.PREF_GARAGE_DETAIL, garageOnMap);
        startActivity(intent);
    }

    @OnClick(R.id.layout_get_ten_garage)
    public void getTenGarage(){
        //todo something
        homeActivity.redirectToListTenGarageScreen();
    }

    @OnClick(R.id.btn_direct)
    public void processRedirect(){
        //todo something
        String uri = String.format("http://maps.google.com/maps?saddr=%s,%s(%s)&daddr=%s,%s(%s)", mLat+"", mLng+"", "Your location",
                garageOnMap.getLat()+"", garageOnMap.getLng()+"", garageOnMap.getName());
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.btn_contact)
    public void processContactGarage(){
        //todo something
        processCallGarage(phone);
        showDialog(garageOnMap);
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
        this.garageOnMap = garageOnMap;
        searchGaras.clear();
        rc_search_garage.setVisibility(View.GONE);
        homeActivity.hideKeyboard();
        moveCamera(garageOnMap.getLat(), garageOnMap.getLng());
        processDisplayInformationOfGarage(garageOnMap);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        layout_detail.setVisibility(View.GONE);
    }

    private void showDialog(GarageOnMap garageOnMap) {

        final Dialog dialog = new Dialog(homeActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_choice_garage);

        TextView txt_nameOfGarage = (TextView) dialog.findViewById(R.id.txt_nameOfGarage);
        TextView txt_distance = (TextView) dialog.findViewById(R.id.txt_distance);
        RatingBar rate_garage = (RatingBar) dialog.findViewById(R.id.rate_garage);
        TextView txt_addressOfGarage = (TextView) dialog.findViewById(R.id.txt_addressOfGarage);
        TextView txt_phone = (TextView) dialog.findViewById(R.id.txt_phone);
        CheckBox chk_location = (CheckBox) dialog.findViewById(R.id.chk_location);
        final EditText edit_location = (EditText) dialog.findViewById(R.id.edit_location);


        Button btn_sm = (Button) dialog.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        try {
            txt_nameOfGarage.setText(garageOnMap.getName());
            txt_distance.setText(Utils.distFrom(new LatLng(mLat, mLng), new LatLng(garageOnMap.getLat(), garageOnMap.getLng())) + " km");
            txt_addressOfGarage.setText(garageOnMap.getAddress());
            txt_phone.setText(garageOnMap.getPhone());
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }

        chk_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    edit_location.setEnabled(false);
                } else {
                    edit_location.setEnabled(true);
                }
            }
        });

        btn_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocation = edit_location.getText().toString();
                processAddOrder();
                dialog.dismiss();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * Process add order
     */
    private void processAddOrder() {
        if (idOfGarage < 0) {
            if (TextUtils.isEmpty(mLocation)){
                presenter.processCreateOrder("", Utils.NAME_CUSTOMER, Utils.PHONE_CUSTOMER, Utils.EMAIL_CUSTOMER,
                        "", "", "", Utils.URL_TROUBLE_CODE, Utils.APP_TOKEN, Utils.LAT, Utils.LNG);
            } else {
                presenter.processCreateOrderWithLocation("", Utils.NAME_CUSTOMER, Utils.PHONE_CUSTOMER, Utils.EMAIL_CUSTOMER,
                        "", "", "", Utils.URL_TROUBLE_CODE, Utils.APP_TOKEN, mLocation);
            }
        } else {
            if (TextUtils.isEmpty(mLocation)) {
                presenter.processCreateOrder(String.valueOf(idOfGarage), Utils.NAME_CUSTOMER, Utils.PHONE_CUSTOMER, Utils.EMAIL_CUSTOMER,
                        "", "", "", Utils.URL_TROUBLE_CODE, Utils.APP_TOKEN, Utils.LAT, Utils.LNG);
            } else {
                presenter.processCreateOrderWithLocation(String.valueOf(idOfGarage), Utils.NAME_CUSTOMER, Utils.PHONE_CUSTOMER, Utils.EMAIL_CUSTOMER,
                        "", "", "", Utils.URL_TROUBLE_CODE, Utils.APP_TOKEN, mLocation);
            }
        }

    }
}
