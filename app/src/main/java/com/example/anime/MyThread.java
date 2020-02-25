package com.example.anime;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread {
    private final int REDRAW_TIME =100000;
    private final int ANIMATION_TIME= 1500;
    private boolean flag;
    private long startTime;
    private long prevRedrawTime;
    private Paint paint;
    private ArgbEvaluator argbEvaluator;
    private SurfaceHolder surfaceHolder;

    MyThread(SurfaceHolder holder){
        surfaceHolder = holder;
        flag = false;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        argbEvaluator = new ArgbEvaluator();
    }
    public long getTime(){
        return System.nanoTime()/1000;

    }

    @Override
    public void run() {
        Canvas canvas;
        startTime = getTime();
        while (flag){
            long currentTime = getTime();
            long elapsedTime = currentTime-prevRedrawTime;
            if (elapsedTime < REDRAW_TIME){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            prevRedrawTime = getTime();

        }
    }

    private void draw(Canvas canvas) {
        long curTime = getTime()-startTime;
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawColor(Color.BLACK);
        int centerX = width/2;
        int centerY = height/2;
        float radius = Math.min(width, height)/2;
        Random random = new Random();
        //int colour = Color.argb(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255));
        float fraction = (float)(curTime%ANIMATION_TIME)/ANIMATION_TIME;
        int color = (int)argbEvaluator.evaluate(fraction,Color.BLUE, Color.GREEN);
        paint.setColor(color);
        canvas.drawCircle(centerX,centerY,radius*fraction,paint);

    }
    public void setRunning(boolean b) {
        flag = b;
        prevRedrawTime = getTime();
    }



}
