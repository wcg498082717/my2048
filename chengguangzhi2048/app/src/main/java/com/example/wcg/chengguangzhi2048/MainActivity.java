package com.example.wcg.chengguangzhi2048;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvScore;
    private int score=0;
    private static MainActivity mainActivity = null;

    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height = dm.heightPixels;

        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
//        预加载图片
//        BitmapFactory.decodeResource(getResource.R.drawable.img,options);
        //获得图像大小
        int bw = options.outWidth;
        int bh = options.outHeight;
//计算图像与视图比例
        int sw = bw/width;
        int sh = bh/height;

        int sampleSize=Math.max(sw,sh);
        //加载图片
        options.inJustDecodeBounds =false;
        options.inSampleSize=sampleSize;
//Bitmap bitmap = BitmapFactory.decodeResource()
//imageview.setimag......


        score=0;
        tvScore = (TextView) findViewById(R.id.tv_score);
    }
    public void clearscore(){
        score=0;
        showscore(0);
    }
    public void addscore(int s){
        this.score+=s;
        showscore();
    }
    public void showscore(){
        tvScore.setText(score + "");
    }
    public void showscore(int s){
//        此处有BUG，大BUG
        tvScore.setText(s+"");
    }

    public int getScore() {
        Log.i("wcg", tvScore.getText().toString() + " " + Integer.parseInt(tvScore.getText().toString()));
        return Integer.parseInt(tvScore.getText().toString());
    }
}
