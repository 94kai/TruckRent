package com.xk.trucktrade.managers;

import android.content.Context;


import com.xk.trucktrade.app.App;
import com.xk.trucktrade.utils.XmlUtils;

import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.AddressPicker.*;

/**
 * Created by warren on 11/7/15.
 * 用于方便获取地址信息
 */
public class AddressManager {
    private List<AddressPicker.Province> provinces;

    public AddressManager() {
        provinces = XmlUtils.parseArea(App.getInstance(), "china_area.xml");
    }

    public AddressManager(Context context) {
        provinces = XmlUtils.parseArea(context, "china_area.xml");
    }

    /**
     * 从 "pcode:11025" 中获取 11025
     *
     * @param rawString 未处理的包含地址代码的字符串
     * @return 地址代码
     */
    public static String getCodeInRawString(String rawString) {
        if (!rawString.contains(":"))
            return "";

        String[] code = rawString.split(":");
        if (code.length < 2)
            return "";

        return code[1];
    }

    //根据 PCODE 获取省份
    public AddressPicker.Province getProvinceByPCode(String pCode) {
        if (pCode == null)
            return null;
        for (int i = 0; i < provinces.size(); i++) {
            if (provinces.get(i).getAreaId().equals(pCode)) {
                return provinces.get(i);
            }
        }
        return null;
    }

    public AddressPicker.City getCityByCCode(Province province, String cCode) {
        if (cCode == null)
            return null;
        for (int i = 0; i < province.getCities().size(); i++) {
            if (province.getCities().get(i).getAreaId().equals(cCode)) {
                return province.getCities().get(i);
            }
        }
        return null;
    }

    public County getDistrictBydCode(City city, String dCode) {
        if (dCode == null)
            return null;
        for (County county : city.getCounties())
            if (county.getAreaId().equals(dCode))
                return county;
        return null;
    }

    /**
     * 从 LeanCloud 中获取的数据中解析出地址字符串的方法
     *
     * @param rawAddress 获取的数据
     * @return 解析出的地址
     */
    public String getFullAddress(List<String> rawAddress) {

        StringBuilder address = new StringBuilder();

        Province province = null;
        City city = null;
        County county;
        String detail;

        for (String rawData : rawAddress) {
            if (rawData.contains("pcode")) {
                province = getProvinceByPCode(getCodeInRawString(rawData));
                address.append(province.getAreaName()).append(" ");
            }

            if (rawData.contains("ccode") && province != null) {
                city = getCityByCCode(province, getCodeInRawString(rawData));
                address.append(city.getAreaName()).append(" ");
            }

            if (rawData.contains("dcode") && city != null) {
                county = getDistrictBydCode(city, getCodeInRawString(rawData));
                address.append(county.getAreaName()).append(" ");
            }
            if (rawData.contains("details")) {
                detail = getCodeInRawString(rawData);
                address.append(detail);
            }
        }

        return address.toString();
    }

    /**
     * 从 LeanCloud 中获取的数据中解析出地址字符串的方法
     * 相比于 {@link #getFullAddress(List)} 只返回省市数据
     *
     * @param rawAddress 获取的数据
     * @return 解析出的地址
     */
    public String getCityAddress(List<String> rawAddress) {

        StringBuilder address = new StringBuilder();

        Province province = null;
        City city;

        for (String rawData : rawAddress) {
            if (rawData.contains("pcode")) {
                province = getProvinceByPCode(getCodeInRawString(rawData));
//                address.append(province.getName()).append(" ");
            }

            if (rawData.contains("ccode") && province != null) {
                city = getCityByCCode(province, getCodeInRawString(rawData));
                address.append(city.getAreaName()).append(" ");
            }
        }

        return address.toString();
    }
}
