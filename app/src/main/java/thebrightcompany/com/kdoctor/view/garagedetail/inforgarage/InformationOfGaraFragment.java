package thebrightcompany.com.kdoctor.view.garagedetail.inforgarage;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.garagedetail.GarageDetail;
import thebrightcompany.com.kdoctor.presenter.detailofgarage.DetailOfGaragePresentor;
import thebrightcompany.com.kdoctor.presenter.detailofgarage.DetailOfGaragePresentorImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.garagedetail.ActivityGarageDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationOfGaraFragment extends Fragment implements InformationOfGarageView{

    public static String TAG = InformationOfGaraFragment.class.getSimpleName();
    private ActivityGarageDetail homeActivity;
    private static final String ARG_ID_GARAGE = "ID_OF_GARAGE";
    public static final String URL = "https://www.chotot.com/kinhnghiem/wp-content/uploads/2017/01/1336427909389350.jpg";

    @BindView(R.id.vpSlideImage)
    ImageView imgProfile;
    @BindView(R.id.tvGarageName)
    TextView txt_nameOfGarage;
    @BindView(R.id.txt_address) TextView txt_addressOfGarage;
    @BindView(R.id.txt_phone) TextView txt_phoneOfGarage;
    @BindView(R.id.txt_email) TextView txt_emailOfGarage;
    @BindView(R.id.txt_service) TextView txt_service;

    private int idOfGarage;
    private double mLat, mLng;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private GarageDetail mGarageDetail;
    private DetailOfGaragePresentor presentor;


    public InformationOfGaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id
     * @return
     */
    public static InformationOfGaraFragment newInstance(String id) {
        InformationOfGaraFragment fragment = new InformationOfGaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_GARAGE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idOfGarage = Integer.parseInt(getArguments().getString(ARG_ID_GARAGE));
            Log.d(TAG, "idOfGarage: " + idOfGarage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information_of_gara, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    /**
     * The method use to init view
     * @param view
     */
    private void initView(View view) {
        //todo something

        presentor = new DetailOfGaragePresentorImpl(this);
        presentor.processGetGarageDetail(Utils.APP_TOKEN, idOfGarage);
        sharedPreferencesUtils = new SharedPreferencesUtils(homeActivity);
        mLat = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Contains.PREF_LAT, "0"));
        mLng = Double.parseDouble(sharedPreferencesUtils.readStringPreference(Contains.PREF_LNG, "0"));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (ActivityGarageDetail) context;
    }

    @Override
    public void getGaraDetailSuccess(String token, GarageDetail garageDetail) {
        //todo something
        homeActivity.updateToken(token);
        Utils.APP_TOKEN = token;
        this.mGarageDetail = garageDetail;
        if (sharedPreferencesUtils != null){
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_RATE, garageDetail.getRate() + "");
        }

        try {
            if (garageDetail.getGallery().size() > 0 && !TextUtils.isEmpty(garageDetail.getGallery().get(0))){
                Glide.with(this).load(garageDetail.getGallery().get(0))
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile);
            } else {
                Glide.with(this).load(URL)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile);
            }
        } catch (Exception e){
            Glide.with(this).load(URL)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }

       try {
           txt_nameOfGarage.setText(mGarageDetail.getName());
           txt_phoneOfGarage.setText(mGarageDetail.getPhone().get(0));
           txt_addressOfGarage.setText(mGarageDetail.getAddress());
           txt_emailOfGarage.setText(mGarageDetail.getEmail());
           txt_service.setText(mGarageDetail.getDescriptiion());
       }catch (NullPointerException e){
           Log.d(TAG, e.toString());
       }
    }

    @Override
    public void getGaraDetailError(String msg, int status_code) {
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

    @OnClick(R.id.layout_phone)
    public void processCall(){
        //todo something
        Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                .parse("tel:" + txt_phoneOfGarage.getText().toString()));
        startActivity(callSupport);
    }

    @OnClick(R.id.btn_route)
    public void processRoute(){
        //todo something
        String uri = String.format("http://maps.google.com/maps?saddr=%s,%s(%s)&daddr=%s,%s(%s)", mLat+"", mLng+"", "Your location",
                mGarageDetail.getLat(), mGarageDetail.getLng()+"", mGarageDetail.getName()+"");
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.btn_contact)
    public void processContact(){
        //todo something
        Intent callSupport = new Intent(Intent.ACTION_CALL, Uri
                .parse("tel:" + txt_phoneOfGarage.getText().toString()));
        startActivity(callSupport);
    }
}
