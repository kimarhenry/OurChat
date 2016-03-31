package com.android.tanique.ourchat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            final AutoCompleteTextView txtUserName = (AutoCompleteTextView)findViewById(R.id.username);
            final EditText txtPassword = (EditText)findViewById(R.id.password);
            Button btnLogin = (Button)findViewById(R.id.login);
            btnLogin.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String username = txtUserName.getText().toString();
                    String password = txtPassword.getText().toString();
                    try{
                        if(username.length() > 0 && password.length() >0)
                        {
                            Databasehelper dbUser = new Databasehelper(LoginActivity.this);
                            dbUser.createDataBase();
                            dbUser.openDataBase();

                            if(dbUser.Login(username, password))
                            {
                                Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("user",username);
                                i.putExtra("pass",password);
                                startActivity(i);
                            }else{
                                Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                            }
                            dbUser.close();
                        }

                    }catch(Exception e)
                    {
                        Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }