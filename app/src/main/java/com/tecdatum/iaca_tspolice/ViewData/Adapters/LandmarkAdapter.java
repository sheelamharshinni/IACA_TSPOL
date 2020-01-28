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
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.Landmark_helper;
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.Landmark_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.LandmarkVmore;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */




public class LandmarkAdapter extends BaseAdapter {


    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<Landmark_helper> vehiclelist = null;
    private ArrayList<Landmark_helper> arraylist;

    public LandmarkAdapter(Context context, List<Landmark_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Landmark_helper>();
        this.arraylist.addAll(vehiclelist);
    }

    public class ViewHolder {
        TextView tv_acc_cno,tv_acc_dt,tv_acc_type,tv_acc_status,tv_acc_vm;
    }

    @Override
    public int getCount() {
        return vehiclelist.size();
    }

    @Override
    public Landmark_helper getItem(int position) {
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
            view = inflater.inflate(R.layout.view_landmark, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);

            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder. tv_acc_status.setText(vehiclelist.get(position).getLandMarkMaster_SubType());
        holder.tv_acc_cno.setText(vehiclelist.get(position).getLandMarkMaster_Type());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getSensitivityLevelId());
        holder.tv_acc_type.setText(vehiclelist.get(position).getNameoWorshipPlace());

        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(mContext,LandmarkVmore.class);
                if ((vehiclelist.get(position).getId()) != null) {
                    intent.putExtra("Id", (vehiclelist.get(position).getId()));
                } else {
                    intent.putExtra("Id","");
                }
                if ((vehiclelist.get(position).getLandMarkMaster_Id()) != null) {
                    intent.putExtra("LandMarkMaster_Id", (vehiclelist.get(position).getLandMarkMaster_Id()));
                } else {
                    intent.putExtra("LandMarkMaster_Id","");
                }
                if ((vehiclelist.get(position).getLandMarkMaster_Type()) != null) {
                    intent.putExtra("LandMarkMaster_Type", (vehiclelist.get(position).getLandMarkMaster_Type()));
                } else {
                    intent.putExtra("LandMarkMaster_Type","");
                }
                if ((vehiclelist.get(position).getLandMarkMaster_SubType()) != null) {
                    intent.putExtra("LandMarkMaster_SubType", (vehiclelist.get(position).getLandMarkMaster_SubType()));
                } else {
                    intent.putExtra("LandMarkMaster_SubType","");
                }
                if ((vehiclelist.get(position).getNameoWorshipPlace()) != null) {
                    intent.putExtra("NameoWorshipPlace", (vehiclelist.get(position).getNameoWorshipPlace()));
                } else {
                    intent.putExtra("NameoWorshipPlace","");
                }

                if ((vehiclelist.get(position).getAddressWorshipPlace()) != null) {
                    intent.putExtra("AddressWorshipPlace", (vehiclelist.get(position).getAddressWorshipPlace()));
                } else {
                    intent.putExtra("AddressWorshipPlace","");
                }
                if ((vehiclelist.get(position).getLattitude1()) != null) {
                    intent.putExtra("Lattitude1", (vehiclelist.get(position).getLattitude1()));
                } else {
                    intent.putExtra("Lattitude1","");
                }
                if ((vehiclelist.get(position).getLongitude1()) != null) {
                    intent.putExtra("Longitude1", (vehiclelist.get(position).getLongitude1()));
                } else {
                    intent.putExtra("Longitude1","");
                }
                if ((vehiclelist.get(position).getNameoftheIncharge()) != null) {
                    intent.putExtra("NameoftheIncharge", (vehiclelist.get(position).getNameoftheIncharge()));
                } else {
                    intent.putExtra("NameoftheIncharge","");
                }
                if ((vehiclelist.get(position).getInchargeDesignation()) != null) {
                    intent.putExtra("InchargeDesignation", (vehiclelist.get(position).getInchargeDesignation()));
                } else {
                    intent.putExtra("InchargeDesignation","");
                }
                if ((vehiclelist.get(position).getContactNo()) != null) {
                    intent.putExtra("ContactNo", (vehiclelist.get(position).getContactNo()));
                } else {
                    intent.putExtra("ContactNo","");
                }
                if ((vehiclelist.get(position).getRemarks()) != null) {
                    intent.putExtra("Remarks", (vehiclelist.get(position).getRemarks()));
                } else {
                    intent.putExtra("Remarks","");
                }
                if ((vehiclelist.get(position).getPSId()) != null) {
                    intent.putExtra("PSId", (vehiclelist.get(position).getPSId()));
                } else {
                    intent.putExtra("PSId","");
                }
                if ((vehiclelist.get(position).getPoliceStation()) != null) {
                    intent.putExtra("PoliceStation", (vehiclelist.get(position).getPoliceStation()));
                } else {
                    intent.putExtra("PoliceStation","");
                }

                if ((vehiclelist.get(position).getSensitivityLevelId()) != null) {
                    intent.putExtra("sensitivityLevelId", (vehiclelist.get(position).getSensitivityLevelId()));
                } else {
                    intent.putExtra("sensitivityLevelId","");
                }
                if ((vehiclelist.get(position).getSensitivitySubLevelId()) != null) {
                    intent.putExtra("SensitivitySubLevelId", (vehiclelist.get(position).getSensitivitySubLevelId()));
                } else {
                    intent.putExtra("SensitivitySubLevelId","");
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        //  holder.population.setText(vehiclelist.get(position).getPopulation());
        // Listen for ListView Item Click
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
            for (Landmark_helper wp : arraylist) {
                if (wp.getNameoWorshipPlace().toLowerCase(Locale.getDefault()).contains(charText)) {
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