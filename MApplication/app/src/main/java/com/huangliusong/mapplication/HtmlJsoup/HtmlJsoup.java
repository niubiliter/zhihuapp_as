package com.huangliusong.mapplication.HtmlJsoup;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/2/8.
 */
public class HtmlJsoup {
    StringBuilder htmlAy = new StringBuilder();// 保存解析后的文字
    StringBuilder htmlAy1 = new StringBuilder();// 保存解析后的文字

    /**
     * 解析HTML 返回登录后的状态！
     *
     * @param html
     * @return String 信息
     */
    public String html_content(String html) {
        Document doc = Jsoup.parse(html);
        Log.e("ss",html);
        Elements linkStrs = doc.getElementsByClass("content");
        for (Element linkStr : linkStrs) {
            // text()得到文本值 attr(String key) 获得元素的数据 getElementsByTag:通过标签获得元素
            String content = linkStr.getElementsByClass("content").text();
            htmlAy.append(content);
            System.out.println("\n\n内容:" + content);
        }
        Log.e("ss",htmlAy.toString());
        return htmlAy.toString();
    }
    public String html_author(String html) {
        Document doc1 = Jsoup.parse(html);
        Elements linkStrs1 = doc1.getElementsByClass("author");
        for (Element linkStr1 : linkStrs1) {
            // text()得到文本值 attr(String key) 获得元素的数据 getElementsByTag:通过标签获得元素
            String author= linkStr1.getElementsByClass("author").text();
            htmlAy1.append(author);
            System.out.println("\n\n作者:" + author);
        }
        System.out.println("htmlAy1"+htmlAy1.toString());
        return htmlAy1.toString();
    }
}
