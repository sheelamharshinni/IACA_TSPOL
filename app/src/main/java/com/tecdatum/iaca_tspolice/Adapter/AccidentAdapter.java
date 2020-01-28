package com.tecdatum.iaca_tspolice.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.tecdatum.iaca_tspolice.Offline.Accident_Offline_Actvities.Accident_OfflineActvity;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.Helper.helper_acci;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by HI on 9/19/2017.
 */


public class AccidentAdapter extends BaseAdapter {

    private Activity activity;
    Context mContext,Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Dialog C_dialog;
    LayoutInflater inflater;
    private List<helper_acci> vehiclelist = null;
    private ArrayList<helper_acci> arraylist;

    public AccidentAdapter(Context context, List < helper_acci > vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<helper_acci>();
        this.arraylist.addAll(vehiclelist);
    }
    public class ViewHolder {
        ImageView iv;
        TextView qv_vid;
        TextView qv_vname;
        TextView qv_vtype;
        TextView tv_C_psname,tv_C_hpName,tv_C_hpType,tv_C_lng,tv_C_locality,tv_C_lat,tv_C_date;;
    }
    @Override
    public int getCount() {
        return vehiclelist.size();
    }
    @Override
    public helper_acci getItem(int position) {
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
            view = inflater.inflate(R.layout.acc_offline, null);
            holder.tv_C_psname = (TextView) view.findViewById(R.id.tv_C_psname);
            holder.tv_C_hpName = (TextView) view.findViewById(R.id.tv_C_hpName);
            holder.tv_C_lat = (TextView) view.findViewById(R.id.tv_C_lat);
            holder.tv_C_lng=(TextView) view.findViewById(R.id.tv_C_lng);
            holder.tv_C_locality = (TextView) view.findViewById(R.id.tv_C_locality);
            holder.tv_C_date=(TextView) view.findViewById(R.id.tv_C_date);
            holder.tv_C_hpType=(TextView) view.findViewById(R.id.tv_C_hpType);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_C_hpType.setText(vehiclelist.get(position).getAccidentType());
        holder.tv_C_hpName.setText(vehiclelist.get(position).getCrimeNumber());
        holder.tv_C_locality.setText(vehiclelist.get(position).getLocation());
        holder.tv_C_lat.setText(vehiclelist.get(position).getLatitude());
        holder.tv_C_lng.setText(vehiclelist.get(position).getLongitude());
        holder.tv_C_date.setText(vehiclelist.get(position).getDateTime());
        holder.tv_C_psname.setText(vehiclelist.get(position).getLocality());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,Accident_OfflineActvity.class);
                intent.putExtra("id", (vehiclelist.get(position).getId()));
                intent.putExtra("crimeNumber", (vehiclelist.get(position).getCrimeNumber()));
                intent.putExtra("AccidentType", (vehiclelist.get(position).getAccidentType()));
                intent.putExtra("dateTime", (vehiclelist.get(position).getDateTime()));
                intent.putExtra("location", (vehiclelist.get(position).getLocation()));
                intent.putExtra("RoadTypeId", (vehiclelist.get(position).getRoadTypeId()));
                intent.putExtra("RoadNumber", (vehiclelist.get(position).getRoadNumber()));
                intent.putExtra("detectedStatus", (vehiclelist.get(position).getDetectedStatus()));
                intent.putExtra("latitude", (vehiclelist.get(position).getLatitude()));
                intent.putExtra("longitude", (vehiclelist.get(position).getLongitude()));
                intent.putExtra("description", (vehiclelist.get(position).getDescription()));
                intent.putExtra("NoOfInjuries", (vehiclelist.get(position).getNoOfInjuries()));
                intent.putExtra("NoOfDeaths", (vehiclelist.get(position).getNoOfDeaths()));
                intent.putExtra("VictimcategorieId", (vehiclelist.get(position).getVictimcategorieId()));
                intent.putExtra("VictimStatus", (vehiclelist.get(position).getVictimStatus()));
                intent.putExtra("VictimAlcoholicPercentage", (vehiclelist.get(position).getVictimAlcoholicPercentage()));
                intent.putExtra("AccusedCategorieId", (vehiclelist.get(position).getAccusedCategorieId()));
                intent.putExtra("AccusedStatus", (vehiclelist.get(position).getAccusedStatus()));
                intent.putExtra("AccusedAlcoholicPercentage", (vehiclelist.get(position).getAccusedAlcoholicPercentage()));
                intent.putExtra("AccidentType1", (vehiclelist.get(position).getAccidentType1()));
                intent.putExtra("RoadType", (vehiclelist.get(position).getRoadType()));
                intent.putExtra("Victimcategorie", (vehiclelist.get(position).getVictimcategorie()));
                intent.putExtra("AccusedCategorie", (vehiclelist.get(position).getAccusedCategorie()));
                intent.putExtra("VictimStatusName", (vehiclelist.get(position).getVictimStatusName()));
                intent.putExtra("AccusedStatusName", (vehiclelist.get(position).getAccusedStatusName()));
                intent.putExtra("Locality", (vehiclelist.get(position).getLocality()));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

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

            try {
                for (helper_acci wp : arraylist) {
                if (wp.getCrimeNumber().toLowerCase(Locale.getDefault()).contains(charText)) {
                    vehiclelist.add(wp);
                }
//                    if (wp.getHp_name().toLowerCase(Locale.getDefault()).contains(charText)) {
//                        vehiclelist.add(wp);
//                    }
//                if (wp.getHptype().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    vehiclelist.add(wp);
//                }


                }
            }catch (Exception e){e.printStackTrace();}
        }
        notifyDataSetChanged();
    }

}