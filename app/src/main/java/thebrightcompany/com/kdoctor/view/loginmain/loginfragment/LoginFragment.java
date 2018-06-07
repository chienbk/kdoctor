package thebrightcompany.com.kdoctor.view.loginmain.loginfragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.api.Status;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.presenter.login.LoginPresenter;
import thebrightcompany.com.kdoctor.presenter.login.LoginPresenterImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.utils.ImageHelper;
import thebrightcompany.com.kdoctor.utils.SharedPreferencesUtils;
import thebrightcompany.com.kdoctor.utils.Utils;
import thebrightcompany.com.kdoctor.view.home.HomeActivity;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;
import thebrightcompany.com.kdoctor.view.loginmain.forgotpasswordfragment.ForgotPasswordFragment;
import thebrightcompany.com.kdoctor.view.loginmain.registerfragment.RegisterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginFragmentView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static final String TAG = LoginFragment.class.getSimpleName();

    private LoginScreenActivity mActivity;
    public static final int RC_SIGN_IN = 007;

    @BindView(R.id.sign_in_button) SignInButton btnSignIn;

    @BindView(R.id.email) EditText txt_email;

    @BindView(R.id.password) EditText txt_password;

    @BindView(R.id.login_button) LoginButton btnLoginFacebook;

    @BindView(R.id.txt_forGotPassWord) TextView txt_forGotPassword;

    @BindView(R.id.layout_login) LinearLayout layout_login;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private String fullName, email, password, device_token, third_token;
    private int type;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private LoginPresenter presenter;

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
        btnLoginFacebook.setReadPermissions("email");
        btnLoginFacebook.setFragment(this);
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
        mGoogleApiClient.connect();

        // Customizing G+ button
       /* btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());*/
    }

    /**
     *
     * @param view
     */
    private void initView(View view) {
        //todo something
        presenter = new LoginPresenterImpl(this);
        sharedPreferencesUtils = new SharedPreferencesUtils(mActivity);

        fragmentManager = getActivity().getSupportFragmentManager();

        SpannableString content = new SpannableString(getString(R.string.lb_for_got_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_forGotPassword.setText(content);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (LoginScreenActivity) context;
    }

    @Override
    public void onLoginError(String msg) {

        //layout_login.startAnimation(shakeAnimation);
        showMessage(msg);
    }

    @Override
    public void onLoginSuccess(String device_token) {
        //todo something
        if (type == 0){
            Utils.APP_TOKEN = device_token;
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_DEVICE_TOKEN, device_token);
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_USER_LOGIN, email);
            sharedPreferencesUtils.writeStringPreference(Contains.PREF_PASSWORD, password);
        }
        startActivity(new Intent(mActivity, HomeActivity.class));
        mActivity.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        email = sharedPreferencesUtils.readStringPreference(Contains.PREF_USER_LOGIN, "");
        password = sharedPreferencesUtils.readStringPreference(Contains.PREF_PASSWORD, "");

        txt_email.setText(email);
        txt_password.setText(password);
    }

    @Override
    public void onEmailError(String msg) {

        layout_login.startAnimation(shakeAnimation);
        txt_email.setError(msg);
        txt_email.requestFocus();
    }

    @Override
    public void onPasswordError(String msg) {

        layout_login.startAnimation(shakeAnimation);
        txt_password.setError(msg);
        txt_password.requestFocus();
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

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @OnClick(R.id.layout_forgotPassword)
    public void processForgotPassword(){
        //todo something
        // Replace forgot password fragment with animation
       replaceFragment(new ForgotPasswordFragment());
    }

    @OnClick(R.id.btn_login)
    public void processLogin(){
        //todo something
        //showMessage("Process login!");
        showProgress();
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();

        presenter.processLogin(email, password, 0, Utils.FCM_TOKEN, "", "");
        /*startActivity(new Intent(mActivity, HomeActivity.class));
        mActivity.finish();*/
    }

    @OnClick(R.id.layout_create_new_account)
    public void processCreateNewAccount(){
        //todo something
        // Replace signup frgament with animation
        replaceFragment(new RegisterFragment());
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.right_enter, R.animator.left_out);
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @OnClick(R.id.sign_in_button)
    public void loginWithGoogle(){
        //showMessage("Login with google");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            txt_email.setText(acct.getEmail());

            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()

           /* if (acct.getPhotoUrl() != null)
                new LoadProfileImage(imgProfilePic).execute(acct.getPhotoUrl().toString());*/

            /*Glide.with(getApplicationContext()).load(acct.getPhotoUrl())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/


            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
            //showMessage("Login with google fail!");
            Log.d(TAG, "Login with google fail");
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //showMessage("Logout success!");
                            Log.d(TAG, "Logout success");
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
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
            hideProgress();
        }

    }

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {
                Bitmap resized = Bitmap.createScaledBitmap(result,200,200, true);
                bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),resized,250,200,200, false, false, false, false));

            }
        }
    }

}
