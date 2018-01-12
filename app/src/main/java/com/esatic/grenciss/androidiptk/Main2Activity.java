package com.esatic.grenciss.androidiptk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esatic.grenciss.androidiptk.classes.IPv4;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setElevation(0);

        Bundle extras = getIntent().getExtras();

        TextView tvIPCIDR = (TextView)findViewById(R.id.tvIPCIDR);
        TextView tvNH = (TextView)findViewById(R.id.tvNH);
        TextView tvNA = (TextView)findViewById(R.id.tvNA);
        TextView tvNM = (TextView)findViewById(R.id.tvNM);
        TextView tvBC = (TextView)findViewById(R.id.tvBC);
        TextView tvRG = (TextView)findViewById(R.id.tvRG);
        Button btnV = (Button)findViewById(R.id.btnVLSM);

        btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Main2Activity.this,VlsmEntry.class);
                startActivity(I);
            }
        });

        IPv4 iPv4 = new IPv4(String.valueOf(extras.get("ip")));

        tvIPCIDR.setText(String.valueOf(extras.get("ip")));
        tvNH.setText(String.valueOf(iPv4.getNumberOfHosts()));
        tvNA.setText(iPv4.getCIDR());
        tvNM.setText(iPv4.getNetmask());
        tvBC.setText(iPv4.getBroadcastAddress());
        tvRG.setText(iPv4.getHostAddressRange());
    }
}
