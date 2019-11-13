package com.example.android.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TestPage extends AppCompatActivity {

    Button nextButton;
    Button backButton;
    TextView wordText;
    TextView defText;
    TextView numLeftText;
    ImageView cardImage;
    ArrayList<Words> wordsList = new ArrayList<Words>();
    WordsDbHelper wordsDatabase;
    String setId = "";
    int listSize = 0;
    int current = 1;

    private static final String TAG = "TestPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        Intent intent = getIntent();
        setId = intent.getStringExtra("test set id");
        wordsDatabase = new WordsDbHelper(this);

        wordsList = getWords(setId);
        listSize = wordsList.size();

        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        numLeftText = findViewById(R.id.numLeftTextView);
        wordText = findViewById(R.id.wordText);
        defText = findViewById(R.id.defText);
        cardImage = findViewById(R.id.cardImageView);

        //displays interactions
        displayNum();
        displayButton();
        displayWords();

        cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordInteractions();
            }
        });


    }

    //get words list from database
    private ArrayList<Words> getWords(String id){
        ArrayList<Words> words = new ArrayList<Words>();

        SQLiteDatabase db = wordsDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM words_table WHERE set_id =?", new String[]{id});


        while(cursor.moveToNext()){
            String tempId = cursor.getString(0);
            String word = cursor.getString(2);
            String def = cursor.getString(3);
            words.add(new Words(tempId,id,word,def));
        }

        cursor.close();
        return words;
    }

    private void displayNum(){
        numLeftText.setText(current + "/" + listSize);
    }

    //display buttons on startup
    //recursively called when buttons are clicked
    private void displayButton(){
        //Check which button to display

        nextButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);

        if (current == 1){
            backButton.setVisibility(View.GONE);
        }

        if (current == listSize){
            nextButton.setVisibility(View.GONE);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current--;
                displayNum();
                displayButton();
                displayWords();
                defText.setVisibility(View.GONE);
                wordText.setVisibility(View.VISIBLE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current++;
                displayNum();
                displayButton();
                displayWords();
                defText.setVisibility(View.GONE);
                wordText.setVisibility(View.VISIBLE);
            }
        });
    }

    //display word on start up, def is set invisible
    //called when button is clicked
    private void displayWords(){
        int index = current;
        index--;

        Words tempWord = wordsList.get(index);

        wordText.setText(tempWord.getWord());
        defText.setText(tempWord.getDefinition());
        defText.setVisibility(View.GONE);
    }

    //when ever the image is clicked on
    private void wordInteractions(){
        if(defText.getVisibility()==View.GONE){
            Log.d(TAG, "Gone");
            defText.setVisibility(View.VISIBLE);
            wordText.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "Not Gone");
            defText.setVisibility(View.GONE);
            wordText.setVisibility(View.VISIBLE);
        }
    }
}
