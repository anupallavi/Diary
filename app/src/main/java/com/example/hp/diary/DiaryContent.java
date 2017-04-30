package com.example.hp.diary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hp on 4/29/2017.
 */

public class DiaryContent extends AppCompatActivity implements TextToSpeech.OnInitListener{
        TextView textView;
        Context context=this;
        DatabaseHelper myDB;
        ContentDatabase contentdb;
        Button save;
        ImageButton calendar,startRecognizer;
private static final int RQS_RECOGNITION = 1;
        EditText editText;
        TextToSpeech tts;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_content);

        Bundle dateData = getIntent().getExtras();
        if(dateData == null) {
        return;
        }
        String dateSet = dateData.getString("Date");
        String content = dateData.getString("Content");

        textView = (TextView) findViewById(R.id.date);
        textView.setText(dateSet);
        calendar = (ImageButton) findViewById(R.id.calendarButton);
        startRecognizer = (ImageButton) findViewById(R.id.micButton);
        startRecognizer.setEnabled(false);
        editText = (EditText) findViewById(R.id.edittext);
        startRecognizer.setOnClickListener(startRecognizerOnClickListener);
        tts = new TextToSpeech(this, this);
        myDB = new DatabaseHelper(this);
        contentdb = new ContentDatabase(this);
        save = (Button) findViewById(R.id.save_button);
        editText.append(content);

        calendar.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(DiaryContent.this, CalendarActivity.class);
        startActivity(intent);
        }
        });}
        public void AddData(View view){
                String content = editText.getText().toString();
                String date = textView.getText().toString();
            contentdb = new ContentDatabase(context);
                SQLiteDatabase db = contentdb.getWritableDatabase();
                contentdb.addData(date,content,db);
                Toast.makeText(getBaseContext(),"one row inserted",Toast.LENGTH_LONG).show();

        }


private Button.OnClickListener startRecognizerOnClickListener = new Button.OnClickListener() {
@Override
public void onClick(View arg0) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-IN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speech to recognize");
        startActivityForResult(intent, RQS_RECOGNITION);

        }
        };

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == RQS_RECOGNITION) && (resultCode == RESULT_OK)) {
        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        //String query = "select " + DatabaseHelper.COL2 + "from " + DatabaseHelper.TABLE_NAME + "where " +DatabaseHelper.COL1+ " = " +result;
        //DisplayStringArray.append(result.get(0));

        Cursor cursor = myDB.translate(result);

        if (cursor.moveToNext()) {
        editText.append(cursor.getString(0));
        } else {

        editText.append(result.get(0));

        }

        }
        }

@Override
public void onInit(int status) {
        startRecognizer.setEnabled(true);
        }
}
