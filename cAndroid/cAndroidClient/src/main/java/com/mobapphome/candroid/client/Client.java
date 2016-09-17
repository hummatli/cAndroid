/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobapphome.candroid.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mobapphome.candroid.R;

/**
 * @author SETTAR
 */


public class Client {
    private static final String TAG = Client.class.getName();

    private Socket socket = null;
    private PrintWriter outPrintWriter = null;
    private BufferedReader inReader = null;
    private Context context;



    public Client(Context context) {
        this.context = context;
    }



    synchronized private boolean connect() {

        if (socket == null || !socket.isConnected()) {

            Log.d(TAG, "connect in");
            try {
                socket = new Socket(((CAndroidApplication)context).getIP(), ((CAndroidApplication)context).getPort());
                socket.setSoTimeout(2000);
                outPrintWriter = new PrintWriter(socket.getOutputStream(), true);
                inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                return true;
            } catch (UnknownHostException ex) {
                Log.e(TAG, "Error: connect, UnknownHostException, exeption = " + ex.getMessage());
            } catch (IOException ex) {
                Log.e(TAG, "Error: connect, IOException, exeption = " + ex.getMessage());
            }
            Log.d(TAG, "connect out");
            return false;

        } else {
            return true;
        }
    }


    synchronized public void connectWithAsyncTask() {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean) {
                    Toast.makeText(context, context.getResources().getString(R.string.txt_connected_dont_msg), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.txt_connected_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                return connect();
            }
        }.execute();
    }

    synchronized private String sendRequest(String command) {
        String strFromServer = null;
        try {
            if (outPrintWriter != null) {
                outPrintWriter.println(command);
            } else {
                if (connect()) {
                    outPrintWriter.println(command);
                }
            }

            if (inReader != null && (strFromServer = inReader.readLine()) != null) {
                Log.d(TAG, "from server is working = " + strFromServer);
            } else {
                Log.d(TAG, "from server is not working = " + strFromServer);
                close();
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Error: sendRequest, IOException, exeption = " + ioe.getMessage());
        }
        return strFromServer;
    }

    synchronized public void sendCommandKeyAsync(int commandType, int key) {
        new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... params) {
                String commandStr = params[0] + "," + params[1];
                Log.d(TAG, commandStr);
                return sendRequest(commandStr);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute(commandType, key);

    }

    synchronized public void sendRequestAsync(String command) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return sendRequest(params[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute(command);

    }

    public boolean close() {
        Log.d(TAG, "clint closed");
        try {
            if (outPrintWriter != null) {
                outPrintWriter.close();
                outPrintWriter = null;
            }
            if (inReader != null) {
                inReader.close();
                inReader = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
            return true;
        } catch (IOException ex) {
            // ex.printStackTrace();
            return false;
        }
    }
}
