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
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.words_list_view, parent, false);
            holder = new ViewHolder();
            holder.defTextView = convertView.findViewById(R.id.defTextView);
            holder.wordTextView = convertView.findViewById(R.id.wordsTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Words word = getItem(position);

        holder.wordTextView.setText(word.getWord());
        holder.defTextView.setText(word.getDefinition());

        return convertView;
    }

    class ViewHolder{
        private TextView wordTextView;
        private TextView defTextView;
    }

}
