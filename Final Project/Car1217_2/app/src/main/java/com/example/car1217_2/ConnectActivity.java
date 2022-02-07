package com.example.car1217_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectActivity extends AppCompatActivity {
    //private final UUID uuid = UUID.fromString("8c4102d5-f0f9-4958-806e-7ba5fd54ce7c");
    private final UUID serialPortUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String name, address;

    private Button buttonConnect, buttonDisconnect;

    private Button mbtnF, mbtnB, mbtnR, mbtnL;
    private String Input;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    Handler samHadler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        final String deviceName = getIntent().getStringExtra("DeviceName");
        final String deviceAddress = getIntent().getStringExtra("DeviceAddress");

        name = deviceName != null ? deviceName : "裝置名稱未顯示";
        address = deviceAddress;

        setTitle(String.format("%s (%s)", address, name));

        buttonConnect = findViewById(R.id.buttonConnect);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);
        mbtnF = findViewById(R.id.btnF);
        mbtnB = findViewById(R.id.btnB);
        mbtnL = findViewById(R.id.btnL);
        mbtnR = findViewById(R.id.btnR);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        mbtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Input = "F";
                send();
                samHadler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                Input = "S";
                                send();
                            }
                        }, 100
                );
            }
        });

        mbtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Input = "B";
                send();
                samHadler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                Input = "S";
                                send();
                            }
                        }, 100
                );
            }
        });

        mbtnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //invert turn
                Input = "R";
                send();
                samHadler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                Input = "S";
                                send();
                            }
                        }, 100
                );
            }
        });

        mbtnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //invert turn
                Input = "L";
                send();
                samHadler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                Input = "S";
                                send();
                            }
                        }, 100
                );
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void connect() {
        final BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

        try {
            socket = device.createRfcommSocketToServiceRecord(serialPortUUID);
            socket.connect();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        if (socket == null) return;

        try {
            socket.close();
            socket = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        if (outputStream == null) return;

        try {
            outputStream.write(Input.getBytes());
            outputStream.flush();
            Input = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
