package com.example.speedometer;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {
	
	LocationManager lm;
    LocationListener ll;
    
    Location previousLocation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location currentLocation) {
		float speed = 0;
		
		((TextView) findViewById(R.id.updated)).
			setText("Updated: "+currentLocation.getTime());
		
		if(previousLocation != null) {
			// distance travelled (meters)
			float distance = currentLocation.distanceTo(previousLocation);
			((TextView) findViewById(R.id.distance)).
				setText("Distance: "+distance);
			
			// time taken (in seconds)
			float timeTaken = ((currentLocation.getTime() 
				- previousLocation.getTime())/1000);
			((TextView) findViewById(R.id.time)).
				setText("Time: "+timeTaken);
			
			// calculate speed	
			if(timeTaken > 0) {
				speed = getAverageSpeed(distance, timeTaken);
			}
			
			if(speed >= 0) {
				DecimalFormat df = new DecimalFormat("#.##");
				((TextView) findViewById(R.id.speedometer)).
					setText("Speed: "+df.format(speed));
			}
		}
		previousLocation = currentLocation;
	}
	
	// TODO move to util class?
    private float getAverageSpeed(float distance, float timeTaken) {
    	//float minutes = timeTaken/60;
		//float hours = minutes/60;
    	float speed = 0;
    	if(distance > 0) {
	    	float distancePerSecond = timeTaken > 0 ? distance/timeTaken : 0;
			float distancePerMinute = distancePerSecond*60;
			float distancePerHour = distancePerMinute*60;
			speed = distancePerHour > 0 ? (distancePerHour/1000) : 0;
    	}
    	
    	return speed;
    }
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
