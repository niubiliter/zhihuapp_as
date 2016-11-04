package com.huangliusong.mapplication.analyze;

import android.util.Log;

import com.huangliusong.mapplication.constan_data.constans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/5.
 */
public class GetData {
    private String line;
    private ArrayList<DataBean_str> array_bean;

    public void get_json() {
        new http_thread().start();
    }

    private class http_thread extends Thread {
        public void run() {
            try {
                URL url = new URL(constans.URL_LASTES);
                // 打开URL
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setRequestProperty("contentType", "UTF-8");
                // 得到输入流，即获得了网页的内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                // 读取输入流的数据，并显示
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                Log.e("line",line);
                array_bean = new ArrayList();
                array_bean = Json_data.parseData(line);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
