package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class JoyStrickView extends View implements ObservableInterface {
private float x;
private float y;
private float radius;
private RectF oval;
private Boolean playMoving = false;
private List<ObserverInterface> obs = new LinkedList<>();


    public JoyStrickView(Context context) {
        super(context);
        this.radius = 100;
    }

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 0, 0));
        myPaint.setStrokeWidth(10);

        Paint myPaint2 = new Paint();
        myPaint2.setColor(Color.rgb(0, 0, 100));
        myPaint2.setStrokeWidth(10);

        canvas.drawOval(this.oval, myPaint2);
        canvas.drawCircle(this.x, this.y, this.radius, myPaint);

    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        returnDefualt();
        this.oval = new RectF((float)getWidth()/8,(float)getHeight()/8 , getWidth()-((float)getWidth()/8), getHeight()-((float)getHeight()/8));
    }

    public void returnDefualt() {
        this.x = (float)getWidth()/2;
        this.y = (float)getHeight()/2;
    }


    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if(CheckIfInside(event.getX(), event.getY())) {
                    this.playMoving = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!this.playMoving)
                    return true;
                if (CheckForLimit(event.getX(), event.getY())) {
                    this.x = event.getX();
                    this.y = event.getY();
                    notifyObservers(new FlightDetails(this.x, this.y));
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP :
                this.playMoving = false;
                returnDefualt();
                invalidate();
        }
        return true;
    }

    Boolean CheckIfInside(float xVal, float yVal) {
        double distance = Math.sqrt((this.x-xVal)*(this.x-xVal) + (this.y-yVal)*(this.y-yVal));
        return (distance <= this.radius);
    }

    Boolean CheckForLimit(float xVal, float yVal) {
        return (this.oval.contains(xVal, yVal) &&
                this.oval.contains(xVal, yVal+radius) &&
                this.oval.contains(xVal, yVal-radius) &&
                this.oval.contains(xVal+radius, yVal) &&
                this.oval.contains(xVal-radius, yVal));
    }


    public void addToObserver(ObserverInterface obs) {
        this.obs.add(obs);
    }


    public void notifyObservers(FlightDetails flightDetails) {
        for(ObserverInterface obs : this.obs) {
            obs.update(flightDetails);
        }
    }

}
