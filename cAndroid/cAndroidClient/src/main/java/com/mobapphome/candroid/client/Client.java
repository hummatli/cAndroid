/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobapphome.candroid.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import candroid.client.R;

/**
 *
 * @author SETTAR
 */

class ClientHandler extends Handler{
	private final static String TAG = ClientHandler.class.getName();
	Client client;
	
	public ClientHandler(Client client) {
		this.client = client;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "-----------");
		Bundle b = msg.getData();
		client.sendCommandKey(b.getInt("COMMAND_TYPE"), b.getInt("PRIMARY_CODE"));
	}
}



public class Client{
	private static final String TAG = Client.class.getName();  
	
	private static final String CONFIG_FILE_PATH  = "candroid_config.txt";
	private File sdDir = Environment.getExternalStorageDirectory();
	private File file;

    private Socket socket = null;
    private PrintWriter outPrintWriter = null;
    private BufferedReader inReader = null;
    
    private String IP;
    private int port;
    private Context context;
    
	ClientHandler clientHandler;

    
    public Client(Context context) {
    	this.context = context;
    	clientHandler = new ClientHandler(this);
		try{
			file = new File(sdDir.getAbsolutePath()+ "/"+ CONFIG_FILE_PATH);
			Log.d(TAG, "file = " + file.getAbsolutePath());
			if(!file.exists()){
				file.createNewFile();
			}
		}catch (IOException e) {
			Log.e(TAG, "Error: onCreate,IOException, " + e.getMessage());			
		}


    }

	public String getIP() {
		return IP;
	}
	
	public int getPort() {
		return port;
	}
	
    synchronized public boolean connect(){
	    Resources res = context.getResources();
	    
    	if(socket == null || !socket.isConnected()){
    	    if(readConfig()){
	    		Log.d(TAG, "connect in");
		        try {
		            socket = new Socket(IP, port);
		            socket.setSoTimeout(2000);
		            outPrintWriter = new PrintWriter(socket.getOutputStream(), true);
		            inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		            Toast.makeText(context,res.getString(R.string.touchpad_act_connected_msg_text) ,5).show();
		            return true;
		        } catch (UnknownHostException ex) {
	            	//Toast.makeText(context,res.getString(R.string.touchpad_act_connected_dont_msg_text) ,5).show();
					Log.e(TAG, "Error: connect, UnknownHostException, exeption = " + ex.getMessage());												
		        } catch (IOException ex) {
		        	//Toast.makeText(context,res.getString(R.string.touchpad_act_connected_dont_msg_text) ,5).show();
					Log.e(TAG, "Error: connect, IOException, exeption = " + ex.getMessage());												
		        }	
	    		Log.d(TAG, "connect out");
		        return false;
			}else{
				Log.e(TAG, "Error: Could not read config file");
				return false;
			}
        }else{
        	return true;
        }
    }
        
	public boolean readConfig(){
		BufferedReader buffReader = null;
		try{
			buffReader = new BufferedReader(new FileReader(file));
			if(buffReader != null){
				String line = null;
				if ((line = buffReader.readLine()) != null) {
					String[] arr = line.split(",");
					IP = arr[0];
					port = Integer.valueOf(arr[1]);
				};
				return true;
			}
		}catch (IOException e) {
			Log.e(TAG, "Error: readConfig, IOException" + e.getMessage());			
		}catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "Config file-i duzgun yazilmayib. Error: " + e.getMessage());			
		}catch(NumberFormatException e){
			Log.e(TAG, "Port number formasinda deyil. Error: " + e.getMessage());						
		}finally{
			if(buffReader != null){
				try{
					buffReader.close();
				}catch (IOException e) {
					Log.e(TAG, "Error: readConfig, IOException, finally, " + e.getMessage());			
				}
			}
		}
		IP = null;
		port = 0;
		return false;
	}

	public boolean writeConfig(String IP, int port){
		BufferedWriter buffWriter = null;
		try{
			buffWriter = new BufferedWriter(new FileWriter(file));
			if(buffWriter != null){
				Log.d(TAG, "IP = " + IP +", port = " + port);
				String line = IP + "," + port;
				buffWriter.write(line);
				return true;
			}
		}catch (IOException e) {
			Log.e(TAG, "Error: writeConfig, IOException" + e.getMessage());			
		}catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "writeConfig, Config file-i duzgun yazilmayib. Error: " + e.getMessage());			
		}catch(NumberFormatException e){
			Log.e(TAG, "writeConfig ,Port number formasinda deyil. Error: " + e.getMessage());						
		}finally{
			if(buffWriter != null){
				try{
					buffWriter.close();
				}catch (IOException e) {
					Log.e(TAG, "Error: writeConfig, IOException, finally, " + e.getMessage());			
				}
			}			
		}
		return false;
		
	}


    
   synchronized public String sendRequest(String command) {
      String strFromServer = null;
        try {
        	if(outPrintWriter != null){
        		outPrintWriter.println(command);
        	}else{
        		if(connect()){
            		outPrintWriter.println(command);        			
        		}
        	}
        	
            if (inReader != null && (strFromServer = inReader.readLine()) != null) {
                Log.d(TAG, "from server is working = " + strFromServer);
            }else{
                Log.d(TAG, "from server is not working = " + strFromServer);
                close();
            }
        } catch (IOException ioe) {
			Log.e(TAG, "Error: sendRequest, IOException, exeption = " + ioe.getMessage());												
        }
        return strFromServer;
    }

   public String sendCommandKey(int commandType, int key){
       String commandStr = commandType + "," + key;
       Log.d(TAG, commandStr);
       return sendRequest(commandStr);  	
   }
   
   public void sendCommandKeyWithHandler(int commandType, int key){
	   Message message = new Message();
	   Bundle bundle = new Bundle();
	   bundle.putInt("COMMAND_TYPE", commandType);
	   bundle.putInt("PRIMARY_CODE", key);
	   message.setData(bundle);
	   clientHandler.sendMessage(message);
   }
   
    public boolean close(){
    	Log.d(TAG, "clint closed");
        try {
        	if(outPrintWriter != null){
        		outPrintWriter.close();
        		outPrintWriter = null;
        	}
        	if(inReader != null){
                inReader.close();     
                inReader = null;
        	}
        	if(socket != null){
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
