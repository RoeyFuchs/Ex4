package com.example.ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * connect to simulator
     * @param view
     */
    public void ConnectSim(View view) {
        //get ip and port
        EditText ip = (EditText) findViewById(R.id.IPInput);
        EditText port = (EditText) findViewById(R.id.PortInput);
        //create new activity
        Intent intent = new Intent(this, JoyStick.class);
        //convert to strings
        String ipNum = ip.getText().toString();
        String portNum = port.getText().toString();
        intent.putExtra("ip", ipNum);
        intent.putExtra("port", portNum);
        startActivity(intent);
    }
}
