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
    private CameraPreview mPreview;
    private Button takePictureButton;
    private Uri uri;
    private String uriString;

    public static final String TAG_URI="uri";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initialization();


    }

    private void initialization() {
        takePictureButton = (Button) findViewById(R.id.take_picture_button_camera_activity);
        mCamera = RandomShit.getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        takePictureButton.setOnClickListener(new onTakePictureClickListener());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCamera.stopPreview();
        mCamera.release();
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
