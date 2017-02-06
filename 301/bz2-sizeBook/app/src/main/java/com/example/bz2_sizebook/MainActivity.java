package com.example.bz2_sizebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * This is class is MAIN class <br>
 *    <pre> in this class, it will count the num of records and display the name of each records
 *    load the file use json
 *    refer to Lonely tweet learn from lab
 *
 *</pre>
 *
 * @author bz2
 */
public class MainActivity extends AppCompatActivity {

    public static final String FILENAME ="file.sav";
    public static List<Records> record = new ArrayList<>();
    private TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = (TextView)findViewById(R.id.count);

        ListView list = (ListView) findViewById(R.id.ListView);
        Button button = (Button) findViewById(R.id.button);

        // add a new record, lead to the main editor class
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), editor_Activity.class);
                startActivity(intent);
                finish();
            }
        });


       loadFromFile();
        //put each records name in a list
        List<String> aList = new ArrayList<>();
        for (Records records : record) {
            aList.add("Name: " + records.getName());
        }
        //count num of record
        count.setText("TOTAL RECORDS= " + String.valueOf(record.size()));
        //Array adapter, learn from http://www.cnblogs.com/loulijun/archive/2011/12/26/2302287.html
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                aList);
        list.setAdapter(adapter);

        //edit the old records, lead to the second editor class
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), editorEX.class);
                intent.putExtra("id",String.valueOf(position));
                startActivity(intent);
                finish();
            }
        });


    }

    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2017-02-05 13:59
            record = gson.fromJson(in, new TypeToken<ArrayList<Records>>(){}.getType());
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            record = new ArrayList<Records>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
