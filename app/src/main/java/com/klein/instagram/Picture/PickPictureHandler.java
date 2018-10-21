package com.klein.instagram.Picture;

import android.os.Handler;
import android.os.Message;

import java.util.List;

/**
 * Created by Weikang on 2018/10/10.
 */
class PickPictureHandler extends Handler {
    final static int SCAN_OK = 1;
    final static int SCAN_ERROR = 2;

    private PickPictureCallback mCallback;

    public PickPictureHandler(PickPictureCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SCAN_OK:
                List<PictureTotal> list = (List<PictureTotal>) msg.obj;
                mCallback.onSuccess(list);
                break;
            case SCAN_ERROR:
                mCallback.onError();
                break;
        }
    }
}