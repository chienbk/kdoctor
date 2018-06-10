package thebrightcompany.com.kdoctor.view.garagedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.adapter.garagedetail.GarageTabPagerAdapter;
import thebrightcompany.com.kdoctor.model.garage.GarageOnMap;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.view.garagedetail.commentgarage.CommentOfGaraFragment;
import thebrightcompany.com.kdoctor.view.garagedetail.inforgarage.InformationOfGaraFragment;

/**
 * Created by ChienNv9 on 6/5/2018.
 */

public class ActivityGarageDetail extends AppCompatActivity implements ActionBar.TabListener{

    public static final String TAG = ActivityGarageDetail.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    private GarageTabPagerAdapter adapter;

    private int idOfGarage;
    private GarageOnMap mGarageOnMap;

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
}
