package com.ebookfrenzy.mylist;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    String Hexcode;
    StringBuilder text = new StringBuilder();
    String filename = "color.txt";
    File myFile = new File(Environment.getExternalStorageDirectory(), filename);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BufferedReader br = new BufferedReader(new FileReader(myFile));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            final RelativeLayout RL = (RelativeLayout) findViewById(R.id.jason);
            RL.setBackgroundColor(Color.parseColor(text.toString()));
            Toast.makeText(getApplicationContext(), "Successfully read color from SD Card", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        String[] colourNames;
        colourNames = getResources().getStringArray(R.array.listArray);
        final ListView lv = (ListView) findViewById(R.id.listView);
        final RelativeLayout RL = (RelativeLayout) findViewById(R.id.jason);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colourNames);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Hexcode = "#" + getResources().getStringArray(R.array.listValues)[position].substring(2);
                RL.setBackgroundColor(Color.parseColor(Hexcode));
            }
        });
        registerForContextMenu(lv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Write Colour to SD Card");
        menu.add(0, v.getId(), 0, "Read Colour from SD Card");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String filename = "color.txt";
        File myFile = new File(Environment.getExternalStorageDirectory(), filename);
        if (item.getTitle() == "Write Colour to SD Card") {
        try {
                if (!myFile.exists()) myFile.createNewFile();
                try {
                    FileWriter fw = new FileWriter(myFile);
                    String[] colourValues = getResources().getStringArray(R.array.listValues);
                    fw.write(Hexcode);
                    fw.close();
                    Toast.makeText(getApplicationContext(), "Successfully written color to SD Card" , Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else if (item.getTitle() == "Read Colour from SD Card") {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(myFile));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close();
                final RelativeLayout RL = (RelativeLayout) findViewById(R.id.jason);
                RL.setBackgroundColor(Color.parseColor(text.toString()));
                Toast.makeText(getApplicationContext(), "Successfully read color from SD Card", Toast.LENGTH_LONG).show();
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}