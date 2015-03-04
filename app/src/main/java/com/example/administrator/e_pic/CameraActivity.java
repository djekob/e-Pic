package com.example.administrator.e_pic;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;


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

        initialization();


    }

    private void initialization() {
        frontCamOn = false;
        takePictureButton = (Button) findViewById(R.id.take_picture_button_camera_activity);
        switchSidesButton = (Button) findViewById(R.id.switch_camera_sides);
        mCamera = RandomShit.getCameraInstance(BACK_CAM_CODE);
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

    private class onSwitchCameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mCamera.stopPreview();
            mCamera.release();
            if(frontCamOn) {
                frontCamOn=false;
                mCamera = RandomShit.getCameraInstance(BACK_CAM_CODE);
                mPreview = new CameraPreview(getContext(), mCamera);
                preview.removeAllViews();
                preview.addView(mPreview);
                mCamera.startPreview();
            } else if(!frontCamOn) {
                frontCamOn=true;
                mCamera = RandomShit.getCameraInstance(FRONT_CAM_CODE);
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
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            intent.putExtra(TAG_URI, uriString);
            getContext().startActivity(intent);
        }
    }
    private Context getContext() {
        return this;
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = RandomShit.getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                uriString = (Uri.fromFile(pictureFile)).toString();
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("", "Error accessing file: " + e.getMessage());
            }
        }
    };
}
