package com.esatic.grenciss.androidiptk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.esatic.grenciss.androidiptk.adapters.NetCardAdapter;
import com.esatic.grenciss.androidiptk.classes.Subnet;
import com.esatic.grenciss.androidiptk.classes.VLSM;
import com.esatic.grenciss.androidiptk.classes.Vlsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VlsmShow extends AppCompatActivity {

    List<Vlsm> vlsms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm_show);

        getSupportActionBar().setTitle("VLSM");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Map<String, Integer> subnets = new ArrayMap<>();

        Bundle extras = getIntent().getExtras();
        int nb = (int) extras.get("nb");

        for (int i=0;i<nb;i++)
        {
//            Object on = extras.get("net_"+i);
            subnets.put("Réseau #"+i, Integer.valueOf(String.valueOf(extras.get("net_"+i)))) ;
        }

        List<Subnet> subnets1;
//        Toast.makeText(this, String.valueOf(extras.get("ipaddr")),Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(subnets.toString()),Toast.LENGTH_LONG).show();
//        Log.d("TAG",String.valueOf(extras.get("ipaddr")));
        VLSM vlsm = new VLSM(String.valueOf(extras.get("ipaddr")),subnets);
        subnets1 = vlsm.out;

        List<Vlsm> vlsms = new ArrayList<>();

        for (Subnet subnet : subnets1) {
            Vlsm vlsm1 = new Vlsm();
            vlsm1.name = subnet.name;
            vlsm1.neededSize = String.valueOf(subnet.neededSize);
            vlsm1.allocatedSize = String.valueOf(subnet.allocatedSize);
            vlsm1.address = subnet.address;
            vlsm1.mask = subnet.decMask+" "+"("+subnet.mask+")";
            vlsm1.range = subnet.range;
            vlsm1.broadcast = subnet.broadcast;

            vlsms.add(vlsm1);
        }

        NetCardAdapter netCardAdapter = new NetCardAdapter(this,vlsms);
        recyclerView.setAdapter(netCardAdapter);
    }


}
