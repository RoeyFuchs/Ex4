package com.example.ex4;


import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TcpClient extends AsyncTask<String, String, Void> {
    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>();


    @Override
    protected Void doInBackground(String... strings) {
        try {
            this.clientSocket = new Socket(strings[0], Integer.parseInt(strings[1]));
            this.outToServer = new DataOutputStream(this.clientSocket.getOutputStream());
            while (true) {
                this.outToServer.writeUTF(queue.take());
                this.outToServer.flush();
            }
        }catch (Exception e) {
            System.out.println(e.toString());
         }
        return null;
    }
public void Send(String str) {
        try {
            this.queue.put(str);
        }catch (Exception e) {
            System.out.println(e.toString());
        }
}

public void Stop() {
        try {
            this.clientSocket.close();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
}


}