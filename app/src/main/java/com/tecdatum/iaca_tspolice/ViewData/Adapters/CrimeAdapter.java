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
 * Created by HI on 4/16/2018.
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
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.Crime_helper;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Crime_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.CrimeVmore;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */

public class CrimeAdapter extends BaseAdapter {

    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<Crime_helper> vehiclelist = null;
    private ArrayList<Crime_helper> arraylist;

    public CrimeAdapter(Context context, List<Crime_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Crime_helper>();
        this.arraylist.addAll(vehiclelist);
    }

    public class ViewHolder {
        TextView tv_acc_cno,tv_acc_dt,tv_acc_status,tv_acc_type,tv_acc_vm;
    }

    @Override
    public int getCount() {
        return vehiclelist.size();
    }

    @Override
    public Crime_helper getItem(int position) {
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
            view = inflater.inflate(R.layout.view_accident, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);
            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_acc_cno.setText(vehiclelist.get(position).getCrimeNumber());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getDateOfOffence());
        holder.tv_acc_type.setText(vehiclelist.get(position).getCrimeType());
        holder.tv_acc_status.setText(vehiclelist.get(position).getCrimeSubtype());

        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(mContext,CrimeVmore.class);
                if ((vehiclelist.get(position).getId()) != null) {
                    intent.putExtra("Id", (vehiclelist.get(position).getId()));
                } else {
                    intent.putExtra("Id","");
                }
                if ((vehiclelist.get(position).getCrimeNumber()) != null) {
                    intent.putExtra("CrimeNumber", (vehiclelist.get(position).getCrimeNumber()));
                } else {
                    intent.putExtra("CrimeNumber","");
                }
                if ((vehiclelist.get(position).getPSID()) != null) {
                    intent.putExtra("PSID", (vehiclelist.get(position).getPSID()));
                } else {
                    intent.putExtra("PSID","");
                }
                if ((vehiclelist.get(position).getPSCode()) != null) {
                    intent.putExtra("PSCode", (vehiclelist.get(position).getPSCode()));
                } else {
                    intent.putExtra("PSCode","");
                }
                if ((vehiclelist.get(position).getCrimeTypeMaster_Id()) != null) {
                    intent.putExtra("CrimeTypeMaster_Id", (vehiclelist.get(position).getCrimeTypeMaster_Id()));
                } else {
                    intent.putExtra("CrimeTypeMaster_Id","");
                }

                if ((vehiclelist.get(position).getCrimeType()) != null) {
                    intent.putExtra("CrimeType", (vehiclelist.get(position).getCrimeType()));
                } else {
                    intent.putExtra("CrimeType","");
                }
                if ((vehiclelist.get(position).getCrimeSubtypeMaster_Id()) != null) {
                    intent.putExtra("CrimeSubtypeMaster_Id", (vehiclelist.get(position).getCrimeSubtypeMaster_Id()));
                } else {
                    intent.putExtra("CrimeSubtypeMaster_Id","");
                }
                if ((vehiclelist.get(position).getCrimeSubtype()) != null) {
                    intent.putExtra("CrimeSubtype", (vehiclelist.get(position).getCrimeSubtype()));
                } else {
                    intent.putExtra("CrimeSubtype","");
                }
                if ((vehiclelist.get(position).getLatitude()) != null) {
                    intent.putExtra("Latitude", (vehiclelist.get(position).getLatitude()));
                } else {
                    intent.putExtra("Latitude","");
                }
                if ((vehiclelist.get(position).getLongitude()) != null) {
                    intent.putExtra("Longitude", (vehiclelist.get(position).getLongitude()));
                } else {
                    intent.putExtra("Longitude","");
                }
                if ((vehiclelist.get(position).getLocation()) != null) {
                    intent.putExtra("Location", (vehiclelist.get(position).getLocation()));
                } else {
                    intent.putExtra("Location","");
                }
                if ((vehiclelist.get(position).getDescr()) != null) {
                    intent.putExtra("Descr", (vehiclelist.get(position).getDescr()));
                } else {
                    intent.putExtra("Descr","");
                }
                if ((vehiclelist.get(position).getDateOfOffence()) != null) {
                    intent.putExtra("DateOfOffence", (vehiclelist.get(position).getDateOfOffence()));
                } else {
                    intent.putExtra("DateOfOffence","");
                }
                if ((vehiclelist.get(position).getDateOfEntry()) != null) {
                    intent.putExtra("DateOfEntry", (vehiclelist.get(position).getDateOfEntry()));
                } else {
                    intent.putExtra("DateOfEntry","");
                }
                if ((vehiclelist.get(position).getCrimeStatusMaster_Id()) != null) {
                    intent.putExtra("CrimeStatusMaster_Id", (vehiclelist.get(position).getCrimeStatusMaster_Id()));
                } else {
                    intent.putExtra("CrimeStatusMaster_Id","");
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
            for (Crime_helper wp : arraylist) {
                if (wp.getCrimeNumber().toLowerCase(Locale.getDefault()).contains(charText)) {
                    vehiclelist.add(wp);
                }
//                if (wp.getS_qv_vname().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    vehiclelist.add(wp);
//                }
////                    if (wp.getS_qv_dvr_ph_no().toLowerCase(Locale.getDefault()).contains(charText)) {
////                        vehiclelist.add(wp);
////                    }
////                    if (wp.getS_qv_vtype().toLowerCase(Locale.getDefault()).contains(charText)) {
////                        vehiclelist.add(wp);
////                    }
////                if (wp.getS_qv_dvr_name().toLowerCase(Locale.getDefault()).contains(charText)) {
////                    vehiclelist.add(wp);
////                }

            }
        }
        notifyDataSetChanged();
    }
}