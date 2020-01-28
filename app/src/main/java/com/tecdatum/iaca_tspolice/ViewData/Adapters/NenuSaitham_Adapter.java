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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by HI on 6/23/2018.
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
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.NenuSaitha_helper;
        import com.tecdatum.iaca_tspolice.ViewData.Helpers.NenuSaitha_helper;
        import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.CCTVVmore;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.NenuSaithamCCtvMore;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by HI on 4/16/2018.
 */




public class NenuSaitham_Adapter extends BaseAdapter {


    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    // Declare Variables
    Context mContext, Context1;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LayoutInflater inflater;
    private List<NenuSaitha_helper> vehiclelist = null;
    private ArrayList<NenuSaitha_helper> arraylist;

    public NenuSaitham_Adapter(Context context, List<NenuSaitha_helper> vehiclelist) {
        mContext = context;
        this.vehiclelist = vehiclelist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<NenuSaitha_helper>();
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
    public NenuSaitha_helper getItem(int position) {
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
            view = inflater.inflate(R.layout.view_nenusaithamcctv, null);
            holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
            holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
            holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
            holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);
            holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_acc_cno.setText(vehiclelist.get(position).getTypeOfEstablishment());
        holder.tv_acc_type.setText(vehiclelist.get(position).getCctvtype());
        holder.tv_acc_status.setText(vehiclelist.get(position).getNameOfEstablishment());
        holder.tv_acc_dt.setText(vehiclelist.get(position).getEnteredBy());




        holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,NenuSaithamCCtvMore.class);


                if ((vehiclelist.get(position).getLocality()) != null) {
                    intent.putExtra("Locality", (vehiclelist.get(position).getLocality()));
                } else {
                    intent.putExtra("Locality","");
                }
                if ((vehiclelist.get(position).getCCTVSpecifications()) != null) {
                    intent.putExtra("CCTVSpecifications", (vehiclelist.get(position).getCCTVSpecifications()));
                } else {
                    intent.putExtra("CCTVSpecifications","");
                }

                if ((vehiclelist.get(position).getPsID()) != null) {
                    intent.putExtra("PsID", (vehiclelist.get(position).getPsID()));
                } else {
                    intent.putExtra("PsID","");
                }
                if ((vehiclelist.get(position).getPSName()) != null) {
                    intent.putExtra("PSName", (vehiclelist.get(position).getPSName()));
                } else {
                    intent.putExtra("PSName","");
                }


                if ((vehiclelist.get(position).getCCTVReasonID()) != null) {
                    intent.putExtra("CCTVReasonID", (vehiclelist.get(position).getCCTVReasonID()));
                } else {
                    intent.putExtra("CCTVReasonID","");
                }


                if ((vehiclelist.get(position).getPsID()) != null) {
                    intent.putExtra("PsID", (vehiclelist.get(position).getPsID()));
                } else {
                    intent.putExtra("PsID","");
                }


                if ((vehiclelist.get(position).getEnteredBy()) != null) {
                    intent.putExtra("EnteredBy", (vehiclelist.get(position).getEnteredBy()));
                } else {
                    intent.putExtra("EnteredBy","");
                }



                if ((vehiclelist.get(position).getCommunityGroup()) != null) {
                    intent.putExtra("CommunityGroup", (vehiclelist.get(position).getCommunityGroup()));
                } else {
                    intent.putExtra("CommunityGroup","");
                }

                if ((vehiclelist.get(position).getCctvtype()) != null) {
                    intent.putExtra("cctvtype", (vehiclelist.get(position).getCctvtype()));
                } else {
                    intent.putExtra("cctvtype","");
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
                if ((vehiclelist.get(position).getVendorName()) != null) {
                    intent.putExtra("VendorName", (vehiclelist.get(position).getVendorName()));
                } else {
                    intent.putExtra("VendorName","");
                }
                if ((vehiclelist.get(position).getVendorAddress()) != null) {
                    intent.putExtra("VendorAddress", (vehiclelist.get(position).getVendorAddress()));
                } else {
                    intent.putExtra("VendorAddress","");
                }
                if ((vehiclelist.get(position).getVendorContactNo()) != null) {
                    intent.putExtra("VendorContactNo", (vehiclelist.get(position).getVendorContactNo()));
                } else {
                    intent.putExtra("VendorContactNo","");
                }
                if ((vehiclelist.get(position).getVendorCompany()) != null) {
                    intent.putExtra("VendorCompany", (vehiclelist.get(position).getVendorCompany()));
                } else {
                    intent.putExtra("VendorCompany","");
                }

                if ((vehiclelist.get(position).getEnteredBy()) != null) {
                    intent.putExtra("EnteredBy", (vehiclelist.get(position).getEnteredBy()));
                } else {
                    intent.putExtra("EnteredBy","");
                }
//                if ((vehiclelist.get(position).getN()) != null) {
//                    intent.putExtra("NoCameraInstalled", (vehiclelist.get(position).getNoCameraInstalled()));
//                } else {
//                    intent.putExtra("NoCameraInstalled","");
//                }
                if ((vehiclelist.get(position).getStorageprovidedinDays()) != null) {
                    intent.putExtra("StorageprovidedinDays", (vehiclelist.get(position).getStorageprovidedinDays()));
                } else {
                    intent.putExtra("StorageprovidedinDays","");
                }

                if ((vehiclelist.get(position).getPSName()) != null) {
                    intent.putExtra("PSName", (vehiclelist.get(position).getPSName()));
                } else {
                    intent.putExtra("PSName","");
                }
                if ((vehiclelist.get(position).getSectorId()) != null) {
                    intent.putExtra("SectorId", (vehiclelist.get(position).getSectorId()));
                } else {
                    intent.putExtra("SectorId","");
                }
                if ((vehiclelist.get(position).getTypeOfEstablishment()) != null) {
                    intent.putExtra("TypeOfEstablishment", (vehiclelist.get(position).getTypeOfEstablishment()));
                } else {
                    intent.putExtra("TypeOfEstablishment","");
                }
                if ((vehiclelist.get(position).getNameOfEstablishment()) != null) {
                    intent.putExtra("NameOfEstablishment", (vehiclelist.get(position).getNameOfEstablishment()));
                } else {
                    intent.putExtra("NameOfEstablishment","");
                }
                if ((vehiclelist.get(position).getContactPerson()) != null) {
                    intent.putExtra("ContactPerson", (vehiclelist.get(position).getContactPerson()));
                } else {
                    intent.putExtra("ContactPerson","");
                }
                if ((vehiclelist.get(position).getContactPersonNo()) != null) {
                    intent.putExtra("ContactPersonNo", (vehiclelist.get(position).getContactPersonNo()));
                } else {
                    intent.putExtra("ContactPersonNo","");
                }
                if ((vehiclelist.get(position).getLocation()) != null) {
                    intent.putExtra("Location", (vehiclelist.get(position).getLocation()));
                } else {
                    intent.putExtra("Location","");
                }
                if ((vehiclelist.get(position).getPatrolCarRegion()) != null) {
                    intent.putExtra("PatrolCarRegion", (vehiclelist.get(position).getPatrolCarRegion()));
                } else {
                    intent.putExtra("PatrolCarRegion","");
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
            for (NenuSaitha_helper wp : arraylist) {
                if (wp.getCctvtype().toLowerCase(Locale.getDefault()).contains(charText)) {
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
