package com.example.nazanin.rhodium;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button startBtn;
    private static int PERMISSION_READ_STATE = 1;
    private static int PERMISSION_COURSE_STATE = 2;
    private TelephonyManager tm;
    private int cellId;
    public static final int GROUP_PERMISSION = 1;
    private ArrayList<String> permissionsNeeded = new ArrayList<>();
    private ArrayList<String> permissionsAvailable = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionsAvailable.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsAvailable.add(Manifest.permission.READ_PHONE_STATE);
        for (String permission : permissionsAvailable){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                permissionsNeeded.add(permission);
            }
        }
        //permission
        if(permissionsNeeded.size()>0){
            RequestPermission(permissionsNeeded);
        }
        startBtn = findViewById(R.id.start);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_COURSE_STATE);
            } else {

                tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                Manager manager = new Manager(this,tm);
                manager.startScan();

//                List<CellInfo> cellInfoList = tm.getAllCellInfo();
//                for (CellInfo info : cellInfoList) {
//                        if (info instanceof CellInfoGsm) {
//                            final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
//                            cellId = identityGsm.getCid();
//                        }
//
//                        // get lte data
//                        else if (info instanceof CellInfoLte) {
//
//                            //serving cell information
//                            if(info.isRegistered()) {
//                                final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
//                                cellId = identityLte.getCi(); //cell identity
//                                int TAC = identityLte.getTac(); //tracking area code
//                                String PLMN = String.valueOf(identityLte.getMcc()) + String.valueOf(identityLte.getMnc()); //plmn = mcc+mnc
//                            }
//                            //parameters every 4 seconds
//                          //  givePowerParameters();
//
//
//                        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
//                                && info instanceof CellInfoWcdma) {
//
//                            final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
//                            cellId = identityWcdma.getCid();
//
//                        } else {
//
//
//                        }

            //    }

            }
        }

    }
    public void start(View view) {
        int generation = getMyNetworkType(tm);


    }



    private int getMyNetworkType(TelephonyManager tm){
        int networkType = tm.getNetworkType();

        switch (networkType)
        {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                Toast.makeText(getApplicationContext(), "Connection Available is 2G",Toast.LENGTH_SHORT).show();
                return 2;

            }
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP: {

                Toast.makeText(getApplicationContext(), "Connection Available is 3G",
                        Toast.LENGTH_SHORT).show();
                return 3;
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {

                Toast.makeText(getApplicationContext(), "Connection Available is 4G",
                        Toast.LENGTH_SHORT).show();
                return 4;
            }

        }
        return 0;
    }

    private void RequestPermission(ArrayList<String> permissions){
        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);
        ActivityCompat.requestPermissions(this,permissionList,GROUP_PERMISSION);
    }


}
