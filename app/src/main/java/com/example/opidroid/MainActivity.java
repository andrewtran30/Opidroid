package com.example.opidroid;

import androidx.appcompat.app.AppCompatActivity;
import org.tensorflow.lite.Interpreter;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Set;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class MainActivity extends AppCompatActivity {
    Interpreter WESADtflite;
    Button btnPaired;
    ListView deviceList;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            WESADtflite = new Interpreter(loadModelfile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        btnPaired = (Button)findViewById(R.id.button);
        deviceList = (ListView)findViewById(R.id.listView);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
          //show a messgae that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Dvice is Unavailable", Toast.LENGTH_LONG).show();

            finish();
        } else {
            if (myBluetooth.isEnabled()) {

            } else {
                // Ask user to turn on his/her bluetooth
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }
        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList(); //method will be called onClick
            }
        });
    }

    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices(); //List of all currently "Paired" devices that aren't necessarily connected
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for(BluetoothDevice bt: pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Getting the device's name and address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(myListClickListener); //method called when the device from the list is clicked
    }

    public float doInference(String inputString) {
        // float input shape is [1]

        float[][] outputval = new float[1][1];

      //  WESADtflite.run(inputVal, outputval);

        float inferredValue = outputval[0][0];

        return inferredValue;
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        // when the specified device is clicked, perform this onItemClicked function
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            // Make an intent to start the next Activity
            Intent i = new Intent(MainActivity.this, ledControl.class); //This will be changed later
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };

    private MappedByteBuffer loadModelfile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("WESAD.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
