package com.huangliusong.mapplication.analyze;

/**
 * Created by Administrator on 2016/2/5.
 */
import java.util.ArrayList;

public class Data_json {
    public ArrayList<STORIES> stories;
    public ArrayList<Top_stories> top_stories;
    public class STORIES {
        public String title;
        public String id;
        public ArrayList images;
    }
    public class Top_stories{
        public String  title;
        public String  id;
        public String  image;

    }
}

