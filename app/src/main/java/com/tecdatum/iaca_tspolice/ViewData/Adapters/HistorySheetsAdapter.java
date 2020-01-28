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
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.HistorySheets_helper;
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.HistorySheets_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.HsVmore;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */




public class HistorySheetsAdapter extends BaseAdapter {


    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<HistorySheets_helper> vehiclelist = null;
    private ArrayList<HistorySheets_helper> arraylist;

    public HistorySheetsAdapter(Context context, List<HistorySheets_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<HistorySheets_helper>();
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
    public HistorySheets_helper getItem(int position) {
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
            view = inflater.inflate(R.layout.view_history, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);
            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_acc_cno.setText(vehiclelist.get(position).getNumber());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getAliasName());
        holder.tv_acc_type.setText(vehiclelist.get(position).getType());
        holder.tv_acc_status.setText(vehiclelist.get(position).getPersonName());

        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(mContext,HsVmore.class);
                if ((vehiclelist.get(position).getId()) != null) {
                    intent.putExtra("Id", (vehiclelist.get(position).getId()));
                } else {
                    intent.putExtra("Id","");
                }
                if ((vehiclelist.get(position).getNumber()) != null) {
                    intent.putExtra("Number", (vehiclelist.get(position).getNumber()));
                } else {
                    intent.putExtra("Number","");
                }
                if ((vehiclelist.get(position).getType()) != null) {
                    intent.putExtra("Type", (vehiclelist.get(position).getType()));
                } else {
                    intent.putExtra("Type","");
                }
                if ((vehiclelist.get(position).getPersonName()) != null) {
                    intent.putExtra("PersonName", (vehiclelist.get(position).getPersonName()));
                } else {
                    intent.putExtra("PersonName","");
                }
                if ((vehiclelist.get(position).getAliasName()) != null) {
                    intent.putExtra("AliasName", (vehiclelist.get(position).getAliasName()));
                } else {
                    intent.putExtra("AliasName","");
                }
                if ((vehiclelist.get(position).getFatherName()) != null) {
                    intent.putExtra("FatherName", (vehiclelist.get(position).getFatherName()));
                } else {
                    intent.putExtra("FatherName","");
                }
                if ((vehiclelist.get(position).getAge()) != null) {
                    intent.putExtra("Age", (vehiclelist.get(position).getAge()));
                } else {
                    intent.putExtra("Age","");
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
                if ((vehiclelist.get(position).getAddress()) != null) {
                    intent.putExtra("Address", (vehiclelist.get(position).getAddress()));
                } else {
                    intent.putExtra("Address","");
                }
                if ((vehiclelist.get(position).getPsid()) != null) {
                    intent.putExtra("Psid", (vehiclelist.get(position).getPsid()));
                } else {
                    intent.putExtra("Psid","");
                }
                if ((vehiclelist.get(position).getEnteryDate()) != null) {
                    intent.putExtra("EnteryDate", (vehiclelist.get(position).getEnteryDate()));
                } else {
                    intent.putExtra("EnteryDate","");
                }
                if ((vehiclelist.get(position).getCreatedBy()) != null) {
                    intent.putExtra("CreatedBy", (vehiclelist.get(position).getCreatedBy()));
                } else {
                    intent.putExtra("CreatedBy","");
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
            for (HistorySheets_helper wp : arraylist) {
                if (wp.getPersonName().toLowerCase(Locale.getDefault()).contains(charText)) {
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