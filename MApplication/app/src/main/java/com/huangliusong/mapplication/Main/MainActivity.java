package com.huangliusong.mapplication.Main;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.huangliusong.mapplication.Data.DataBean;
import com.huangliusong.mapplication.Data.Data_ArrayList;
import com.huangliusong.mapplication.Image.ImageShow;
import com.huangliusong.mapplication.Image.Image_array;
import com.huangliusong.mapplication.List.ListAdapter;
import com.huangliusong.mapplication.R;
import com.huangliusong.mapplication.analyze.DataBean_str;
import com.huangliusong.mapplication.analyze.Json_data;
import com.huangliusong.mapplication.constan_data.constans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecycler_View;
    private static Bitmap bmImg;
    private ArrayList<DataBean_str> array_bean;
    private List<DataBean> datas;
    private ProgressDialog pd1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  int lastPos;
    private  int fristPos;
    private LinearLayoutManager layoutManager;
    private ListAdapter adapter;
    private ArrayList<Image_array> image_data = new ArrayList<Image_array>();
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                Data_ArrayList.data_bean = array_bean;
                for (int i = 0; i < array_bean.size(); i++) {
                    DataBean bean = new DataBean();
                    bean.icon = image_data.get(i).image;
                    bean.name = array_bean.get(i).name_title;
                    datas.add(bean);
                }
                adapter=new ListAdapter(MainActivity.this,datas);
                mRecycler_View.setAdapter(adapter);//
                pd1.dismiss();
            }
           else if (msg.what == 2) {
                Data_ArrayList.data_bean = array_bean;
                for (int i = 0; i < array_bean.size(); i++) {
                    DataBean bean = new DataBean();
                    bean.icon = image_data.get(i).image;
                    bean.name = array_bean.get(i).name_title;
                    datas.add(bean);
                }
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecycler_View = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 6000);
            }
        });

        //上拉加载
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecycler_View.setOnScrollListener(new RecyclerView.OnScrollListener(){


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    swipeRefreshLayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    new http_thread_befor().start();
                    /*new Handler().postDelayed(new Runnable() {
                        public void run() {

                        }
                    }, 3000);*/

                }
            }
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPos = layoutManager.findLastVisibleItemPosition();
            }
        });



    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadStaggeredData(false, true);
        PregressDialog();
        pd1.show();
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            loadStaggeredData(false, true);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadStaggeredData(boolean reverse, boolean vertical) {
        datas = new ArrayList<>();
        new http_thread().start();
        //设置布局管理器
        //mRecycler_View加载数据
        //设置布局管理器
        layoutManager= new LinearLayoutManager(this);
        //设置是否反向显示：
        layoutManager.setReverseLayout(reverse);
        fristPos = layoutManager.findFirstVisibleItemPosition();
        lastPos = layoutManager.findLastVisibleItemPosition();
        //int position=layoutManager.findViewByPosition();
        //设置显示方向
        mRecycler_View.setItemAnimator(new DefaultItemAnimator());
        layoutManager.setOrientation(vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mRecycler_View.setLayoutManager(layoutManager);

    }




    //启动子线程获取新数据
    private class http_thread extends Thread {
        public void run() {
            Message msg = new Message();
            try {
                msg.what = 1;
                ImageShow is = new ImageShow();
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
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                array_bean = new ArrayList();
                array_bean = Json_data.parseData(line);
                for (int i = 0; i < array_bean.size(); i++) {
                    String image_url = array_bean.get(i).icon_url;
                    bmImg = is.returnBitMap(image_url);
                    Image_array ia = new Image_array();
                    ia.image = bmImg;
                    image_data.add(ia);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(msg);

        }
    }


    //启动子线程获取新数据
    private class http_thread_befor extends Thread {
        public void run() {
            Message msg = new Message();
            try {
                msg.what = 2;
                ImageShow is = new ImageShow();
                URL url = new URL(constans.URL_BEFOR);
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

                array_bean = new ArrayList();
                array_bean = Json_data.parseData(line);
                for (int i = 0; i < array_bean.size(); i++) {
                    String image_url = array_bean.get(i).icon_url;
                    bmImg = is.returnBitMap(image_url);
                    Image_array ia = new Image_array();
                    ia.image = bmImg;
                    image_data.add(ia);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(msg);

        }
    }

    /**
     * 加载等待窗体
     */

    public void PregressDialog() {
        pd1 = new ProgressDialog(MainActivity.this);
        // 设置对话框的标题
        pd1.setTitle("连接服务器中");
        // 设置对话框显示的内容
        pd1.setMessage("正在为您玩命加载，请稍后...");
        // 设置对话框能用“取消”按钮关闭
        pd1.setCancelable(true);
        // 设置对话框的进度条风格
        pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置对话框的进度条是否显示进度
        pd1.setIndeterminate(true);

    }

}
