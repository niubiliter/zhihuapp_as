package com.huangliusong.mapplication.analyze;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/5.
 */
public class Json_data {
    static ArrayList<DataBean_str> d_str;
    public static ArrayList<DataBean_str> parseData(String json) {
        Gson gson = new Gson();

        d_str=new ArrayList<DataBean_str>();
        // json表示传入要解析的json 生成 返回一个对象
        Data_json databean = gson.fromJson(json, Data_json.class);
        ArrayList<Data_json.STORIES> stories = databean.stories;
        for (Data_json.STORIES s : stories) {
            DataBean_str ds=new DataBean_str();
            String title = s.title;
            String id_=s.id;
            String url = s.images.get(0).toString();
           // System.out.println("title->>>>  " + title);
            //System.out.println("title->>>>  " + url);
            ds.name_title=title;
            ds.icon_url=url;
            ds.id=id_;
            d_str.add(ds);
        }


        return d_str;
    }
}
