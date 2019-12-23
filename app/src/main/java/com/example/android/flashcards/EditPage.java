package com.example.android.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditPage extends AppCompatActivity {

    private static final String TAG = "EditPage";

    EditText wordEditText;
    EditText defEditText;
    EditText wordEditEditText;
    EditText defEditEditText;
    Button addButton;
    Button testButton;
    ListView wordList;
    String setId = "";
    WordsAdapter wordsAdapter;
    WordsDbHelper wordsDatabase;
    ArrayList<Words> words = new ArrayList<Words>();
    String word;
    String def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        //get setId from selected set
        Intent intent = getIntent();
        setId = intent.getStringExtra("set id");

        wordsDatabase = new WordsDbHelper(this);

        EditPage.this.setTitle("Edit Flashcard Set");

        //Loads db into sests
        DisplayWords(setId);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                addNewWord(setId);
            }
        });

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestPage.class);
                intent.putExtra("test set id", setId);
                startActivity(intent);
            }
        });

        wordsAdapter = new WordsAdapter(this, words);
        wordList = findViewById(R.id.wordsList);
        wordList.setAdapter(wordsAdapter);

        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words tempWord = (Words) parent.getItemAtPosition(position);
                word = tempWord.getWord();
                def = tempWord.getDefinition();

                displayEditDialog(word, def, tempWord);
                words.set(position, tempWord);
                wordsAdapter.notifyDataSetChanged();
            }
        });
    }

    //Read from editText and add to list
    //triggered by button
    public void addNewWord(String tempId){

        wordEditText = findViewById(R.id.wordsEditText);
        defEditText = findViewById(R.id.defEditText);

        String word = wordEditText.getText().toString();
        String def = defEditText.getText().toString();

        if(word.isEmpty() || def.isEmpty()){
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        } else {
            AddWord(tempId, word, def);
        }

        wordEditText.getText().clear();
        defEditText.getText().clear();

    }

    //Add new word to db and set
    public void AddWord(String tempSetId, String word, String def){
        wordsDatabase.insertData(tempSetId, word, def);

        //inserts newest id into set
        String tempId = "";
        SQLiteDatabase db = wordsDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM words_table ORDER BY id DESC LIMIT 1", null );
        if( cursor != null && cursor.moveToFirst() ) {
            tempId = (cursor.getString(0));
        }

        words.add(new Words(tempId, tempSetId, word, def));
        wordsAdapter.notifyDataSetChanged();

        if(cursor != null){
            cursor.close();
        }
    }

    //insert database into arrayList
    //called on startup
    public void DisplayWords(String tempSetId){
        Cursor cursor = wordsDatabase.getAllData(tempSetId);

        while(cursor.moveToNext()){
            String tempId = cursor.getString(0);
            String word = cursor.getString(2);
            String def = cursor.getString(3);
            words.add(new Words(tempId, tempSetId,word,def));
        }
        cursor.close();
    }

    //display when clicked on words list
    public void displayEditDialog(String oldWord, String oldDef, final Words tempWord){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog_layout, (ViewGroup) null);
        wordEditEditText = view.findViewById(R.id.editWordEditText);
        defEditEditText = view.findViewById(R.id.editDefEditText);

        wordEditEditText.setText(oldWord);
        defEditEditText.setText(oldDef);

        builder.setView(view);
        builder.setTitle("Edit Word");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Edit the set to match changes
                String newWord = wordEditEditText.getText().toString();
                String newDef = defEditEditText.getText().toString();
                String wordId = tempWord.getId();
                tempWord.editWord(newWord, newDef);

                //Edit the database to match changes
                SQLiteDatabase db = wordsDatabase.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("word", newWord);
                cv.put("definition", newDef);

                db.update("words_table", cv, "id =?", new String[]{wordId});
            }
        });
        builder.show();
    }
}
