package com.example.bz2_sizebook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * This is class is MAIN editor for user to add a new record <br>
 *    <pre> in this class, the difference with the editorEX is it can read the data from the editText
 *     and save the data in a file use funtion saveInFile()
 *</pre>
 *
 * @author bz2
 */
public class editor_Activity extends AppCompatActivity {

    Button save;
    Calendar calendar;
    EditText nameText;
    EditText dateText;
    EditText numNeck;
    EditText numBust;
    EditText numChest;
    EditText numWaist;
    EditText numHip;
    EditText numInseam;
    EditText CommentText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_);

        save = (Button) findViewById(R.id.button3);
        nameText = (EditText) findViewById(R.id.editText);
        dateText = (EditText) findViewById(R.id.editText8);
        numNeck = (EditText) findViewById(R.id.editText9);
        numBust = (EditText) findViewById(R.id.editText10);
        numChest = (EditText) findViewById(R.id.editText11);
        numWaist = (EditText) findViewById(R.id.editText12);
        numHip = (EditText) findViewById(R.id.editText13);
        numInseam = (EditText) findViewById(R.id.editText14);
        CommentText = (EditText) findViewById(R.id.editText15);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final Intent intent1 = new Intent(this.getApplicationContext(),
                MainActivity.class);


        //get the date
        getDate();
        System.out.println(id);



        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Records records = loadRecords();
                MainActivity.record.add(records);
                //check if the name is entered
                if (records.getName().matches("")) {
                    Toast.makeText(editor_Activity.this, getString(R.string.no_name), Toast.LENGTH_SHORT).show();
                    records.setName("NEED A NAME");
                    saveInFile();
                    startActivity(intent1);
                    finish();
                }else {
                    saveInFile();
                    startActivity(intent1);
                    finish();
                }

                }
            }
        );


    }

    /**
     * this funtion will take the data from user enter and then load to a Records
     * for each file, get the text and check is null or not: if null-> save the null else -> save the text(toString)
     *
     * @return a Records
     */
    public Records loadRecords() {
        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        Integer neck = numNeck.getText().toString().isEmpty() ?
                null : Integer.valueOf(numNeck.getText().toString());
        Integer bust = numBust.getText().toString().isEmpty() ?
                null : Integer.valueOf(numBust.getText().toString());
        Integer chest = numChest.getText().toString().isEmpty() ?
                null : Integer.valueOf(numChest.getText().toString());
        Integer waist = numWaist.getText().toString().isEmpty() ?
                null : Integer.valueOf(numWaist.getText().toString());
        Integer hip = numHip.getText().toString().isEmpty() ?
                null : Integer.valueOf(numHip.getText().toString());
        Integer inseam = numInseam.getText().toString().isEmpty() ?
                null : Integer.valueOf(numInseam.getText().toString());
        String comment = CommentText.getText().toString();

        Records records = new Records(name);

        records.setBust(bust);
        records.setChest(chest);
        records.setComment(comment);
        records.setDate(date);
        records.setHip(hip);
        records.setInseam(inseam);
        records.setNeck(neck);
        records.setWaist(waist);
        return records;
    }

    /**
     * reference to the Lonely Tweet
     *  saves tweets to a specified file in JSON format
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(MainActivity.record, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    /**
     * POPUP A CALENDAR FOR USER TO ENTER DATE
     * reference;
     *https://www.tutorialspoint.com/android/android_datepicker_control.htm
     */
    public void getDate() {
        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateText.setText(sdf.format(calendar.getTime()));
            }
        };
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(editor_Activity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}