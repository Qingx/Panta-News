package com.zonghe.one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zonghe.one.JSONNewsEntityClass.Contentlist;
import com.zonghe.one.JSONNewsEntityClass.Imageurls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

public class home_toutiao extends Fragment {
    private static String TAG = "home_toutiao";
    private static bottom_fragment_home context;
    View view;
    Banner toutiao_banner;

    private String jsonString;
    private int showapi_res_code;
    private String showapi_res_error;
    private RecyclerView mRecyclerView;
    private NewsRecyclerListAdapter2 mNewsListAdapter;
    private List<Contentlist> mContentlistList;
    private List<Imageurls> mImageurlsList;
    private List<String> mBannerImagesList;
    private List<String> mBannerTitlesList;
    private ViewGroup mViewGroup;
    private int[] P = new int[6];
    String FILENAME = "toutiao";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int page =1;


    public static home_toutiao createFragment(bottom_fragment_home home_tt) {
        context = home_tt;
        return new home_toutiao();
    }

    @Override
    @Nullable
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_toutiao, container, false);
        //蒋recyclerView开始
        mViewGroup = container;
        mContentlistList = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.RecyclerView_toutiao);
        final Handler handler = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    saveJsonToFile(jsonString);

                    Log.d(TAG, "HandlerMessage: 请求完成即将进入解析");
                    handleJsonData(jsonString);
                    //解析完成后开始展示banner。
                    banner();
                    Log.d(TAG, "handleMessage: +mContentList=" + mContentlistList);
                    Log.d(TAG, "HandlerMessage: 完成解析");
                    if (showapi_res_code == 0) {
                        //Log.d(TAG, "handleMessage: mContentlistList.size()="+mContentlistList.size());
                        mNewsListAdapter = new NewsRecyclerListAdapter2(container.getContext(), mContentlistList);
                        mRecyclerView.setAdapter(mNewsListAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));

                        mNewsListAdapter.setOnItemClickListener(new NewsListAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                goToEveryNewsActivity(position, container);
                            }
                        });
                    }
                }

            }


        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (fileIsExists(FILENAME)) {
                    getJsonFromeFile(FILENAME);
                } else {
                    if (NetWork.isNetConnected(getContext()))
                        requestDataByGet(page);
                    page++;
                    Log.d(TAG, "run: json有没有" + jsonString);
                }

                handler.sendEmptyMessage(1);
            }
        }).start();


        //蒋recyclerview结尾

        doRefresh(container);
        return view;
    }

    private void doRefresh(@Nullable final ViewGroup container) {
        /**
         * 下拉刷新
         */
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshlayout_toutiao);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "handleMessage: 进入刷新");
                banner();
                //发送网络请求，刷新RecyclerView
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        saveJsonToFile(jsonString);
                        if (msg.what == 2) {
                            Log.d(TAG, "handleMessage: 进入刷新解析数据");

                            handleJsonData(jsonString);
                            Log.d(TAG, "handleMessage: +mContentList=" + mContentlistList);
                            Log.d(TAG, "HandlerMessage: 完成解析");
                            if (showapi_res_code == 0) {
                                //刷新RecyclerView
                                mNewsListAdapter.notifyDataSetChanged();
                                //停止刷新
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                };


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDataByGet(page++);
                        Log.d(TAG, "handleMessage: 进入刷新请求数据");
                        handler.sendEmptyMessage(2);
                    }
                }).start();

            }
        });


        /**
         * 上拉加载更多
         *
         */


    }

    private void goToEveryNewsActivity(int position, @Nullable ViewGroup container) {
        Intent intent = new Intent(container.getContext(), every_news_activity.class);
        Contentlist sendContentlist = new Contentlist();
        sendContentlist.setHtml(mContentlistList.get(position).getHtml());
        sendContentlist.setSource(mContentlistList.get(position).getSource());
        sendContentlist.setPubDate(mContentlistList.get(position).getPubDate());
        sendContentlist.setTitle(mContentlistList.get(position).getTitle());
        sendContentlist.setLink(mContentlistList.get(position).getLink());

        intent.putExtra("sendContentlist", (Serializable) sendContentlist);
        startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private void requestDataByGet(int page) {
        String appid = "91287";//要替换成自己的
        String secret = "aece97a8085e42f398fa0f39ff8d2cea";//要替换成自己的
        //String channelName = "\";
        String title = "头条";
        try {
            URL url = new URL("https://route.showapi.com/109-35?channelId=&channelName=&id=&maxResult=50&needAllList=0&needContent=0&needHtml=1&page=" + page + "&showapi_appid=" + appid + "&showapi_timestamp=&title=" + title + "&showapi_sign=" + secret);
            Log.d(TAG, "requestDataByGet: url=" + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("GET");
            connection.connect();
            int responCode = connection.getResponseCode();
            if (responCode == HTTP_OK) {
                InputStream inPutStream = connection.getInputStream();
                jsonString = streamToString(inPutStream);
                Log.d(TAG, "requestDataByGet: 请求到json数据 jsonString=" + jsonString);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleJsonData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString); //获得整个json对象
            Log.d(TAG, "handleJsonData: 获得了整个json对象" + jsonObject);
            showapi_res_code = jsonObject.getInt("showapi_res_code");
            showapi_res_error = jsonObject.getString("showapi_res_error");
            JSONArray contentlist = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");

            if (contentlist != null && contentlist.length() > 0) {
                mContentlistList.clear();
                for (int index = 0; index < contentlist.length(); index++) {
                    Log.d(TAG, "handleJsonData: 进入循环");
                    JSONObject everyNewsObject = (JSONObject) contentlist.get(index);
                    String pubDate = everyNewsObject.getString("pubDate");
                    String link = everyNewsObject.getString("link");
                    String id = everyNewsObject.getString("id");
                    Boolean havePic = everyNewsObject.getBoolean("havePic");
                    String title = everyNewsObject.getString("title");
                    String source = everyNewsObject.getString("source");
                    String html = everyNewsObject.getString("html");
                    //每个contenlist中的图片对象
                    JSONArray imageurls = everyNewsObject.getJSONArray("imageurls");
                    mImageurlsList = new ArrayList<>();
                    if (imageurls != null && imageurls.length() > 0) {
                        for (int i = 0; i < imageurls.length(); i++) {
                            JSONObject everyNewsImageUrlObject = (JSONObject) imageurls.get(i);
                            String url = everyNewsImageUrlObject.getString("url");
                            int height = everyNewsImageUrlObject.getInt("height");
                            int width = everyNewsImageUrlObject.getInt("width");
                            Imageurls imageurlsItem = new Imageurls();
                            imageurlsItem.setHeight(height);
                            imageurlsItem.setWidth(width);
                            imageurlsItem.setUrl(url);
                            mImageurlsList.add(imageurlsItem);
                        }
                    } else {

                    }

                    Contentlist contentlistItem = new Contentlist();
                    contentlistItem.setPubDate(pubDate);
                    contentlistItem.setLink(link);
                    contentlistItem.setId(id);
                    contentlistItem.setTitle(title);
                    contentlistItem.setHavePic(havePic);
                    contentlistItem.setSource(source);
                    contentlistItem.setHtml(html);
                    contentlistItem.setImageurls(mImageurlsList);
                    mContentlistList.add(contentlistItem);
                }
                Log.d(TAG, "handleJsonData: 总的" + mContentlistList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "handleJsonData: 解析出错！！");
        }
    }

    private void banner() {


        /**
         * 从json中随机获得图片和title的List集合⬇️
         */
        getBannerImagesAndTitles();

        toutiao_banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        toutiao_banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        toutiao_banner.setImages(mBannerImagesList);

        //设置title集合
        toutiao_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        toutiao_banner.setBannerTitles(mBannerTitlesList);


        //banner设置方法全部调用完毕时最后调用
        toutiao_banner.start();

        //增加点击事件
        toutiao_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position) {
                    case 0:
                        goToEveryNewsActivity(P[0], mViewGroup);
                        break;
                    case 1:
                        goToEveryNewsActivity(P[1], mViewGroup);
                        break;
                    case 2:
                        goToEveryNewsActivity(P[2], mViewGroup);
                        break;
                    case 3:
                        goToEveryNewsActivity(P[3], mViewGroup);
                        break;
                    case 4:
                        goToEveryNewsActivity(P[4], mViewGroup);
                        break;
                    case 5:
                        goToEveryNewsActivity(P[5], mViewGroup);
                        break;

                }
            }
        });
    }

    private void getBannerImagesAndTitles() {
        Log.d(TAG, "getBannerImagesAndTitles: 哦哦+" + mContentlistList.size());
        mBannerImagesList = new ArrayList();
        mBannerTitlesList = new ArrayList<>();
        int i = (int) (Math.random() * 49);
        Log.d(TAG, "getBannerImagesAndTitles: 随机数i=" + i);
        //建立数组保存随到的数字，来判断是否随机的数相同。
        int[] n = {222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222, 222,};
        int a = 0;
        for (int k = 0; mBannerImagesList.size() <= 5; k++) {
            Log.d(TAG, "getBannerImagesAndTitles: i是" + i);
            Log.d(TAG, "getBannerImagesAndTitles:isExistRandomNumber(i,n)= " + isExistRandomNumber(i, n));
            if (isExistRandomNumber(i, n)) {
                //如果存在则重新生成 i。
                i = (int) (0 + Math.random() * 49);
            } else {
                Log.d(TAG, "getBannerImagesAndTitles: mContentlistList.get(i).getHavePic()" + mContentlistList.get(i).getHavePic());
                if (mContentlistList.get(i).getHavePic() == true) {
                    n[k] = i;
                    Log.d(TAG, "getBannerImagesAndTitles: aaaa嗷嗷=" + i);
                    P[a] = i;
                    a++;
                    mBannerImagesList.add(mContentlistList.get(i).getImageurls().get(0).getUrl());
                    mBannerTitlesList.add(mContentlistList.get(i).getTitle());
                    i = (int) (0 + Math.random() * 49);

                } else {
                    n[k] = i;
                }
            }
        }

        for (int q = 0; q < P.length; q++) {
            Log.d(TAG, "getBannerImagesAndTitles: qqqq=" + P[q]);
        }

    }

    private boolean isExistRandomNumber(int i, int[] j) {
        for (int n = 0; n < j.length; n++) {
            if (i == j[n]) {
                return true;
            }
        }
        return false;
    }

    private void saveJsonToFile(String jsonString) {
        try {
            FileOutputStream fos = this.getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(jsonString);
            osw.flush();
            osw.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getJsonFromeFile(String FILENAME) {
        try {
            FileInputStream fis = this.getActivity().openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] data = new char[fis.available()];
            isr.read(data);
            isr.close();
            fis.close();
            jsonString = new String(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean fileIsExists(String strFile) {

        try {
            File f = new File("/data/data/com.zonghe.one/files/" + strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
