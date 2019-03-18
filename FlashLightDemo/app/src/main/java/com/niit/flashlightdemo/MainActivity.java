package com.niit.flashlightdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Switch sw;
    ImageView iv;
    TextView on,off,msg,last;
    int arr[]={R.drawable.blb,R.drawable.bnn};
    static final int REQUESTCODE=123;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.imageView);
        sw=findViewById(R.id.swt1);
        iv.setImageResource(arr[0]);
        on=findViewById(R.id.on);
        msg=findViewById(R.id.msg);
        off=findViewById(R.id.off);
        on.setVisibility(View.GONE);
        off.setVisibility(View.VISIBLE);
        msg.setText("Flash Light : OFF");
        last=findViewById(R.id.textView4);


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUESTCODE);
                    }
                    else
                    {
                        CameraManager cameraManager = (CameraManager) getApplicationContext().getSystemService(CAMERA_SERVICE);

                        try
                        {
                            on.setVisibility(View.VISIBLE);
                            off.setVisibility(View.GONE);
                            msg.setText("Flash Light : ON");
                            last.setText("Bandh Kar");
                            iv.setImageResource(arr[1]);
                            String cameraId = cameraManager.getCameraIdList()[0];
                            cameraManager.setTorchMode(cameraId, true);
                        }
                        catch (CameraAccessException e) {

                        }
                    }
                }
                else
                {
                    CameraManager cameraManager = (CameraManager) getApplicationContext().getSystemService(CAMERA_SERVICE);

                    try
                    {
                        iv.setImageResource(arr[0]);
                        on.setVisibility(View.GONE);
                        off.setVisibility(View.VISIBLE);
                        msg.setText("Flash Light : OFF");
                        last.setText("Chalu Kar");
                        String cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, false);
                    }
                    catch (CameraAccessException e) {

                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        CameraManager cameraManager = (CameraManager) getApplicationContext().getSystemService(CAMERA_SERVICE);

        try
        {
            iv.setImageResource(arr[0]);
            on.setVisibility(View.GONE);
            off.setVisibility(View.VISIBLE);
            msg.setText("Flash Light : OFF");
            last.setText("Chalu Kar");
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        }
        catch (CameraAccessException e)
        {

        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        if(requestCode==REQUESTCODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                CameraManager cameraManager = (CameraManager) getApplicationContext().getSystemService(CAMERA_SERVICE);

                try
                {
                    iv.setImageResource(arr[1]);
                    on.setVisibility(View.VISIBLE);
                    off.setVisibility(View.GONE);
                    msg.setText("Flash Light : ON");
                    last.setText("Bandh Kar");
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, true);
                }
                catch (CameraAccessException e) {

                }
            }
            else
            {
                 finish();
            }
        }
    }
}


