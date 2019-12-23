package com.example.android.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    EditText nameEditText;
    EditText desEditText;
    SetsDbHelper setDatabase;
    ListView list;
    SetsAdapter setsAdapter;
    ArrayList<Sets> sets = new ArrayList<Sets>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDatabase = new SetsDbHelper(this);

        //Set up fab
        fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });

        //Loads db into sets
        DisplaySets();

        setsAdapter = new SetsAdapter(this, sets);
        list = findViewById(R.id.list);
        list.setAdapter(setsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditPage.class);
                Sets tempSet = (Sets) parent.getItemAtPosition(position);
                intent.putExtra("set id", tempSet.getId());
                startActivity(intent);
            }
        });

    }

    //Displays dialog and saves name and description into String[]
    //Called by fab
    public void displayDialog(){

        //initialize dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, (ViewGroup)null);
        nameEditText = view.findViewById(R.id.nameEditText);
        desEditText = view.findViewById(R.id.desEditText);

        //Dialog UI
        builder.setView(view);
        builder.setTitle("New Flashcard");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                String des = desEditText.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a set name", Toast.LENGTH_SHORT).show();
                } else {
                    AddSets(name, des);
                }
            }
        });
        builder.show();
    }

    //insert set information into database and into the set
    //updates adapter when called
    public void AddSets(String name, String des){
        setDatabase.insertData(name,des);

        //Adds newest id to sets
        String tempId = "";
        SQLiteDatabase db = setDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM sets_table ORDER BY id DESC LIMIT 1", null);
        if( cursor != null && cursor.moveToFirst() ) {
            tempId = (cursor.getString(0));
        }

        sets.add(new Sets(tempId, name,des));
        setsAdapter.notifyDataSetChanged ();

        if(cursor != null){
            cursor.close();
        }
    }

    //inserts database data into arrayList
    //called on startup
    public void DisplaySets(){
        Cursor result = setDatabase.getAllData();

        while(result.moveToNext()){
            String id = result.getString(0);
            String name = result.getString(1);
            String des = result.getString(2);
            sets.add(new Sets(id,name,des));
        }
        result.close();
    }
}
