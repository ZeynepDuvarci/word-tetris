package com.yazlab.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {


    private Button btn_easy_mode,btn_hard_mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_easy_mode=findViewById(R.id.btn_easy_mode);
        btn_hard_mode=findViewById(R.id.btn_hard_mode);

        btn_easy_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_MainActivity=new Intent(StartActivity.this,MainActivity.class);
                i_MainActivity.putExtra("mode","easy");
                startActivity(i_MainActivity);

            }
        });

        btn_hard_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_MainActivity=new Intent(StartActivity.this,MainActivity.class);
                i_MainActivity.putExtra("mode","hard");
                startActivity(i_MainActivity);

            }
        });


    }
}