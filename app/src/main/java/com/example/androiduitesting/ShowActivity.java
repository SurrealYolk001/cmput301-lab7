package com.example.androiduitesting;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity);


        TextView myTextView = findViewById(R.id.textView);
        String receivedItem = getIntent().getStringExtra("City");
        myTextView.setText(receivedItem);


        final Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // closes this screen and returns to the previous one
            }
        });
    }



}