package thebrightcompany.com.kdoctor.view.garagedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.garagedetail.GarageTabPagerAdapter;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.view.garagedetail.commentgarage.CommentOfGaraFragment;
import thebrightcompany.com.kdoctor.view.garagedetail.inforgarage.InformationOfGaraFragment;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;

/**
 * Created by ChienNv9 on 6/5/2018.
 */

public class ActivityGarageDetail extends AppCompatActivity implements ActionBar.TabListener, GarageDetailView{

    public static final String TAG = ActivityGarageDetail.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    private GarageTabPagerAdapter adapter;

    private int idOfGarage;
    private GarageOnMap mGarageOnMap;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_detail);
        ButterKnife.bind(this);
        try {
            mGarageOnMap = (GarageOnMap) getIntent().getSerializableExtra(Contains.PREF_GARAGE_DETAIL);
            idOfGarage = mGarageOnMap.getId();
            Log.d(TAG, "idOfGarage: " + idOfGarage);
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
        initView();
    }

    /**
     * The method use to initView
     */
    private void initView() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mGarageOnMap != null){
            setTitle(mGarageOnMap.getName());
        }else {
            setTitle("Garage ô tô");
        }
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * The method use to setup viewpager
     */
    private void setupViewPager() {
        adapter = new GarageTabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(InformationOfGaraFragment.newInstance(idOfGarage  + ""), "Thông tin");
        adapter.addFragment(CommentOfGaraFragment.newInstance(idOfGarage + ""), "Đánh giá");
        viewPager.setAdapter(adapter);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

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
     * The method use to update token device
     *
     * @param token is token of device
     */
    public void updateToken(String token){
        //todo something
        if (sharedPreferencesUtils != null){
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_DEVICE_TOKEN, token);
        }else {
            sharedPreferencesUtils = new SharedPreferencesUtils(this);
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_DEVICE_TOKEN, token);
        }
    }

    /**
     * The method use to logout
     */
    public void logout() {
        updateToken("");
        startActivity(new Intent(ActivityGarageDetail.this, LoginScreenActivity.class));
        updateToken("");
        finish();
    }
}
