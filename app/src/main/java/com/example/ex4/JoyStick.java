package com.example.ex4;

import android.content.Intent;
import android.content.res.Configuration;
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

    /**
     * initialize values in joystick creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_stick);
        //get joystick activity
        Intent intent = getIntent();
        try {
            this.tcpClient = new TcpClient();
            //get ip and port
            this.tcpClient.execute(intent.getStringExtra("ip"), intent.getStringExtra("port"), null);
        }catch(Exception e) {
            System.out.println(e.toString());
        }
        JoyStrickView joyStrickView = new JoyStrickView(this);
        joyStrickView.addToObserver(this);
        setContentView(joyStrickView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * stop tcp client when this class is destroyed
     */
    public void onDestroy() {
        this.tcpClient.Stop();
        super.onDestroy();
    }

    /**
     * send ailron and elevator through tcp client
     * @param flightDetails
     */
    public void update(FlightDetails flightDetails){
        tcpClient.Send(this.alironCommand + flightDetails.getAilron());
        tcpClient.Send(this.elevatorCommand + flightDetails.getElevator());
    }


}
