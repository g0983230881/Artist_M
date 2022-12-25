package com.example.artist_m;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "verbose";
    Button btn_picker, b1, b2, b4, b5, b6, b7, b8;
    ImageView img, img2;
    ContentResolver resolver;
    Uri srcUri;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.brown2));
        setContentView(R.layout.activity_main);

        img =  findViewById(R.id.imageView);
        img2 = findViewById(R.id.imageView2);

        resolver=this.getContentResolver();

        btn_picker=findViewById(R.id.btn_picker);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button3);

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }


        btn_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //選擇相片後接回傳
                startActivityForResult(intent, 1);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img.getDrawable() != null){
                    Togray();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img.getDrawable() != null){
                    Tocorrosion();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img.getDrawable() != null){
                    Todilate();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img.getDrawable() != null){
                    TomedianBlur();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(img.getDrawable() != null){
                    ToGaussian();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(img.getDrawable() != null){
                   CannyScan();
                }
                else{
                    Toast.makeText(MainActivity.this, "請先選照片，再做後續處理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img2.getDrawable() != null){
                    SavePng();
                }
                else{
                    Toast.makeText(MainActivity.this, "請選擇其一處理方式，再儲存圖片", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void Togray(){
        //灰階處理
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Mat src = new Mat();
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap,src);
        Imgproc.cvtColor(src,dst,Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(dst,bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    private void Tocorrosion(){
        //腐蝕
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Mat src = new Mat();
        Mat dst = new Mat();
        Mat element = Imgproc.getStructuringElement(MORPH_RECT, new Size(10,10));

        Utils.bitmapToMat(bitmap,src);
        Imgproc.erode(src, dst, element);
        Utils.matToBitmap(dst,bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    private void Todilate(){
        //膨胀
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Mat src = new Mat();
        Mat dst = new Mat();
        Mat element = Imgproc.getStructuringElement(MORPH_RECT, new Size(10,10));;

        Utils.bitmapToMat(bitmap,src);
        Imgproc.dilate(src, dst, element);
        Utils.matToBitmap(dst,bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    private void TomedianBlur(){
        //中值濾波
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Mat src = new Mat();
        Mat ret = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.medianBlur(src, ret, 31);
        Utils.matToBitmap(ret, bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        ret.release();
    }

    private void ToGaussian(){
        //高斯模糊
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Mat src = new Mat();
        Mat ret = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.GaussianBlur(src,ret,new Size(7,7),5, 5);
        Utils.matToBitmap(ret, bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        ret.release();
    }

    private void CannyScan(){
        //边缘检测
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(srcUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);

        Mat gray = new Mat();
        Imgproc.cvtColor(src,gray,Imgproc.COLOR_BGR2GRAY);//灰度处理
        Mat ret = src.clone();
        Imgproc.Canny(src, ret, 20, 80);
        Utils.matToBitmap(ret, bitmap);

        img2.setImageBitmap(bitmap);
        src.release();
        gray.release();
        ret.release();
    }

    private void SavePng() {
        Bitmap bitmap = ((BitmapDrawable)img2.getDrawable()).getBitmap();
        ContentResolver cr = getContentResolver();
        String title = "myBitmap";
        String description = "My bitmap created by Android-er";
        String savedURL = MediaStore.Images.Media
                .insertImage(cr, bitmap, title, description);

        Toast.makeText(MainActivity.this,
                savedURL,
                Toast.LENGTH_LONG).show();
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                // now we can call opencv code !

            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.d("debug", "onResume");

        if (mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug", "onPause");
        mediaPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug", "onStop");
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug", "onDestroy");
        mediaPlayer.release();
        mediaPlayer = null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();//取得相片路徑
                srcUri = uri;
                try {
                    //將該路徑的圖片轉成bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                    //設定ImageView圖片
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
}