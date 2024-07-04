package com.example.executorandhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    int time = 3000;

    TextView intro;
    ImageView image;
    Button threadBtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intro = findViewById(R.id.textView);
        image = findViewById(R.id.imageView);
        threadBtn = findViewById(R.id.threadBtn);

        /* EXECUTORSERVICE & HANDLERS
         *  THE NEWER VERSION TO REPLACE ASYNC TASK. ITS CONSIDERED DEPRECATED SINCE ANDROID 30
         *  INSTANTIATE AS Executors.newFixedThreadPool(1); or Executors.newSingleThreadExecutor();
         *  HANDLER IS A FUNCTION THAT ACTS AS THE BRINDGE FOR THE UI THREAD
         *  LOOPER OF getMainLooper IS REFERENCING THE UI THREAD */
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler threadHandler = new Handler(Looper.getMainLooper());

        threadBtn.setOnClickListener(view -> {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    // CALL FUNCTION OR WORK THAT WILL BE EXECUTED ASYNCHRONOUSLY
                    // FUNCTION MUST BE A STATIC. EXAMPLE: https://www.youtube.com/watch?v=nKBKe1O_W5A
                    // TESTING METHOD THAT CAN CALL A NON-STATIC FROM THE THREAD
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = ProgressDialog.show(MainActivity.this,
                                    "ProgressDialog",
                                    "Wait for" + (time/1000) + "second(s)");
                        }
                    });

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    threadHandler.post( new Runnable( ) {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    } );

                }
            } );

            service.shutdown();
        } );
    }
}