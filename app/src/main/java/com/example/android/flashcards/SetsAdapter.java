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

        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_view, parent, false);
            holder = new ViewHolder();
            holder.desTextView = convertView.findViewById(R.id.dbDesTextView);
            holder.nameTextView = convertView.findViewById(R.id.dbNameTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sets set = getItem(position);

        holder.nameTextView.setText(set.getName());
        holder.desTextView.setText(set.getDes());

        return convertView;
    }

    class ViewHolder {
        private TextView nameTextView;
        private TextView desTextView;
    }
}
