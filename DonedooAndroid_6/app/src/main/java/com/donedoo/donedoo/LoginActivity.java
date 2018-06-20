package com.donedoo.donedoo;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.donedoo.model.Data;
import com.donedoo.model.User;
import com.donedoo.services.ServiceClient;

public class LoginActivity extends AppCompatActivity {

 public static final String EXTRA_MESSAGE = "Donedoo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView loginResponse = (TextView) findViewById(R.id.login_response);
        loginResponse.setText(Data.getNotification());

    }


    public void createGroup(View view) {

        Intent intent = new Intent(this, LoginCheckActivity.class);


        EditText groupName = (EditText) findViewById(R.id.groupName);
        Data.setGroupName(groupName.getText().toString());

        EditText userName = (EditText) findViewById(R.id.userName);
        Data.setUserName(userName.getText().toString());

        EditText password = (EditText) findViewById(R.id.password);
        Data.setPassword(password.getText().toString());

        final User u1= new User();

        u1.setGroupName(Data.getGroupName());
        u1.setUserName(Data.getUserName());
        u1.setPassword(Data.getPassword());
        u1.setAdmin(1);

        Data.setCheck("wait");
        ServiceClient.createGroup(u1);
        startActivity(intent);


    }

    public void login(View view) {
       Intent intent = new Intent(this, LoginCheckActivity.class);


        EditText groupName = (EditText) findViewById(R.id.groupName);
        Data.setGroupName(groupName.getText().toString());

        EditText userName = (EditText) findViewById(R.id.userName);
        Data.setUserName(userName.getText().toString());

        EditText password = (EditText) findViewById(R.id.password);
        Data.setPassword(password.getText().toString());

        Data.setCheck("wait");
        ServiceClient.existCheck(Data.getGroupName(), Data.getUserName(), Data.getPassword());
        startActivity(intent);
    }

    public static void dummy(){

    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        dummy();

    }

}
