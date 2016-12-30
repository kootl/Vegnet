package com.sunstar.vegnet.kootl.ssqselect.model;

import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.kootl.ssqselect.bean.Area;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaDataWrapper;
import com.sunstar.vegnet.kootl.ssqselect.bean.City;
import com.sunstar.vegnet.kootl.ssqselect.bean.Province;
import com.sunstar.vegnet.kootl.ssqselect.contract.SSQSelectContract;
import com.sunstar.vegnet.kootl.ssqselect.helper.KooPinyinHelper;
import com.sunstar.vegnet.kootl.ssqselect.helper.SSQHelper;
import com.sunstar.vegnet.kootl.ssqselect.ui.SSQSelectActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sunstar.vegnet.kootl.ssqselect.helper.KooPinyinHelper.parseCharToPinyin;

/**
 * Created by louisgeek on 2016/12/26
 */

public class SSQSelectModelImpl implements SSQSelectContract.Model<List<AreaDataWrapper>> {

    @Override
    public void loadData(BaseCallBack<List<AreaDataWrapper>> baseCallBack) {

    }

    @Override
    public void loadSSQData(int nowShowWhere, BaseCallBack<List<AreaDataWrapper>> baseCallBack) {
        List<AreaDataWrapper> areaDataWrapperList = new ArrayList<>();

        List<Province> provinceList = SSQHelper.getProvinceList();

        for (int i = 0; i < provinceList.size(); i++) {
            String pinyin = KooPinyinHelper.parseCharToPinyin(provinceList.get(i).getProvinceName());
            provinceList.get(i).setProvinceEnName(pinyin);
        }
        //排序
        Collections.sort(provinceList, new Comparator<Province>() {
            @Override
            public int compare(Province first, Province second) {
                return first.getProvinceEnName().compareTo(second.getProvinceEnName());
            }
        });
        //保存  排序后的源数据
        DataHolderSingleton.getInstance().putData(CommFinalData.HOLD_SORTED_PROVINCE_LIST, provinceList);
        //
        switch (nowShowWhere) {
            case SSQSelectActivity.SHOW_PROVINCE:
                //
                for (int i = 0; i < provinceList.size(); i++) {
                    Province province = provinceList.get(i);
                    String pinyin = KooPinyinHelper.parseCharToPinyin(province.getProvinceEnName());
                    //
                    AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                    areaDataWrapper.setName(province.getProvinceName());
                    areaDataWrapper.setPinYin(pinyin);
                    areaDataWrapper.setShowMoreIcon(true);
                    areaDataWrapper.setNowShowWhere(nowShowWhere);
                    areaDataWrapper.setHoldSelectedProvincePos(i);
                    areaDataWrapper.setHoldSelectedCityPos(-1);
                    areaDataWrapper.setHoldSelectedAreaPos(-1);
                    areaDataWrapper.setAreaId(province.getProvinceID());
                    areaDataWrapperList.add(areaDataWrapper);
                }
                break;
            case SSQSelectActivity.SHOW_CITY:
                //
                int selectedProvincePos = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_PROVINCE_POS);
                if (selectedProvincePos < 0) {
                    ToastTool.showImageWarn("selectedProvincePos is null");
                    return;
                }
                //city
                List<City> cityList = provinceList.get(selectedProvincePos).getCites();
                for (int i = 0; i < cityList.size(); i++) {
                    City city = cityList.get(i);
                    String pinyin = KooPinyinHelper.parseCharToPinyin(city.getCityEnName());
                    //
                    AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                    areaDataWrapper.setName(city.getCityName());
                    areaDataWrapper.setPinYin(pinyin);
                    areaDataWrapper.setShowMoreIcon(true);
                    areaDataWrapper.setNowShowWhere(nowShowWhere);
                    areaDataWrapper.setHoldSelectedProvincePos(selectedProvincePos);
                    areaDataWrapper.setHoldSelectedCityPos(i);
                    areaDataWrapper.setHoldSelectedAreaPos(-1);
                    areaDataWrapper.setAreaId(city.getCityID());
                    areaDataWrapperList.add(areaDataWrapper);
                }
                break;
            case SSQSelectActivity.SHOW_AREA:
                //
                int selectedProvincePos2 = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_PROVINCE_POS);
                int selectedCityPos = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_CITY_POS);
                if (selectedProvincePos2 < 0 || selectedCityPos < 0) {
                    ToastTool.showImageWarn("selectedProvincePos2 or selectedCityPos is null");
                    return;
                }
                //area
                List<Area> areaList = provinceList.get(selectedProvincePos2).getCites().get(selectedCityPos).getAreas();
                for (int i = 0; i < areaList.size(); i++) {
                    Area area = areaList.get(i);
                    String pinyin = KooPinyinHelper.parseCharToPinyin(area.getAreaEnName());
                    //
                    AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                    areaDataWrapper.setName(area.getAreaName());
                    areaDataWrapper.setPinYin(pinyin);
                    areaDataWrapper.setShowMoreIcon(false);
                    areaDataWrapper.setNowShowWhere(nowShowWhere);
                    areaDataWrapper.setHoldSelectedProvincePos(selectedProvincePos2);
                    areaDataWrapper.setHoldSelectedCityPos(selectedCityPos);
                    areaDataWrapper.setHoldSelectedAreaPos(i);
                    areaDataWrapper.setAreaId(area.getAreaID());
                    areaDataWrapperList.add(areaDataWrapper);
                }
                break;
        }

        if (areaDataWrapperList != null) {
            baseCallBack.onSuccess(areaDataWrapperList);
        } else {
            baseCallBack.onError("data  is  null");
        }
    }


    @Override
    public void querySSQData(List<Province> forQueryProvinceList, String newText, BaseCallBack<List<AreaDataWrapper>> baseCallBack) {
        List<AreaDataWrapper> areaDataWrapperList = new ArrayList<>();
        //省
        for (int i = 0; i < forQueryProvinceList.size(); i++) {
            Province province = forQueryProvinceList.get(i);
            if (province.getProvinceName().contains(newText)) {
                String pinyin = parseCharToPinyin(province.getProvinceName());
                //
                AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                areaDataWrapper.setName(province.getProvinceName());
                areaDataWrapper.setPinYin(pinyin);
                areaDataWrapper.setShowMoreIcon(true);
                areaDataWrapper.setNowShowWhere(SSQSelectActivity.SHOW_PROVINCE);
                areaDataWrapper.setHoldSelectedProvincePos(i);
                areaDataWrapper.setHoldSelectedCityPos(-1);
                areaDataWrapper.setHoldSelectedAreaPos(-1);
                areaDataWrapper.setAreaId(province.getProvinceID());
                areaDataWrapperList.add(areaDataWrapper);
            }
            /**
             * 城市
             */
            for (int j = 0; j < forQueryProvinceList.get(i).getCites().size(); j++) {
                City city = forQueryProvinceList.get(i).getCites().get(j);
                if (city.getCityName().contains(newText)) {
                    String pinyin = parseCharToPinyin(city.getCityName());
                    //
                    AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                    areaDataWrapper.setName(city.getCityName());
                    areaDataWrapper.setPinYin(pinyin);
                    areaDataWrapper.setShowMoreIcon(true);
                    areaDataWrapper.setNowShowWhere(SSQSelectActivity.SHOW_CITY);
                    areaDataWrapper.setHoldSelectedProvincePos(i);
                    areaDataWrapper.setHoldSelectedCityPos(j);
                    areaDataWrapper.setHoldSelectedAreaPos(-1);
                    areaDataWrapper.setAreaId(city.getCityID());
                    areaDataWrapperList.add(areaDataWrapper);
                }
                /**
                 * 地区
                 */
                for (int k = 0; k < forQueryProvinceList.get(i).getCites().get(j).getAreas().size(); k++) {
                    Area area = forQueryProvinceList.get(i).getCites().get(j).getAreas().get(k);
                    if (area.getAreaName().contains(newText)) {
                        String pinyin = parseCharToPinyin(area.getAreaName());
                        //
                        AreaDataWrapper areaDataWrapper = new AreaDataWrapper();
                        areaDataWrapper.setName(area.getAreaName());
                        areaDataWrapper.setPinYin(pinyin);
                        areaDataWrapper.setShowMoreIcon(false);
                        areaDataWrapper.setHoldSelectedProvincePos(i);
                        areaDataWrapper.setHoldSelectedCityPos(j);
                        areaDataWrapper.setHoldSelectedAreaPos(k);
                        areaDataWrapper.setNowShowWhere(SSQSelectActivity.SHOW_AREA);
                        areaDataWrapper.setAreaId(area.getAreaID());
                        areaDataWrapperList.add(areaDataWrapper);
                    }
                }
            }
        }

        if (areaDataWrapperList != null) {
            baseCallBack.onSuccess(areaDataWrapperList);
        } else {
            baseCallBack.onError("data is null");
        }
    }
}