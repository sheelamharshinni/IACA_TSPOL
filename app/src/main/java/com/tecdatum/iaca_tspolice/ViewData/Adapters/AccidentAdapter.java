package com.tecdatum.iaca_tspolice.ViewData.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Accident_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.AccidentVmore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */




public class AccidentAdapter extends BaseAdapter {


    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<Accident_helper> vehiclelist = null;
    private ArrayList<Accident_helper> arraylist;

    public AccidentAdapter(Context context, List<Accident_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Accident_helper>();
        this.arraylist.addAll(vehiclelist);
    }

    public class ViewHolder {
        TextView     tv_acc_cno,tv_acc_dt,tv_acc_type,tv_acc_status,tv_acc_vm;
    }

    @Override
    public int getCount() {
        return vehiclelist.size();
    }

    @Override
    public Accident_helper getItem(int position) {
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
            view = inflater.inflate(R.layout.view_accidentt, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);

            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_acc_cno.setText(vehiclelist.get(position).getCrimeNumber());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getDateOfOffence());
        holder.tv_acc_type.setText(vehiclelist.get(position).getCrimeSubtype());
        holder.tv_acc_status.setText(vehiclelist.get(position).getCrimeStatusMaster_Id());



        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,AccidentVmore.class);
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


                if ((vehiclelist.get(position).getDateofReport()) != null) {
                    intent.putExtra("DateofReport", (vehiclelist.get(position).getDateofReport()));
                } else {
                    intent.putExtra("DateofReport","");
                }
                if ((vehiclelist.get(position).getNoofInjuries()) != null) {
                    intent.putExtra("NoofInjuries", (vehiclelist.get(position).getNoofInjuries()));
                } else {
                    intent.putExtra("NoofInjuries","");
                }
                if ((vehiclelist.get(position).getNoofDeaths()) != null) {
                    intent.putExtra("NoofDeaths", (vehiclelist.get(position).getNoofDeaths()));
                } else {
                    intent.putExtra("NoofDeaths","");
                }
                if ((vehiclelist.get(position).getRoadTypeID()) != null) {
                    intent.putExtra("RoadTypeID", (vehiclelist.get(position).getRoadTypeID()));
                } else {
                    intent.putExtra("RoadTypeID","");
                }
                if ((vehiclelist.get(position).getRoadNumber()) != null) {
                    intent.putExtra("RoadNumber", (vehiclelist.get(position).getRoadNumber()));
                } else {
                    intent.putExtra("RoadNumber","");
                }
                if ((vehiclelist.get(position).getVictimCategoryID()) != null) {
                    intent.putExtra("VictimCategoryID", (vehiclelist.get(position).getVictimCategoryID()));
                } else {
                    intent.putExtra("VictimCategoryID","");
                }

                if ((vehiclelist.get(position).getVictimAlcoholicORNot()) != null) {
                    intent.putExtra("VictimAlcoholicORNot", (vehiclelist.get(position).getVictimAlcoholicORNot()));
                } else {
                    intent.putExtra("VictimAlcoholicORNot","");
                }
                if ((vehiclelist.get(position).getVictimVehicleNo()) != null) {
                    intent.putExtra("VictimVehicleNo", (vehiclelist.get(position).getVictimVehicleNo()));
                } else {
                    intent.putExtra("VictimVehicleNo","");
                }
                if ((vehiclelist.get(position).getAccusedCategoryID()) != null) {
                    intent.putExtra("AccusedCategoryID", (vehiclelist.get(position).getAccusedCategoryID()));
                } else {
                    intent.putExtra("AccusedCategoryID","");
                }
                if ((vehiclelist.get(position).getAccusedAlcoholicORNot()) != null) {
                    intent.putExtra("AccusedAlcoholicORNot", (vehiclelist.get(position).getAccusedAlcoholicORNot()));
                } else {
                    intent.putExtra("AccusedAlcoholicORNot","");
                }
                if ((vehiclelist.get(position).getAccusedVehicleNo()) != null) {
                    intent.putExtra("AccusedVehicleNo", (vehiclelist.get(position).getAccusedVehicleNo()));
                } else {
                    intent.putExtra("AccusedVehicleNo","");
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
            for (Accident_helper wp : arraylist) {
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