package thebrightcompany.com.kdoctor.view.loginmain.loginfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;
import thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment.ForgotPasswordFragment;
import thebrightcompany.com.kdoctor.view.loginmain.registerfragment.RegisterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginFragmentView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static final String TAG = LoginFragment.class.getSimpleName();

    private LoginScreenActivity mActivity;
    private static final int RC_SIGN_IN = 007;

    @BindView(R.id.sign_in_button) SignInButton btnSignIn;

    @BindView(R.id.email) EditText txt_email;

    @BindView(R.id.login_button) LoginButton btnLoginFacebook;

    @BindView(R.id.txt_forGotPassWord) TextView txt_forGotPassword;

    @BindView(R.id.layout_login) LinearLayout layout_login;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCallbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);
        initView(view);

        try {
            initGoogleSignIn();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }

        try {
            initFaceBookSignIn();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }

        return view;
    }

    /**
     * The method use to login with facebook
     */
    private void initFaceBookSignIn() {
        btnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG, "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "Login canceled.");

            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG, "Login failed.");
            }
        });
    }

    /**
     * The method use to login with google
     */
    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    /**
     *
     * @param view
     */
    private void initView(View view) {
        //todo something
        fragmentManager = getActivity().getSupportFragmentManager();

        SpannableString content = new SpannableString(getString(R.string.lb_for_got_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_forGotPassword.setText(content);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (LoginScreenActivity) context;
    }

    @Override
    public void onLoginError(String msg) {

        layout_login.startAnimation(shakeAnimation);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onEmailError(String msg) {

        layout_login.startAnimation(shakeAnimation);
    }

    @Override
    public void onPasswordError(String msg) {

        layout_login.startAnimation(shakeAnimation);
    }

    @Override
    public void showProgress() {
        mActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        mActivity.hideProgress();
    }

    @Override
    public void showMessage(String message) {
        mActivity.showMessage(message);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @OnClick(R.id.layout_forgotPassword)
    public void processForgotPassword(){
        //todo something
        // Replace forgot password fragment with animation
       /* fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.right_enter, R.animator.left_out)
                .replace(R.id.frameContainer,
                        new ForgotPasswordFragment(),
                        Utils.ForgotPassword_Fragment).commit();*/

       replaceFragment(new ForgotPasswordFragment());
    }

    @OnClick(R.id.btn_login)
    public void processLogin(){
        //todo something
        showMessage("Process login!");
    }

    @OnClick(R.id.layout_create_new_account)
    public void processCreateNewAccount(){
        //todo something
        // Replace signup frgament with animation
       /* fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.right_enter, R.animator.left_out)
                .replace(R.id.frameContainer, new RegisterFragment(),
                        Utils.SignUp_Fragment).commit();*/
        replaceFragment(new RegisterFragment());
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.right_enter, R.animator.left_out);
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @OnClick(R.id.sign_in_button)
    public void loginWithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            //String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            txt_email.setText(email);
            txt_email.setSelection(email.length());
            String token = result.getSignInAccount().getServerAuthCode();
            Log.d(TAG, "Token: " + token);

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", token: " + token);
        } else {
            //todo something
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr;
        try {
            opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                showProgress();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        hideProgress();
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
    }
}
