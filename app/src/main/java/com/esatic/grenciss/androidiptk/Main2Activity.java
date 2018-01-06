package com.esatic.grenciss.androidiptk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

        IPv4 iPv4 = new IPv4(String.valueOf(extras.get("ip")));

        tvIPCIDR.setText(iPv4.getCIDR());
        tvNH.setText(String.valueOf(iPv4.getNumberOfHosts()));
        tvNA.setText(iPv4.getCIDR());
        tvNM.setText(iPv4.getNetmask());
        tvBC.setText(iPv4.getBroadcastAddress());
        tvRG.setText(iPv4.getHostAddressRange());
    }
}
