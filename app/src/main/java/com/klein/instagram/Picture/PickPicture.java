package com.klein.instagram.Picture;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class PickPicture {


    /**
     * * Created by hupei on 2016/7/14.
     * */
        private Context mContext;
        private HashMap<String, List<String>> mGroupMap = new HashMap<>();
        private List<PictureTotal> mPictureItems = new ArrayList<>();
        private PickPictureThread mThread;
        private PickPictureHandler mHandler;
        private PickPictureCallback mCallback;

        public PickPicture(Context context, PickPictureCallback callback) {
            this.mContext = context;
            this.mCallback = callback;
            mThread = new PickPictureThread() {
                @Override
                public void pickPictureThreadRun() {
                    readPicture();
                }
            };
            mHandler = new PickPictureHandler(mCallback);
        }

        void start() {
            mThread.start();
        }

        private void readPicture() {
            mGroupMap.clear();
            mPictureItems.clear();
            Uri pictureUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = mContext.getContentResolver();
            //Only search png and jpeg formats
            Cursor cursor = contentResolver.query(pictureUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            if (cursor == null || cursor.getCount() == 0) {
                mHandler.sendEmptyMessage(PickPictureHandler.SCAN_ERROR);
            } else {
                while (cursor.moveToNext()) {
                    //get path to image
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    try {
                        //get image parent path
                        String parentName = new File(path).getParentFile().getName();
                        //set groupmap according to parent path
                        if (!mGroupMap.containsKey(parentName)) {
                            List<String> chileList = new ArrayList<>();
                            chileList.add(path);
                            mGroupMap.put(parentName, chileList);
                        } else {
                            mGroupMap.get(parentName).add(path);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cursor.close();
                //Tell handler that image scan is complete
                mPictureItems = subGroupOfPicture(mGroupMap);
                Message message = mHandler.obtainMessage();
                message.obj = mPictureItems;
                message.what = PickPictureHandler.SCAN_OK;
                message.sendToTarget();
            }
        }

        /**
         * Convert HashMap of images to a List, because we store the images in HashMap from scan
         * @param groupMap
         * @return
         */
        private List<PictureTotal> subGroupOfPicture(HashMap<String, List<String>> groupMap) {
            List<PictureTotal> list = new ArrayList<>();
            if (groupMap.size() == 0) {
                return list;
            }
            Iterator<Map.Entry<String, List<String>>> it = groupMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<String>> entry = it.next();
                PictureTotal pictureTotal = new PictureTotal();
                String key = entry.getKey();
                List<String> value = entry.getValue();
                SortPictureList sortList = new SortPictureList();
                Collections.sort(value, sortList);//Sort by Modified
                pictureTotal.setFolderName(key);
                pictureTotal.setPictureCount(value.size());
                pictureTotal.setTopPicturePath(value.get(0));//Get first image from list
                list.add(pictureTotal);
            }
            return list;
        }

        List<String> getChildPathList(int position) {
            List<String> childList = new ArrayList<>();
            if (mPictureItems.size() == 0)
                return childList;
            PictureTotal pictureTotal = mPictureItems.get(position);
            childList = mGroupMap.get(pictureTotal.getFolderName());
            SortPictureList sortList = new SortPictureList();
            Collections.sort(childList, sortList);
            return childList;
        }
    }

