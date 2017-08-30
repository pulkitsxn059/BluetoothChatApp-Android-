package com.example.sivali.bluetoothchatapp;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ChatActivity extends AppCompatActivity {

    android.support.v7.app.ActionBar bar;
    BluetoothAdapter adapter;
    BluetoothChatService bcs;
    ListView lv;
    EditText et;
    ArrayList<String> al;
    ArrayAdapter<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        adapter=BluetoothAdapter.getDefaultAdapter();
        lv=(ListView)findViewById(R.id.list);
        et=(EditText)findViewById(R.id.editText);
        al=new ArrayList<String>();
        arr=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,al);
        bar=getSupportActionBar();
        Intent intn=getIntent();
        Bundle bundle=intn.getExtras();
        boolean b=bundle.getBoolean("isClient");
        bar.setTitle(adapter.getName());
        lv.setAdapter(arr);
        if(b)
        {
            BluetoothDevice bd=(BluetoothDevice)bundle.get("server");
            String n=bd.getName();
            bar.setSubtitle("Connected to:"+n);
            bcs=new BluetoothChatService(true,bd,h);
        }
        else
        {
            bcs=new BluetoothChatService(false,null,h);

        }
    }
    android.os.Handler h=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                 bar.setSubtitle("Connected to:"+msg.obj.toString());
                    break;
                case 2:
                    al.add(msg.obj.toString());
                    arr.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void sendMessage(View arg)
    {
        String s=et.getText().toString();
        bcs.rwt.writeMessage(s);
        al.add("Me: " +s);
        arr.notifyDataSetChanged();
        et.setText("");
    }
}
