package com.example.android.flashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SetsAdapter extends ArrayAdapter<Sets> {

    SetsAdapter(@NonNull Context context, ArrayList<Sets> sets) {
        super(context, 0, sets);
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);

        Sets set = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.dbNameTextView);
        TextView desTextView = listItemView.findViewById(R.id.dbDesTextView);

        nameTextView.setText(set.getName());
        desTextView.setText(set.getDes());

        return listItemView;
    }
}
