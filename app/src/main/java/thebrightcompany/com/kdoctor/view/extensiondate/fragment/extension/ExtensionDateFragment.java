package thebrightcompany.com.kdoctor.view.extensiondate.fragment.extension;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.packages.Package;
import thebrightcompany.com.kdoctor.view.extensiondate.ExtensionDateActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtensionDateFragment extends Fragment implements ExtensionDateView{

    public static final String TAG = ExtensionDateFragment.class.getSimpleName();



    private ExtensionDateActivity activity;

    public ExtensionDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extension_date, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (ExtensionDateActivity) context;
    }

    @Override
    public void getListExtension(List<Package> packages) {

    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showProgress() {
        activity.showProgress();
    }

    @Override
    public void hideProgress() {
        activity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        activity.showMessage(message);
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.right_enter, R.animator.left_out);
            fragmentTransaction.replace(R.id.framelayout_extension, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
