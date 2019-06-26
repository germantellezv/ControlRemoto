package com.example.controlremoto;
//package com.example.joysticktest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;

    private void setupDimensions()
    {
        centerX = getWidth()/2;
        centerY = getWidth()/2;
        baseRadius = Math.min(getWidth(),getHeight())/4;
        hatRadius = Math.min(getWidth(),getHeight())/6;
    }

    public JoystickView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
        {
            joystickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes, int style)
    {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
        {
            joystickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener)
        {
            joystickCallback = (JoystickListener) context;
        }
    }


    private void drawJoystick(float newX, float newY)
    {
        if(getHolder().getSurface().isValid())
        {

            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();

            //myCanvas.drawColor(0x00AAAAAA, PorterDuff.Mode.CLEAR); // con esta linea puedo controlar el color de fondo del layout del joystick
            myCanvas.drawARGB(255,25,118,210); // COLOR DE FONDO DEL JOYSTICK VIEW. ALPHA ES LA TRANSPARENCIA.
            colors.setARGB(100,50,50,50); //colors.setARGB(int alpha,int red, int green, int blue); // colors of joystick itself
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // Draw the joystick base
            colors.setARGB(255,128,214,255);
            myCanvas.drawCircle(newX,newY,hatRadius,colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();

        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        if(v.equals(this))
        {
            if(e.getAction() != e.ACTION_UP)
            {
                float displacement = (float) Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY, 2));
                if(displacement < baseRadius)
                {
                    drawJoystick(e.getX(), e.getY());
                    joystickCallback.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius, getId());
                    //joystickCallback.onJoystickMoved(e.getX()-centerX,centerY-e.getY(),centerX,centerY, baseRadius, getId());
                }
                else
                {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX)*ratio;
                    float constrainedY = centerY + (e.getY() - centerY)*ratio;
                    drawJoystick(constrainedX, constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());

                }

            }
            else
            {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }

        return true;
    }

    public interface JoystickListener
    {
        void onJoystickMoved(float xPercent, float yPercent, int source);
        //void onJoystickMoved(float posX, float posY, float centroX, float centroY, float radio, int source);
    }
}
