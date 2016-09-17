package com.mobapphome.candroid.client.controls;

import java.util.Date;

import com.mobapphome.candroid.R;
import com.mobapphome.candroid.client.CAndroidApplication;
import com.mobapphome.candroid.commands.Commands;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

enum SlideDirection {
    Right, Left
}

public class SlideShowControllerActivity extends AppCompatActivity implements OnClickListener, OnTouchListener {
    static final String TAG = SlideShowControllerActivity.class.getName();
    int PRIMARY_CODE_F5 = -0X74;
    int PRIMARY_CODE_ESC = -0X1B;
    int PRIMARY_CODE_RIGHT = -0X27;
    int PRIMARY_CODE_LEFT = -0X25;

    float initX;
    long downTime;

    boolean started = false;
    Button btnSSStart;

    CAndroidApplication cAndroidApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show_controller_activity);

        cAndroidApplication = (CAndroidApplication) getApplicationContext();

        btnSSStart = (Button) findViewById(R.id.btnSSStart);
        btnSSStart.setOnClickListener(this);
        btnSSStart.setText(getResources().getString(R.string.slide_show_act_btn_start));
        started = false;

        findViewById(R.id.linLayoutSSSlide).setOnTouchListener(this);


        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
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
        } else {
            cAndroidApplication.getClient().connectWithAsyncTask();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.settings_act_match_dialog_title));
        builder.setMessage(getResources().getString(R.string.dialog_slide_show_enabling_info));
        builder.setPositiveButton(getResources().getString(R.string.dialg_go_on), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            Log.i("test", npe.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slide_show_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(SlideShowControllerActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_control) {
            startActivity(new Intent(SlideShowControllerActivity.this, TouchPadActivity.class));
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSSStart:
                if (!started) {
                    started = true;
                    btnSSStart.setText(getResources().getString(R.string.slide_show_act_btn_end));
                    Log.d(TAG, "This boutton dont have  onClick Listener. Minus");
                    cAndroidApplication.getClient().sendCommandKeyAsync(
                            Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_F5);
                } else {
                    started = false;
                    btnSSStart.setText(getResources().getString(R.string.slide_show_act_btn_start));
                    Log.d(TAG, "This boutton dont have  onClick Listener. Plus");
                    cAndroidApplication.getClient().sendCommandKeyAsync(
                            Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_ESC);
                }
                break;

            default:
                Log.e(TAG, "This boutton dont have  onClick Listener. id = " + v.getId());
                break;
        }
    }

    public void createSlideEvent(SlideDirection slideDirection) {
        if (!started) {
            return;
        }
        Log.d(TAG, "Diff X = " + slideDirection);
        if (slideDirection == SlideDirection.Right) {
            cAndroidApplication.getClient().sendCommandKeyAsync(
                    Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_RIGHT);
        } else if (slideDirection == SlideDirection.Left) {
            cAndroidApplication.getClient().sendCommandKeyAsync(
                    Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_LEFT);
        } else {
            Log.e(TAG, "This SlideDirection is not defined = " + slideDirection);
        }

    }

    public boolean onTouch(View v, MotionEvent me) {
        switch (v.getId()) {
            case R.id.linLayoutSSSlide:
                if (me.getAction() == MotionEvent.ACTION_DOWN) {
                    initX = me.getX();
                    downTime = me.getEventTime();
                } else if (me.getAction() == MotionEvent.ACTION_UP) {
                    float diffX = me.getX() - initX;
                    long diffTime = me.getEventTime() - downTime;
                    float speed = diffX / diffTime;
                    if (Math.abs(speed) > 1.0) {
                        if (speed > 0) {
                            createSlideEvent(SlideDirection.Right);
                        } else {
                            createSlideEvent(SlideDirection.Left);
                        }
                    }
                }
                break;
            default:
                Log.e(TAG, "There is not onTouch action for Id = = " + v.getId());
                break;
        }
        return true;

    }

    @Override
    protected void onStop() {
        cAndroidApplication.getClient().sendCommandKeyAsync(
                Commands.COMMAND_TYPE_KEY_PRESSED_RELEASED, PRIMARY_CODE_ESC);
        super.onStop();
    }
}
