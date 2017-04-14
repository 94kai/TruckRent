package com.xk.trucktrade.utils;

import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker.*;


/**
 * 解析地区信息xml的组件
 * Created by WilliamHao on 15/10/10.
 */
public class XmlUtils {

    public static ArrayList<Province> parseArea(Context context, String xmlPath) {
        ArrayList<Province> provinces = new ArrayList<>();
        Province province = null;

        ArrayList<City> cities = new ArrayList<>();
        City city = null;

        ArrayList<County> districts = new ArrayList<>();
        County county = null;

        XmlPullParser xmlParser = Xml.newPullParser();

        try {
            //得到文件流，并设置编码方式
            InputStream inputStream = context.getResources().getAssets().open(xmlPath);
            xmlParser.setInput(inputStream, "utf-8");
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
            //一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName();
                        //如果是river标签开始，则说明需要实例化对象了
                        if (tag.equalsIgnoreCase("province")) {
                            province = new Province();
                            //取出river标签中的一些属性值
                            province.setAreaId(xmlParser.getAttributeValue(null, "provinceID"));
                            province.setAreaName(xmlParser.getAttributeValue(null, "province"));
                        }
                        if (tag.equalsIgnoreCase("City")) {
                            city = new City();
                            city.setAreaId(xmlParser.getAttributeValue(null, "CityID"));
                            city.setAreaName(xmlParser.getAttributeValue(null, "City"));
                        }
                        if (tag.equalsIgnoreCase("Piecearea")) {
                            county = new County();
                            county.setAreaId(xmlParser.getAttributeValue(null, "PieceareaID"));
                            county.setAreaName(xmlParser.getAttributeValue(null, "Piecearea"));
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        //如果遇到river标签结束，则把river对象添加进集合中
                        if (xmlParser.getName().equalsIgnoreCase("Piecearea") && county != null) {
                            districts.add(county);
                            county = null;
                        }
                        if (xmlParser.getName().equalsIgnoreCase("City") && city != null) {
                            city.setCounties(districts);
                            cities.add(city);
                            city = null;
                            districts = new ArrayList<>();
                        }
                        if (xmlParser.getName().equalsIgnoreCase("province") && province != null) {
                            province.setCities(cities);
                            provinces.add(province);
                            province = null;
                            cities = new ArrayList<>();
                        }
                        break;
                    default:
                        break;

                }
                //如果xml没有结束，则导航到下一个river节点
                evtType = xmlParser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return provinces;
    }

}
