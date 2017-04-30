package com.example.hp.diary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Hp on 4/29/2017.
 */

public class HomeActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = (TextView)findViewById(R.id.welcome_txt);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);
    }
}
