package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NewListActivity extends AppCompatActivity {
    String[] items = {"Cloths", "Feeding", "Health", "Skin", "Sleep"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;

    // updating it to firebase
    DatabaseReference dbReference;
    ArrayList<String> listItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewID);
        adapter = new ArrayAdapter<String>(this, R.layout.category_list, items);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                autoCompleteTextView.setText(item);
            }
        });
    }
}