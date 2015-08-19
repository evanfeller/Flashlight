package com.example.ejf011.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    public static Camera cam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turnOn();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnOn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isLightOn(){
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void toggleLight(){
        try{
            if ( isLightOn() )
                turnOff();
            else
                turnOn();
        } catch ( Exception e ){
            Log.d("Exception", e.toString());
        }
    }

    public void turnOff(){
        try {
        cam.stopPreview();;
        cam.release();
        cam = null;
        } catch (Exception e ) { Log.d("Exception - Off : ", e.toString());}
    }

    public void turnOn(){
        try {
            cam = Camera.open();
            Camera.Parameters p = cam.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
        } catch (Exception e ) { Log.d("Exception - On : ", e.toString());}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        turnOff();
    }
}
