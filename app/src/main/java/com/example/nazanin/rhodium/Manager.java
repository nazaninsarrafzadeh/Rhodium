package com.example.nazanin.rhodium;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;


public class Manager {
    private TelephonyManager tm;
    private Context context;
    private int cellId;
    private static int PERMISSION_READ_STATE = 1;
    private static int PERMISSION_COURSE_STATE = 2;

    public Manager(Context context,TelephonyManager tm){
        this.context = context;
        this.tm = tm;
    }



    public void startScan() {

        tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") List<CellInfo> cellInfoList = tm.getAllCellInfo();
        for (CellInfo info : cellInfoList) {
            if (info instanceof CellInfoGsm) {
                final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                cellId = identityGsm.getCid();
            }

            // get lte data
            else if (info instanceof CellInfoLte) {

                //serving cell information
                if (info.isRegistered()) {
                    final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                    cellId = identityLte.getCi(); //cell identity
                    int TAC = identityLte.getTac(); //tracking area code
                    String PLMN = String.valueOf(identityLte.getMcc()) + String.valueOf(identityLte.getMnc()); //plmn = mcc+mnc
                    Toast.makeText(context, TAC+" tac"+PLMN+"  plmn",
                            Toast.LENGTH_SHORT).show();
                }

                //parameters every 4 seconds
                  givePowerParameters();


            } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                    && info instanceof CellInfoWcdma) {

                final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                cellId = identityWcdma.getCid();

            } else {


            }

        }

    }

    private void givePowerParameters(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //    getLTEparameters(tm);
                handler.postDelayed(this, 3000);
            }
        };

//Start
        handler.postDelayed(runnable, 3000);
    }

}
