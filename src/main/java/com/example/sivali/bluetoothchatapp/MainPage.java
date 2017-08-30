package com.example.sivali.bluetoothchatapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MainPage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayAdapter<String> arr;
    ArrayList<String> al1;
    ArrayList<String> al2;
    ArrayList<BluetoothDevice> al3;
    BluetoothAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        lv=(ListView)findViewById(R.id.listView);
        al1=new ArrayList<String>();
        al2=new ArrayList<String>();
        al3=new ArrayList<BluetoothDevice>();
        lv.setOnItemClickListener(this);
        adapter=BluetoothAdapter.getDefaultAdapter();
        getPairedDevices();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intn=new Intent(MainPage.this,ChatActivity.class);
        intn.putExtra("isClient",true);
        intn.putExtra("server",al3.get(position));
        startActivity(intn);
    }

    public void getPairedDevices()
    {
       Set<BluetoothDevice> devices=adapter.getBondedDevices();
        Iterator<BluetoothDevice> itr=devices.iterator();
        while(itr.hasNext())
        {
            BluetoothDevice bd=itr.next();
            al3.add(bd);
            al1.add(bd.getName());
            al2.add(bd.getAddress());
        }
        lv.setAdapter(new MyAdapter(MainPage.this,R.layout.cust_list));
    }

    public class MyAdapter extends ArrayAdapter
    {
        MyAdapter(Context ctx,int res)
        {
          super(ctx,res);
        }
        @Override
        public int getCount() {
            return al1.size();
        }

        @Override
        public View getView(int position, View converttvView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View v=inflater.inflate(R.layout.cust_list, parent, false);
            TextView tv1=(TextView)v.findViewById(R.id.textView);
            TextView tv2=(TextView)v.findViewById(R.id.textView2);
            tv1.setText(al1.get(position));
            tv2.setText(al2.get(position));
            return v;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.cust_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intn=new Intent(MainPage.this,ChatActivity.class);
        intn.putExtra("isClient",false);
        startActivity(intn);
        return true;
    }
}
