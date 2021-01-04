package com.example.createdatelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    private TextView tvListDates;
    private EditText edTopic;
    private EditText edMail;
    ArrayList<String> listDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);
        tvListDates = findViewById(R.id.tv_list_dates);
        edTopic = findViewById(R.id.etTopic);
        edMail = findViewById(R.id.etMail);
        tvListDates.setMovementMethod(new ScrollingMovementMethod());
        listDates = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        tvListDates.setText("Группа ");
        for (String listDate : listDates) {
            tvListDates.append(listDate+"\n");
        }
        edTopic.setText("CDL-Даты Группа "+listDates.get(0));

        Button next = findViewById(R.id.buttonBack);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        Button mail = (Button) findViewById(R.id.buttonMail);
        mail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String to=edMail.getText().toString();

                String subject=edTopic.getText().toString();

                String message=tvListDates.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }

        });
    }
}
