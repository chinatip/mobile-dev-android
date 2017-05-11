package chinatip.login_firebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ViewLocation extends AppCompatActivity {
    private Button getLocation, stopGPS;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private int counter = 1;
    GPSTracker gps;
    //Thread
    Handler h = new Handler();
    Thread task;
    private long startTime;
    private String timeString;
    private TextView timerView;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        timerView = (TextView) findViewById(R.id.timerText);
        getLocation = (Button) findViewById(R.id.getLocation);
        stopGPS = (Button) findViewById(R.id.stopGPS);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GPS").push();
        try {
            if(ActivityCompat.checkSelfPermission(this, mPermission) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopHandlerTask();
                timerView.setText("Location Service is stopped");
            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                counter = 1;
            }
        });
    }

    public void startTimer() {
        startTime = System.currentTimeMillis() - startTime;
        task = new Thread() {
            public void run() {
                long mills = System.currentTimeMillis() - startTime;
                final long secs = mills / 1000 % 60; // seconds 0 - 59

                timeString = String.format("%02d", secs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerView.setText(timeString);
                        if(secs % 5 == 0) {
                            getLocation();
                        }
                    }
                });
                h.postDelayed(task, 1000);
            }
        };
        h.postDelayed(task, 1000);
    }

    public void stopHandlerTask() {
        h.removeCallbacks(task);
    }

    public void getLocation() {
        gps = new GPSTracker(ViewLocation.this);

        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Current location is \n Lat: " + latitude + "Long: " + longitude,
                    Toast.LENGTH_LONG).show();

            String mName = "Lat: " + latitude + " Long: "+ longitude;
            mDatabaseRef.setValue(mName);
        } else {
            gps.showSettingsAlert();
        }
    }
}
