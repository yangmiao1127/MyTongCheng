package com.example.ttt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.ttt.R;

/**
 * Created by 1 on 2015/10/5.
 */
public class UserInfoActivity extends Activity{
    private ImageView userImage;
    private TextView userName;
    private String userName1;
    private String userImageUrl;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        userImage= (ImageView) findViewById(R.id.user_image);
        userName= (TextView) findViewById(R.id.user_name);
        Intent intent=getIntent();
        userName1=intent.getStringExtra("userName");
        userImageUrl=intent.getStringExtra("userImage");
        userId=intent.getStringExtra("userId");

        Log.d("IIIII",userName1);
        Log.d("IIIII",userImageUrl);
        Log.d("IIIII",userId);

        userName.setText(userName1);
        setTitle("查看详情");

        String url=userId;

        RequestQueue queue= Volley.newRequestQueue(this);
        ImageLoader imageLoader=new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener=ImageLoader.getImageListener(userImage, R.drawable.ic_perm_identity_blue_400_18dp,R.drawable.arrow);
        imageLoader.get(url, listener);
        String website="https://api.douban.com/v2/event/user_created/"+userImageUrl;


    }
}
