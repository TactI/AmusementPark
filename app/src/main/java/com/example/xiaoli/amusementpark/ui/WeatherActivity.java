package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    WeatherActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/8  14:05
 *    描述：       天气指南
 */

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.WeatherBean;
import com.example.xiaoli.amusementpark.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_bar;
    private ImageView iv_back;
    private String cityname;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public static final int CITY_HANDLER=106;
    public static final int VIEW_HANDLER=108;
   // private MyThread myThread=new MyThread();

    private TextView name,time,wind,week,temperature,weather;
    private ImageView iv_weather;
    private GridView mGridView;
    //存更新时间
    private String up_time;
    private String wind_strong;
    private List<WeatherBean> mList=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CITY_HANDLER:
                    name.setText(cityname.substring(0,2));
                    volleyQuest(name.getText().toString());
                    break;
                case VIEW_HANDLER:
                    time.setText("发布时间:"+up_time);
                    wind.setText(wind_strong);
                    week.setText(mList.get(0).getWeek());
                    weather.setText(mList.get(0).getWeather());
                    temperature.setText(mList.get(0).getTempearture());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        initLocation();
        //注册监听函数
        mLocationClient.start();
       // myThread.run();
    }
//    public class MyThread implements Runnable{
//        @Override
//        public void run() {
//
//        }
//    }
    public void volleyQuest(String string){
        String code=null;
        try {
            code= URLEncoder.encode(string,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RxVolley.get("http://v.juhe.cn/weather/index?cityname="+code+"&key=f15cf6b0c4b35cf5098098c27979289f",
                new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.e(t);
                        try {
                            JSONObject jsonObject=new JSONObject(t);
                            JSONObject result=jsonObject.getJSONObject("result");
                            JSONObject sk=result.getJSONObject("sk");
                            up_time=sk.getString("time");
                            wind_strong=sk.getString("wind_direction")+sk.getString("wind_strength");
                            JSONObject future= result.getJSONObject("future");
                            handler.sendEmptyMessage(VIEW_HANDLER);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("天气指南");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        name= (TextView) findViewById(R.id.name);
        time= (TextView) findViewById(R.id.time);
        wind= (TextView) findViewById(R.id.wind);
        week= (TextView) findViewById(R.id.week);
        weather= (TextView) findViewById(R.id.weather);
        temperature= (TextView) findViewById(R.id.temperature);
        iv_weather= (ImageView) findViewById(R.id.iv_weather);
        mGridView= (GridView) findViewById(R.id.mGridView);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=0;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息
                cityname=location.getCity();
                handler.sendEmptyMessage(CITY_HANDLER);
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            Log.i("BaiduLocationApiDem", sb.toString());

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            L.e(s);
        }
    }
}
