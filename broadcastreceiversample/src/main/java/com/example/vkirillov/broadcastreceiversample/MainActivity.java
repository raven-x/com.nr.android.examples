package com.example.vkirillov.broadcastreceiversample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSendMessage;
    private TextView txtResult;
    private BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        txtResult = (TextView) findViewById(R.id.txtResult);

        //Create broadcast receiver
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Start intent service to process received message asynchronously
                Intent serviceIntent = new Intent(context, ReceiverService.class);
                serviceIntent.putExtra(Const.PARAM_TIME, intent.getLongExtra(Const.PARAM_TIME, 0L));
                context.startService(serviceIntent);
            }
        };
        //Create filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter(Const.BROADCAST_ACTION);
        //Register broadcast receiver
        registerReceiver(br, intentFilter);
    }

    private void sendMessage(){
        Intent intent = new Intent(Const.BROADCAST_ACTION);
        intent.putExtra(Const.PARAM_TIME, System.currentTimeMillis());
        //Send broadcast message
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver
        unregisterReceiver(br);
    }
}
