package com.mteam.chat_professional;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stealer Of Souls on 4/28/2018.
 */

public class Utils {
    public static boolean checkPermisson(Context context, List<String>listPermissonRequest){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> listPermisson=new ArrayList<>();

        //android.Manifest.permission.RECORD_AUDIO
        for(String s:listPermissonRequest){
            if (ActivityCompat.checkSelfPermission(context, s) == PackageManager.PERMISSION_DENIED) {
                Log.d("", "checkPermisson: "+s.toString());

                listPermisson.add(s);
            }
        }
        if(listPermisson.size()==0){
            return true;
        }
        int index=0;
        String permisson[]=new String[listPermisson.size()];
        for(String list:listPermisson){
            permisson[index]=list;
            index++;
        }
        ActivityCompat.requestPermissions((Activity) context,permisson,100);
        return false;
    }
}
