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
import com.tecdatum.iaca_tspolice.ViewData.Helpers.CCTV_helper;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.CCTV_helper;
import com.tecdatum.iaca_tspolice.ViewData.ViewMoreData.CCTVVmore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

    /**
     * Created by HI on 4/16/2018.
     */




    public class CCTVAdapter extends BaseAdapter {


        private Activity activity;
        // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
        // Declare Variables
        Context mContext, Context1;
        public static final String MY_PREFS_NAME = "MyPrefsFile";
        LayoutInflater inflater;
        private List<CCTV_helper> vehiclelist = null;
        private ArrayList<CCTV_helper> arraylist;

        public CCTVAdapter(Context context, List<CCTV_helper> vehiclelist) {
            mContext = context;
            this.vehiclelist = vehiclelist;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<CCTV_helper>();
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
        public CCTV_helper getItem(int position) {
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
                view = inflater.inflate(R.layout.view_cctv, null);
                holder.tv_acc_cno = (TextView) view.findViewById(R.id.tv_acc_cno);
                holder.tv_acc_dt = (TextView) view.findViewById(R.id.tv_acc_dt);
                holder.tv_acc_type = (TextView) view.findViewById(R.id.tv_acc_type);
                holder.tv_acc_vm = (TextView) view.findViewById(R.id.tv_acc_vm);
                holder.tv_acc_status = (TextView) view.findViewById(R.id.tv_acc_status);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_acc_cno.setText(vehiclelist.get(position).getCommunityGroup());
            holder.tv_acc_type.setText(vehiclelist.get(position).getCCTVType());
            holder.tv_acc_status.setText(vehiclelist.get(position).getCCTVWorkingStatusID());
            holder.tv_acc_dt.setText(vehiclelist.get(position).getEnteredDate());




            holder.tv_acc_vm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Intent intent=new Intent(mContext,CCTVVmore.class);

                    if ((vehiclelist.get(position).getGovernmentSectorNo()) != null) {
                        intent.putExtra("GovernmentSectorNo", (vehiclelist.get(position).getGovernmentSectorNo()));
                    } else {
                        intent.putExtra("GovernmentSectorNo","");
                    }
                    if ((vehiclelist.get(position).getLocality()) != null) {
                        intent.putExtra("Locality", (vehiclelist.get(position).getLocality()));
                    } else {
                        intent.putExtra("Locality","");
                    }
                    if ((vehiclelist.get(position).getCameraSpecificationMasterID()) != null) {
                        intent.putExtra("CameraSpecificationMasterID", (vehiclelist.get(position).getCameraSpecificationMasterID()));
                    } else {
                        intent.putExtra("CameraSpecificationMasterID","");
                    }
                    if ((vehiclelist.get(position).getCameraCapacityMasterID()) != null) {
                        intent.putExtra("CameraCapacityMasterID", (vehiclelist.get(position).getCameraCapacityMasterID()));
                    } else {
                        intent.putExtra("CameraCapacityMasterID","");
                    }
                    if ((vehiclelist.get(position).getPoleNo()) != null) {
                        intent.putExtra("PoleNo", (vehiclelist.get(position).getPoleNo()));
                    } else {
                        intent.putExtra("PoleNo","");
                    }

                    if ((vehiclelist.get(position).getPsID()) != null) {
                        intent.putExtra("PsID", (vehiclelist.get(position).getPsID()));
                    } else {
                        intent.putExtra("PsID","");
                    }
                    if ((vehiclelist.get(position).getPolicestation()) != null) {
                        intent.putExtra("Policestation", (vehiclelist.get(position).getPolicestation()));
                    } else {
                        intent.putExtra("Policestation","");
                    }
                    if ((vehiclelist.get(position).getSectorNo()) != null) {
                        intent.putExtra("SectorNo", (vehiclelist.get(position).getSectorNo()));
                    } else {
                        intent.putExtra("SectorNo","");
                    }
                    if ((vehiclelist.get(position).getCCTVWorkingStatusID()) != null) {
                        intent.putExtra("CCTVWorkingStatusID", (vehiclelist.get(position).getCCTVWorkingStatusID()));
                    } else {
                        intent.putExtra("CCTVWorkingStatusID","");
                    }
                    if ((vehiclelist.get(position).getCCTVReasonID()) != null) {
                        intent.putExtra("CCTVReasonID", (vehiclelist.get(position).getCCTVReasonID()));
                    } else {
                        intent.putExtra("CCTVReasonID","");
                    }
                    if ((vehiclelist.get(position).getCCTVVendorID()) != null) {
                        intent.putExtra("CCTVVendorID", (vehiclelist.get(position).getCCTVVendorID()));
                    } else {
                        intent.putExtra("CCTVVendorID","");
                    }

                    if ((vehiclelist.get(position).getPsConnectionID()) != null) {
                        intent.putExtra("PsConnectionID", (vehiclelist.get(position).getPsConnectionID()));
                    } else {
                        intent.putExtra("PsConnectionID","");
                    }
                    if ((vehiclelist.get(position).getIPAddress()) != null) {
                        intent.putExtra("IPAddress", (vehiclelist.get(position).getIPAddress()));
                    } else {
                        intent.putExtra("IPAddress","");
                    }
                    if ((vehiclelist.get(position).getNVRIPAddress()) != null) {
                        intent.putExtra("NVRIPAddress", (vehiclelist.get(position).getNVRIPAddress()));
                    } else {
                        intent.putExtra("NVRIPAddress","");
                    }
                    if ((vehiclelist.get(position).getChannelNo()) != null) {
                        intent.putExtra("ChannelNo", (vehiclelist.get(position).getChannelNo()));
                    } else {
                        intent.putExtra("ChannelNo","");
                    }

                    if ((vehiclelist.get(position).getPersonName()) != null) {
                        intent.putExtra("PersonName", (vehiclelist.get(position).getPersonName()));
                    } else {
                        intent.putExtra("PersonName","");
                    }
                    if ((vehiclelist.get(position).getMobileNo()) != null) {
                        intent.putExtra("MobileNo", (vehiclelist.get(position).getMobileNo()));
                    } else {
                        intent.putExtra("MobileNo","");
                    }


                    if ((vehiclelist.get(position).getRemarks()) != null) {
                        intent.putExtra("Remarks", (vehiclelist.get(position).getRemarks()));
                    } else {
                        intent.putExtra("Remarks","");
                    }
                    if ((vehiclelist.get(position).getEnteredDate()) != null) {
                        intent.putExtra("EnteredDate", (vehiclelist.get(position).getEnteredDate()));
                    } else {
                        intent.putExtra("EnteredDate","");
                    }
                    if ((vehiclelist.get(position).getEnteredBy()) != null) {
                        intent.putExtra("EnteredBy", (vehiclelist.get(position).getEnteredBy()));
                    } else {
                        intent.putExtra("EnteredBy","");
                    }
                    if ((vehiclelist.get(position).getIsActive()) != null) {
                        intent.putExtra("IsActive", (vehiclelist.get(position).getIsActive()));
                    } else {
                        intent.putExtra("IsActive","");
                    }
                    if ((vehiclelist.get(position).getCCTVCategoryID()) != null) {
                        intent.putExtra("CCTVCategoryID", (vehiclelist.get(position).getCCTVCategoryID()));
                    } else {
                        intent.putExtra("CCTVCategoryID","");
                    }


                    if ((vehiclelist.get(position).getID()) != null) {
                        intent.putExtra("ID", (vehiclelist.get(position).getID()));
                    } else {
                        intent.putExtra("ID","");
                    }
                    if ((vehiclelist.get(position).getCommunityGroupMasterID()) != null) {
                        intent.putExtra("CommunityGroupMasterID", (vehiclelist.get(position).getCommunityGroupMasterID()));
                    } else {
                        intent.putExtra("CommunityGroupMasterID","");
                    }
                    if ((vehiclelist.get(position).getCommunityGroup()) != null) {
                        intent.putExtra("CommunityGroup", (vehiclelist.get(position).getCommunityGroup()));
                    } else {
                        intent.putExtra("CommunityGroup","");
                    }
                    if ((vehiclelist.get(position).getCCTVTypemasterID()) != null) {
                        intent.putExtra("CCTVTypemasterID", (vehiclelist.get(position).getCCTVTypemasterID()));
                    } else {
                        intent.putExtra("CCTVTypemasterID","");
                    }
                    if ((vehiclelist.get(position).getCCTVType()) != null) {
                        intent.putExtra("CCTVType", (vehiclelist.get(position).getCCTVType()));
                    } else {
                        intent.putExtra("CCTVType","");
                    }
                    if ((vehiclelist.get(position).getLandMark()) != null) {
                        intent.putExtra("LandMark", (vehiclelist.get(position).getLandMark()));
                    } else {
                        intent.putExtra("LandMark","");
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
                    if ((vehiclelist.get(position).getVname()) != null) {
                        intent.putExtra("vname", (vehiclelist.get(position).getVname()));
                    } else {
                        intent.putExtra("vname","");
                    }
                    if ((vehiclelist.get(position).getVAddress()) != null) {
                        intent.putExtra("vadd", (vehiclelist.get(position).getVAddress()));
                    } else {
                        intent.putExtra("vadd","");
                    }
                    if ((vehiclelist.get(position).getVNo()) != null) {
                        intent.putExtra("vno", (vehiclelist.get(position).getVNo()));
                    } else {
                        intent.putExtra("vno","");
                    }
                    if ((vehiclelist.get(position).getCameraFixedDate()) != null) {
                        intent.putExtra("CameraFixedDate", (vehiclelist.get(position).getCameraFixedDate()));
                    } else {
                        intent.putExtra("CameraFixedDate","");
                    }
                    if ((vehiclelist.get(position).getNoOfCamerasCoverd()) != null) {
                        intent.putExtra("NoOfCamerasCoverd", (vehiclelist.get(position).getNoOfCamerasCoverd()));
                    } else {
                        intent.putExtra("NoOfCamerasCoverd","");
                    }
                    if ((vehiclelist.get(position).getNoCameraInstalled()) != null) {
                        intent.putExtra("NoCameraInstalled", (vehiclelist.get(position).getNoCameraInstalled()));
                    } else {
                        intent.putExtra("NoCameraInstalled","");
                    }
                    if ((vehiclelist.get(position).getStorageinDays()) != null) {
                        intent.putExtra("StorageinDays", (vehiclelist.get(position).getStorageinDays()));
                    } else {
                        intent.putExtra("StorageinDays","");
                    }
                    if ((vehiclelist.get(position).getCommunityName()) != null) {
                        intent.putExtra("CommunityName", (vehiclelist.get(position).getCommunityName()));
                    } else {
                        intent.putExtra("CommunityName","");
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
                for (CCTV_helper wp : arraylist) {
                    if (wp.getCCTVType().toLowerCase(Locale.getDefault()).contains(charText)) {
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
