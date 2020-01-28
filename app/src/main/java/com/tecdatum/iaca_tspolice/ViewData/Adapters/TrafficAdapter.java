package com.tecdatum.iaca_tspolice.ViewData.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tecdatum.iaca_tspolice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by HI on 4/18/2018.
 */


        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.tecdatum.iaca_tspolice.R;
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.Traffic_helper;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Traffic_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.TrafficVmore;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */




public class TrafficAdapter extends BaseAdapter {


    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<Traffic_helper> vehiclelist = null;
    private ArrayList<Traffic_helper> arraylist;

    public TrafficAdapter(Context context, List<Traffic_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Traffic_helper>();
        this.arraylist.addAll(vehiclelist);
    }

    public class ViewHolder {
        TextView tv_acc_cno, tv_acc_dt, tv_acc_type, tv_acc_status, tv_acc_vm;
    }

    @Override
    public int getCount() {
        return vehiclelist.size();
    }

    @Override
    public Traffic_helper getItem(int position) {
        return vehiclelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.view_traffic, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);

            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_acc_status.setText(vehiclelist.get(position).getNoOfVehicles());
        holder.tv_acc_cno.setText(vehiclelist.get(position).getType());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getPS());
        holder.tv_acc_type.setText(vehiclelist.get(position).getService());

        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TrafficVmore.class);
                if ((vehiclelist.get(position).getId()) != null) {
                    intent.putExtra("Id", (vehiclelist.get(position).getId()));
                } else {
                    intent.putExtra("Id", "");
                }
                if ((vehiclelist.get(position).getPSId()) != null) {
                    intent.putExtra("PSId", (vehiclelist.get(position).getPSId()));
                } else {
                    intent.putExtra("PSId", "");
                }
                if ((vehiclelist.get(position).getPS()) != null) {
                    intent.putExtra("PS", (vehiclelist.get(position).getPS()));
                } else {
                    intent.putExtra("PS", "");
                }
                if ((vehiclelist.get(position).getType()) != null) {
                    intent.putExtra("Type", (vehiclelist.get(position).getType()));
                } else {
                    intent.putExtra("Type", "");
                }

                if ((vehiclelist.get(position).getService()) != null) {
                    intent.putExtra("Service", (vehiclelist.get(position).getService()));
                } else {
                    intent.putExtra("Service", "");
                }
                if ((vehiclelist.get(position).getLocation()) != null) {
                    intent.putExtra("Location", (vehiclelist.get(position).getLocation()));
                } else {
                    intent.putExtra("Location", "");
                }
                if ((vehiclelist.get(position).getRemarks()) != null) {
                    intent.putExtra("Remarks", (vehiclelist.get(position).getRemarks()));
                } else {
                    intent.putExtra("Remarks", "");
                }
                if ((vehiclelist.get(position).getNoOfVehicles()) != null) {
                    intent.putExtra("NoOfVehicles", (vehiclelist.get(position).getNoOfVehicles()));
                } else {
                    intent.putExtra("NoOfVehicles", "");
                }

                if ((vehiclelist.get(position).getLatitude()) != null) {
                    intent.putExtra("Latitude", (vehiclelist.get(position).getLatitude()));
                } else {
                    intent.putExtra("Latitude", "");
                }
                if ((vehiclelist.get(position).getLongitude()) != null) {
                    intent.putExtra("Longitude", (vehiclelist.get(position).getLongitude()));
                } else {
                    intent.putExtra("Longitude", "");
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        vehiclelist.clear();
        if (charText.length() == 0) {
            vehiclelist.addAll(arraylist);
        } else {
            for (Traffic_helper wp : arraylist) {
                if (wp.getType().toLowerCase(Locale.getDefault()).contains(charText)) {
                    vehiclelist.add(wp);
                }


            }
        }
        notifyDataSetChanged();
    }
}