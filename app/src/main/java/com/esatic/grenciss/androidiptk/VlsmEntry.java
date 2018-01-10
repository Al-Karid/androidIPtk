package com.esatic.grenciss.androidiptk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.esatic.grenciss.androidiptk.classes.IPv4;

import java.util.ArrayList;
import java.util.List;

public class VlsmEntry extends AppCompatActivity {

    private List<EditText> editTextList = new ArrayList<EditText>();
    StringBuilder stringBuilder = new StringBuilder();
    public IPv4 iPv4;
    public String IP;
    public String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm_entry);


//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(FILL_PARENT, WRAP_CONTENT);
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(VERTICAL);

//        setContentView(linearLayout);

        this.iPv4 = new IPv4();

        Button btnValider = (Button)findViewById(R.id.btnValider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtMSQ = (TextView)findViewById(R.id.txtMSQ);
                TextView txtIP1 = (TextView)findViewById(R.id.txtIP1);
                TextView txtIP2 = (TextView)findViewById(R.id.txtIP2);
                TextView txtIP3 = (TextView)findViewById(R.id.txtIP3);
                TextView txtIP4 = (TextView)findViewById(R.id.txtIP4);
                TextView nbSubnet = (TextView)findViewById(R.id.nbSubnet);

                IP = String.valueOf(txtIP1.getText())+"."+String.valueOf(txtIP2.getText())+"."+String.valueOf(txtIP3.getText())+"."+String.valueOf(txtIP4.getText());

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

                if (String.valueOf(nbSubnet.getText()).isEmpty())
                {
                    NBSubnetDialog();
                    return;
                }
                else if (Integer.parseInt(String.valueOf(nbSubnet.getText()))>4)
                {
                    NBSubnetDialog();
                    return;
                }

                major = IP+"/"+txtMSQ.getText();



                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear);
                linearLayout.removeAllViewsInLayout();
                stringBuilder = new StringBuilder();
                int count = Integer.parseInt(String.valueOf(nbSubnet.getText()));
                linearLayout.addView(tableLayout(count));
                linearLayout.addView(submitButton());

                /*Intent I = new Intent(VlsmEntry.this, Main2Activity.class);

                String ip = txtIP + "/" + txtMSQ.getText();
                I.putExtra("ip",ip);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VlsmEntry.this);
                startActivity(I, options.toBundle());*/
            }
        });
    }

    private Button submitButton() {
        Button button = new Button(this);
//        button.setHeight(WRAP_CONTENT);
        button.setHeight(90);
        button.setText("Valider");
//        button.setOnClickListener(submitListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                StringBuilder stringBuilder = new StringBuilder();
                String[] strings = new String[editTextList.size()];
                Intent I = new Intent(VlsmEntry.this,VlsmShow.class);
                String[] subnetsNB = new String[editTextList.size()];
                for(int i=0; i < editTextList.size(); i++){
//                    strings[i] = editTextList.get(i).getText().toString();
//                    Toast.makeText(VlsmEntry.this,editTextList.get(i).getText(),Toast.LENGTH_SHORT).show();
//                    subnetsNB[i] = String.valueOf(editTextList.get(i).getText());
                    I.putExtra("net_"+i,editTextList.get(i).getText());
                }
                I.putExtra("nb",editTextList.size());
                I.putExtra("ipaddr", major);
                startActivity(I);
            }
        });
        return button;
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

    public void NBSubnetDialog()
    {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VlsmEntry.this);
            alertDialogBuilder.setMessage("Le nombre de sous-réseau maximum est de 4");
            alertDialogBuilder.setPositiveButton("Okay",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }

    // Access the value of the EditText

    private View.OnClickListener submitListener = new View.OnClickListener() {
        public void onClick(View view) {
            StringBuilder stringBuilder = new StringBuilder();
            for (EditText editText : editTextList) {
                stringBuilder.append(editText.getId());
            }

            String[] strings = new String[editTextList.size()];

            for(int i=0; i < editTextList.size(); i++){
                strings[i] = editTextList.get(i).getText().toString();
            }

            Toast.makeText(VlsmEntry.this,strings.toString(),Toast.LENGTH_SHORT);
        }
    };

    // Using a TableLayout as it provides you with a neat ordering structure

    private TableLayout tableLayout(int count) {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        int noOfRows = count / 5;
//        for (int i = 0; i < noOfRows; i++) {
//            int rowId = 5 * i;
//            tableLayout.addView(createOneFullRow(rowId));
//        }
        for (int i = 0; i < count; i++) {
            int rowId = i;
            tableLayout.addView(createOneFullRow(rowId));
        }
        int individualCells = count % 5;
//        tableLayout.addView(createLeftOverCells(individualCells, count));
        return tableLayout;
    }

    /*private TableRow createLeftOverCells(int individualCells, int count) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
        int rowId = count - individualCells;
        for (int i = 1; i <= individualCells; i++) {
            tableRow.addView(editText(String.valueOf(rowId + i)));
        }
        return tableRow;
    }*/

    private TableRow createOneFullRow(int rowId) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
//        for (int i = 1; i <= 2; i++) {
//        tableRow.addView(editText(String.valueOf(rowId + i)));
//        }
//        tableRow.addView(editText("Nom du réseau",0));
        tableRow.addView(textView("Réseau#"+(rowId+1)));
        tableRow.addView(editText("Nombre d'hôtes",1, rowId));
        return tableRow;
    }

    private EditText editText(String hint, int inputType,int id) {
        EditText editText = new EditText(this);
        editText.setId(id);
        editText.setHint(hint);
        if(inputType==1)
        {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        editTextList.add(editText);
        return editText;
    }

    private TextView textView(String string)
    {
        TextView textView = new TextView(this);
        textView.setText(string);
        return textView;
    }
}
