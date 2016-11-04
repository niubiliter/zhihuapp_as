package com.huangliusong.mapplication.HtmlJsoup;

import android.util.Log;

import com.huangliusong.mapplication.Image.ImageShow;
import com.huangliusong.mapplication.constan_data.constans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/8.  获取json数据
 */
public class get_json_data {
    public String http_json(String id){
        try{
            ImageShow is = new ImageShow();
            URL url = new URL(constans.URL_MESSAGE+id);
            Log.e("id",id);
            // 打开URL
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("contentType", "UTF-8");
            // 得到输入流，即获得了网页的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            // 读取输入流的数据，并显示
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            line = sb.toString();
            return line;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
