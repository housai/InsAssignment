package com.klein.instagram.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.CursorLoader;
import android.util.Log;
/**
 * Image editing utils
 * @author yaozu
 *
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();


    /**
     * get content by URI
     * @param contentUri
     * @return
     */
    public static String getRealPathByURI(Uri contentUri,Context context) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri,
                proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * Create image URI, used for storing image after photo
     *
     * @param context
     * @return image URI
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        // ContentValues includes the metadata we want upon image creation
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// Checks for SD card, if none, use internal storage
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        Log.i("", "image file path：" + imageFilePath.toString());
        return imageFilePath;
    }

    /**
     * Compress image
     *
     * @param
     * @param file
     */
    public static void compressBmpToFile(File file,int height,int width) {
        Bitmap bmp = decodeSampledBitmapFromFile(file.getPath(), height, width);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		/*while (baos.toByteArray().length / 1024 > 30) {
			baos.reset();
			if (options - 10 > 0) {
				options = options - 10;
				bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			}
			if (options - 10 <= 0) {
				break;
			}
		}*/
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert image to bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getImageBitmap(String path) {
        Bitmap bitmap = null;
        File file = new File(path);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }


//=================================Image Compression Method===============================================

    /**
     * Size compress
     * @author ping 2015-1-5 下午1:29:58
     * @param image
     * @param maxkb
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image,int maxkb) {
        //L.showlog(compressed image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Size compression, when 100 we don't compress, it is placed in baos
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        int options = 100;
        // Continuously condition check is maxkb is larger than 50kb continue to compress
        while (baos.toByteArray().length / 1024 > maxkb) {
            // Reset and clear baos
            baos.reset();
            if(options-10>0){
                // Reduce by 10 each time
                options -= 10;
            }
            // Compress options% here, after compression place in baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // Place compressed in baos into ByteArrayInputStream
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // Convert ByteArrayInputStream to bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     *
     * @param res
     * @param resId
     * @param reqWidth
     *            Width Size of compressed image
     * @param reqHeight
     *            Height Size of compressed image
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     *
     * @param filepath
     * 			 image path
     * @param reqWidth
     *			required image width
     * @param reqHeight
     *          required image height
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filepath,int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     *
     * @param bitmap
     * @param reqWidth
     * 			required Width
     * @param reqHeight
     * 			required Height
     * @return
     */
    public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap,
                                                       int reqWidth, int reqHeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] data = baos.toByteArray();

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * Calculate compression ration(improved by touch_ping)
     *
     * original 2>4>8...x Compression
     * current 2>3>4...x Compression
     *
     * @param options
     *            decode options
     * @param reqWidth
     *            required width minimum 0
     * @param reqHeight
     *            required height minimum 0
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int picheight = options.outHeight;
        final int picwidth = options.outWidth;

        int targetheight = picheight;
        int targetwidth = picwidth;
        int inSampleSize = 1;

        if (targetheight > reqHeight || targetwidth > reqWidth) {
            while (targetheight  >= reqHeight
                    && targetwidth>= reqWidth) {
                inSampleSize += 1;
                targetheight = picheight/inSampleSize;
                targetwidth = picwidth/inSampleSize;
            }
        }

        Log.i("===","Final Compression Ration:" +inSampleSize + "x");
        Log.i("===", "New size:" +  targetwidth + "*" +targetheight);
        return inSampleSize;
    }

    // Read image rotation
    public static int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * Rotate image by a degree
     *
     * @param bm
     *            image bitmap to be rotated
     * @param degree
     *            rotation degree
     * @return image after rotation
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // Create transformation matrix from rotation degree
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // Transform original image with matrix
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     *
     * @param mBitmap
     * @param fileName
     */
    public static void saveBitmapToLocal(Bitmap mBitmap,String fileName) {
        if(mBitmap != null){
            FileOutputStream fos = null;
            try {
                File file = new File(fileName);
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                fos = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{

                try {
                    if(fos != null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Save image to SD card and return path
     * @param context
     * @param bitName
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context,String bitName, Bitmap mBitmap) {
        String path = null;
        File f;
        if(mBitmap != null){
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                f = new File(Environment.getExternalStorageDirectory() + File.separator +"images/");
                String fileName = Environment.getExternalStorageDirectory() + File.separator +"images/"+ bitName + ".png";
                path = fileName;
                FileOutputStream fos = null;
                try {
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File file = new File(fileName);
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{

                    try {
                        if(fos != null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                //local path
                f = new File(context.getFilesDir() + File.separator +"images/");
                Log.i(TAG, "Local Path:"+context.getFilesDir() + File.separator +"images/"+ bitName + ".png");
                path = context.getFilesDir() + File.separator +"images/"+ bitName + ".png";
                FileOutputStream fos = null;
                try {
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File file = new File(path);
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        if(fos != null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return path;
    }

    /**
     * Delete image
     * @param context
     * @param bitName
     */
    public void deleteFile(Context context,String bitName) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File dirFile = new File(Environment.getExternalStorageDirectory() + File.separator + "images/"+ bitName + ".png");
            if (!dirFile.exists()) {
                return;
            }

            dirFile.delete();
        } else {
            File f = new File(context.getFilesDir() + File.separator
                    + "images/" + bitName + ".png");
            if(!f.exists()){
                return;
            }
            f.delete();
        }
    }


    /**
     * Get absolute path from image URI
     * @return If URI to image exists return absolute path otherwise null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * for > api19 can get real path from URI
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = { MediaStore.Images.Media.DATA };
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = { id };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * api11-18 get real path from URI
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * for <api11 get real path from URI
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

}

