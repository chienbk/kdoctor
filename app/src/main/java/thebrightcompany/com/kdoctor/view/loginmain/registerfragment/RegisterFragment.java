package thebrightcompany.com.kdoctor.view.loginmain.registerfragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.presenter.register.RegisterPresenter;
import thebrightcompany.com.kdoctor.presenter.register.RegisterPresenterImpl;
import thebrightcompany.com.kdoctor.utils.Contains;
import thebrightcompany.com.kdoctor.view.customview.Crop;
import thebrightcompany.com.kdoctor.view.loginmain.LoginScreenActivity;
import thebrightcompany.com.kdoctor.view.loginmain.loginfragment.LoginFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterFragmentView{

    public static final String TAG = RegisterFragment.class.getSimpleName();
    private static final int RC_CAMERA = 1;
    private static final int RC_GALLERY = 2;

    private static final int SELECT_IMAGE_AVATAR = 1;
    private static final int PERMISSIONS_REQUEST_WRITE_FILE = 102;
    private int mSelectImg = 0;
    private File mFileAvatar;
    private File mFileTempImage;

    @BindView(R.id.layout_register) LinearLayout layout_register;
    @BindView(R.id.fullName)
    EditText txt_fullName;
    @BindView(R.id.email)
    EditText txt_email;
    @BindView(R.id.phone)
    EditText txt_phone;
    @BindView(R.id.password)
    EditText txt_password;
    @BindView(R.id.re_password)
    EditText txt_re_password;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;

    private String fullName, email, phone, password, rePassword;

    private LoginScreenActivity mActivity;

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private RegisterPresenter presenter;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //todo something
        presenter = new RegisterPresenterImpl(this);
        fragmentManager = getActivity().getSupportFragmentManager();
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
    public void onEmailError(String msg) {
        layout_register.startAnimation(shakeAnimation);
        txt_email.setError(msg);
        txt_email.requestFocus();

    }

    @Override
    public void onPasswordError(String msg) {
        layout_register.startAnimation(shakeAnimation);
        txt_password.setError(msg);
        txt_password.requestFocus();
    }

    @Override
    public void onRePasswordError(String msg) {
        layout_register.startAnimation(shakeAnimation);
        txt_re_password.setError(msg);
        txt_re_password.requestFocus();
    }

    @Override
    public void onPhoneError(String msg) {
        layout_register.startAnimation(shakeAnimation);
        txt_phone.setError(msg);
        txt_phone.requestFocus();
    }

    @Override
    public void onRegisterError(String msg) {
        //layout_register.startAnimation(shakeAnimation);
        showMessage(msg);
    }

    @Override
    public void onRegisterSuccess(String msg) {
        // Replace signup frgament with animation
        showMessage(msg);
        replaceFragment(new LoginFragment());
    }

    @Override
    public void onAvatarError(String msg) {
        showMessage(msg);
    }

    @OnClick(R.id.btn_back)
    public void processBackLoginScreen(){
        //todo something
        // Replace signup frgament with animation
        replaceFragment(new LoginFragment());
    }

    @OnClick(R.id.btn_register)
    public void processRegister(){
        //todo something
        showMessage("Process register");
        fullName = txt_fullName.getText().toString();
        email = txt_email.getText().toString();
        phone = txt_phone.getText().toString();
        password = txt_password.getText().toString();
        rePassword = txt_re_password.getText().toString();
        File file = null;

        presenter.processRegister(fullName, email, phone, password, rePassword, file);
    }

    @OnClick(R.id.layoutProfile)
    public void choiceAvatar(){
        if (isStoragePermissionGranted())
            //todo something
            showSelection(SELECT_IMAGE_AVATAR);
    }

    /**
     * The method use to check permission
     * @return
     */
    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_FILE);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_WRITE_FILE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                isStoragePermissionGranted();
            } else {
                showMessage("Sorry!!! Permission Denied");
            }
        }
    }

    /**
     * The method use to select avatar
     * @param selectImageAvatar
     */
    private void showSelection(int selectImageAvatar) {
        mSelectImg = selectImageAvatar;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Thêm ảnh ");
        final CharSequence[] items = {"Máy ảnh", "Thư viện", "Hủy bỏ"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Máy ảnh")) {
                    selectCamera();
                } else if (items[item].equals("Thư viện")) {
                    selectGallery();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * The method use to select gallery
     */
    private void selectGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), RC_GALLERY);
    }

    /**
     * The method use to select camera
     *
     */
    private void selectCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        createTempImage();
        Uri uri = Uri.fromFile(mFileTempImage);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, RC_CAMERA);
    }

    /**
     * The method use to create temp file
     *
     */
    private void createTempImage() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, Contains.PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(file, "tempFileImage.jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mFileTempImage = file;
    }

    /**
     * The method use replace fragment
     *
     * @param fragment
     */
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

    @Override
    public void onCommonError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            int height = 300;
            int width = 460;
            if (requestCode == RC_CAMERA) {
                if (mFileTempImage == null) {
                    createTempImage();

                }
                Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
                if (mSelectImg == SELECT_IMAGE_AVATAR) {
                    Crop.of(Uri.fromFile(mFileTempImage), destination).withAspect(13, 13).withMaxSize(300, 300).start(getActivity(), this);
                } else {
                    Crop.of(Uri.fromFile(mFileTempImage), destination).withAspect(13, 9).withMaxSize(width, height).start(getActivity(), this);
                }
            } else if (requestCode == RC_GALLERY) {
                Uri selectedImageUri = data.getData();
                Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
                if (mSelectImg == SELECT_IMAGE_AVATAR) {
                    Crop.of(selectedImageUri, destination).withAspect(13, 13).withMaxSize(300, 300).start(getActivity(), this);
                } else {
                    Crop.of(selectedImageUri, destination).withAspect(13, 9).withMaxSize(width, height).start(getActivity(), this);
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(Crop.getOutput(data).getPath());
                switch (mSelectImg) {
                    case SELECT_IMAGE_AVATAR:
                        mFileAvatar = saveBitmapToFile(imageBitmap, "image_avatar");
                        //Picasso.with(getActivity()).load(mFileAvatar).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_CACHE).into(img_avatar);
                        break;
                }
            }
        }
    }

    /**
     * The method use to save bitmap to file
     *
     * @param bitmap
     * @param name
     * @return
     */
    private File saveBitmapToFile(Bitmap bitmap, String name) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "VantaiDriver/");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(file, "/" + name + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            file = null;
            e.printStackTrace();
        }
        return file;
    }
}
