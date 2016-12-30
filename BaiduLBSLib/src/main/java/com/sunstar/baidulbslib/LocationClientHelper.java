package com.sunstar.baidulbslib;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/26.
 */

public class LocationClientHelper {
    private static LocationClient mLocationClient = null;
    public static KooBDLocationListenerImpl mKooBDLocationListenerImpl;

    public static void initLocationClient(Context context) {
        mLocationClient = new LocationClient(context);     //声明LocationClient类 getApplicationContext()
        mKooBDLocationListenerImpl = new KooBDLocationListenerImpl();
        mLocationClient.registerLocationListener(mKooBDLocationListenerImpl);    //注册监听函数
        initLocationClientOption();
    }

    /**
     * start：启动定位SDK。
     */
    public static void startLocation(OnLocationCallBack onLocationCallBack) {
        if (mLocationClient == null) {
            return;
        }
        if (onLocationCallBack != null) {
            mKooBDLocationListenerImpl.setOnLocationCallBack(onLocationCallBack);
            mLocationClient.registerLocationListener(mKooBDLocationListenerImpl);    //注册监听函数
        }
        mLocationClient.start();
    }

    /**
     * stop：关闭定位SDK。调用start之后只需要等待定位结果自动回调即可。
     * 开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop函数即可。
     * 如果stop之后仍然想进行定位，可以再次start等待定位结果回调即可。
     * 如果开发者想按照自己逻辑请求定位，可以在start之后按照自己的逻辑请求locationclient.requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。
     */
    public static void stopLocation() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.stop();
    }


    /**
     * 高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
     * 低功耗定位模式：这种定位模式下，不会使用GPS进行定位，只会使用网络定位（WiFi定位和基站定位）；
     * 仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。
     * <p>
     * 定位SDK可以返回BD09、BD09ll、GCJ-02三种类型坐标（海外地区只能返回WGS-84类型的坐标） ，
     * 若需要将定位点的位置通过百度Android地图 SDK进行地图展示，请返回BD09ll，将无偏差的叠加在百度地图上。
     */
    private static void initLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 61 ： GPS定位结果，GPS定位成功。
     * 62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位。
     * 63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     * 65 ： 定位缓存的结果。
     * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     * 161： 网络定位结果，网络定位成功。
     * 162： 请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
     * 167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     * 502： AK参数错误，请按照说明文档重新申请AK。
     * 505：AK不存在或者非法，请按照说明文档重新申请AK。
     * 601： AK服务被开发者自己禁用，请按照说明文档重新申请AK。
     * 602： key mcode不匹配，您的AK配置过程中安全码设置有问题，请确保：SHA1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请AK。
     * 501～700：AK验证失败，请按照说明文档重新申请AK。
     */
    public static class KooBDLocationListenerImpl implements BDLocationListener {
        private OnLocationCallBack callBack;

        public void setOnLocationCallBack(OnLocationCallBack onLocationCallBack) {
            callBack = onLocationCallBack;
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation
                    || bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
                if (callBack != null) {
                    callBack.onLocation(bdLocation);
                }
                stopLocation();//手动停止
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(bdLocation.getLocType());
            sb.append("\n纬度latitude : ");
            sb.append(bdLocation.getLatitude());
            sb.append("\n经线lontitude : ");
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(bdLocation.getRadius());
            //获取定位返回错误码
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(bdLocation.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(bdLocation.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(bdLocation.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
            List<Poi> list = bdLocation.getPoiList();// POI数据
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
    }


    public interface OnLocationCallBack {
        void onLocation(BDLocation bdLocation);
    }
}
