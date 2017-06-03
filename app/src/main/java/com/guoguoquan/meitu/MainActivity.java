package com.guoguoquan.meitu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> photos;
    private FloatingActionButton mFabFloatingActionButton;
    private Toolbar mToolbarToolbar;
    private MyColorMatrixView mImageViewCenterPhoto;
    private Bitmap bitmap;
    private float colorArray[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private DecimalFormat df = new DecimalFormat("00000");
    private SeekBar mProgressBar1, mProgressBar2, mProgressBar3, mProgressBar4,mProgressBar5;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1)
                mImageViewCenterPhoto.setBitmap(bitmap);
            else if (message.what == 2)
                mImageViewCenterPhoto.setMatrix(colorArray);
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void setListener() {
        mFabFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(MainActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });

        mProgressBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println(i + "------------" + Integer.toBinaryString(i) + "=========" + df.format(Integer.valueOf(Integer.toBinaryString(i))));
                sumResult(df.format(Integer.valueOf(Integer.toBinaryString(i))), 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mProgressBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sumResult(df.format(Integer.valueOf(Integer.toBinaryString(i))), 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mProgressBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sumResult(df.format(Integer.valueOf(Integer.toBinaryString(i))), 3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mProgressBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sumResult(df.format(Integer.valueOf(Integer.toBinaryString(i))), 4);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mProgressBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mImageViewCenterPhoto.setRotate(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void sumResult(String format, int n) {
        switch (n) {
            case 1:
                for (int i = 0; i < 5; i++) {
                    colorArray[i] = Integer.valueOf(format.substring(i, i + 1));
                }
                break;
            case 2:
                for (int i = 0; i < 5; i++) {
                    colorArray[5 + i] = Integer.valueOf(format.substring(i, i + 1));
                }
                break;
            case 3:
                for (int i = 0; i < 5; i++) {
                    colorArray[10 + i] = Integer.valueOf(format.substring(i, i + 1));
                }
                break;
            case 4:
                for (int i = 0; i < 5; i++) {
                    colorArray[15 + i] = Integer.valueOf(format.substring(i, i + 1));
                }
                break;
        }

        handler.sendEmptyMessage(2);
        System.out.println(colorArray);

    }

    private void initView() {
        mProgressBar1 = (SeekBar) findViewById(R.id.progressBar1);
        mProgressBar2 = (SeekBar) findViewById(R.id.progressBar2);
        mProgressBar3 = (SeekBar) findViewById(R.id.progressBar3);
        mProgressBar4 = (SeekBar) findViewById(R.id.progressBar4);
        mProgressBar5 = (SeekBar) findViewById(R.id.progressBar5);
        mToolbarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarToolbar);
        mFabFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mImageViewCenterPhoto = (MyColorMatrixView) findViewById(R.id.iv_center_photon);
    }

    private void createBitmap(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(url);
                    bitmap = BitmapFactory.decodeStream(fis);
                    handler.sendEmptyMessage(1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() > 0)
                    createBitmap(photos.get(0));
            }
        }
    }
}
