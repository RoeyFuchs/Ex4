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

    public void ConnectSim(View view) {
        EditText ip = (EditText) findViewById(R.id.IPInput);
        EditText port = (EditText) findViewById(R.id.PortInput);
        Intent intent = new Intent(this, JoyStick.class);
        String ipNum = ip.getText().toString();
        String portNum = port.getText().toString();
        intent.putExtra("ip", ipNum);
        intent.putExtra("port", portNum);
        startActivity(intent);
    }
}
