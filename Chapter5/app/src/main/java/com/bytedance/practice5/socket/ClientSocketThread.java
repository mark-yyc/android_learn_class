package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketThread extends Thread {
    public static final String TAG="socket";

    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    //head请求内容
    private static String content = "HEAD /xxjj/index.html HTTP/1.1\r\nHost:www.sjtu.edu.cn\r\n\r\n";

    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        try{
            Socket socket=new Socket("www.sjtu.edu.cn",80);
            BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is=new BufferedInputStream((socket.getInputStream()));
            byte[] data=new byte[1024*5];
            if(socket.isConnected()){
                    Log.d(TAG,"client send:"+content);
                    os.write(content.getBytes());
                    os.flush();
                    int receiveLen=is.read(data);
                    String receive=new String(data,0,receiveLen);
                    Log.d(TAG,"client receive: "+receive);
                    callback.onResponse(receive);
            }
            Log.d(TAG,"client disconnect");
            os.flush();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}