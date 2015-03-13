package com.example.administrator.e_pic;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends CustomActivity {

    private Camera mCamera;
    private SurfaceView mPreview;
    private Button takePictureButton, switchSidesButton;
    private Uri uri;
    private String uriString;
    private boolean frontCamOn;
    private FrameLayout preview;

    public static final String TAG_URI="uri";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int BACK_CAM_CODE = 0;
    public static final int FRONT_CAM_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if(checkCameraHardware(this)) initialization();


    }

    private void initialization() {
        frontCamOn = false;
        takePictureButton = (Button) findViewById(R.id.take_picture_button_camera_activity);
        switchSidesButton = (Button) findViewById(R.id.switch_camera_sides);
        mCamera = getCameraInstance(BACK_CAM_CODE);
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);



        switchSidesButton.setOnClickListener(new onSwitchCameraClickListener());
        takePictureButton.setOnClickListener(new onTakePictureClickListener());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCamera.stopPreview();
        mCamera.release();
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(int i){
        Camera c = null;
        try {
            c = Camera.open(i); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private class onSwitchCameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mCamera.stopPreview();
            mCamera.release();
            if(frontCamOn) {
                frontCamOn=false;
                mCamera = getCameraInstance(BACK_CAM_CODE);
                mPreview = new CameraPreview(getContext(), mCamera);
                preview.removeAllViews();
                preview.addView(mPreview);
                mCamera.startPreview();
            } else if(!frontCamOn) {
                frontCamOn=true;
                mCamera = getCameraInstance(FRONT_CAM_CODE);
                mPreview = new FrontCameraPreview(getContext(), mCamera);
                preview.removeAllViews();
                preview.addView(mPreview);
                mCamera.startPreview();

            }

        }
    }
    private class onTakePictureClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mCamera.takePicture(null, null, mPicture);

        }
    }
    private Context getContext() {
        return this;
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                //uriString = (Uri.fromFile(pictureFile)).toString();
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("", "Error accessing file: " + e.getMessage());
            }


            Intent intent = new Intent(getContext(), PictureActivity.class);
            intent.putExtra("naam bestand", pictureFile.getAbsolutePath());
            ((CameraActivity)getContext()).startActivityForResult(intent,1);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String bestand;
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                bestand = data.getStringExtra("naam bestand");
                new Connections(this, bestand, Connections.ADD_PICTURE_CODE);
            }
            else{
                bestand = data.getStringExtra("naam bestand");
                File f = new File(bestand);
                System.out.println(f.delete());
                Intent intent = new Intent(getContext(), PictureActivity.class);
                intent.putExtra("naam bestand", bestand);
                ((CameraActivity)getContext()).startActivityForResult(intent,1);
            }
        }
    }

    static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
