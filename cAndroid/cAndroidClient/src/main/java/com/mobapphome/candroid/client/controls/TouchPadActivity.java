package com.mobapphome.candroid.client.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import com.mobapphome.candroid.commands.Commands;

public class TouchPadActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    /** Called when the activity is first created. */
    View viewTouchPad;
    Button btnLeft;
    Button btnRight;
    Button btnKeyBoard;
    PCKeyboardView pcKeyboardView;
    View touchPadButtonPanel;
    String TAG = "MyTest";
    static float xInit = (float) 0.0;
    static float yInit = (float) 0.0;
    static float yInitScroll = (float) 0.0;
    long downTime = 0;
    long upTime = 0;
    
    boolean btnLeftIsLongPressed = false;
    
    private boolean keyBoardVisible = false;

    CAndroidApplication cAndroidApplication;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cAndroidApplication = (CAndroidApplication) getApplicationContext();
        setContentView(R.layout.touch_pad_activity);
        
        pcKeyboardView = (PCKeyboardView) findViewById(R.id.pcKeyboardView); 
        pcKeyboardView.setParentActivity(this);
		touchPadButtonPanel = findViewById(R.id.viewTouchPadBP);
        viewTouchPad = this.findViewById(R.id.viewTouchPad);
        btnLeft = (Button) this.findViewById(R.id.btnLeft);
        btnRight = (Button) this.findViewById(R.id.btnRight);
        btnKeyBoard = (Button) this.findViewById(R.id.btnKeyBoard);
        viewTouchPad.setOnTouchListener(this);
        btnLeft.setOnTouchListener(this);
        btnRight.setOnTouchListener(this);
        btnKeyBoard.setOnClickListener(this);
        findViewById(R.id.viewScrollPad).setOnTouchListener(this);
        
	    final WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	    int wifi = wifiManager.getWifiState();
		if (wifi != WifiManager.WIFI_STATE_ENABLED && wifi != WifiManager.WIFI_STATE_ENABLING) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.settings_act_match_dialog_title));
			builder.setMessage(getResources().getString(R.string.dialog_wi_fi_enabling_question));
			builder.setPositiveButton(getResources().getString(R.string.dialg_yes), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			        	   wifiManager.setWifiEnabled(true);
			           }
			       });
			 builder.setNegativeButton(getResources().getString(R.string.dialg_no), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}else{
            cAndroidApplication.getClient().connectWithAsyncTask();
		}
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setBtnLongPress(boolean press){
    	if(press){
    		btnLeftIsLongPressed = true;
            cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_MOUSE_PRESSED, Commands.MOUSE_KEY_LEFT);
    	}else{
       		btnLeftIsLongPressed = false;
    		btnLeft.setPressed(false);
            cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_MOUSE_RElEASED, Commands.MOUSE_KEY_LEFT);
    	}
    }
    
    public boolean onTouch(View view, MotionEvent me) {

        switch (view.getId()) {
        case R.id.viewTouchPad:
            float x = me.getX();
            float y = me.getY();
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                TouchPadActivity.xInit = x;
                TouchPadActivity.yInit = y;
                downTime = me.getEventTime();
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                upTime = me.getEventTime();
                long diff = upTime - downTime;
                Log.d(TAG, "Diff time = " + diff);

                if (diff < 100) {
               		if(btnLeftIsLongPressed){
            			setBtnLongPress(false);
               		}else{
	                    cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_MOUSE_PRESSED,Commands.MOUSE_KEY_LEFT);
	                    cAndroidApplication.getClient().sendCommandKeyAsync(Commands.COMMAND_TYPE_MOUSE_RElEASED , Commands.MOUSE_KEY_LEFT);
                    }
                }
            } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
                int diffX = (int) (x - xInit);
                int diffY = (int) (y - yInit);
                if (diffX != 0 || diffY != 0) {
                    String commandStr = Commands.COMMAND_TYPE_MOUSE_MOVE + "," + diffX + "," + diffY;
                    Log.d(TAG, commandStr);
                    cAndroidApplication.getClient().sendRequestAsync(commandStr);
                }

                TouchPadActivity.xInit = x;
                TouchPadActivity.yInit = y;
                return true;
            }
            break;
        case R.id.viewScrollPad:
            float yScroll = me.getY();
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                TouchPadActivity.yInitScroll = yScroll;
            }else if (me.getAction() == MotionEvent.ACTION_MOVE) {
                int diffYScroll = (int) (yScroll - yInitScroll);
                if (diffYScroll != 0) {
                    String commandStr = Commands.COMMAND_TYPE_MOUSE_WHEEL + "," + diffYScroll;
                    Log.d(TAG, commandStr);
                    cAndroidApplication.getClient().sendRequestAsync(commandStr);
                }
                TouchPadActivity.yInitScroll = yScroll;
                return true;
            }
            break;
            case R.id.btnLeft:
            	String commandStr;
            	if (me.getAction() == MotionEvent.ACTION_DOWN) {
            		btnLeft.setPressed(true);
                } else if (me.getAction() == MotionEvent.ACTION_UP) {
                	long diff = me.getEventTime()- me.getDownTime();
                	Log.d(TAG, "Dif == " + diff);
                	if(diff > 900){
                		if(!btnLeftIsLongPressed){
                			setBtnLongPress(true);
                        }
                	}else{
                		if(btnLeftIsLongPressed){
                			setBtnLongPress(false);
                		}else{
                			btnLeft.setPressed(false);
	                        commandStr = Commands.COMMAND_TYPE_MOUSE_PRESSED + "," + Commands.MOUSE_KEY_LEFT;
	                        Log.d(TAG, commandStr);
	                        cAndroidApplication.getClient().sendRequestAsync(commandStr);
	                        commandStr = Commands.COMMAND_TYPE_MOUSE_RElEASED + "," + Commands.MOUSE_KEY_LEFT;
	                        Log.d(TAG, commandStr);
	                        cAndroidApplication.getClient().sendRequestAsync(commandStr);
                 		}
                	}
                }
                break;
            case R.id.btnRight:
            	setBtnLongPress(false);
            	if (me.getAction() == MotionEvent.ACTION_DOWN) {
            		btnRight.setPressed(true);
                } else if (me.getAction() == MotionEvent.ACTION_UP) {
                    commandStr = Commands.COMMAND_TYPE_MOUSE_PRESSED + "," + Commands.MOUSE_KEY_RIGHT;
                    Log.d(TAG, commandStr);
                    cAndroidApplication.getClient().sendRequestAsync(commandStr);
            		btnRight.setPressed(false);
                    commandStr = Commands.COMMAND_TYPE_MOUSE_RElEASED + "," + Commands.MOUSE_KEY_RIGHT;
                    Log.d(TAG, commandStr);
                    cAndroidApplication.getClient().sendRequestAsync(commandStr);
                }
                break;
            default:
                Log.e(TAG, "There is not onTouch action for Id = = " + view.getId());
                break;
        }
        return true;
    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnKeyBoard:
        	setKeyBoardVisible(!isKeyBoardVisible());			
			break;

		default:
            Log.e(TAG, "There is not onClick action for Id = " + v.getId());
			break;
		}
	}
	
	public boolean isKeyBoardVisible(){
		return keyBoardVisible;
	}
	
	public void setKeyBoardVisible(boolean visible){
		keyBoardVisible = visible;
		if(visible){
			  pcKeyboardView.setVisibility(View.VISIBLE);
			  touchPadButtonPanel.setVisibility(View.GONE);		
			  setBtnLongPress(false);
		}else{
			  pcKeyboardView.setVisibility(View.GONE);
			  touchPadButtonPanel.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(pcKeyboardView.isKeyBoardVisible()){				
				setKeyBoardVisible(false);
				return true;
			}
		 }
		 
		return super.onKeyDown(keyCode, event);
	}

}
