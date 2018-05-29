package thebrightcompany.com.kdoctor.view.extensiondate;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.packages.Package;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.extensiondate.fragment.extension.ExtensionDateFragment;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

public class ExtensionDateActivity extends AppCompatActivity implements ExtensionView{

    public static final String TAG = ExtensionDateActivity.class.getSimpleName();

    @BindView(R.id.progress) ProgressBar progressBar;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_date);
        ButterKnife.bind(this);
        initView();

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new ExtensionDateFragment(),
                            Utils.Login_Fragment).commit();
        }
    }

    /**
     * The method use to init view
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitleMain("Gia hạn thiết bị");
    }

    public void setTitleMain(String title){
        setTitle(title);
    }

   /* @Override
    public void getListPackages(List<Package> packages) {
        List<Package> packageList = new ArrayList<>();

    }*/

    /**
     * The method use to get list
     */
    private void getList(){
        List<Package> packageList = new ArrayList<>();
        Package aPackage_1 = new Package(1, "Gói dịch vụ 1 tháng", "580.000 VNĐ", "Đây là gói dịch vụ một tháng");
        packageList.add(aPackage_1);
        packageList.add(aPackage_1);
        packageList.add(aPackage_1);
        packageList.add(aPackage_1);


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

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Gia hạn thiết bị");
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
    public void onCommonError(String msg) {

    }
}
