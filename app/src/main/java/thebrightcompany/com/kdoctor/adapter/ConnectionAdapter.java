package thebrightcompany.com.kdoctor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.kdoctor.R;
import thebrightcompany.com.kdoctor.model.connection.BluetoothConnection;

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder>{

    private Context mContext;
    private List<BluetoothConnection> mListBLE;


    public ConnectionAdapter(Context mContext, List<BluetoothConnection> mListBLE) {
        this.mContext = mContext;
        this.mListBLE = mListBLE;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ble_connection, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BluetoothConnection device = mListBLE.get(position);
        holder.txt_nameOfDevice.setText(device.getNameOfDevice());
        holder.txt_macOfDevice.setText(device.getMacAddress());
        if (device.isExpired()){
            holder.txt_dateTime.setVisibility(View.INVISIBLE);
            holder.txt_date.setText(mContext.getText(R.string.lb_is_expired));
            holder.txt_date.setTextColor(mContext.getColor(R.color.color_expired));
            holder.layout_connected.setVisibility(View.VISIBLE);
        }else {
            holder.txt_date.setText(mContext.getString(R.string.lb_expire_date));
            holder.txt_dateTime.setText(device.getExpireDate());
            holder.layout_connected.setVisibility(View.INVISIBLE);
        }

        holder.img_menu_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo something
                showPopupMenu(device,view, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameOfDevice, txt_macOfDevice, txt_dateTime, txt_date;
        public RelativeLayout layout_connected;
        public ImageButton img_menu_connection;

        public MyViewHolder(View view) {
            super(view);
            txt_nameOfDevice = (TextView) view.findViewById(R.id.txt_nameOfDevice);
            txt_macOfDevice = (TextView) view.findViewById(R.id.txt_macOfDevice);
            txt_dateTime = (TextView) view.findViewById(R.id.txt_dateTime);
            txt_date = (TextView) view.findViewById(R.id.date);
            layout_connected = (RelativeLayout) view.findViewById(R.id.layout_connected);
            img_menu_connection = (ImageButton) view.findViewById(R.id.img_menu_connection);
        }
    }

    @SuppressLint("RestrictedApi")
    private void showPopupMenu(final BluetoothConnection device, View v, final int position) {

        final PopupMenu popupMenu = new PopupMenu(mContext, v);

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
                            break;
                        case R.id.menu_edit_device:
                            //todo something
                            /*final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle(AppContrains.USER.getName());
                            builder.setMessage("Are you sure you want delete : " + "<" + todo.getTitle() + ">");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mListener.onDeleteToDo(todo, position);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.create().show();*/
                            break;
                        case R.id.menu_add_date:
                            //todo something
                            break;
                    }
                    return true;
                }

            });

    }
}
