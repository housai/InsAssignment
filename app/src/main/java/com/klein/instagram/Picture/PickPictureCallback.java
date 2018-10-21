package com.klein.instagram.Picture;
import java.util.List;
/**
 * * Created by Weikang on 2018/10/10.
 * */
    public interface PickPictureCallback {
        void onStart();

        void onSuccess(List<PictureTotal> list);

        void onError();
    }

