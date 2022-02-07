package com.example.car1217;

import androidx.appcompat.app.AppCompatActivity;
import com.example.car1217.TBlue;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TBlue tBlue;
    private int skipped;
    private String btAddress = "98:D3:81:FD:88:8F";
    private String sCommand;

    private Button addressBn, disconnectBn, commandBn;
    private Button num1Bn, num2Bn, num3Bn, num4Bn, num5Bn;
    private EditText addressEt, commandEt;
    private TextView messagesTv, terminalTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGUI();
        try { actionController(); } catch(Exception e) {msgMessage("Action Error!\n");}
    }

    @Override
    public void onResume() {
        super.onResume();
        //timerHandler.postDelayed(sendToArduino, 1000);
        //skipped=9999; // force Bluetooth reconnection
    }

    public void initGUI() {
        addressBn = (Button)findViewById(R.id.addressBn);
        disconnectBn = (Button)findViewById(R.id.disconnectBn);
        commandBn = (Button)findViewById(R.id.commandBn);
        num1Bn = (Button)findViewById(R.id.num1Bn);
        num2Bn = (Button)findViewById(R.id.num2Bn);
        num3Bn = (Button)findViewById(R.id.num3Bn);
        num4Bn = (Button)findViewById(R.id.num4Bn);
        num5Bn = (Button)findViewById(R.id.num5Bn);
        addressEt = (EditText) findViewById(R.id.addressEt);
        commandEt = (EditText) findViewById(R.id.commandEt);
        messagesTv = (TextView) findViewById(R.id.messagesTv);
        terminalTv = (TextView) findViewById(R.id.terminalTv);

        messagesTv.setText(""); terminalTv.setText("");
        commandEt.setText(""); addressEt.setText(btAddress);
    }

    public void actionController() throws Exception {
        // 建立 Connect Button 的點擊監聽物件
        addressBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                msgMessage("Bluetooth initialization... \n");
                try {initBluetooth(); } catch(Exception e) {}
            }
        });

        // 建立 DisConnect Button 的點擊監聽物件
        disconnectBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {closeBluetooth();} catch(Exception e) {};
            }
        });

        // 建立 Send Button 的點擊監聽物件
        commandBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT(commandEt.getText().toString());}
        });

        // 建立 Number Button 的點擊監聽物件
        num1Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("F");}
        });
        num2Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("B");}
        });

        num3Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("L"); }
        });

        num4Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("R"); }
        });

        num5Bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { sendBT("S"); }
        });
    }

    /*** Bluetooth ***/

    void initBluetooth() throws Exception {
        skipped=0;

        btAddress = addressEt.getText().toString();

        tBlue=new TBlue(btAddress);
        if (tBlue.streaming()) {
            msgMessage("Bluetooth Connected. \n");
        } else {
            msgMessage("Error: Bluetooth connection failed. \n");
            closeBluetooth();
        }
    }

    void closeBluetooth()
    {
        msgMessage("Bluetooth Disconnect... \n");
        tBlue.close();
    }

    void receiveBT() {
        String inString=tBlue.read();
        msgTerminal("->: " + inString + "\n");    //receive signal from Arduino
    }

    void sendBT(String s)
    {
        if (skipped>=10) tBlue.connect();
        if (tBlue.streaming()) {    //send signal to Arduino
            Log.i("fb", "Clear to send, sending... ");
            tBlue.write(s);
            msgTerminal("<-: " + s + "\n");
            skipped=0;
        } else {    //skip
            Log.i("fb", "Not ready, skipping send. in: \""+ s +"\"");
            skipped++;
            msgTerminal(".\n");
            return;
        }
        new CountDownTimer(1000,1000){
            public void onTick(long ms){}
            public void onFinish(){receiveBT();}    //receive feedback from Arduino
        }.start();

    }

    public void msgMessage(String s)
    {
        Log.i("FB", s);
        if (5<=messagesTv.getLineCount()) messagesTv.setText("");
        messagesTv.append(s);
    }

    public void msgTerminal(String s)
    {
        Log.i("FB", s);
        if (16<terminalTv.getLineCount()) terminalTv.setText("");
        terminalTv.append(s);
    }


}
