package com.example.android.flashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Words> {

    public WordsAdapter(Context context, ArrayList<Words> words){
        super(context, 0, words);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.words_list_view, parent, false);

        Words word = getItem(position);

        TextView wordTextView = listItemView.findViewById(R.id.wordsTextView);
        TextView defTextView = listItemView.findViewById(R.id.defTextView);

        wordTextView.setText(word.getWord());
        defTextView.setText(word.getDefinition());

        return listItemView;
    }

}
