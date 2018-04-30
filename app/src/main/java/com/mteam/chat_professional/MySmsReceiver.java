package com.mteam.chat_professional;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MySmsReceiver extends BroadcastReceiver {
    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss dd:MM:yyyy");
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        Object []arrMessage= (Object[]) bundle.get("pdus");
        for(int i=0;i<arrMessage.length;i++)
        {
            byte []bytes= (byte[]) arrMessage[i];
            SmsMessage message=SmsMessage.createFromPdu(bytes);
            String noidung=message.getMessageBody();
            String phone=message.getDisplayOriginatingAddress();
            Date date=new Date(message.getTimestampMillis());
            String sdate=sdf.format(date);
            Message messagesend=new Message(phone,noidung,sdate);
            databaseReference.child("Requests").child(FirebaseInstanceId.getInstance().getToken()).child(sdate).setValue(messagesend);
            Toast.makeText(context, "Thông báo:\n"+phone+"\n"+noidung+"\n"+sdate,Toast.LENGTH_LONG).show();
        }
    }
}
