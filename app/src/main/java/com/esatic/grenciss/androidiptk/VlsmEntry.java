package com.esatic.grenciss.androidiptk;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VlsmEntry extends AppCompatActivity {

    public IPv4 iPv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm_entry);

        this.iPv4 = new IPv4();

        Button btnValider = (Button)findViewById(R.id.btnValider);
        TextView txtMSQ = (TextView)findViewById(R.id.txtMSQ);
        TextView txtIP1 = (TextView)findViewById(R.id.txtIP1);
        TextView txtIP2 = (TextView)findViewById(R.id.txtIP2);
        TextView txtIP3 = (TextView)findViewById(R.id.txtIP3);
        TextView txtIP4 = (TextView)findViewById(R.id.txtIP4);

         CharSequence charSequence = txtIP1.getText();
         StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("test");
        stringBuilder.append(txtIP1.getText().toString());
         ArrayList arrayList = new ArrayList();
        arrayList.add("One");
        arrayList.add("Two");
        stringBuilder.append(arrayList);



         String p1 = String.valueOf(txtIP1.getText());
         String p2 = String.valueOf(txtIP2.getText());
        String p3 = String.valueOf(txtIP3.getText());
        String p4 = String.valueOf(txtIP4.getText());

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtMSQ = (TextView)findViewById(R.id.txtMSQ);
                TextView txtIP1 = (TextView)findViewById(R.id.txtIP1);
                TextView txtIP2 = (TextView)findViewById(R.id.txtIP2);
                TextView txtIP3 = (TextView)findViewById(R.id.txtIP3);
                TextView txtIP4 = (TextView)findViewById(R.id.txtIP4);

                String IP = String.valueOf(txtIP1.getText())+"."+String.valueOf(txtIP2.getText())+"."+String.valueOf(txtIP3.getText())+"."+String.valueOf(txtIP4.getText());

                if (!iPv4.validateIPAddress(String.valueOf(IP)))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VlsmEntry.this);
                    alertDialogBuilder.setMessage("L'adresse IP est invalide\n" + IP);
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

                if (String.valueOf(txtMSQ.getText()).isEmpty())
                {
                    MSQAlertDialog();
                    return;
                }
                else if (Integer.parseInt(String.valueOf(txtMSQ.getText()))>32)
                {
                    MSQAlertDialog();
                    return;
                }

                /*Intent I = new Intent(VlsmEntry.this, Main2Activity.class);

                String ip = txtIP + "/" + txtMSQ.getText();
                I.putExtra("ip",ip);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VlsmEntry.this);
                startActivity(I, options.toBundle());*/
            }
        });
    }

    public void MSQAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VlsmEntry.this);
        alertDialogBuilder.setMessage("Le masque est invalide");
        alertDialogBuilder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
