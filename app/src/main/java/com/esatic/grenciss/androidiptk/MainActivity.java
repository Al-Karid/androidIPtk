package com.esatic.grenciss.androidiptk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    IPv4 iPv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        this.iPv4 = new IPv4();

        Button btnValider = (Button)findViewById(R.id.btnValider);
        final TextView txtIP = (TextView)findViewById(R.id.txtIP);
        TextView txtMSQ = (TextView)findViewById(R.id.txtMSQ);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!iPv4.validateIPAddress(String.valueOf(txtIP.getText())))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("L'adresse IP est invalide");
                        alertDialogBuilder.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return;
                }

                Intent I = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(I);
            }
        });
    }
}
