package com.example.hp.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    TextView signup_text;
    Button login;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_email = getString(R.string.prompt_email);
                String p_password = getString(R.string.prompt_password);
                if(email.getText().toString().equals(p_email) && password.getText().toString().equals(p_password)){
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));}
                else{
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("username and password do not match ..");
                    builder.setMessage("please fill all the field correctly..");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //close alert dialog
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });
        signup_text = (TextView)findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
