package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class JoyStickView extends View implements ObservableInterface {
private float x = 0;
private float y = 0;
private final float radius = 100;
private float startWid;
private float endWid;
private float startHei;
private float endHei;
private RectF oval;
private Boolean playMoving = false;
private List<ObserverInterface> obs = new LinkedList<>();


    public JoyStickView(Context context) {
        super(context);
    }

    /**
     * draw oval and circle according to x,y,radius
     * @param canvas
     */
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

    /**
     * called when the user resize the window
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.startWid = (float)getWidth()/8;
        this.endWid = (float)getWidth()-((float)getWidth()/8);
        this.startHei = (float)getHeight()/8;
        this.endHei = getHeight()-((float)getHeight()/8);
        this.oval = new RectF(this.startWid,this.startHei , this.endWid, this.endHei);
        returnDefualt();
    }

    /**
     * set default values for x,y, notify observer about the change
     */
    public void returnDefualt() {
        this.x = (float)getWidth()/2;
        this.y = (float)getHeight()/2;
        notifyObservers(new FlightDetails(normelizeAilron(this.x), normelizeElevator(this.y)));
    }

    /**
     * change x,y according user's input, notify observers
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            //if the user touched the screen
            case MotionEvent.ACTION_DOWN: {
                //check if the input is inside the circle
                if(CheckIfInside(event.getX(), event.getY())) {
                    this.playMoving = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!this.playMoving)
                    return true;
                //make sure user input is inside limits
                if (CheckForLimit(event.getX(), event.getY())) {
                    this.x = event.getX();
                    this.y = event.getY();
                    notifyObservers(new FlightDetails(normelizeAilron(this.x), normelizeElevator(this.y)));
                    invalidate();
                }
                break;
            }
            //user input's is finished
            case MotionEvent.ACTION_UP :
                this.playMoving = false;
                returnDefualt();
                //call on draw
                invalidate();
        }
        return true;
    }

    /**
     * check if user touching inside the circle
     * @param xVal
     * @param yVal
     * @return
     */
    Boolean CheckIfInside(float xVal, float yVal) {
        double distance = Math.sqrt((this.x-xVal)*(this.x-xVal) + (this.y-yVal)*(this.y-yVal));
        return (distance <= this.radius);
    }

    /**
     * make sure give x,y inside the oval shape
     * @param xVal
     * @param yVal
     * @return
     */
    Boolean CheckForLimit(float xVal, float yVal) {
        return (this.oval.contains(xVal, yVal));
    }


    public void addToObserver(ObserverInterface obs) {
        this.obs.add(obs);
    }


    public void notifyObservers(FlightDetails flightDetails) {
        for(ObserverInterface obs : this.obs) {
            obs.update(flightDetails);
        }
    }

    public float normelizeAilron(float x) {
        return (x-((this.startWid+this.endWid)/2))/((this.endWid-this.startWid)/2);
    }

    public float normelizeElevator(float y) {
        return (y-((this.startHei+this.endHei)/2))/((this.startHei-this.endHei)/2);
    }

}
