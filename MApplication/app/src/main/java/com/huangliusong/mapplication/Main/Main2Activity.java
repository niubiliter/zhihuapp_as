package com.huangliusong.mapplication.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huangliusong.mapplication.HtmlJsoup.get_json_data;
import com.huangliusong.mapplication.Message.Message_json;
import com.huangliusong.mapplication.R;

public class Main2Activity extends AppCompatActivity {
    private WebView web_views;
    private String http_json;
    private String id;
    private TextView tv_title;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                String content = parseMessage(http_json);
                Log.e("content",content);
                web_views.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        String title = bundle.getString("title");    //获取Bundle里面的字符串
        id = bundle.getString("id");    //获取Bundle里面的字符串
        web_views = (WebView) findViewById(R.id.web_views);
        tv_title=(TextView)findViewById(R.id.message_title);
        //wv_img=(WebView)findViewById(R.id.wv_img);
        //wv_img.loadDataWithBaseURL(null, "<img class=\"avatar\" src=\"http://pic1.zhimg.com/735b212f7cba855a5bdea572405d52ac.jpg\">", "text/html", "utf-8", null);
        tv_title.setText(title);
        new get_message().start();
        //web_views.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        //web_views.loadUrl("http://news-at.zhihu.com/api/4/news/3892357");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class get_message extends Thread {
        public void run() {
            Message msg = new Message();
            try {
                http_json = new get_json_data().http_json(id);
                Log.e("http_json",http_json);
                msg.what = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(msg);
        }
    }

    public String  parseMessage(String json) {
        Gson gson = new Gson();
        // json表示传入要解析的json 生成 返回一个对象
        Message_json message_json=gson.fromJson(json, Message_json.class);
        String str=message_json.body;
        System.out.print(str);
        return str;
    }
}
