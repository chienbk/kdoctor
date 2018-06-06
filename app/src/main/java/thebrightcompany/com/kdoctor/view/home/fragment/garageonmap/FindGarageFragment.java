package thebrightcompany.com.kdoctor.view.home.fragment.garageonmap;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.model.garage.LatLongMessage;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGarageFragment extends Fragment implements FindGarageView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    public static final String TAG = FindGarageFragment.class.getSimpleName();
    private HomeActivity homeActivity;
    private GoogleMap mGoogleMap;
    private List<GarageOnMap> mListGarages = new ArrayList<>();

    //Use to get my location
    private double mLon = 0;
    private double mLat = 0;
    private Marker currentMarker;
    private boolean isFirstOpen = true;

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
        homeActivity.setTitle("Tìm Garage");
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
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.setOnMarkerClickListener(this);
            mGoogleMap.getUiSettings().isMyLocationButtonEnabled();
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        homeActivity.setTitle("Địa chỉ garage");
    }

    @Override
    public void onCommonError(String msg) {

    }

    @Override
    public void onSearchGarageSuccess(List<GarageOnMap> garageOnMaps) {

    }

    @Override
    public void onSearchGarageError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onGetListGaragesSuccess(List<GarageOnMap> garageOnMaps) {

        if (mGoogleMap != null){
            addGarageToMap(garageOnMaps);
        }
    }

    /**
     * The method use to get list data
     * @param garageOnMaps
     */
    private void addGarageToMap(List<GarageOnMap> garageOnMaps) {

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

        if (mGoogleMap != null){

            if (currentMarker != null){
                currentMarker.remove();
            }

            LatLng yourLocation = new LatLng(mLat, mLon);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(yourLocation).title("Your location!"));
            marker.setTag(-1);
            currentMarker = marker;
            if (isFirstOpen){
                moveCamera(mLat, mLon);
                isFirstOpen = false;
            }
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
        return false;
    }
}
