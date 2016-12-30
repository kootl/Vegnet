package com.sunstar.vegnet.kootl.ssqselect.helper;

import android.util.Log;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.ssqselect.bean.Area;
import com.sunstar.vegnet.kootl.ssqselect.bean.City;
import com.sunstar.vegnet.kootl.ssqselect.bean.Province;
import com.sunstar.vegnet.kootl.comm.tool.RawTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by louisgeek on 2016/6/12.
 */

public class SSQHelper {
    private static List<Province> provinceList;

    public static List<Province> getProvinceList() {
        if (provinceList == null) {
            String ssqJson = RawTool.getStringFromRaw(KooApplication.getAppContext(), R.raw.ssq);
            provinceList = parseJsonStrToBeanList(ssqJson);
        }
        return provinceList;
    }

    private static List<Province> parseJsonStrToBeanList(String ssq_json) {
        provinceList = new ArrayList<>();
        try {
            //
            JSONObject jsonObject = new JSONObject(ssq_json);
            JSONObject o_provinces = jsonObject.getJSONObject("provinces");
            JSONArray a_province = o_provinces.getJSONArray("province");
            //===

            for (int i = 0; i < a_province.length(); i++) {
                JSONObject o_province = (JSONObject) a_province.get(i);
                String ssqid = o_province.getString("ssqid");
                String ssqname = o_province.getString("ssqname");
                String ssqename = o_province.getString("ssqename");
                JSONObject o_cities = o_province.getJSONObject("cities");
                JSONArray a_city = o_cities.getJSONArray("city");
                //===
                List<City> cityList = new ArrayList<>();
                for (int j = 0; j < a_city.length(); j++) {
                    //===
                    JSONObject o_city = (JSONObject) a_city.get(j);
                    String ssqid_C = o_city.getString("ssqid");
                    String ssqname_C = o_city.getString("ssqname");
                    String ssqename_C = o_city.getString("ssqename");
                    JSONObject o_areas_C = o_city.getJSONObject("areas");
                    JSONArray a_area = o_areas_C.getJSONArray("area");
                    //===
                    List<Area> areaList = new ArrayList<>();
                    for (int k = 0; k < a_area.length(); k++) {
                        JSONObject O_area = (JSONObject) a_area.get(k);
                        String ssqid_A = O_area.getString("ssqid");
                        String ssqname_A = O_area.getString("ssqname");
                        String ssqename_A = O_area.getString("ssqename");
                        //===
                        Area area = new Area(ssqid_A, ssqname_A, "简称", ssqename_A);
                        areaList.add(area);
                    }
                    //===
                    City city = new City(ssqid_C, ssqname_C, "简称", ssqename_C, areaList);
                    cityList.add(city);
                }
                //===
                Province province = new Province(ssqid, ssqname, "简称", ssqename, cityList);
                provinceList.add(province);
            }

            Log.d("XXX", "LOUIS" + provinceList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("XXX", "LOUIS" + e.getMessage());
        }
        return provinceList;
    }


    public static String getProvinceCityAreaNameStrByOnlyAreaID(String areaKey) {
        if (areaKey == null || areaKey.trim().equals("") || areaKey.trim().toLowerCase().equals("null")) {
            return "";
        }

        // String ssqJson = getStringFromRawOld(context, R.raw.ssq);
        List<Province> provinceList = getProvinceList();
        //
        String province = "";
        String city = "";
        String area = "";
        //
        String provinceID = areaKey.substring(0, 2) + "0000";//从0开始数，其中不包括endIndex位置的字符
        String cityID = areaKey.substring(0, 4) + "00";
        String areaID = areaKey;

        int nowProvinceIndex = 0;
        for (int i = 0; i < provinceList.size(); i++) {
            if (provinceID.equals(provinceList.get(i).getProvinceID())) {
                province = provinceList.get(i).getProvinceName();
                nowProvinceIndex = i;
                break;
            }
        }
        int nowCityIndex = 0;
        for (int i = 0; i < provinceList.get(nowProvinceIndex).getCites().size(); i++) {
            if (cityID.equals(provinceList.get(nowProvinceIndex).getCites().get(i).getCityID())) {
                city = provinceList.get(nowProvinceIndex).getCites().get(i).getCityName();
                nowCityIndex = i;
                break;
            }
        }
        for (int i = 0; i < provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().size(); i++) {
            if (areaID.equals(provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().get(i).getAreaID())) {
                area = provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().get(i).getAreaName();
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (province != null && !province.equals("")) {
            stringBuilder.append(province);
            stringBuilder.append("-");
        }
        if (city != null && !city.equals("")) {
            stringBuilder.append(city);
            stringBuilder.append("-");
        }
        if (area != null && !area.equals("")) {
            stringBuilder.append(area);
        }
        String temp = stringBuilder.toString();
        return temp.equals("--") ? "" : temp;
    }

    public static String getProvinceCityAreaNameStrByOnlyAreaIDWithOutFix(String areaKey) {
        return getProvinceCityAreaNameStrByOnlyAreaID(areaKey).replace("-", "");
    }

    public static String getAreaID(String province, String city, String area) {
        List<Province> provinceList = getProvinceList();
        for (int i = 0; i < provinceList.size(); i++) {
            if (provinceList.get(i).getProvinceName().contains(province)) {
                for (int j = 0; j < provinceList.get(i).getCites().size(); j++) {
                    if (provinceList.get(i).getCites().get(j).getCityName().contains(city)) {
                        for (int k = 0; k < provinceList.get(i).getCites().get(j).getAreas().size(); k++) {
                            if (provinceList.get(i).getCites().get(j).getAreas().get(k).getAreaName().contains(area)) {
                                return provinceList.get(i).getCites().get(j).getAreas().get(k).getAreaID();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
