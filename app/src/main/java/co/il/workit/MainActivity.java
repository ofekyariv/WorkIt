package co.il.workit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , PhoneCallReceiver.ICallMessageReceiver {

    //Declare the cb interface static in your activity
    private static PhoneCallReceiver.ICallMessageReceiver iCallMessageReceiver;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static int travel_ex=20;
    public static int over_time=7;
    public static int salary=25;
    Button btnWI;
    Button btnCS;
    Button btnMS;
    ImageButton btnWaze;
    EditText etAddress;
    SwitchCompat swSFS;
    TextView sfShift;
    Chronometer cmTimer;
    Boolean resume = false;
    long elapsedTime;
    PhoneCallReceiver callReceiver;
    String phoneNo;
    private boolean enableSms;
    private boolean enableCall;
    private IntentFilter intentFilter;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swSFS=(SwitchCompat)findViewById(R.id.swSFS);
        swSFS.setOnCheckedChangeListener(onCheckedChanged());
        btnWI=(Button)findViewById(R.id.btnWI);
        btnWI.setOnClickListener(this);
        btnCS=(Button)findViewById(R.id.btnCS);
        btnCS.setOnClickListener(this);
        btnMS=(Button)findViewById(R.id.btnMS);
        btnMS.setOnClickListener(this);
        btnWaze=(ImageButton) findViewById(R.id.btnWaze);
        btnWaze.setOnClickListener(this);
        etAddress=(EditText) findViewById(R.id.etAddress);

        cmTimer = (Chronometer) findViewById(R.id.cmTimer);
        // example setOnChronometerTickListener
        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                if (!resume) {
                    hours = (int) (((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000)/3600);
                    minutes = (int) ((((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) / 60) - hours*60);
                    seconds = (int) (((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) % 60);
                    elapsedTime = SystemClock.elapsedRealtime();

                } else {
                    hours = (int) (((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000)/3600);
                    minutes = (int) ((((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) / 60) - hours*60);
                    seconds = (int) (((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) % 60);
                    elapsedTime = elapsedTime + 1000;


                }
            }
        });
        //Assign this
        iCallMessageReceiver = this;
        callReceiver = new PhoneCallReceiver();
        callReceiver.registerCallback(iCallMessageReceiver);

        intentFilter = new IntentFilter("co.il.workit.PhoneCallReceiver.SOME_ACTION");
        registerReceiver(callReceiver, intentFilter);
        checkForPermission();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(callReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callReceiver);
    }
    private void checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            enableSms = false;
        } else {
            // Permission already granted. Enable the SMS button.
            enableSms = true;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            enableCall = false;
        } else {
            // Permission already granted.
            enableCall = true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                    enableSms = true;

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    enableSms = false;
                    return;
                }

            }
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && permissions.length > 0
                        && permissions[0].equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    enableCall = true;
                } else {
                    // Permission denied. Stop the app.
                    enableCall = false;
                }
            }
        }

    }

    @Override
    public void sendCallMessage(String incomingNumber) {
        if ((elapsedTime > 0) && (enableSms) && (enableCall)) {
            SmsManager.getDefault().sendTextMessage(incomingNumber, null, "I'm in work", null, null);
        }
    }
    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Intent intent = new Intent(MainActivity.this, NotificationService.class);

                switch (buttonView.getId()) {
                    case R.id.swSFS:
                        if (isChecked) {
                            startService(intent);
                            if (!resume) {
                                cmTimer.setBase(SystemClock.elapsedRealtime());
                                cmTimer.start();
                            } else {
                                cmTimer.start();
                            }
                        } else {
                            stopService(intent);
                            cmTimer.stop();
                            resume = true;
                            ShiftDataBase shiftDataBase=new ShiftDataBase(getApplicationContext());
                            // SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyy");
                            Date date = Calendar.getInstance().getTime();
                            Shift Shift = new Shift("",0,0);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            try {
                                 Shift =new Shift(df.format(date),hours*3600+minutes*60+seconds);
                            } catch (Exception e) {
                                 Shift =new Shift("",0);
                            }
                            shiftDataBase.setRecord(Shift);
                        }
                        break;

                }
            }
        };
    }

    @Override
    public void onClick(View v)
    {
        if (btnMS == v)
        {
            Intent intent = new Intent(this, MonthlySumUp.class);
            startActivity(intent);
        }
        if (btnWI == v)
        {
            Intent intent = new Intent(this, WorkInformation.class);
            startActivity(intent);
        }
        if (btnCS == v)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (btnWaze == v)
        {
            try
            {
                // Launch Waze to look for Hawaii:
                String url = "https://waze.com/ul?q=" + etAddress.getText().toString();
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                startActivity( intent );
            }
            catch ( ActivityNotFoundException ex  )
            {
                // If Waze is not installed, open it in Google Play:
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                startActivity(intent);
            }
        }
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  true;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.share){
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm using WorkIt, check it out!");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return true;
    }
}