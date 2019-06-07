package com.example.ex4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class JoyStick extends AppCompatActivity implements ObserverInterface {

private TcpClient tcpClient;
private  String alironCommand = "set /controls/flight/aileron ";
private String elevatorCommand = "set /controls/flight/elevator ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_stick);
        Intent intent = getIntent();
        try {
            this.tcpClient = new TcpClient();
            this.tcpClient.execute(intent.getStringExtra("ip"), intent.getStringExtra("port"), null);
        }catch(Exception e) {
            System.out.println(e.toString());
        }
        JoyStrickView joyStrickView = new JoyStrickView(this);
        joyStrickView.addToObserver(this);
        setContentView(joyStrickView);
    }

    public void onDestroy() {
        this.tcpClient.Stop();
        super.onDestroy();
    }

    public void update(FlightDetails flightDetails){
        tcpClient.Send(this.alironCommand + flightDetails.getAilron());
        tcpClient.Send(this.elevatorCommand + flightDetails.getElevator());
    }

}
