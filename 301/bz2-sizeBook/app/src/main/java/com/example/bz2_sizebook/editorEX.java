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

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * This is class is second editor for user to edit the old record  <br>
 *    <pre> in this class, the difference with the editor_activity is it can read the data from the file
 *     and place on the editText for user to make change, and it can delete the record.
 *     the reords in a file,
 *</pre>
 *
 * @author bz2
 */
public class editorEX extends AppCompatActivity {
    Button delete;
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


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_ex);

        delete = (Button) findViewById(R.id.button5);
        save = (Button) findViewById(R.id.button4);
        nameText = (EditText) findViewById(R.id.editText2);
        dateText = (EditText) findViewById(R.id.editText3);
        numNeck = (EditText) findViewById(R.id.editText4);
        numBust = (EditText) findViewById(R.id.editText5);
        numChest = (EditText) findViewById(R.id.editText6);
        numWaist = (EditText) findViewById(R.id.editText7);
        numHip = (EditText) findViewById(R.id.editText16);
        numInseam = (EditText) findViewById(R.id.editText17);
        CommentText = (EditText) findViewById(R.id.editText18);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final Intent intent1 = new Intent(this.getApplicationContext(),
                MainActivity.class);

        getDate();
        System.out.println(id);


        final Records records = MainActivity.record.get(Integer.valueOf(id));
        System.out.println(records.getName());

        nameText.setText(records.getName());
        dateText.setText(records.getDate());


        //GET THE INFORMATION FROM THE SAVED FILE, CHECK FOR NULL VALUE,
        //THEN SHOW THE DATA ON SRCEEN FOR USER TO EDITING
        String neck = records.getNeck() == null ?
                "" : records.getNeck().toString();
        String bust = records.getBust() == null ?
                "" : records.getBust().toString();
        String chest = records.getChest() == null ?
                "" : records.getChest().toString();
        String waist = records.getWaist() == null ?
                "" : records.getWaist().toString();
        String hip = records.getHip() == null ?
                "" : records.getHip().toString();
        String inseam = records.getInseam() == null?
                "" : records.getInseam().toString();

        numNeck.setText(neck);
        numBust.setText(bust);
        numChest.setText(chest);
        numWaist.setText(waist);
        numHip.setText(hip);
        numInseam.setText(inseam);
        CommentText.setText(records.getComment());


        //SAVE THE RECORD
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Records eiditRecords = LoadRecords();
                MainActivity.record.set(Integer.valueOf(id), eiditRecords);
                saveInFile();
                startActivity(intent1);
                finish();

            }
        });

        //DELETE
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainActivity.record.remove(MainActivity.record.indexOf(records));
                saveInFile();
                startActivity(intent1);
                finish();
            }

        });

    }

    /**
     * this funtion will take the data from user enter and then load to a Records
     * for each file, get the text and check is null or not: if null-> save the null else -> save the text(toString)
     *
     * @return a Records
     */
    public Records LoadRecords() {

        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        Integer neck = numNeck.getText().toString().isEmpty()?
                null :Integer.valueOf(numNeck.getText().toString());
        Integer bust = numBust.getText().toString().isEmpty()?
                null :Integer.valueOf(numBust.getText().toString());
        Integer chest = numChest.getText().toString().isEmpty()?
                null :Integer.valueOf(numChest.getText().toString());
        Integer waist = numWaist.getText().toString().isEmpty()?
                null :Integer.valueOf(numWaist.getText().toString());
        Integer hip = numHip.getText().toString().isEmpty()?
                null :Integer.valueOf(numHip.getText().toString());
        Integer inseam = numInseam.getText().toString().isEmpty()?
                null :Integer.valueOf(numInseam.getText().toString());
        String comment = CommentText.getText().toString();

        Records r = new Records(name);
        r.setBust(bust);
        r.setChest(chest);
        r.setComment(comment);
        r.setDate(date);
        r.setHip(hip);
        r.setInseam(inseam);
        r.setNeck(neck);
        r.setWaist(waist);

        return r;

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
                System.out.println("Almost done");
                new DatePickerDialog(editorEX.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /**
     * SAVE RECORDS TO A FILE IN JSON FORMAT
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


}
