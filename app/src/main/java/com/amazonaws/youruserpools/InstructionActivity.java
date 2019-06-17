package com.amazonaws.youruserpools;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class InstructionActivity extends AppCompatActivity {

    MediaController m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        Button buttonPlayVideo2 = (Button)findViewById(R.id.button1);

        getWindow().setFormat(PixelFormat.UNKNOWN);

////displays a video file
//        VideoView mVideoView2 = (VideoView)findViewById(R.id.videoView1);

//        String uriPath2 = "android.resource://com.amazonaws.youruserpools/"+R.raw.trial;
//        Uri uri2 = Uri.parse(uriPath2);
//        mVideoView2.setVideoURI(uri2);
//        mVideoView2.requestFocus();
//        mVideoView2.start();

        buttonPlayVideo2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = new MediaController(InstructionActivity.this);
                VideoView mVideoView2 = (VideoView) findViewById(R.id.videoView1);
                // VideoView mVideoView = new VideoView(this);
                String uriPath = "android.resource://com.amazonaws.youruserpools/" + R.raw.trial;
                Uri uri2 = Uri.parse(uriPath);
                mVideoView2.setVideoURI(uri2);
                mVideoView2.setMediaController(m);
                m.setAnchorView(mVideoView2);
                mVideoView2.requestFocus();
                mVideoView2.start();

            }


        });
    }
}
