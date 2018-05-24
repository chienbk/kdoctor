package thebrightcompany.com.kdoctor.adapter.connection;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.connection.BluetoothConnection;

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder>{

    public static final String TAG = ConnectionAdapter.class.getSimpleName();

    private Context mContext;
    private List<BluetoothConnection> mListBLE;
    private ItemOnClickListener mListener;
    private EditDeviceListener mEditDeviceListener;
    private ExtensionDateListener mExtensionDateListener;


    public ConnectionAdapter(Context mContext, List<BluetoothConnection> mListBLE, ItemOnClickListener listener,
                             EditDeviceListener editDeviceListener, ExtensionDateListener extensionDateListener) {
        this.mContext = mContext;
        this.mListBLE = mListBLE;
        this.mListener = listener;
        this.mEditDeviceListener = editDeviceListener;
        this.mExtensionDateListener = extensionDateListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_connection, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d(TAG, "Size: " + mListBLE.size());
        final BluetoothConnection device = mListBLE.get(position);

       try {
           holder.txt_nameOfDevice.setText(device.getNameOfDevice());
           holder.txt_macOfDevice.setText(device.getMacAddress());

           if (device.isExpired()){
               holder.txt_dateTime.setVisibility(View.INVISIBLE);
               holder.txt_date.setText(mContext.getText(R.string.lb_is_expired));
               holder.txt_date.setTextColor(mContext.getColor(R.color.color_expired));
           }else {
               holder.txt_date.setText(mContext.getString(R.string.lb_expire_date));
               holder.txt_dateTime.setText(device.getExpireDate());
           }

           if (device.isConnected()){
               holder.layout_connected.setVisibility(View.VISIBLE);
           }else {
               holder.layout_connected.setVisibility(View.INVISIBLE);
           }

           holder.img_menu_connection.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //todo something
                   showPopupMenu(device,view, position);
               }
           });

           if (!device.isExpired()){
               holder.layout_connection.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mListener.onItemClickListener(position, device);
                   }
               });
           }
       }catch (Exception e){
           Log.d(TAG, e.toString());
       }

    }

    @Override
    public int getItemCount() {
        return mListBLE.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameOfDevice, txt_macOfDevice, txt_dateTime, txt_date;
        public RelativeLayout layout_connected;
        public ImageButton img_menu_connection;
        public LinearLayout layout_connection;

        public MyViewHolder(View view) {
            super(view);
            txt_nameOfDevice = (TextView) view.findViewById(R.id.txt_nameOfDevice);
            txt_macOfDevice = (TextView) view.findViewById(R.id.txt_macOfDevice);
            txt_dateTime = (TextView) view.findViewById(R.id.txt_dateTime);
            txt_date = (TextView) view.findViewById(R.id.date);
            layout_connected = (RelativeLayout) view.findViewById(R.id.layout_connected);
            img_menu_connection = (ImageButton) view.findViewById(R.id.img_menu_connection);
            layout_connection = (LinearLayout) view.findViewById(R.id.layout_item_connection);
        }
    }

    @SuppressLint("RestrictedApi")
    private void showPopupMenu(final BluetoothConnection device, View v, final int position) {

        final PopupMenu popupMenu = new PopupMenu(mContext, v, Gravity.RIGHT);
            popupMenu.setGravity(Gravity.END);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_connection, popupMenu.getMenu());
            MenuPopupHelper menuHelper = new MenuPopupHelper(mContext, (MenuBuilder) popupMenu.getMenu(), v);
            menuHelper.setForceShowIcon(true);
            menuHelper.show();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_connection:
                            //todo something
                            mListener.onItemClickListener(position, device);
                            break;
                        case R.id.menu_edit_device:
                            //todo something

                            showDialog(position, device);
                            break;
                        case R.id.menu_add_date:
                            //todo something
                            mExtensionDateListener.onExtensionListener();
                            break;
                    }
                    return true;
                }

            });

    }

    /**
     * The method use to show dialog to update device
     *
     * @param position
     * @param device
     */
    private void showDialog(final int position, final BluetoothConnection device) {

        final Dialog dialog = new Dialog(mContext, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_layout_edit_device);

        final EditText edit_nameOfCar = (EditText) dialog.findViewById(R.id.edit_nameOfCar);
        EditText edit_addressOfMac = (EditText) dialog.findViewById(R.id.edit_addressOfMac);
        EditText edit_vin = (EditText) dialog.findViewById(R.id.edit_vin);

        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_update = (Button) dialog.findViewById(R.id.btn_update);

        edit_addressOfMac.setText(device.getMacAddress());
        if (device.getAddressOfVin() != null){
            edit_vin.setText(device.getAddressOfVin());
        }else {
            edit_vin.setText("");
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device.setNameOfDevice(edit_nameOfCar.getText().toString());

                //todo update
                mEditDeviceListener.onEditItemListener(position, device);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    public void notifyDataSetChanged(List<BluetoothConnection> connections) {
        if(mListBLE != null){
            mListBLE.clear();
            this.mListBLE.addAll(connections);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mListBLE.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, BluetoothConnection connection){
        mListBLE.remove(position);
        notifyItemRemoved(position);
        mListBLE.add(position, connection);
        notifyItemInserted(position);
    }
}
