package com.stecon.patipan_on.diarycar.my_class_temp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by patipan_on on 11/15/2017.
 */

public class MyModifyJson {

    private String strUrl = "http://csclub.ssru.ac.th/s56122201044/csc4202/monitor/php_get_monitor.php";

    public MyModifyJson() {
        Request request = new Request.Builder()
                .get()
                .url(strUrl)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Check =>", "onFailed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Check => ", "onResponse");
                String dataResponse = response.body().string();
                int codeResponse = response.code();
                Log.d("Response data=> ", dataResponse);
                Log.d("codeResponse => ", codeResponse +"");

                try {
//                    JSONObject jsonObject = new JSONObject(dataResponse);
//                    String product_id = jsonObject.getString("product_id");
//                    Log.d("prpduct id => ", product_id.toString());
                    final JSONArray jsonArray = new JSONArray(dataResponse);
                    final String[] iconString = new String[jsonArray.length()];
                    final String[] titleString = new String[jsonArray.length()];
                    final String[] priceString = new String[jsonArray.length()];
                    final String[] brandString = new String[jsonArray.length()];
                    final String[] sizeStrings = new String[jsonArray.length()];
                    final String[] curveStrings = new String[jsonArray.length()];
                    final String[] typeStrings = new String[jsonArray.length()];
                    final String[] detailStrings = new String[jsonArray.length()];

                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        iconString[i] = jsonObject.getString("image");
                        titleString[i] = jsonObject.getString("product_name");
                        priceString[i] = jsonObject.getString("price");
                        brandString[i] = jsonObject.getString("brand_name");
                        sizeStrings[i] = jsonObject.getString("size");
                        curveStrings[i] = jsonObject.getString("curve");
                        typeStrings[i] = jsonObject.getString("type_name");
                        detailStrings[i] = jsonObject.getString("product_detail");

                    }
                    String temp = jsonArray.getJSONObject(0).getString("product_name");
                    Log.d("temp => ", temp);

                } catch (JSONException e) {
                    Log.d("JSONException e => ", e.toString());
                }

                //Chang Json

            }
        });
    }
}
