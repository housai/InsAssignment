package com.burhanrashid52.imageeditor.network;

import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 创建者:    wangchao
 * 创建时间:   2017/11/6 16:54
 * 描述: TODO
 */
public abstract class JsonCallback extends AbsCallback<JSONObject> {
    @Override
    public JSONObject convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        Log.d("error", s);
        JSONObject jsonObject = new JSONObject(s);
        response.close();
        return jsonObject;
    }

    @Override
    public void onSuccess(JSONObject jsonObject, Call call, Response response) {
        Log.d("oldma", "*");
        Log.d("oldma", "**");
        Log.d("oldma", "***");
        Log.d("oldma", "****");
        Log.d("oldma", "*****");
        Log.d("oldma", jsonObject.toString());
        Log.d("oldma", "*****");
        Log.d("oldma", "****");
        Log.d("oldma", "***");
        Log.d("oldma", "**");
        Log.d("oldma", "*");
        try {
            onSucess(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        String url = request.getBaseUrl();
        HttpParams params = request.getParams();
        params.toString();
        Log.d("oldma", "*");
        Log.d("oldma", "**");
        Log.d("oldma", "***");
        Log.d("oldma", "****");
        Log.d("oldma", "*****");
        Log.d("oldma", url.toString());
        Log.d("oldma", params.toString());
        Log.d("oldma", "*****");
        Log.d("oldma", "****");
        Log.d("oldma", "***");
        Log.d("oldma", "**");
        Log.d("oldma", "*");
    }

    @Override
    public void onAfter(JSONObject jsonObject, Exception e) {
        super.onAfter(jsonObject, e);

    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        onError(e);
    }

    public abstract void onSucess(JSONObject jsonObject);

    public abstract void onError(Exception e);
}
