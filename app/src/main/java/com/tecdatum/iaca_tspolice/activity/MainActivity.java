package com.tecdatum.iaca_tspolice.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;



import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;


import com.tecdatum.iaca_tspolice.DataEntry.Accident_New_Changes;
import com.tecdatum.iaca_tspolice.DataEntry.AddNenuSaithamCCTV;
import com.tecdatum.iaca_tspolice.DataEntry.CCTV_Add;
import com.tecdatum.iaca_tspolice.DataEntry.Traffic.Landmark_act;
import com.tecdatum.iaca_tspolice.DataEntry.cct.OLD_fragments.CCtv_Tabs;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.List.AddAccident_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddCrime_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddHistorySheets_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddReligious_View;
import com.tecdatum.iaca_tspolice.ViewData.List.CCtv_View;
import com.tecdatum.iaca_tspolice.ViewData.List.NenuSaithamCCTV_View;
import com.tecdatum.iaca_tspolice.ViewData.List.TrafficView.Traffic_act_ViewTabs;
import com.tecdatum.iaca_tspolice.ViewData.List.Traffic_act_View;
import com.tecdatum.iaca_tspolice.activity.Settings.ContactUs;
import com.tecdatum.iaca_tspolice.activity.Settings.ResetPassword;

import com.tecdatum.iaca_tspolice.DataEntry.Accident_New;
import com.tecdatum.iaca_tspolice.DataEntry.AddCrime;
import com.tecdatum.iaca_tspolice.DataEntry.AddHistorySheets;
import com.tecdatum.iaca_tspolice.DataEntry.AddHp;
import com.tecdatum.iaca_tspolice.DataEntry.AddPCCTV;
import com.tecdatum.iaca_tspolice.DataEntry.AddReligious;
import com.tecdatum.iaca_tspolice.DataEntry.UpdateCrime_Acc;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ImageView crime, acc, roadj, rel, lmks, hsheets, cctv, iv_nenusaithamcctv,pcctv;
    String S_Uname, S_Orgid,CPControlID,S_LevelId, s_OrgName;
    TextView tv_VersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_VersionCode = (TextView) findViewById(R.id.tv_version);
        crime = (ImageView) findViewById(R.id.iv_c);
        acc = (ImageView) findViewById(R.id.iv_a);
        roadj = (ImageView) findViewById(R.id.iv_hp);
        rel = (ImageView) findViewById(R.id.iv_rl);
        lmks = (ImageView) findViewById(R.id.iv_lm);
        hsheets = (ImageView) findViewById(R.id.iv_hs);
        cctv = (ImageView) findViewById(R.id.iv_cctv);
        pcctv = (ImageView) findViewById(R.id.iv_pcctv);
        iv_nenusaithamcctv = (ImageView) findViewById(R.id.iv_nenusaithamcctv);
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        s_OrgName = bb.getString("Orgname", "");
        S_LevelId = bb.getString("LevelId", "");
        CPControlID= bb.getString("CPControlID", "");
        S_Orgid = bb.getString("Orgid", "");
        Log.e("Volley1",""+S_LevelId );
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        TextView tv_unsme = (TextView) findViewById(R.id.tv_u_name);
        tv_unsme.setText("" + S_Uname);
        iv_nenusaithamcctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {


                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getApplicationContext(), NenuSaithamCCTV_View.class);
                            startActivity(intent);

                        }
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent(getApplicationContext(), NenuSaithamCCTV_View.class);
                                startActivity(intent);

                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getApplicationContext(), NenuSaithamCCTV_View.class);
                            startActivity(intent);

                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });


        crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), AddCrime.class);
//                        startActivity(intent);
//
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), AddCrime_View.class);
//                        startActivity(intent);
//
//                    }

                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddCrime_View.class);
                                startActivity(intent);
                            }


                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), AddCrime.class);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddCrime_View.class);
                                startActivity(intent);

                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), AddCrime.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(getApplicationContext(), AddCrime_View.class);
                            startActivity(intent);

                        }

                    }

                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), AddAccident.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), AddAccident_View.class);
//                        startActivity(intent);
//                    }


                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddAccident_View.class);
                                startActivity(intent);
                            }

                      //      Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        } else {
                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), Accident_New_Changes.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddAccident_View.class);
                                startActivity(intent);
                            }

                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), Accident_New_Changes.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), AddAccident_View.class);
                            startActivity(intent);
                        }


                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        roadj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), AddHp.class);
//                        startActivity(intent);
//                    } else {
////                        Intent intent=new Intent(getApplicationContext(),AddHp_View.class);
////                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
//
//                    }

                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }

                           // Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), AddHp.class);
                                startActivity(intent);
                            } else {
//                        Intent intent=new Intent(getApplicationContext(),AddHp_View.class);
//                        startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), AddHp.class);
                            startActivity(intent);
                        } else {
//                        Intent intent=new Intent(getApplicationContext(),AddHp_View.class);
//                        startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();

                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), AddReligious.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), AddReligious_View.class);
//                        startActivity(intent);
//                    }



                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddReligious_View.class);
                                startActivity(intent);
                            }

                          // Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), AddReligious.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddReligious_View.class);
                                startActivity(intent);
                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), AddReligious.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), AddReligious_View.class);
                            startActivity(intent);
                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        lmks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), Landmark_act.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), Traffic_act_ViewTabs.class);
//                        startActivity(intent);
//                    }



                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), Traffic_act_ViewTabs.class);
                                startActivity(intent);
                            }

                         //   Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), Landmark_act.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), Traffic_act_ViewTabs.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), Landmark_act.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Traffic_act_ViewTabs.class);
                            startActivity(intent);
                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        hsheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), AddHistorySheets.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getApplicationContext(), AddHistorySheets_View.class);
//                        startActivity(intent);
//                    }




                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddHistorySheets_View.class);
                                startActivity(intent);
                            }

                        //    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), AddHistorySheets.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), AddHistorySheets_View.class);
                                startActivity(intent);
                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), AddHistorySheets.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), AddHistorySheets_View.class);
                            startActivity(intent);
                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });
        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {


                        if (S_Orgid.equalsIgnoreCase("31") ) {
                            if (S_LevelId.equalsIgnoreCase("2")) {


                                if (CPControlID.equalsIgnoreCase("1")) {
                                    startActivity(new Intent(MainActivity.this, CCTV_Add.class));
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), CCtv_View.class);
                                    startActivity(intent);
                                }


                            } else {

                                if (S_LevelId.equalsIgnoreCase("5")) {
                                    //startActivity(new Intent(MainActivity.this, CCtv_Tabs.class));
                                  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), CCtv_View.class);
                                    startActivity(intent);
                                }
                            }
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), CCTV_Add.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), CCtv_View.class);
                                startActivity(intent);
                        }


                    }



                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });

        pcctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
//                    if (S_LevelId.equalsIgnoreCase("5")) {
//                        Intent intent = new Intent(getApplicationContext(), UpdateCrime_Acc.class);
//                        startActivity(intent);
//                    } else {
////                        Intent intent=new Intent(getApplicationContext(),UpdateCrime_Acc.class);
////                        startActivity(intent);
//
//                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
//                    }




                    if (S_Orgid.equalsIgnoreCase("31") ) {
                        if (S_LevelId.equalsIgnoreCase("2")) {
                            //Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();

                            if (CPControlID.equalsIgnoreCase("1")) {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            if (S_LevelId.equalsIgnoreCase("5")) {
                                Intent intent = new Intent(getApplicationContext(), UpdateCrime_Acc.class);
                                startActivity(intent);
                            } else {
//                        Intent intent=new Intent(getApplicationContext(),UpdateCrime_Acc.class);
//                        startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {

                        if (S_LevelId.equalsIgnoreCase("5")) {
                            Intent intent = new Intent(getApplicationContext(), UpdateCrime_Acc.class);
                            startActivity(intent);
                        } else {
//                        Intent intent=new Intent(getApplicationContext(),UpdateCrime_Acc.class);
//                        startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();


        if (S_Orgid.equalsIgnoreCase("31") ) {
            if (S_LevelId.equalsIgnoreCase("2")) {
                        if (CPControlID.equalsIgnoreCase("1")) {
                            MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                            dataentry.setVisible(true);

                            MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                            viewrecords.setVisible(true);
                        } else {
                            MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                            dataentry.setVisible(false);


                            MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                            viewrecords.setVisible(true);
                        }


            }
            else {
                if (S_LevelId.equalsIgnoreCase("5")) {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                    dataentry.setVisible(true);

                    MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                    viewrecords.setVisible(true);


                } else {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                    dataentry.setVisible(false);
                    MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                    viewrecords.setVisible(true);
                }
            }
        } else {
            if (S_LevelId.equalsIgnoreCase("5")) {
                MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                dataentry.setVisible(true);

                MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                viewrecords.setVisible(true);


            } else {
                MenuItem dataentry = menuNav.findItem(R.id.nav_dataentry);
                dataentry.setVisible(false);
                MenuItem viewrecords = menuNav.findItem(R.id.nav_viewrecords);
                viewrecords.setVisible(true);
            }
        }



        try {
            tv_VersionCode.setText("Version - " + getVersionCode());


        } catch (Exception e) {
            e.printStackTrace();
        }
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent a = new Intent(getApplicationContext(),Dashboard.class);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(a);
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }
    public String getVersionCode() {
        String v = "";
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return v;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {


            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.clear();
            edit.commit();
            Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (id == R.id.nav_crime) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {

                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();

                } else {
                        Intent intent = new Intent(getApplicationContext(), AddCrime.class);
                        startActivity(intent);
                }
            } else {
                    Intent intent = new Intent(getApplicationContext(), AddCrime.class);
                    startActivity(intent);
            }


           // startActivity(new Intent(MainActivity.this, AddCrime.class));
        }
        if (id == R.id.nav_Accident) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, Accident_New_Changes.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, Accident_New_Changes.class));
            }

        }

        if (id == R.id.nav_CCTV) {
            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    startActivity(new Intent(MainActivity.this, CCTV_Add.class));
                } else {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                }
            } else {
                startActivity(new Intent(MainActivity.this, CCTV_Add.class));
            }
        }
        if (id == R.id.nav_NenuSaithamCCTV) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                    startActivity(intent);
                } else {
                        Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                        startActivity(intent);
                }
            } else {
                Intent intent = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                startActivity(intent);
            }


        //    startActivity(new Intent(MainActivity.this, AddNenuSaithamCCTV.class));
        }
        if (id == R.id.nav_pCCTV) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddPCCTV.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddPCCTV.class));
            }

        }
        if (id == R.id.nav_Landmarks) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, Landmark_act.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, Landmark_act.class));
            }

        }
        if (id == R.id.nav_hp) {
            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddHp.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddHp.class));
            }


        }

        if (id == R.id.nav_History) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddHistorySheets.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddHistorySheets.class));
            }


        }

        if (id == R.id.nav_Religious) {
            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddReligious.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddReligious.class));
            }

        }
        if (id == R.id.nav_update) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, UpdateCrime_Acc.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, UpdateCrime_Acc.class));
            }

        }

        if (id == R.id.nav_crime1) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    if (CPControlID.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddCrime_View.class);
                        startActivity(intent);
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, AddCrime_View.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddCrime_View.class));
            }

        }
        if (id == R.id.nav_Accident1) {
            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                  //  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();

                    if (CPControlID.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddAccident_View.class);
                        startActivity(intent);
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, AddAccident_View.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddAccident_View.class));
            }
        }
        if (id == R.id.nav_CCTV1) {
            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    if (CPControlID.equalsIgnoreCase("1")) {
                        startActivity(new Intent(MainActivity.this, CCtv_View.class));
                    } else {
                        Intent intent = new Intent(getApplicationContext(), CCtv_View.class);
                        startActivity(intent);
                    }
                    } else {

                    if (S_LevelId.equalsIgnoreCase("5")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), CCtv_View.class);
                        startActivity(intent);
                    }

                    }
            } else {
                startActivity(new Intent(MainActivity.this, CCtv_View.class));
            }
        }

        if (id == R.id.nav_NenuSaithamCCTV1) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {
                    if (CPControlID.equalsIgnoreCase("1")) {
                      //  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), NenuSaithamCCTV_View.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), NenuSaithamCCTV_View.class);
                        startActivity(intent);
                    }

                //    startActivity(new Intent(MainActivity.this, NenuSaithamCCTV_View.class));
                } else {
                    startActivity(new Intent(MainActivity.this, NenuSaithamCCTV_View.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, NenuSaithamCCTV_View.class));
            }


        }
        if (id == R.id.nav_Landmarks1) {

            if (S_Orgid.equalsIgnoreCase("31")) {
                if (S_LevelId.equalsIgnoreCase("2")) {

                    if (CPControlID.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Traffic_act_ViewTabs.class);
                        startActivity(intent);
                    }
                  //  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, Traffic_act_ViewTabs.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, Traffic_act_ViewTabs.class));
            }


        }


        if (id == R.id.nav_History1) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {

                    if (CPControlID.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddHistorySheets_View.class);
                        startActivity(intent);
                    }
                  //  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddHistorySheets_View.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddHistorySheets_View.class));
            }

        }

        if (id == R.id.nav_Religious1) {

            if (S_Orgid.equalsIgnoreCase("31") ) {
                if (S_LevelId.equalsIgnoreCase("2")) {

                    if (CPControlID.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddReligious_View.class);
                        startActivity(intent);
                    }

                  //  Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, AddReligious_View.class));;
                }
            } else {
                startActivity(new Intent(MainActivity.this, AddReligious_View.class));
            }
        } else if (id == R.id.nav_logout) {

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.clear();
            edit.commit();
            Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            //  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(a);
            finish();
        }
        if (id == R.id.nav_resetpassword) {
            startActivity(new Intent(MainActivity.this, ResetPassword.class));
        }

        if (id == R.id.nav_contact) {
            startActivity(new Intent(MainActivity.this, ContactUs.class));
        }

//        if (id == R.id.nav_crime1) {
//            startActivity(new Intent(MainActivity.this, ViewCrime.class));
//        }
//
//        if (id == R.id.nav_Accident1) {
//            startActivity(new Intent(MainActivity.this, ViewAccident.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setIcon(R.drawable.alert)
//                .setTitle("Closing Application")
//                .setMessage("Are you sure you want to exit from this Application?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent a = new Intent(Intent.ACTION_MAIN);
//                        a.addCategory(Intent.CATEGORY_HOME);
//                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(a);
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//
//    }


}