package com.esatic.grenciss.androidiptk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setElevation(0);

        CardView i = (CardView) findViewById(R.id.i);
        CardView v = (CardView) findViewById(R.id.v);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Dashboard.this, MainActivity.class);
                startActivity(I);
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Dashboard.this, VlsmEntry.class);
                startActivity(I);
            }
        });
    }
}
