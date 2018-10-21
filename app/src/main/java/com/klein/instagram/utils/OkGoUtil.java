package com.klein.instagram.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.klein.instagram.base.BaseActivity;
import com.klein.instagram.network.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
/**
 * Created by Weikang on 2018/10/10.
 */
public class OkGoUtil {

    //Default print
    private static boolean isDBUG = false;
    //Default timeout
    private static int Num = 30;
    //Set all reconnection time to 30s
    private static int time = 120000;
    private static OkGo go;

    //Temporarily load one by one
    private static OkGo getInstance() {
        if (go == null) {
            go = OkGo.getInstance();
            if (isDBUG) {
                go.debug("OkGo", Level.INFO, true);
            }
            go.setRetryCount(Num)
                    .setConnectTimeout(time)
                    .setReadTimeOut(time)
                    .setCookieStore(new MemoryCookieStore())
                    .setWriteTimeOut(time)
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
        }
        return go;
    }

    ;
    /**
     * Simple GET request
     *
     * @param context
     * @param url
     * @param map
     * @param call
     */
    public static void jsonGet(final Context context, String url, Map<String, String> map, JsonCallback call) {
        Log.d("oldma", "*****");
        Log.w("oldma", url.toString());
        Log.d("oldma", "*****");
        GetRequest request = getInstance().get(url);
        request.params(map);
        request.tag(context);
        request.execute(call);
    }


    /**
     * Simple POST request
     *  @param context
     * @param url
     * @param map
     * @param b
     * @param call
     */
    public static void jsonPost(final Context context, String url, Map<String, String> map, boolean b, JsonCallback call) {
        Log.d("oldma", "*****");
        Log.w("oldma", url.toString());
        if(map!=null){
            Log.w("oldma", map.toString());
        }
        Log.d("oldma", "*****");
        PostRequest request = getInstance().post(url);
        request.params(map);
        request.tag(context);
        request.execute(call);
    }

    //Cancel request any time
    public static void cancle(Context con) {
        getInstance().cancelTag(con);
    }


}
