package com.ss.android.ugc.demo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

public class ClockHandlerThread extends HandlerThread implements Handler.Callback{

    private Handler handler;

    private OnTimeChangeListener listener;

    public static final int MSG_TIME_CHANGE=100;

    public ClockHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared(){
        super.onLooperPrepared();
        handler=new Handler(getLooper(),this);
        handler.sendEmptyMessage(MSG_TIME_CHANGE);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if(msg.what==MSG_TIME_CHANGE){
            if(listener!=null)
                listener.afterChanged();
            handler.sendEmptyMessageDelayed(MSG_TIME_CHANGE,1000);
        }
        return false;
    }

    public void setOnTimeChangeLister(OnTimeChangeListener onTimeChangeLister){
        this.listener=onTimeChangeLister;
    }

    public interface OnTimeChangeListener {

        void afterChanged();
    }
}
